package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description:
 * ===>供电数据管理清洗辅助业务类
 *
 * @author 龙宏 Created on 2017-10-27 9:20
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class PowerSupplyServicelmplSupp {

    /**
     * 供电开户信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> AccountInfo(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn22(time);
            String code ="520402000000";//西秀区编码
            column.setColumn19(code);
            String type = column.getColumn12();
            if (type.equals("居民生活")){
                    column.setColumn12("1");
            }else if (type.equals("普通工业")){
                    column.setColumn12("2");
            }else if (type.equals("农业生产")){
                column.setColumn12("3");
            }else if (type.equals("商业")){
                column.setColumn12("4");
            }else if (type.equals("非居民")) {
                column.setColumn12("5");
            }else if (type.equals("非工业")) {
                column.setColumn12("6");
            }else if (type.equals("农业排灌")) {
                column.setColumn12("7");
            }else if (type.equals("大工业用电")) {
                column.setColumn12("8");
            }else {
                column.setColumn12("1");
            }
            String status = column.getColumn13();
            if (status.equals("停用")){
                column.setColumn13("0");

            }else {
                column.setColumn13("1");
            }
            //System.out.println(column.getColumn1()+">>"+column.getColumn2());
            if (column.getColumn1() == ""||column.getColumn1() == null ||column.getColumn2() == null||column.getColumn1().equals("用户名")||column.getColumn1().equals("1" )||column.getColumn2() == ""||column.getColumn11() == ""||column.getColumn11() == null) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    /**
     * 抄表信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> readingInfo(List<ExcelColumnInfo> list) throws Exception {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn11(time);
            String code ="520402000000";//西秀区编码
            column.setColumn8(code);
            String num = column.getColumn5();
            if (num.equals("") ||num==null){
                column.setColumn5("0");
            }
            String free = column.getColumn6();
            if (free.equals("") || free == null){
                column.setColumn6("0");
            }
            //System.out.println(column.getColumn1()+">>"+column.getColumn3()+">>"+column.getColumn6());
            if (column.getColumn1() == "" ||column.getColumn1() == null ||column.getColumn1().equals("1") ||column.getColumn1().equals("用户编码")||column.getColumn7() == ""||column.getColumn7() == null ) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

}
