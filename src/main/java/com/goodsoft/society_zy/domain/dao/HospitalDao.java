package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Repository;

/**
 * 医院信息操作数据库接口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@Repository
public interface HospitalDao {
    /**
     * 医院信息录入
     *
     * @param msg 医院信息
     * @throws Exception
     */
    void addHospitalInfoDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 医务人员信息录入
     *
     * @param msg 医务人员信息
     * @throws Exception
     */
    void addMedicalStaffInfoDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 就医信息录入
     *
     * @param msg 就医信息
     * @throws Exception
     */
    void addConsultInfoDao(ExcelColumnInfo msg) throws Exception;
}
