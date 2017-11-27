package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.Number;
import com.goodsoft.society_zy.domain.entity.resident.Residential;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 小区信息操作数据接口类
 * Created by 龙宏 on 2017/10/25.
 *
 * @version V1.0
 */
@Repository
public interface ResidentialDao {
    /**
     * 小区信息录入
     *
     * @param msg 小区信息
     * @throws Exception 异常
     */
    void addResidentialInfoDao(ExcelColumnInfo msg) throws Exception;

    /**
     * 小区业主信息录入
     * @param msg  小区业主信息
     * @throws Exception 异常
     */
    void  addCommunityOwnerInfo(ExcelColumnInfo msg) throws  Exception;

    /**
     * 小区物业人员信息录入
     * @param msg 小区物业人员信息
     * @throws Exception 异常
     */
    void  addPropertyPersonnelInfo(ExcelColumnInfo msg) throws  Exception;

    /**
     * 小区编码查询类
     * @param name 名称
     * @return 结果
     * @throws Exception 异常
     */
     Residential findResidentialCode(@Param("name") String name) throws  Exception;

    /**
     * 查询小区个数
     * @return  结果
     * @throws Exception 异常
     */
     Number findResidentNum()throws  Exception;

    /**
     * 业主数
     * @return 结果
     * @throws Exception 异常
     */
     Number findOwnerNum()throws  Exception;

    /**
     * 物业数
     * @return 结果
     * @throws Exception 异常
     */
     Number findPropertyNum()throws  Exception;




}
