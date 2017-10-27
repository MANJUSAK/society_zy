package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * ===>教育数据管理清洗辅助业务类
 *
 * @author 严彬荣 Created on 2017-10-27 9:20
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class SchoolServicelmplSupp {

    public List<ExcelColumnInfo> schoolServiceSupp(List<ExcelColumnInfo> list) {
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

    public List<ExcelColumnInfo> teacherServiceSupp(List<ExcelColumnInfo> list) {
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

    public List<ExcelColumnInfo> studentPrimaryServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn4().length() < 15 || column.getColumn4().length() > 18 ||
                    column.getColumn18().length() < 15 || column.getColumn18().length() > 18 || column.getColumn22().length() < 15 ||
                    column.getColumn22().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    public List<ExcelColumnInfo> studentMiddleServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn4().length() < 15 || column.getColumn4().length() > 18 ||
                    column.getColumn19().length() < 15 || column.getColumn19().length() > 18 || column.getColumn23().length() < 15 ||
                    column.getColumn23().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    public List<ExcelColumnInfo> studentAdvancedServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" || column.getColumn4().length() < 15 || column.getColumn4().length() > 18 ||
                    column.getColumn14().length() < 15 || column.getColumn14().length() > 18 || column.getColumn18().length() < 15 ||
                    column.getColumn18().length() > 18) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }
}
