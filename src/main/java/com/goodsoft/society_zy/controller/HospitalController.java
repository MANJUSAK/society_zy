package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 医院信息数据传输接口访问类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    @Resource
    private HospitalService service;
    //实例化日志工具管理类
    private Logger logger = LoggerFactory.getLogger(HospitalController.class);

    /**
     * 医院信息录入接口
     *
     * @param request 请求
     * @param files   文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/hospital/data.shtml", method = RequestMethod.POST)
    public Object addHospitalController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addHospitalInfoService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 就医信息录入接口
     *
     * @param request 请求
     * @param files   文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/consult/data.shtml", method = RequestMethod.POST)
    public Object addConsultController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addConsultInfoService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 医务人员信息数据录入接口
     *
     * @param request 请求
     * @param files   文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/medicalStaff/data.shtml", method = RequestMethod.POST)
    public Object addMedicalStaffController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.service.addMedicalStaffInfoService(request, files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }
}
