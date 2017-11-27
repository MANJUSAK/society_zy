package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 小区信息文件业务接口类
 * Created by 龙宏 on 2017/11/1.
 *
 * @version V1.0
 */
public interface ResidentialService {

    /**
     * 小区信息录入
     * @param request
     * @param files  小区信息文件
     * @return
     * @throws Exception
     */

    <T> T addResidentialInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 小区业主信息录入
     * @param request
     * @param files 小区业主信息文件
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T addCommunityOwnerInfo(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 小区物业人员信息录入
     * @param request
     * @param files 小区物业人员信息文件
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T addPropertyPersonnelInfo(HttpServletRequest request,MultipartFile[] files) throws  Exception;

    /**
     * 辖区派出所编码
     * @param request
     * @param files 编码信息文件
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T addAreaCode(HttpServletRequest request,MultipartFile[] files) throws  Exception;

    /**
     * 查询小区数
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findResidentNum()throws  Exception;

}