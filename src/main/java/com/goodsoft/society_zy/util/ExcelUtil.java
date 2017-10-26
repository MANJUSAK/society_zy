package com.goodsoft.society_zy.util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * function 苗圃Excel表格工具类
 * Created by 严彬荣 on 2017/9/4.
 * version v1.0
 */
@SuppressWarnings("ALL")
public class ExcelUtil {
    /**
     * 创建ExcelUtil类的单例（详情见本包下UUIDUtil类） start
     **/
    private volatile static ExcelUtil instance;

    private ExcelUtil() {
    }

    public static ExcelUtil getInstance() {
        if (instance == null) {
            synchronized (ExcelUtil.class) {
                if (instance == null)
                    instance = new ExcelUtil();
            }
        }
        return instance;
    }

    //实例化获取服务器系统标识工具类
    private GetOsNameUtil getOs = GetOsNameUtil.getInstance();
    //实例化创建excel工具类
    private WriteExcelUtil writeExcel = WriteExcelUtil.getInstance();
    //实例化UUID工具类
    private UUIDUtil uuid = UUIDUtil.getInstance();

    /**
     * @param list
     * @param path
     * @return excel路径地址
     * @throws Exception
     */
    public String writeExcel(List list) throws Exception {
        boolean bl = this.getOs.getOsName();
        StringBuilder sb = new StringBuilder();
        if (bl) {
            sb.append("/usr/society_zy");
        } else {
            sb.append("D:/society_zy");
        }
        sb.append("/sofile/");
        File folder = new File(sb.toString());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = this.uuid.getUUID().toString() + ".xlsx";
        String path = "/sofile/" + fileName;
        sb.append(fileName);
        File file = new File(sb.toString());
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            file.createNewFile();
        }
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook();
            this.writeExcel.createExcel(wb, list).write(new BufferedOutputStream(fileOut));
        } finally {
            fileOut.close();
        }
        return path;
    }
}
