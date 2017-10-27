package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.FileDao;
import com.goodsoft.society_zy.domain.dao.SchoolDao;
import com.goodsoft.society_zy.domain.entity.file.FileData;
import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.result.Result;
import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.FileService;
import com.goodsoft.society_zy.service.SchoolService;
import com.goodsoft.society_zy.service.supp.SchoolServicelmplSupp;
import com.goodsoft.society_zy.util.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                for (int i = 0; i < len; ++i) {
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
                for (int i = 0; i < len; ++i) {
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
     * 高中部学生信息数据录入业务方法
     *
     * @param request 请求
     * @param files   学生信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addStudent_advancedService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.studentAdvancedServiceSupp(sdData);
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
                    schoolDao.addStudent_advancedDao(sdData.get(i));
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
     * 初中部学生信息数据录入业务方法
     *
     * @param request 请求
     * @param files   学生信息文件
     * @param <T>
     * @return 录入结果
     * @throws Exception
     */
    @Override
    @Transactional
    public <T> T addStudent_middleService(HttpServletRequest request, MultipartFile[] files) throws Exception {
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
        List<ExcelColumnInfo> errorList = this.servicelmplSupp.studentMiddleServiceSupp(sdData);
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
                    schoolDao.addStudent_middleDao(sdData.get(i));
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
     * 小学学生信息数据录入业务方法
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
                for (int i = 0; i < len; ++i) {
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
}
