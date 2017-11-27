package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
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
// @SpringBootApplication
//@EnableScheduling
public class HospitalController {
    @Resource
    private HospitalService service;
    //实例化日志工具管理类
    private static Logger logger = LoggerFactory.getLogger(HospitalController.class);
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
        logger.info("My Spring Boot Application Started");
    }

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

    /**
     * 虹山医院 医院信息数据获取录入
     * @return
     * @throws Exception
     */
    @RequestMapping("/find/hospital/data.shtml")
    @ResponseBody
  //  @Scheduled(cron="0 0/5 * * * ?")
    public Object addHospitalInfo()throws  Exception{
        try {
            return  this.service.addHospitalInfo();
        }catch (Exception e){
            this.logger.error(e.toString());
            return new Status(StatusEnum.NO_URL.getCODE(),StatusEnum.NO_URL.getEXPLAIN());
        }

    }

    /**
     * 虹山医院医务人员信息获取录入
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/find/medical/data.shtml")
    public Object addMedicalStaffInfo() throws Exception{
        try {
            return this.service.addMedicalStaffInfo();
        }catch (Exception e){
            this.logger.error(e.toString());
            return new Status(StatusEnum.NO_URL.getCODE(),StatusEnum.NO_URL.getEXPLAIN());
        }
    }

    /**
     * 虹山医院 就医信息录入
     * @return 返回结果
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/find/treatment/data.shtml")
    public Object addTreatmentInfo() throws  Exception{
        try {
            return  this.service.addTreatmentInfo();
        }catch (Exception e){
            this.logger.error(e.toString());
            return new Status(StatusEnum.NO_URL.getCODE(),StatusEnum.NO_URL.getEXPLAIN());
        }
    }
}
