package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.FileDao;
import com.goodsoft.society_zy.domain.dao.HospitalDao;
import com.goodsoft.society_zy.domain.entity.file.FileData;
import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.result.Result;
import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.FileService;
import com.goodsoft.society_zy.service.HospitalService;
import com.goodsoft.society_zy.service.supp.HospitalServicelmplSupp;
import com.goodsoft.society_zy.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 医院信息业务接口实现类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class HospitalServicelmpl implements HospitalService {
    @Resource
    private SqlSessionTemplate sqlSession;
    @Resource
    private HospitalDao dao;
    @Resource
    private FileDao fileDao;
    @Resource
    private FileService fileService;
    @Resource
    private HospitalServicelmplSupp servicelmplSupp;
    //实例化excel读取工具类
    private ReadExcelXlsUtil readExcelXls = ReadExcelXlsUtil.getInstance();
    private ReadExcelXlsxUtil readExcelXlsx = ReadExcelXlsxUtil.getInstance();
    //实例化文件删除工具类
    private DeleteFileUtil deleteFile = DeleteFileUtil.getInstance();
    //实例化UUID工具类
    private UUIDUtil uuid = UUIDUtil.getInstance();
    //实例化excel表格导出工具类
    private ExcelUtil writeExcel = ExcelUtil.getInstance();
    //实例化获取服务器域名工具类
    private DomainNameUtil url = DomainNameUtil.getInstance();
    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 医院信息数据录入业务方法
     *
     * @param request 请求
     * @param files   医院信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addHospitalInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        //文件上传
        int arg = this.fileService.fileUploadService(files, "excel", uuid);
        switch (arg) {
            case 604:
                return (T) new Status(StatusEnum.NO_FILE.getCODE(), StatusEnum.NO_FILE.getEXPLAIN());
            case 603:
                return (T) new Status(StatusEnum.FILE_FORMAT.getCODE(), StatusEnum.FILE_FORMAT.getEXPLAIN());
            case 601:
                return (T) new Status(StatusEnum.FILE_SIZE.getCODE(), StatusEnum.FILE_SIZE.getEXPLAIN());
            case 600:
                return (T) new Status(StatusEnum.FILE_UPLOAD.getCODE(), StatusEnum.FILE_UPLOAD.getEXPLAIN());
        }
        //获取上传文件路径
        FileData file = this.fileDao.queryFileOneDao(uuid);
        StringBuilder sb = new StringBuilder(file.getBases());
        sb.append(file.getPath());
            /*获取上传excel文件数据 start*/
        List<ExcelColumnInfo> sdData;
        if (file.getSuffix().endsWith("xls")) {
            this.readExcelXls.process(sb.toString());
            sdData = this.readExcelXls.list;
        } else {
            this.readExcelXlsx.process(sb.toString());
            sdData = this.readExcelXlsx.list;
        }
        //去掉重复数据
        Set<ExcelColumnInfo> set = new HashSet<>();
        set.addAll(sdData);
        sdData.clear();
        sdData.addAll(set);
            /*获取上传excel文件数据 end*/
        //解析excel数据有效性，返回错误数据
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.hospitalServiceSupp(sdData);
        int len = sdData.size();
        //判断解析数据是否满足正确格式数据，是存库，否删除原始文件
        if (len > 0) {
            //导出错误数据到excel
            int len1 = errorList.size();
            if (len1 > 0) {
                sb.delete(0, sb.length());
                sb.append(this.url.getServerDomainName(request));
                sb.append(this.writeExcel.writeExcel(errorList));
            }
            SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            HospitalDao hospitalDao = session.getMapper(HospitalDao.class);
            try {
                for (int i = 0; i < len; ++i) {
                    hospitalDao.addHospitalInfoDao(sdData.get(i));
                }
                session.commit();
            } finally {
                session.close();
                sdData.clear();
                session.clearCache();
            }
            if (len1 > 0) {
                return (T) new Result(StatusEnum.SUCCESS_MSG.getCODE(), StatusEnum.SUCCESS_MSG.getEXPLAIN(), sb.toString());
            } else {
                return (T) new Status(StatusEnum.SUCCESS.getCODE(), StatusEnum.SUCCESS.getEXPLAIN());
            }
        } else {
            //删除磁盘上的文件
            this.deleteFile.deleteFile(file);
            //删除数据库文件数据
            this.fileDao.deleteFileDao(uuid);
            return (T) new Status(StatusEnum.EXCEL_NO_DATA.getCODE(), StatusEnum.EXCEL_NO_DATA.getEXPLAIN());
        }
    }

    /**
     * 医务人员信息录入业务方法
     *
     * @param request
     * @param files   医务人员信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addMedicalStaffInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        //文件上传
        int arg = this.fileService.fileUploadService(files, "excel", uuid);
        switch (arg) {
            case 604:
                return (T) new Status(StatusEnum.NO_FILE.getCODE(), StatusEnum.NO_FILE.getEXPLAIN());
            case 603:
                return (T) new Status(StatusEnum.FILE_FORMAT.getCODE(), StatusEnum.FILE_FORMAT.getEXPLAIN());
            case 601:
                return (T) new Status(StatusEnum.FILE_SIZE.getCODE(), StatusEnum.FILE_SIZE.getEXPLAIN());
            case 600:
                return (T) new Status(StatusEnum.FILE_UPLOAD.getCODE(), StatusEnum.FILE_UPLOAD.getEXPLAIN());
        }
        //获取上传文件路径
        FileData file = this.fileDao.queryFileOneDao(uuid);
        StringBuilder sb = new StringBuilder(file.getBases());
        sb.append(file.getPath());
            /*获取上传excel文件数据 start*/
        List<ExcelColumnInfo> sdData;
        //判断excel文件格式
        if (file.getSuffix().endsWith("xls")) {
            this.readExcelXls.process(sb.toString());
            sdData = this.readExcelXls.list;
        } else {
            this.readExcelXlsx.process(sb.toString());
            sdData = this.readExcelXlsx.list;
        }
        //去掉重复数据
        Set<ExcelColumnInfo> set = new HashSet<>();
        set.addAll(sdData);
        sdData.clear();
        sdData.addAll(set);
            /*获取上传excel文件数据 end*/
        //解析excel数据有效性
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.medicalStaffServiceSupp(sdData);
        int len = sdData.size();
        //判断解析数据是否满足正确格式数据，是存库，否删除原始文件
        if (len > 0) {
            int len1 = errorList.size();
            if (len1 > 0) {
                sb.delete(0, sb.length());
                sb.append(this.url.getServerDomainName(request));
                sb.append(this.writeExcel.writeExcel(errorList));
            }
            SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            HospitalDao hospitalDao = session.getMapper(HospitalDao.class);
            try {
                for (int i = 0; i < len; ++i) {
                    hospitalDao.addMedicalStaffInfoDao(sdData.get(i));
                }
                session.commit();
            } finally {
                session.close();
                session.clearCache();
                sdData.clear();
            }
            if (len1 > 0) {
                return (T) new Result(StatusEnum.SUCCESS_MSG.getCODE(), StatusEnum.SUCCESS_MSG.getEXPLAIN(), sb.toString());
            } else {
                return (T) new Status(StatusEnum.SUCCESS.getCODE(), StatusEnum.SUCCESS.getEXPLAIN());
            }
        } else {
            sdData.clear();
            //删除磁盘上的文件
            this.deleteFile.deleteFile(file);
            //删除数据库文件数据
            this.fileDao.deleteFileDao(uuid);
            return (T) new Status(StatusEnum.EXCEL_NO_DATA.getCODE(), StatusEnum.EXCEL_NO_DATA.getEXPLAIN());
        }
    }

    /**
     * 就医信息数据录入业务方法
     *
     * @param request 请求
     * @param files   就医信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addConsultInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        //文件上传
        int arg = this.fileService.fileUploadService(files, "excel", uuid);
        switch (arg) {
            case 604:
                return (T) new Status(StatusEnum.NO_FILE.getCODE(), StatusEnum.NO_FILE.getEXPLAIN());
            case 603:
                return (T) new Status(StatusEnum.FILE_FORMAT.getCODE(), StatusEnum.FILE_FORMAT.getEXPLAIN());
            case 601:
                return (T) new Status(StatusEnum.FILE_SIZE.getCODE(), StatusEnum.FILE_SIZE.getEXPLAIN());
            case 600:
                return (T) new Status(StatusEnum.FILE_UPLOAD.getCODE(), StatusEnum.FILE_UPLOAD.getEXPLAIN());
        }
        //获取上传文件路径
        FileData file = this.fileDao.queryFileOneDao(uuid);
        StringBuilder sb = new StringBuilder(file.getBases());
        sb.append(file.getPath());
            /*获取上传excel文件数据 start*/
        List<ExcelColumnInfo> sdData;
        if (file.getSuffix().endsWith("xls")) {
            this.readExcelXls.process(sb.toString());
            sdData = this.readExcelXls.list;
        } else {
            this.readExcelXlsx.process(sb.toString());
            sdData = this.readExcelXlsx.list;
        }
        //去掉重复数据
        Set<ExcelColumnInfo> set = new HashSet<>();
        set.addAll(sdData);
        sdData.clear();
        sdData.addAll(set);
            /*获取上传excel文件数据 end*/
        //解析excel数据有效性
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.consultServiceSupp(sdData);
        int len = sdData.size();
        //判断解析数据是否满足正确格式数据，是存库，否删除原始文件
        if (len > 0) {
            int len1 = errorList.size();
            if (len1 > 0) {
                sb.delete(0, sb.length());
                sb.append(this.url.getServerDomainName(request));
                sb.append(this.writeExcel.writeExcel(errorList));
            }
            SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            HospitalDao hospitalDao = session.getMapper(HospitalDao.class);
            try {
                for (int i = 0; i < len; ++i) {
                    hospitalDao.addConsultInfoDao(sdData.get(i));
                }
                session.commit();
            } finally {
                session.close();
                session.clearCache();
                sdData.clear();
            }
            if (len1 > 0) {
                return (T) new Result(StatusEnum.SUCCESS_MSG.getCODE(), StatusEnum.SUCCESS_MSG.getEXPLAIN(), sb.toString());
            } else {
                return (T) new Status(StatusEnum.SUCCESS.getCODE(), StatusEnum.SUCCESS.getEXPLAIN());
            }
        } else {
            sdData.clear();
            //删除磁盘上的文件
            this.deleteFile.deleteFile(file);
            //删除数据库文件数据
            this.fileDao.deleteFileDao(uuid);
            return (T) new Status(StatusEnum.EXCEL_NO_DATA.getCODE(), StatusEnum.EXCEL_NO_DATA.getEXPLAIN());
        }
    }

    /**
     * 虹山医院信息获取录入
     *
     * @param <T>
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 0/10 * * * ?") //每分钟执行一次
    public <T> T addHospitalInfo() throws Exception {
        logger.info("每分钟执行一次。开始……");
        System.out.println(new Date() + "10分钟执行一次：开始........");
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); //获取当前时间
        String time1 = sd.format(new Date());
        String url = "http://test.lkasp.com:10088/WdWeb/Service1.asmx?WSDL";
        String code = "2010";
        String startTime = "2017-11-12 21:48:43";
        String endTime = time1 + " 23:59:59";
        String result = UrlUtil.url(url, code, startTime, endTime);
        //System.out.println(result);
        String list = Base64Util.decode(result);
        try {
            //将字符串转换成jsonObject对象
            JSONObject jsonObject = new JSONObject().fromObject(list);
            Object object = jsonObject.get("Info");
            JSONArray array = JSONArray.fromObject(object);
            if (array.size() != 0) {
                String al = "00000";
                for (Object arr : array){
                    ExcelColumnInfo colum = new ExcelColumnInfo();
                    String str = uuid.substring(0, 4);
                    al = CodeUtil.fiveCode(al);
                    Integer a = Integer.parseInt(al);
                    if (a >= 99999) {
                        al = "00000";
                        al = CodeUtil.fiveCode(al);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq = time.substring(4);
                    String bm = "520402000000"; //西秀机构代码
                    String BH = "ZQH" + bm + str + "01" + rq + al;
                    // String BH =JSONObject.fromObject(arr).get("BH").toString();
                    String YYBM = JSONObject.fromObject(arr).get("YYBM").toString();
                    String YYMC = JSONObject.fromObject(arr).get("YYMC").toString();
                    String YYDZ = JSONObject.fromObject(arr).get("YYDZ").toString();
                    String FRDB = JSONObject.fromObject(arr).get("FRDB").toString();
                    String FRDBSFZHM = JSONObject.fromObject(arr).get("FRDBSFZHM").toString();
                    String FRDBLXDH = JSONObject.fromObject(arr).get("FRDBLXDH").toString();
                    String SSXQ = JSONObject.fromObject(arr).get("SSXQ").toString();
                    String RKSJ = JSONObject.fromObject(arr).get("RKSJ").toString();
                    colum.setColumn(BH);
                    colum.setColumn1(YYBM);
                    colum.setColumn2(YYMC);
                    colum.setColumn3(YYDZ);
                    colum.setColumn4(FRDB);
                    colum.setColumn5(FRDBSFZHM);
                    colum.setColumn6(FRDBLXDH);
                    colum.setColumn7(SSXQ);
                    colum.setColumn10(RKSJ);
                    if (colum.getColumn1() != null || colum.getColumn2() != null) {
                        this.dao.addHospitalInfoDao(colum);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list != null) {
            return (T) new Result(0, "获取成功", list);
        }
        return (T) new Status(StatusEnum.NO_DATA.getCODE(), StatusEnum.NO_DATA.getEXPLAIN());

    }

    /**
     * 虹山医院获取就医人员信息录入逻辑类
     *
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T addMedicalStaffInfo() throws Exception {
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); //获取当前时间
        String time1 = sd.format(new Date());
        String url = "http://test.lkasp.com:10088/WdWeb/Service1.asmx?WSDL";
        String code = "2011";
        String startTime = "2017-11-12 21:48:43";
        String endTime = time1 + " 23:59:59";
        String result = UrlUtil.url(url, code, startTime, endTime);
        //System.out.println(result);
        String list = Base64Util.decode(result);
        list = list.replace(" ", "");
        try {
            //将字符串转换成jsonObject对象
            JSONObject jsonObject = new JSONObject();
            jsonObject = JSONObject.fromObject(list);
            Object object = jsonObject.get("Info");
            JSONArray array = JSONArray.fromObject(object);
            if (array.size() != 0) {
                String al = "00000";
                for (Object arr : array){
                    ExcelColumnInfo colum = new ExcelColumnInfo();
                    String str = uuid.substring(0, 4);
                    al = CodeUtil.fiveCode(al);
                    Integer a = Integer.parseInt(al);
                    if (a >= 99999) {
                        al = "00000";
                        al = CodeUtil.fiveCode(al);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq = time.substring(4);
                    String bm = "520402000000"; //西秀机构代码
                    String BH = "ZQH" + bm + str + "01" + rq + al;
                    // String BH =JSONObject.fromObject(arr).get("BH").toString();
                    String YYBM = JSONObject.fromObject(arr).get("YYBM").toString();
                    String XM = JSONObject.fromObject(arr).get("XM").toString();
                    String SFZHM = JSONObject.fromObject(arr).get("SFZHM").toString();
                    String XB = JSONObject.fromObject(arr).get("XB").toString();
                    String MZ = JSONObject.fromObject(arr).get("MZ").toString();
                    String XZZ = JSONObject.fromObject(arr).get("XZZ").toString();
                    String HJD = JSONObject.fromObject(arr).get("HJD").toString();
                    String LXDH = JSONObject.fromObject(arr).get("LXDH").toString();
                    String SSKS = JSONObject.fromObject(arr).get("SSKS").toString();
                    String SSXQ = JSONObject.fromObject(arr).get("SSXQ").toString();
                    String RKSJ = JSONObject.fromObject(arr).get("RKSJ").toString();
                    colum.setColumn(BH);
                    colum.setColumn1(YYBM);
                    colum.setColumn2(XM);
                    colum.setColumn3(SFZHM);
                    colum.setColumn4(XB);
                    colum.setColumn5(MZ);
                    colum.setColumn6(XZZ);
                    colum.setColumn7(HJD);
                    colum.setColumn8(LXDH);
                    colum.setColumn9(SSKS);
                    colum.setColumn10(SSXQ);
                    colum.setColumn13(RKSJ);
                    // System.out.println(colum.getColumn4());
                    if (colum.getColumn1() != null || colum.getColumn2() != null || colum.getColumn4().length() < 2) {
                        this.dao.addMedicalStaffInfoDao(colum);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list != null) {
            return (T) new Result(0, "获取成功");
        }
        return (T) new Status(StatusEnum.NO_DATA.getCODE(), StatusEnum.NO_DATA.getEXPLAIN());
    }

    /**
     * 虹山医院 获取就医信息
     *
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T addTreatmentInfo() throws Exception {
        //设置文件编号
        String uuid = this.uuid.getUUID().toString();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); //获取当前时间
        String time1 = sd.format(new Date());
        Date now = new Date();
        Date now_10 = new Date(now.getTime() - 600000); //10分钟前的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String startTime = dateFormat.format(now_10);
        String url = "http://test.lkasp.com:10088/WdWeb/Service1.asmx?WSDL";
        String code = "2012";
        String endTime = dateFormat.format(new Date());
        // String endTime = time1+" 23:59:59";
        String result = UrlUtil.url(url, code, startTime, endTime);
        //System.out.println(result);
        String list = Base64Util.decode(result);
        list = list.replace(" ", "");
        try {
            //将字符串转换成jsonObject对象
            JSONObject jsonObject = new JSONObject();
            jsonObject = JSONObject.fromObject(list);
            Object object = jsonObject.get("Info");
            JSONArray array = JSONArray.fromObject(object);
            if (array.size() != 0) {
                String al = "00000";
                for (Object arr : array) {
                    ExcelColumnInfo colum = new ExcelColumnInfo();
                    String str = uuid.substring(0, 4);
                    al = CodeUtil.fiveCode(al);
                    Integer a = Integer.parseInt(al);
                    if (a >= 99999) {
                        al = "00000";
                        al = CodeUtil.fiveCode(al);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq = time.substring(4);
                    String bm = "520402000000"; //西秀机构代码
                    String BH = "ZQH" + bm + str + "02" + rq + al;
                    // String BH =JSONObject.fromObject(arr).get("BH").toString();
                    String YYBM = JSONObject.fromObject(arr).get("YYBM").toString();
                    String XM = JSONObject.fromObject(arr).get("XM").toString();
                    String XB = JSONObject.fromObject(arr).get("XB").toString();
                    String CSRQ = JSONObject.fromObject(arr).get("CSRQ").toString();
                    String NL = JSONObject.fromObject(arr).get("NL").toString();
                    String SFZHM = JSONObject.fromObject(arr).get("SFZHM").toString();
                    String ZY = JSONObject.fromObject(arr).get("ZY").toString();
                    String HJD = JSONObject.fromObject(arr).get("HJD").toString();
                    String XZZ = JSONObject.fromObject(arr).get("XZZ").toString();
                    String LXDH = JSONObject.fromObject(arr).get("LXDH").toString();
                    String GZDWMC = JSONObject.fromObject(arr).get("GZDWMC").toString();
                    String GZDWDH = JSONObject.fromObject(arr).get("GZDWDH").toString();
                    String LXRXM = JSONObject.fromObject(arr).get("LXRXM").toString();
                    String LXRDZ = JSONObject.fromObject(arr).get("LXRDZ").toString();
                    String LXRDH = JSONObject.fromObject(arr).get("LXRDH").toString();
                    String JZRQ = JSONObject.fromObject(arr).get("JZRQ").toString();
                    String RYRQ = JSONObject.fromObject(arr).get("RYRQ").toString();
                    String CYRQ = JSONObject.fromObject(arr).get("CYRQ").toString();
                    String JZKS = JSONObject.fromObject(arr).get("JZKS").toString();
                    String ZZYS = JSONObject.fromObject(arr).get("ZZYS").toString();
                    String SSXQ = JSONObject.fromObject(arr).get("SSXQ").toString();
                    String RKSJ = JSONObject.fromObject(arr).get("RKSJ").toString();
                    colum.setColumn(BH);
                    colum.setColumn1(YYBM);
                    colum.setColumn2(XM);
                    colum.setColumn3(XB);
                    colum.setColumn4(CSRQ);
                    colum.setColumn5(NL);
                    colum.setColumn6(SFZHM);
                    colum.setColumn7(ZY);
                    colum.setColumn8(HJD);
                    colum.setColumn9(XZZ);
                    colum.setColumn10(LXDH);
                    colum.setColumn11(GZDWMC);
                    colum.setColumn12(GZDWDH);
                    colum.setColumn13(LXRXM);
                    colum.setColumn14(LXRDZ);
                    colum.setColumn15(LXRDH);
                    colum.setColumn16(JZRQ);
                    colum.setColumn17(RYRQ);
                    colum.setColumn18(CYRQ);
                    colum.setColumn19(JZKS);
                    colum.setColumn20(ZZYS);
                    colum.setColumn21(SSXQ);
                    colum.setColumn24(RKSJ);
                    System.out.println(colum.getColumn() + ">>" + colum.getColumn1() + ">>" + colum.getColumn2() + ">>" + colum.getColumn3() + ">>" + colum.getColumn4() + ">>" + colum.getColumn5() + ">>" + colum.getColumn6() + ">>" + colum.getColumn7() + ">>" + colum.getColumn8() + ">>" + colum.getColumn9() + ">>" + colum.getColumn10() + ">>" + colum.getColumn11() + ">>" + colum.getColumn12() + ">>" + colum.getColumn13() + ">>" + colum.getColumn14() + ">>" + colum.getColumn15() + ">>" + colum.getColumn16() + ">>" + colum.getColumn17() + ">>" + colum.getColumn18() + ">>" + colum.getColumn19() + ">>" + colum.getColumn20() + ">>" + colum.getColumn21() + ">>" + colum.getColumn22() + ">>" + colum.getColumn23() + ">>" + colum.getColumn24());
                    if (colum.getColumn1() != null || colum.getColumn2() != null || colum.getColumn4().length() < 2) {
                        this.dao.addConsultInfoDao(colum);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list != null) {
            return (T) new Result(0, "获取成功", list);
        }
        return (T) new Status(StatusEnum.NO_DATA.getCODE(), StatusEnum.NO_DATA.getEXPLAIN());
    }
}
