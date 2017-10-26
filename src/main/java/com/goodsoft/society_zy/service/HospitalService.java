package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 医院信息文件业务接口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
public interface HospitalService {
    /**
     * 医院信息录入
     *
     * @param files 医院信息文件
     * @throws Exception
     */
    <T> T addHospitalInfoService(HttpServletRequest request,MultipartFile[] files) throws Exception;

    /**
     * 医务人员信息录入
     *
     * @param files 医务人员信息文件
     * @throws Exception
     */
    <T> T addMedicalStaffInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 就医信息录入
     *
     * @param files 就医信息文件
     * @throws Exception
     */
    <T> T addConsultInfoService(HttpServletRequest request,MultipartFile[] files) throws Exception;
}
