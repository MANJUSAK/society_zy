package com.goodsoft.society_zy.util;


import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * function 写入数据到excel表工具类
 * Created by 严彬荣 on 2017/9/27.
 * version v1.0
 */
@SuppressWarnings("ALL")
public class WriteExcelUtil {
    /**
     * 创建Excel2007类的单例（详情见本包下UUIDUtil类） start
     **/
    private volatile static WriteExcelUtil instance;

    private WriteExcelUtil() {
    }

    public static WriteExcelUtil getInstance() {
        if (instance == null) {
            synchronized (WriteExcelUtil.class) {
                if (instance == null)
                    instance = new WriteExcelUtil();
            }
        }
        return instance;
    }

    /**
     * 创建excel表格
     * 将表单数据写入excel表格中
     *
     * @param list 写入到表格数据
     * @return XSSFWorkbook
     */
    protected XSSFWorkbook createExcel(XSSFWorkbook wb, List list) {
        return this.createHeaderStyle(wb, list);
    }

    /**
     * 创建excel表头样式表格
     *
     * @param list 写入到表格数据
     * @return XSSFWorkbook
     */
    private XSSFWorkbook createHeaderStyle(XSSFWorkbook wb, List list) {
        XSSFCellStyle style = wb.createCellStyle();
        //水平居中
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //设置单元格背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        //设置上下左右边框
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 自动换行
        style.setWrapText(true);
        XSSFFont font = wb.createFont();
        // 设置字体颜色
        font.setColor(HSSFColor.BLACK.index);
        // 设置字体样式
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 设置字体粗细
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式中
        style.setFont(font);
        return this.writeHeaderCellExcel(wb, style, list);
    }


    /**
     * 创建excel表头单元格
     * 将表单数据写入excel表格中
     *
     * @param wb
     * @param style
     * @param list  导出数据
     * @param type  导出类型
     * @return XSSFWorkbook
     */
    private XSSFWorkbook writeHeaderCellExcel(XSSFWorkbook wb, XSSFCellStyle style, List list) {
        List data = new ArrayList();
        int size = list.size();
        XSSFSheet sheet = null;
        CellRangeAddress cra = null;
        //判断数据是否超过一万条
        if (size > 10000) {
            //算出sheet表数
            int i = (size + 10000) / 10000;
            for (int k = 1; k <= i; ++k) {
                //判断是否为最后一张sheet表
                if (k < i) {
                    //截取写入到分表的数据
                    data = list.subList(((k - 1) * 10000), k * 10000);
                    //创建一张excel表
                    sheet = wb.createSheet("当前导入数据错误信息表" + k);
                    //合并单元格（第一行，第1-20列）
                    cra = new CellRangeAddress(0, 0, 0, 10);
                    //在sheet里增加合并单元格
                    sheet.addMergedRegion(cra);
                    //获取第一行 写入标题名称
                    XSSFRow row = sheet.createRow(0);
                    //设置列宽
                    sheet.setDefaultColumnWidth(35);
                    //创建表头
                    row = sheet.createRow(0);
                    //设置行高
                    row.setHeight((short) (16 * 25));
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue("当前导入数据错误信息");
                    cell.setCellStyle(style);
                    /*writeHeaderCell(sheet, cell, style);*/
                    wb = this.createCellStyle(wb, sheet, data);
                } else {
                    data = list.subList(((k - 1) * 10000), size);
                    //创建一张excel表
                    sheet = wb.createSheet("当前导入数据错误信息表" + k);
                    cra = new CellRangeAddress(0, 0, 0, 10);
                    //在sheet里增加合并单元格
                    sheet.addMergedRegion(cra);
                    XSSFRow row = sheet.createRow(0);
                    //设置列宽
                    sheet.setDefaultColumnWidth(35);
                    //创建表头
                    row = sheet.createRow(0);
                    //设置行高
                    row.setHeight((short) (16 * 25));
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue("当前导入数据错误信息");
                    cell.setCellStyle(style);
                  /* writeHeaderCell(sheet, cell, style);*/
                    wb = this.createCellStyle(wb, sheet, data);
                }
            }
            return wb;
        } else {
            //创建一张excel表
            sheet = wb.createSheet("当前导入数据错误信息表");
            cra = new CellRangeAddress(0, 0, 0, 10);
            //在sheet里增加合并单元格
            sheet.addMergedRegion(cra);
            XSSFRow row = sheet.createRow(0);
            //设置列宽
            sheet.setDefaultColumnWidth(35);
            //创建表头
            row = sheet.createRow(0);
            //设置行高
            row.setHeight((short) (16 * 25));
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("当前导入数据错误信息表");
            cell.setCellStyle(style);
            //写入字段名称
            /*writeHeaderCell(sheet, cell, style);*/
            return this.createCellStyle(wb, sheet, list);
        }
    }

   /* */

    /**
     * excel表头信息
     *
     * @param sheet
     * @param cell
     * @param style
     */
   /* private void writeHeaderCell(XSSFSheet sheet, XSSFCell cell, XSSFCellStyle style) {
        XSSFRow row = sheet.createRow(1);
        for (int i = 0; i < 41; ++i) {
            cell = row.createCell(i);
            cell.setCellValue("column" + (i + 1));
            cell.setCellStyle(style);
        }
    }*/

    /**
     * 创建excel单元格样式
     * 将表单数据写入excel表格中
     *
     * @param wb    表属性
     * @param sheet 表
     * @param list  写入到表格数据
     * @return XSSFWorkbook
     */
    private XSSFWorkbook createCellStyle(XSSFWorkbook wb, XSSFSheet sheet, List list) {
        XSSFCellStyle style = wb.createCellStyle();
        //水平居中
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //设置上下左右边框
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        //设置自动换行
        //style.setWrapText(true);
        Font font = wb.createFont();
        // 设置字体颜色
        font.setColor(HSSFColor.BLACK.index);
        // 设置字体样式
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 9);
        // 设置字体粗细
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式中
        style.setFont(font);
        return this.nursery(wb, sheet, style, list);
    }

    /**
     * 创建excel单元格内容
     * 将表单数据写入excel表格中
     *
     * @param wb    表属性
     * @param sheet 表
     * @param style 表样式
     * @param list  写入到表格数据
     * @return XSSFWorkbook
     */
    private XSSFWorkbook nursery(XSSFWorkbook wb, XSSFSheet sheet, XSSFCellStyle style, List list) {
        XSSFCell cell = null;

        for (int i = 0, length = list.size(); i < length; ++i) {
            XSSFRow row = sheet.createRow(1 + i);
            ExcelColumnInfo data = (ExcelColumnInfo) list.get(i);
            //写入数据到sheet表中
            writeData(row, cell, style, data);
        }
        return wb;
    }

    /**
     * 写入数据到excel表中
     *
     * @param row
     * @param cell
     * @param style
     * @param data  导出数据
     */
    private void writeData(XSSFRow row, XSSFCell cell, XSSFCellStyle style, ExcelColumnInfo data) {
        StringBuilder sb = new StringBuilder();
        // 创建表内容单元格
        cell = row.createCell(0);
        cell.setCellValue(data.getColumn());
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(data.getColumn1());
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue(data.getColumn2());
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue(data.getColumn3());
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue(data.getColumn4());
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue(data.getColumn5());
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue(data.getColumn6());
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue(data.getColumn7());
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue(data.getColumn8());
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue(data.getColumn9());
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue(data.getColumn10());
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue(data.getColumn11());
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue(data.getColumn12());
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue(data.getColumn13());
        cell.setCellStyle(style);
        cell = row.createCell(14);
        cell.setCellValue(data.getColumn14());
        cell.setCellStyle(style);
        cell = row.createCell(15);
        cell.setCellValue(data.getColumn15());
        cell.setCellStyle(style);
        cell = row.createCell(16);
        cell.setCellValue(data.getColumn16());
        cell.setCellStyle(style);
        cell = row.createCell(17);
        cell.setCellValue(data.getColumn17());
        cell.setCellStyle(style);
        cell = row.createCell(18);
        cell.setCellValue(data.getColumn18());
        cell.setCellStyle(style);
        cell = row.createCell(19);
        cell.setCellValue(data.getColumn19());
        cell.setCellStyle(style);
        cell = row.createCell(20);
        cell.setCellValue(data.getColumn20());
        cell.setCellStyle(style);
        cell = row.createCell(21);
        cell.setCellValue(data.getColumn21());
        cell.setCellStyle(style);
        cell = row.createCell(22);
        cell.setCellValue(data.getColumn22());
        cell.setCellStyle(style);
        cell = row.createCell(23);
        cell.setCellValue(data.getColumn23());
        cell.setCellStyle(style);
        cell = row.createCell(24);
        cell.setCellValue(data.getColumn24());
        cell.setCellStyle(style);
        cell = row.createCell(25);
        cell.setCellValue(data.getColumn25());
        cell.setCellStyle(style);
        cell = row.createCell(26);
        cell.setCellValue(data.getColumn26());
        cell.setCellStyle(style);
        cell = row.createCell(27);
        cell.setCellValue(data.getColumn27());
        cell.setCellStyle(style);
        cell = row.createCell(28);
        cell.setCellValue(data.getColumn28());
        cell.setCellStyle(style);
        cell = row.createCell(29);
        cell.setCellValue(data.getColumn29());
        cell.setCellStyle(style);
        cell = row.createCell(30);
        cell.setCellValue(data.getColumn30());
        cell.setCellStyle(style);
        cell = row.createCell(31);
        cell.setCellValue(data.getColumn31());
        cell.setCellStyle(style);
        cell = row.createCell(32);
        cell.setCellValue(data.getColumn32());
        cell.setCellStyle(style);
        cell = row.createCell(33);
        cell.setCellValue(data.getColumn33());
        cell.setCellStyle(style);
        cell = row.createCell(34);
        cell.setCellValue(data.getColumn34());
        cell.setCellStyle(style);
        cell = row.createCell(35);
        cell.setCellValue(data.getColumn35());
        cell.setCellStyle(style);
        cell = row.createCell(36);
        cell.setCellValue(data.getColumn36());
        cell.setCellStyle(style);
        cell = row.createCell(37);
        cell.setCellValue(data.getColumn37());
        cell.setCellStyle(style);
        cell = row.createCell(38);
        cell.setCellValue(data.getColumn38());
        cell.setCellStyle(style);
        cell = row.createCell(39);
        cell.setCellValue(data.getColumn39());
        cell.setCellStyle(style);
        cell = row.createCell(40);
        cell.setCellValue(data.getColumn40());
        cell.setCellStyle(style);
    }


}
