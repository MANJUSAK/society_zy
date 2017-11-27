package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 学校信息数据传输接口访问入口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@RestController
@RequestMapping("/education")
public class SchoolController {
    @Resource
    private SchoolService service;
    //实例化日志工具管理类
    private Logger logger = LoggerFactory.getLogger(SchoolController.class);

    /**
     * 学校信息数据录入接口
     *
     * @param request 请求
     * @param files   学校信息文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/school/data.shtml", method = RequestMethod.POST)
    public Object addSchoolInfoController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addSchoolInfoService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 教师信息数据录入接口
     *
     * @param request 请求
     * @param files   教师数据文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/teacher/data.shtml", method = RequestMethod.POST)
    public Object addTeacherInfoController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addTeacherInfoService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 学生信息数据录入接口
     *
     * @param request 请求
     * @param files   学生数据文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/student_x/data.shtml", method = RequestMethod.POST)
    public Object addStudentPrimaryController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addStudent_primaryService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 学校数据条数
     * @return
     * @throws Exception
     */
    @RequestMapping("/find/schoolNum/data.shtml")
    @ResponseBody
    public  Object findSchoolNum() throws  Exception{
        return  this.service.findSchoolNum();
    }
}
