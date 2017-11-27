package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.PowerSupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 供电信息数据传输接口访问入口类
 * Created by 龙宏 on 2017/11/15.
 *
 * @version V1.0
 */
@RestController
@RequestMapping("/supply")
public class PowerSupplyController {
    @Resource
    private PowerSupplyService powerSupplyService;
    //实例化日志工具管理类
    private Logger logger = LoggerFactory.getLogger(PowerSupplyController.class);

    /**
     * 供电开户信息数据传入调用类
     * @param request 请求
     * @param files  文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/account/data.shtml", method = RequestMethod.POST)
    public Object addPowerSupplyAccountInfo(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.powerSupplyService.addPowerSupplyAccountInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 抄表信息数据录入接口
     *
     * @param request 请求
     * @param files   抄表数据文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/reading/data.shtml", method = RequestMethod.POST)
    public Object addPowerSupplyReadingInfo(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.powerSupplyService.addPowerSupplyReadingInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }
    @RequestMapping("/find/powerNum/data.shtml")
    @ResponseBody
    public Object findPowerNum() throws  Exception{
        return  powerSupplyService.findPowerNum();
    }

}
