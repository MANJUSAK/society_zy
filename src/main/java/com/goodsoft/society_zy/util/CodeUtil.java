package com.goodsoft.society_zy.util;


/**
 * 编码生成
 * Created by 龙宏 on 2017/11/4.
 */
@SuppressWarnings("ALL")
public class CodeUtil {
    public static String code(String aa){
        //四位流水号
        String str = "";
        //String bb = aa.substring(4,8);
        long cc = new Long(aa) + 1;
        if (cc < 10) {
            str = "000"+cc;
        }else if (cc < 100) {
            str = "00"+cc;
        }else if (cc < 1000) {
            str = "0"+cc;
        }else {
            str = ""+cc;
        }
        return  str;
    }
    public static String threeCode(String aa){
        //四位流水号
        String str = "";
        //String aa = "0000";
        //String bb = aa.substring(4,8);
        long cc = new Long(aa) + 1;
        if (cc < 10) {
            str = "00"+cc;
        }else if (cc < 100) {
            str = "0"+cc;
        }else {
            str = ""+cc;
        }
        return  str;
    }
    public static String fiveCode(String aa){
        //五位流水号
        String al = "";
       // String bb = "00000";
        //String bb = aa.substring(4,8);
        long num = new Long(aa) + 1;
        if (num < 10) {
            al = "0000"+num;
        }else if (num < 100) {
            al = "000"+num;
        }else if (num < 1000) {
            al = "00"+num;
        }else if (num < 10000){
            al = "0"+num;
        }else {
            al = ""+num;
        }
        return al;
    }
}
