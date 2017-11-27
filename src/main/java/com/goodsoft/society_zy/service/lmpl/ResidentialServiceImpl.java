package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.AreaCodeDao;
import com.goodsoft.society_zy.domain.dao.FileDao;
import com.goodsoft.society_zy.domain.dao.ResidentialDao;
import com.goodsoft.society_zy.domain.dao.SchoolDao;
import com.goodsoft.society_zy.domain.entity.file.FileData;
import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.AreaInfo;
import com.goodsoft.society_zy.domain.entity.resident.Number;
import com.goodsoft.society_zy.domain.entity.resident.Residential;
import com.goodsoft.society_zy.domain.entity.result.Result;
import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.FileService;
import com.goodsoft.society_zy.service.ResidentialService;
import com.goodsoft.society_zy.service.supp.ResidetialServicelmplSupp;
import com.goodsoft.society_zy.util.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 小区信息录入接口业务逻辑类
 * Created by 龙宏 on 2017/11/1.
 */
@SuppressWarnings("ALL")
@Service
public class ResidentialServiceImpl implements ResidentialService {
    @Resource
    private ResidentialDao residentialDao; //数据操作类
    @Resource
    private AreaCodeDao areaCodeDao;  // 辖区编码数据操作类
    @Resource
    private FileDao fileDao;  //文件访问类
    @Resource
    private FileService fileService; // 文件业务逻辑类
    @Resource
    private SqlSessionTemplate sqlSession;
    @Resource
    private ResidetialServicelmplSupp residetialServicelmplSupp;  //数据清洗辅助业务类
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
     * 小区信息录入业务逻辑类
     * @param request 请求
     * @param files  小区信息文件
     * @return  录入值
     * @throws Exception
     */
    public <T> T addResidentialInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.residetialServicelmplSupp.residetialServiceSupp(sdData);
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
                    String code="ZTC"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String name = sdData.get(i).getColumn10();
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
                   String xqbm = "ZTC" + bh + num;
                    sdData.get(i).setColumn1(xqbm);
                    residentialDao.addResidentialInfoDao(sdData.get(i));
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
     * 小区业主信息录入业务逻辑类
     * @param request 请求
     * @param files 小区业主信息文件
     * @param <T>
     * @return 响应结果
     * @throws Exception
     */
    public <T> T addCommunityOwnerInfo(HttpServletRequest request, MultipartFile[] files) throws Exception{
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
        List<ExcelColumnInfo> errorList = this.residetialServicelmplSupp.communityOwnerInfoServiceSupp(sdData);
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
               // String num="000";
                for (int i = 0; i < len; ++i) {
                    str =CodeUtil.code(str);
                    al =CodeUtil.fiveCode(al);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq =time.substring(4);
                    String bm="520402000000"; //西秀机构代码
                    String code="ZTC"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String name = sdData.get(i).getColumn1();
                  //  System.out.println(sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn5()+">>"+sdData.get(i).getColumn3());
                    Residential resi = residentialDao.findResidentialCode(name);
                    String bh="";
                    String hm = resi.getXqbm();
                    if ( resi !=null && !"".equals(resi.getXqbm()) ){
                        bh = hm;
                    }
                   sdData.get(i).setColumn1(bh);
                    residentialDao.addCommunityOwnerInfo(sdData.get(i));

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
     * 小区物业人员信息录入业务逻辑类
     *
     * @param request  请求
     * @param files 小区物业人员信息文件
     * @param <T>
     * @return  响应结果
     * @throws Exception
     */
    public <T> T addPropertyPersonnelInfo(HttpServletRequest request,MultipartFile[] files) throws  Exception{
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
        List<ExcelColumnInfo> errorList = this.residetialServicelmplSupp.propertyPersonnelInfoServiceSupp(sdData);
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
              //  String num="000";
                for (int i = 0; i < len; ++i) {
                    str =CodeUtil.code(str);
                    al =CodeUtil.fiveCode(al);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    String rq =time.substring(4);
                    String bm="520402000000"; //西秀机构代码
                    String code="ZTC"+bm+str+"01"+rq+al;
                    sdData.get(i).setColumn(code);
                    String name = sdData.get(i).getColumn1();
                    Residential resi = residentialDao.findResidentialCode(name);
                    String bh="";
                    String hm = resi.getXqbm();
                    if ( resi !=null && !"".equals(resi.getXqbm()) ){
                        bh = hm;
                    }
                    sdData.get(i).setColumn1(bh);
                  //  System.out.println(sdData.get(i).getColumn()+">>"+sdData.get(i).getColumn1()+">>"+sdData.get(i).getColumn2()+">>"+sdData.get(i).getColumn3()+">>"+sdData.get(i).getColumn4()+">>"+sdData.get(i).getColumn5()+">>"+sdData.get(i).getColumn6()+">>"+sdData.get(i).getColumn7()+">>"+sdData.get(i).getColumn8()+">>"+sdData.get(i).getColumn9()+">>"+sdData.get(i).getColumn10()+">>"+sdData.get(i).getColumn11()+">>"+sdData.get(i).getColumn12()+">>"+sdData.get(i).getColumn13());
                    residentialDao.addPropertyPersonnelInfo(sdData.get(i));
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
     * 辖区派出所信息录入逻辑类
     * @param request
     * @param files 编码信息文件
     * @param <T>
     * @return  响应结果
     * @throws Exception
     */
    public <T> T addAreaCode(HttpServletRequest request,MultipartFile[] files) throws  Exception{
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
        List<ExcelColumnInfo> errorList = this.residetialServicelmplSupp.areaCodeServiceSupp(sdData);
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
                for (int i = 0; i < len; ++i) {
                    String id = this.uuid.getUUID().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String time = sdf.format(new Date());
                    sdData.get(i).setColumn3(time);
                    sdData.get(i).setColumn2(id);
                    areaCodeDao.addAreaCode(sdData.get(i));
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
     * 查询小区数
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findResidentNum()throws  Exception{
        Map<String,Object> map = new HashMap<String,Object>();
       // List list=new ArrayList();
        Number  num = residentialDao.findResidentNum(); //小区
        int a = num.getNum();
        map.put("xqNum",num.getNum());
        num =residentialDao.findOwnerNum();     //业主
        int b = num.getNum();
        map.put("yzNum",num.getNum());
        num = residentialDao.findPropertyNum(); // 物业
        int c =num.getNum();
        map.put("wyNum",num.getNum());
        int d = a+b+c;
        map.put("totalNum",d);
        if (map.size()>0) {
            return (T) new Result(0,"查询成功", map);
        }
        return (T) new Status(StatusEnum.NO_DATA.getCODE(), StatusEnum.NO_DATA.getEXPLAIN());
    }
}
