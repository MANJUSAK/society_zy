package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 供水信息
 * Created by 龙宏 on 2017/11/27.
 */
public interface WaterSupplyService {
    /**
     * 供水开户信息录入
     * @param request 请求
     * @param files 文件
     * @param <T>
     * @return 结果
     * @throws Exception 异常
     */
    <T> T addWaterSupplyAccountInfo(HttpServletRequest request, MultipartFile[] files) throws  Exception;

    /**
     * 供水抄表信息录入
     * @param request 请求
     * @param files 文件
     * @param <T>
     * @return 结果
     * @throws Exception 异常
     */
    <T>T addWaterSupplyReadingInfo(HttpServletRequest request,MultipartFile[] files) throws Exception ;
}
