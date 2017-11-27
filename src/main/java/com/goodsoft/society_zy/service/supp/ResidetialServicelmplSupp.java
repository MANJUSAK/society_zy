package com.goodsoft.society_zy.service.supp;

import com.goodsoft.society_zy.domain.dao.ResidentialDao;
import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import com.goodsoft.society_zy.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description:
 * ===>小区数据管理清洗辅助业务类
 *
 * @author 龙宏 Created on 2017-10-27 9:20
 * @version V1.0
 */
@SuppressWarnings("ALL")
@Service
public class ResidetialServicelmplSupp {
    //实例化UUID工具类
    private UUIDUtil uuid = UUIDUtil.getInstance();
    @Resource
    private ResidentialDao residentialDao; //数据操作类
    /**
     * 小区信息录入判断类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> residetialServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn9(time);
            String code ="520402000000";//西秀区编码
            column.setColumn6(code);
            if (column.getColumn2()== "" || column.getColumn2().equals("2") ||column.getColumn2().equals("小区名称")) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    /**
     * 业主信息录入判断类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> communityOwnerInfoServiceSupp(List<ExcelColumnInfo> list) throws  Exception {

        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn22(time);
            String code ="520402000000";//西秀区编码
            column.setColumn19(code);
//            String name = column.getColumn1();
//            String bh = "";
//            System.out.println(column.getColumn1());
//            if(!"1".equals(column.getColumn1()) || !"小区编码".equals(column.getColumn1())){
//            if (column !=null && !"".equals(column.getColumn5())) {
//                Residential resi = residentialDao.findResidentialCode(name);
//                String hm = resi.getXqbm();
//                if (resi != null && !"".equals(resi.getXqbm())) {
//                    bh = hm;
//                } else {
//                    bh = "";
//                }
//            }else {
//                bh = "";
//            }
//            }
//            column.setColumn1(bh);
           // System.out.println(column.getColumn1()+">>"+column.getColumn5());
            if (column.getColumn1() == ""||column.getColumn1() == null ||column.getColumn1().equals("1") || column.getColumn2() == ""||  column.getColumn6().length()>18  || column.getColumn5().equals("业主1姓名")) {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
                //column.getColumn() == ""||column.getColumn() == "编号" || column.getColumn4()=="" || column.getColumn1()==""|| column.getColumn2()==""|| column.getColumn3()==""|| column.getColumn5()==""|| column.getColumn19()==""|| column.getColumn22()=="") {
                //column.getColumn6().length() > 18 || column.getColumn12().length()>18||
            }
        }
        return errorMsg;
    }

    /**
     * 物业信息录入判断类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> propertyPersonnelInfoServiceSupp(List<ExcelColumnInfo> list) throws  Exception {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            String uuid = this.uuid.getUUID().toString();
            ExcelColumnInfo column = list.get(i);
            //System.out.print(list.get(i).getColumn12());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            column.setColumn13(time);
            String code ="520402000000";//西秀区编码
            column.setColumn10(code);
//            String name = column.getColumn1();
//            Residential resi = residentialDao.findResidentialCode(name);
//            String bh="";
//            String hm = resi.getXqbm();
//            if ( resi !=null && !"".equals(resi.getXqbm()) ){
//                bh = hm;
//            }else {
//                bh="";
//            }
//            column.setColumn1(bh);
            //System.out.println(column.getColumn1()+">>"+column.getColumn2()+">>"+column.getColumn13());
            if (column.getColumn1()=="" ||column.getColumn1()==null||column.getColumn3().equals("身份证号") ||column.getColumn1().equals("1") ||  column.getColumn2()=="" || column.getColumn2()== null||
                    column.getColumn10()==""|| column.getColumn13()=="") {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }

    /**
     * 编码格式判断类
     * @param list
     * @return
     */
    public List<ExcelColumnInfo> areaCodeServiceSupp(List<ExcelColumnInfo> list) {
        List<ExcelColumnInfo> errorMsg = new ArrayList<ExcelColumnInfo>();
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            ExcelColumnInfo column = list.get(i);
            if (column.getColumn() == "" ||column.getColumn() == "派出所" ||  column.getColumn1()=="") {
                errorMsg.add(column);
                list.remove(i);
                --i;
                len = list.size();
            }
        }
        return errorMsg;
    }
}
