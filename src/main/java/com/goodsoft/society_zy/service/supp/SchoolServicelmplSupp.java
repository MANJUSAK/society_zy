package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 学校信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> schoolServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn11(time);
            String code ="520402000000";//西秀区编码
            column.setColumn8(code);
            if (column.getColumn1() == "" ||column.getColumn2() == null||column.getColumn2().equals("学校名称")||column.getColumn1() == "1" ||column.getColumn2() == "") {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    /**
     * 教师信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> teacherServiceSupp(List<ExcelColumnInfo> list) throws Exception {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn23(time);
            String code ="520402000000";//西秀区编码
            column.setColumn20(code);
            String sex = column.getColumn3();
            if (sex.equals("男")){
                column.setColumn3("1");
            }else if (sex.equals("女")){
                column.setColumn3("2");
            }else {
                column.setColumn3("9");
            }
//            System.out.println(column.getColumn2());
            if (column.getColumn1() == "" ||column.getColumn2() == ""||column.getColumn1() == "1"||column.getColumn1().equals("学校编码")||column.getColumn2() == null ) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    /**
     * 学生信息录入判断逻辑类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> studentPrimaryServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn39(time);
            String code ="520402000000";//西秀区编码
            column.setColumn36(code);
            String sex = column.getColumn4();
            if (sex.equals("男")){
                column.setColumn4("1");
            }else if (sex.equals("女")){
                column.setColumn4("2");
            }else {
                column.setColumn4("9");
            }
            String b = column.getColumn13();
            if (b.equals("是")){
                column.setColumn13("1");
            }else {
                column.setColumn13("0");
            }
            String d = column.getColumn14();
            if (d.equals("是")){
                column.setColumn14("1");
            }else {
                column.setColumn14("0");
            }
            String c = column.getColumn15();
            if (c.equals("走读")){
                column.setColumn15("1");
            }else {
                column.setColumn15("0");
            }
            String a = column.getColumn16();
            if (a.equals("是")){
                column.setColumn16("1");
            }else {
                column.setColumn16("0");
            }
            String e = column.getColumn35();
            if (a.equals("在读")){
                column.setColumn35("1");
            }else {
                column.setColumn35("0");
            }
           // System.out.println(column.getColumn2());
            if (column.getColumn1() == ""|| "".equals(column.getColumn2())||column.getColumn2() == "" ||column.getColumn2() == null||column.getColumn1() == "1" ||column.getColumn1().equals("学校编码")) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }
}
