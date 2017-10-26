package com.goodsoft.society_zy.service;

import org.springframework.web.multipart.MultipartFile;

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
    void addSchoolInfoService(MultipartFile[] files) throws Exception;

    /**
     * 教师信息录入
     *
     * @param files 教师信息文件
     * @throws Exception
     */
    void addTeacherInfoService(MultipartFile[] files) throws Exception;

    /**
     * 高中学生信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    void addStudent_advancedService(MultipartFile[] files) throws Exception;

    /**
     * 初中学校信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    void addStudent_middleService(MultipartFile[] files) throws Exception;

    /**
     * 小学学生信息录入
     *
     * @param files 学生信息文件
     * @throws Exception
     */
    void addStudent_primaryService(MultipartFile[] files) throws Exception;
}
