package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 供水数据清洗辅助业务类
 * Created by 龙宏 on 2017/11/27.
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class WaterSupplyServicelmplSupp {
    /**
     * 供水开户信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> waterAccountInfo(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn22(time);
            String code ="520402000000";//西秀区编码
            column.setColumn19(code);
            String type = column.getColumn10();
            if (type.equals("私人")){
                column.setColumn10("1");
            }else if (type.equals("单位")){
                column.setColumn10("2");
            }else if (type.equals("特殊")){
                column.setColumn10("3");
            }else {
                column.setColumn12("");
            }
            String status = column.getColumn11();
            if (status.equals("正常")){
                column.setColumn11("0");

            }else if (status.equals("换表")){
                column.setColumn13("1");
            }else if (status.equals("销户")){
                column.setColumn13("2");
            }else if (status.equals("停用")){
                column.setColumn13("4");
            }else if (status.equals("拆表")){
                column.setColumn13("5");
            }else if (status.equals("新户")){
                column.setColumn13("10");
            }else {
                column.setColumn13("0");
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
     * 供水抄表信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> waterReadingInfo(List<ExcelColumnInfo> list) throws Exception {
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
