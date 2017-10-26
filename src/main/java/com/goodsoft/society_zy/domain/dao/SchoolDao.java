package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Repository;

/**
 * 学校信息操作数据接口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@Repository
public interface SchoolDao {
    /**
     * 学校信息录入
     *
     * @param msg 学校信息
     * @throws Exception
     */
    void addSchoolInfoDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 教师信息录入
     *
     * @param msg 教师信息
     * @throws Exception
     */
    void addTeacherInfoDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 高中学生信息录入
     *
     * @param msg 学生信息
     * @throws Exception
     */
    void addStudent_advancedDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 初中学校信息录入
     *
     * @param msg 学生信息
     * @throws Exception
     */
    void addStudent_middleDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 小学学生信息录入
     *
     * @param msg 学生信息
     * @throws Exception
     */
    void addStudent_primaryDao(ExcelColumnInfo msg) throws Exception;
}
