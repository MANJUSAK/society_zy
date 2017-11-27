package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.service.ResidentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 小区信息数据传输接口访问入口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@RestController
@RequestMapping("/resident")
public class ResidentialController {
    @Resource
    private ResidentialService residentialService;
    //实例化日志工具管理类
    private Logger logger = LoggerFactory.getLogger(ResidentialController.class);

    /**
     * 小区信息录入访问接口
     *
     * @param request 请求
     * @param files  小区信息文件
     * @return  响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/rensidentInfo/data.shtml", method = RequestMethod.POST)
    @ResponseBody
    public Object addResidentialInfoController( HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] files) {

        try {
            return this.residentialService.addResidentialInfoService(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 小区业主信息录入访问接口
     *
     * @param request 请求
     * @param files  小区业主文件 信息
     * @return   响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/communityOwnerInfo/data.shtml", method = RequestMethod.POST)
    @ResponseBody
    public Object addCommunityOwnerInfoController(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.residentialService.addCommunityOwnerInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 小区物业人员信息录入请求接口
     *
     * @param request 请求
     * @param files 小区物业人员信息文件
     * @return 响应结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/propertyPersonnelInfo/data.shtml", method = RequestMethod.POST)
    @ResponseBody
    public Object addPropertyPersonnelInfo(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.residentialService.addPropertyPersonnelInfo(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 辖区派出所编码录入调用接口
     * @param request 请求
     * @param files 文件
     * @return 结果
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping(value = "/add/areaCode/data.shtml", method = RequestMethod.POST)
    @ResponseBody
    public Object addAreaCode(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        try {
            return this.residentialService.addAreaCode(request,files);
        } catch (Exception e) {
            this.logger.error(e.toString());
            return new Status(StatusEnum.EXCEL_ERROR.getCODE(), StatusEnum.EXCEL_ERROR.getEXPLAIN());
        }
    }

    /**
     * 查询小区数
     * @return
     * @throws Exception
     */

    @RequestMapping("/find/residentNum/data.shtml")
    @ResponseBody
    public Object findResidentNum()throws Exception{
        return this.residentialService.findResidentNum();
    }
}
