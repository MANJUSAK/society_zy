package com.goodsoft.society_zy.util;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * function 超大excel数据读取工具类（xlsx）
 * Created by 严彬荣 on 2017/9/30.
 * version v1.0
 */
@SuppressWarnings("ALL")
public class ReadExcelXlsxUtil extends DefaultHandler {
    /**
     * 创建ReadExcelXlsxUtil类的单例（详情见本包下UUIDUtil类） start
     **/
    private volatile static ReadExcelXlsxUtil instance;

    private ReadExcelXlsxUtil() {
    }

    public static ReadExcelXlsxUtil getInstance() {
        if (instance == null) {
            synchronized (ReadExcelXlsxUtil.class) {
                if (instance == null)
                    instance = new ReadExcelXlsxUtil();
            }
        }
        return instance;
        //创建ReadExcelXlsxUtil类的单例（详情见本包下UUIDUtil类） start
    }

    // 共享字符串表
    private SharedStringsTable sst;
    // 上一次的内容
    private String lastContents;
    private boolean nextIsString;
    private int sheetIndex = -1;
    private List<String> rowList = new ArrayList<String>();
    public List<ExcelColumnInfo> list = new ArrayList<ExcelColumnInfo>();
    private String str = "";
    // 当前行
    private int curRow = 0;
    // 当前列
    private int curCol = 0;
    // 日期标志
    private boolean dateFlag;
    // 数字标志
    private boolean numberFlag;
    // 空值标志
    private boolean isTElement;

    /**
     * 遍历工作簿中所有的电子表格
     *
     * @param filename
     * @throws Exception
     */
    public void process(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        pkg.close();
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     *
     * @param filename
     * @param sheetId
     * @throws Exception
     */
    public void process(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if ("s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
            // 日期格式
            String cellDateType = attributes.getValue("s");
            if ("1".equals(cellDateType)) {
                dateFlag = true;
            } else {
                dateFlag = false;
            }
            String cellNumberType = attributes.getValue("s");
            if ("2".equals(cellNumberType)) {
                numberFlag = true;
            } else {
                numberFlag = false;
            }
        }
        // 当元素为t时
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = true;
        }
        // 置空
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {
            }
        }
        // t元素也包含字符串
        if (isTElement) {
            String value = lastContents.trim();
            rowList.add(curCol, value);
            curCol++;
            isTElement = false;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if ("v".equals(name)) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            try {
                // 日期格式处理
                if (dateFlag) {
                    Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    value = dateFormat.format(date);
                }
                // 数字类型处理
                if (numberFlag) {
                    BigDecimal bd = new BigDecimal(value);
                    value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
                }
            } catch (Exception e) {
                // 转换失败仍用读出来的值
                e.printStackTrace();
            }
            rowList.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 getRows() 方法
            if (name.equals("row")) {
                try {
                    getRows(sheetIndex + 1, curRow, rowList);
                } catch (Exception e) {
                }
                rowList.clear();
                curRow++;
                curCol = 0;
            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 获取行数据回调
     *
     * @param sheetIndex
     * @param curRow
     * @param rowList
     */
    public void getRows(int sheetIndex, int curRow, List<String> rowList) {
        ExcelColumnInfo data = new ExcelColumnInfo();
        for (int i = 0, len = rowList.size(); i < len; ++i) {
            this.str = rowList.get(i);
            switch (i) {
                case 0:
                    data.setColumn(this.str);
                    break;
                case 1:
                    data.setColumn1(this.str);
                    break;
                case 2:
                    data.setColumn2(this.str);
                    break;
                case 3:
                    data.setColumn3(this.str);
                    break;
                case 4:
                    data.setColumn4(this.str);
                    break;
                case 5:
                    data.setColumn5(this.str);
                    break;
                case 6:
                    data.setColumn6(this.str);
                    break;
                case 7:
                    data.setColumn7(this.str);
                    break;
                case 8:
                    data.setColumn8(this.str);
                    break;
                case 9:
                    data.setColumn9(this.str);
                    break;
                case 10:
                    data.setColumn10(this.str);
                    break;
                case 11:
                    data.setColumn11(this.str);
                    break;
                case 12:
                    data.setColumn12(this.str);
                    break;
                case 13:
                    data.setColumn13(this.str);
                    break;
                case 14:
                    data.setColumn14(this.str);
                    break;
                case 15:
                    data.setColumn15(this.str);
                    break;
                case 16:
                    data.setColumn16(this.str);
                    break;
                case 17:
                    data.setColumn17(this.str);
                    break;
                case 18:
                    data.setColumn18(this.str);
                    break;
                case 19:
                    data.setColumn19(this.str);
                    break;
                case 20:
                    data.setColumn20(this.str);
                    break;
                case 21:
                    data.setColumn21(this.str);
                    break;
                case 22:
                    data.setColumn22(this.str);
                    break;
                case 23:
                    data.setColumn23(this.str);
                    break;
                case 24:
                    data.setColumn24(this.str);
                    break;
                case 25:
                    data.setColumn25(this.str);
                    break;
                case 26:
                    data.setColumn26(this.str);
                    break;
                case 27:
                    data.setColumn27(this.str);
                    break;
                case 28:
                    data.setColumn28(this.str);
                    break;
                case 29:
                    data.setColumn29(this.str);
                    break;
                case 30:
                    data.setColumn30(this.str);
                    break;
                case 31:
                    data.setColumn31(this.str);
                    break;
                case 32:
                    data.setColumn32(this.str);
                    break;
                case 33:
                    data.setColumn33(this.str);
                    break;
                case 34:
                    data.setColumn34(this.str);
                    break;
                case 35:
                    data.setColumn35(this.str);
                    break;
                case 36:
                    data.setColumn36(this.str);
                    break;
                case 37:
                    data.setColumn37(this.str);
                    break;
                case 38:
                    data.setColumn38(this.str);
                    break;
                case 39:
                    data.setColumn39(this.str);
                    break;
                case 40:
                    data.setColumn40(this.str);
                    break;
                default:
                    break;
            }
        }
        list.add(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadExcelXlsxUtil)) return false;
        ReadExcelXlsxUtil that = (ReadExcelXlsxUtil) o;
        return Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }
}
