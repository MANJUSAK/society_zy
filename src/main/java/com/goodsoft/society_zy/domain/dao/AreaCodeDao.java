package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.domain.entity.resident.AreaInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 辖区编码信息操作数据接口类
 * Created by 龙宏 on 2017/10/25.
 *
 * @version V1.0
 */
@Repository
public interface AreaCodeDao {
    /**
     * 辖区编码信息录入
     *
     * @param msg 辖区派出所信息
     * @throws Exception
     */
    void addAreaCode(ExcelColumnInfo msg) throws Exception;

    /**
     * 派出所机构代码查询
     * @param name
     * @return
     * @throws Exception
     */
    public AreaInfo findAreaCode(@Param("name") String name) throws  Exception;

}
