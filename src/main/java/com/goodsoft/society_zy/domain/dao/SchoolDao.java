package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.Number;
import com.goodsoft.society_zy.domain.entity.school.SchoolInfo;
import org.apache.ibatis.annotations.Param;
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
     * 小学学生信息录入
     *
     * @param msg 学生信息
     * @throws Exception
     */
    void addStudent_primaryDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 查询学校编码
     * @param name
     * @return
     */
    public SchoolInfo findSchoolCode(@Param("name") String name)throws  Exception;

    /**
     * 学校数量
     * @return
     * @throws Exception
     */
    public Number findSchoolNum()throws  Exception;

    /**
     * 教师数量
     * @return
     * @throws Exception
     */
    public Number findTeacherNum() throws  Exception;

    /**
     * 学生数量
     * @return
     * @throws Exception
     */
    public Number findStudebtNum()throws Exception;

}
