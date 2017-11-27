package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 *供电信息
 * Created by 龙宏 on 2017/11/14.
 */
public interface PowerSupplyService {
    /**
     * 供电开户信息录入
     * @param request  请求
     * @param files 文件
     * @param <T>
     * @return 响应结果
     * @throws Exception
     */
    <T> T addPowerSupplyAccountInfo(HttpServletRequest request, MultipartFile[] files) throws  Exception;

    /**
     * 供电抄表信息录入
     * @param request 请求
     * @param files 文件
     * @param <T>
     * @return 响应结果
     * @throws Exception
     */
    <T> T addPowerSupplyReadingInfo(HttpServletRequest request,MultipartFile[] files)throws  Exception;

    /**
     * 供电数据
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findPowerNum() throws  Exception;
}
