package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * description:
 * ===>学校信息文件管理业务接口类
 *
 * @author 严彬荣 Created on 2017-10-25 16:22
 * @version V1.0
 */
public interface SchoolService {
    /**
     * 学校信息录入
     *
     * @param files 学校信息文件
     * @throws Exception
     */
    <T> T addSchoolInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 教师信息录入
     *
     * @param files 教师信息文件
     * @throws Exception
     */
    <T> T addTeacherInfoService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 高中学生信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    <T> T addStudent_advancedService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 初中学校信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    <T> T addStudent_middleService(HttpServletRequest request, MultipartFile[] files) throws Exception;

    /**
     * 小学学生信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    <T> T addStudent_primaryService(HttpServletRequest request, MultipartFile[] files) throws Exception;
}
