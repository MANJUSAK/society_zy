package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.WaterSupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 供水信息数据传输接口访问入口类
 * Created by 龙宏 on 2017/11/27.
 *
 * @version V1.0
 */
@Controller
@RequestMapping("/water")
public class WaterSupplyController {
    @Resource
    private WaterSupplyService waterSupplyService;
    //实例化日志工具管理类
    private Logger logger = LoggerFactory.getLogger(PowerSupplyController.class);

    /**
     * 供水开户信息录入接口
     * @param request 请求
     * @param files 文件
     * @return 结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/account/data.shtml", method = RequestMethod.POST)
    public Object addWaterSupplyAccountInfo(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.waterSupplyService.addWaterSupplyAccountInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }
    /**
     * 供水抄表信息录入接口
     * @param request 请求
     * @param files 文件
     * @return 结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/reding/data.shtml", method = RequestMethod.POST)
    public Object addWaterSupplyReadingInfo(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.waterSupplyService.addWaterSupplyReadingInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }
}
