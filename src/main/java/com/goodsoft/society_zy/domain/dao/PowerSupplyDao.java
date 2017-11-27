package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.Number;
import org.springframework.stereotype.Repository;

/**
 * 供电信息数据操作接口类
 * Created by 龙宏 on 2017/11/14.
 */
@Repository
public interface PowerSupplyDao {
    /**
     * 供电开户信息数据操作类
     * @param msg
     * @throws Exception
     */
    public  void  addPowerSupplyAccountInfo(ExcelColumnInfo msg)throws  Exception;

    /**
     * 供电抄表信息
     * @param msg 实体信息
     * @throws Exception
     */
    public  void  addPowerSupplyReadingInfo(ExcelColumnInfo msg)throws  Exception;

    /**
     * 开户条数
     * @return
     * @throws Exception
     */
    public Number findAccountNum() throws  Exception;

    /**
     * 抄表条数
     * @return
     * @throws Exception
     */
    public Number findReadingNum() throws  Exception;
}
