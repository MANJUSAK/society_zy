package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * ===>医院数据管理数据清洗辅助业务类
 *
 * @author 严彬荣 Created on 2017-10-25 17:24
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class HospitalServicelmplSupp {

    public List<ExcelColumnInfo> hospitalServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn4().length() < 15 || column.getColumn4().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    public List<ExcelColumnInfo> medicalStaffServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn2().length() < 15 || column.getColumn2().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    public List<ExcelColumnInfo> consultServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn5().length() < 15 || column.getColumn5().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

}
