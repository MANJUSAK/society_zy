package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.AreaCodeDao;
import com.goodsoft.society_zy.domain.dao.FileDao;
import com.goodsoft.society_zy.domain.dao.SchoolDao;
import com.goodsoft.society_zy.domain.entity.file.FileData;
import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.AreaInfo;
import com.goodsoft.society_zy.domain.entity.resident.Number;
import com.goodsoft.society_zy.domain.entity.result.Result;
import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.domain.entity.school.SchoolInfo;
import com.goodsoft.society_zy.service.FileService;
import com.goodsoft.society_zy.service.SchoolService;
import com.goodsoft.society_zy.service.supp.SchoolServicelmplSupp;
import com.goodsoft.society_zy.util.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * description:
 * ===>学校信息管理业务接口实现类
 *
 * @author 严彬荣 Created on 2017-10-25 16:26
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class SchoolServicelmpl implements SchoolService {
    @Resource
    private SqlSessionTemplate sqlSession;
    @Resource
    private AreaCodeDao areaCodeDao;
    @Resource
    private SchoolDao dao;
    @Resource
    private FileDao fileDao;
    @Resource
    private FileService fileService;
    @Resource
    private SchoolServicelmplSupp servicelmplSupp;
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

    /**
     * 学校信息数据录入业务方法
     *
     * @param request 请求
     * @param files   学校信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addSchoolInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.schoolServiceSupp(sdData);
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
            SchoolDao schoolDao = session.getMapper(SchoolDao.class);
            try {
                String str = "0000";
                String al ="00000";
                String num="000";
                for (int i = 0; i < len; ++i) {
                    str =CodeUtil.code(str);
                    al=CodeUtil.fiveCode(al);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq =time.substring(4);
                    String bm="520402000000"; //西秀机构代码
                    String code="ZPF"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String name = sdData.get(i).getColumn1();
                    // System.out.println(sdData.get(i).getColumn2());
                    String bh="";
                    AreaInfo areaInfo = new AreaInfo();
                    areaInfo = areaCodeDao.findAreaCode(name);
                    //System.out.println(areaInfo!= null);
                    if (areaInfo!= null && !"".equals(areaInfo.getCode())){
                        // System.out.println(areaInfo.getCode());
                        bh = areaInfo.getCode();
                    }else {
                        bh = bm;
                    }
                    num =CodeUtil.threeCode(num);
                    String xqbm = "ZPF" + bh + num;
                    sdData.get(i).setColumn1(xqbm);
                   // System.out.println(sdData.get(i).getColumn()+">>"+sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn2()+">>"+sdData.get(i).getColumn3()+">>"+sdData.get(i).getColumn4()+">>"+sdData.get(i).getColumn5()+">>"+sdData.get(i).getColumn6()+">>"+sdData.get(i).getColumn7()+">>"+sdData.get(i).getColumn8()+">>"+sdData.get(i).getColumn9()+">>"+sdData.get(i).getColumn10()+">>"+sdData.get(i).getColumn11());
                    schoolDao.addSchoolInfoDao(sdData.get(i));
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
     * 教师信息数据录入业务方法
     *
     * @param request 请求
     * @param files   教师信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addTeacherInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.teacherServiceSupp(sdData);
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
            SchoolDao schoolDao = session.getMapper(SchoolDao.class);
            try {
                String str = "0000";
                String al ="00000";
                for (int i = 0; i < len; ++i) {
                    str =CodeUtil.code(str);  //四位序号
                    al =CodeUtil.fiveCode(al); //五位序号
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq =time.substring(4);
                    String bm="520402000000"; //西秀机构代码
                    String code="ZPF"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String t = sdData.get(i).getColumn4();
                    //System.out.println(sdData.get(i).getColumn2()+">>"+ t);
                    String  date = null; //初始化date
                   // System.out.println(!t.equals("")+">>"+ !"".equals(sdData.get(i).getColumn4()));
                    if (!t.equals("") && !"".equals(sdData.get(i).getColumn4())){
                        Date date1 = HSSFDateUtil.getJavaDate(Double.parseDouble(t));
                        DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        date = df.format(date1); //Mon Jan 14 00:00:00 CST 2013
                        sdData.get(i).setColumn4(date);
                    }
                    String name = sdData.get(i).getColumn1();
                    //  System.out.println(sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn5()+">>"+sdData.get(i).getColumn3());
                    SchoolInfo schoolInfo = schoolDao.findSchoolCode(name);
                    String bh="";
                    String hm = schoolInfo.getXxbm();
                    if ( schoolInfo !=null && !"".equals(schoolInfo.getXxbm()) ){
                        bh = hm;
                    }
                    sdData.get(i).setColumn1(bh);
                    //System.out.println(sdData.get(i).getColumn2()+">>"+sdData.get(i).getColumn4());
                    schoolDao.addTeacherInfoDao(sdData.get(i));
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
     * 学生信息数据录入业务方法
     *
     * @param request 请求
     * @param files   学生信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addStudent_primaryService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.studentPrimaryServiceSupp(sdData);
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
            SchoolDao schoolDao = session.getMapper(SchoolDao.class);
            try {
                String str = "0000";
                String al ="00000";
                for (int i = 0; i < len; ++i) {
                    str =CodeUtil.code(str);  //四位序号
                    al =CodeUtil.fiveCode(al); //五位序号
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq =time.substring(4);
                    String bm="520402000000"; //西秀机构代码
                    String code="ZPF"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String name = sdData.get(i).getColumn1();
                   // System.out.println(sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn2()+">>"+sdData.get(i).getColumn3()+">>"+sdData.get(i).getColumn13()+">>"+sdData.get(i).getColumn14()+">>"+sdData.get(i).getColumn15()+">>"+sdData.get(i).getColumn16()+">>"+sdData.get(i).getColumn35()+">>"+sdData.get(i).getColumn4());
                    SchoolInfo schoolInfo = schoolDao.findSchoolCode(name);
                    String bh="";
                    String hm = schoolInfo.getXxbm();
                    if ( schoolInfo !=null && !"".equals(schoolInfo.getXxbm()) ){
                        bh = hm;
                    }
                    sdData.get(i).setColumn1(bh);
                    //System.out.println(sdData.get(i).getColumn()+">>"+sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn2()+">>"+sdData.get(i).getColumn3()+">>"+sdData.get(i).getColumn4()+">>"+sdData.get(i).getColumn5()+">>"+sdData.get(i).getColumn6()+">>"+sdData.get(i).getColumn7()+">>"+sdData.get(i).getColumn8()+">>"+sdData.get(i).getColumn9()+">>"+sdData.get(i).getColumn10()+">>"+sdData.get(i).getColumn11()+">>"+sdData.get(i).getColumn12()+">>"+sdData.get(i).getColumn13()+">>"+sdData.get(i).getColumn14()+">>"+sdData.get(i).getColumn15()+">>"+sdData.get(i).getColumn16()+">>"+sdData.get(i).getColumn17()+">>"+sdData.get(i).getColumn18()+">>"+sdData.get(i).getColumn19()+">>"+sdData.get(i).getColumn20()+">>"+sdData.get(i).getColumn21()+">>"+sdData.get(i).getColumn22()+">>"+sdData.get(i).getColumn23()+">>"+sdData.get(i).getColumn24()+">>"+sdData.get(i).getColumn25()+">>"+sdData.get(i).getColumn26()+">>"+sdData.get(i).getColumn27()+">>"+sdData.get(i).getColumn28()+">>"+sdData.get(i).getColumn29()+">>"+sdData.get(i).getColumn30()+">>"+sdData.get(i).getColumn31()+">>"+sdData.get(i).getColumn32()+">>"+sdData.get(i).getColumn33()+">>"+sdData.get(i).getColumn34()+">>"+sdData.get(i).getColumn35()+">>"+sdData.get(i).getColumn36()+">>"+sdData.get(i).getColumn37()+">>"+sdData.get(i).getColumn38()+">>"+sdData.get(i).getColumn39());
                    schoolDao.addStudent_primaryDao(sdData.get(i));
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
     * 学校数据
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findSchoolNum() throws  Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        // List list=new ArrayList();
        Number num = dao.findSchoolNum(); //学校
        int a = num.getNum();
        map.put("schoolNum",num.getNum());
        num =dao.findTeacherNum();     //教师
        int b = num.getNum();
        map.put("teacherNum",num.getNum());
        num = dao.findStudebtNum(); // 学生
        int c =num.getNum();
        map.put("studentNum",num.getNum());
        int d = a+b+c;
        map.put("totalNum",d);
        if (map.size()>0) {
            return (T) new Result(0,"查询成功", map);
        }
        return (T) new Status(StatusEnum.NO_DATA.getCODE(), StatusEnum.NO_DATA.getEXPLAIN());
    }
}
