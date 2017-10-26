package com.goodsoft.society_zy.util;

import com.goodsoft.society_zy.domain.entity.param.ExcelColumnInfo;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * description:
 * ===>超大excel数据读取工具类（xls）
 *
 * @author 严彬荣 Created on 2017-10-25 15:44
 * @version V1.0
 */
@SuppressWarnings("ALL")
public class ReadExcelXlsUtil implements HSSFListener {
    /**
     * 创建ReadExcelXlsxUtil类的单例（详情见本包下UUIDUtil类） start
     **/
    private volatile static ReadExcelXlsUtil instance;

    private ReadExcelXlsUtil() {
    }

    public static ReadExcelXlsUtil getInstance() {
        if (instance == null) {
            synchronized (ReadExcelXlsUtil.class) {
                if (instance == null)
                    instance = new ReadExcelXlsUtil();
            }
        }
        return instance;
        //创建ReadExcelXlsxUtil类的单例（详情见本包下UUIDUtil类） start
    }

    private int minColumns = -1;
    private POIFSFileSystem fs;
    private int lastRowNumber;
    private int lastColumnNumber;
    /**
     * Should we output the formula, or the value it has?
     */
    private boolean outputFormulaValues = true;
    /**
     * For parsing Formulas
     */
    private SheetRecordCollectingListener workbookBuildingListener;
    // excel2003工作薄
    private HSSFWorkbook stubWorkbook;
    // Records we pick up as we process
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;
    // 表索引
    private int sheetIndex = 0;
    private BoundSheetRecord[] orderedBSRs;
    private ArrayList boundSheetRecords = new ArrayList();
    // For handling formulas with string results
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    // 当前行
    private int curRow = 0;
    // 存储行记录的容器
    private List<String> rowlist = new ArrayList<String>();
    //封装数据容器
    public List<ExcelColumnInfo> list = new ArrayList<ExcelColumnInfo>();
    private String sheetName;
    private String str = "";

    /**
     * 遍历excel下所有的sheet
     *
     * @throws IOException
     */
    public void process(String fileName) throws IOException {
        this.fs = new POIFSFileSystem(new FileInputStream(fileName));
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        factory.processWorkbookEvents(request, fs);
        this.fs.close();
    }

    /**
     * HSSFListener 监听方法，处理 Record
     */
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;
        String value = null;
        switch (record.getSid()) {
            case BoundSheetRecord.sid:
                boundSheetRecords.add(record);
                break;
            case BOFRecord.sid:
                BOFRecord br = (BOFRecord) record;
                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                    // 如果有需要，则建立子工作薄
                    if (workbookBuildingListener != null && stubWorkbook == null) {
                        stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                    }
                    if (orderedBSRs == null) {
                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                    }
                    sheetName = orderedBSRs[sheetIndex].getSheetname();
                }
                break;
            case SSTRecord.sid:
                sstRecord = (SSTRecord) record;
                break;
            case BlankRecord.sid:
                BlankRecord brec = (BlankRecord) record;
                thisRow = brec.getRow();
                thisColumn = brec.getColumn();
                thisStr = "";
                rowlist.add(thisColumn, thisStr);
                break;
            case BoolErrRecord.sid: // 单元格为布尔类型
                BoolErrRecord berec = (BoolErrRecord) record;
                thisRow = berec.getRow();
                thisColumn = berec.getColumn();
                thisStr = berec.getBooleanValue() + "";
                rowlist.add(thisColumn, thisStr);
                break;
            case FormulaRecord.sid: // 单元格为公式类型
                FormulaRecord frec = (FormulaRecord) record;
                thisRow = frec.getRow();
                thisColumn = frec.getColumn();
                if (outputFormulaValues) {
                    if (Double.isNaN(frec.getValue())) {
                        // Formula result is a string
                        // This is stored in the next record
                        outputNextStringRecord = true;
                        nextRow = frec.getRow();
                        nextColumn = frec.getColumn();
                    } else {
                        thisStr = formatListener.formatNumberDateCell(frec);
                    }
                } else {
                    thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                }
                rowlist.add(thisColumn, thisStr);
                break;
            case StringRecord.sid:// 单元格中公式的字符串
                if (outputNextStringRecord) {
                    // String for formula
                    StringRecord srec = (StringRecord) record;
                    thisStr = srec.getString();
                    thisRow = nextRow;
                    thisColumn = nextColumn;
                    outputNextStringRecord = false;
                }
                break;
            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;
                curRow = thisRow = lrec.getRow();
                thisColumn = lrec.getColumn();
                value = lrec.getValue().trim();
                value = value.equals("") ? " " : value;
                this.rowlist.add(thisColumn, value);
                break;
            case LabelSSTRecord.sid: // 单元格为字符串类型
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                curRow = thisRow = lsrec.getRow();
                thisColumn = lsrec.getColumn();
                if (sstRecord == null) {
                    rowlist.add(thisColumn, " ");
                } else {
                    value = sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
                    value = value.equals("") ? " " : value;
                    rowlist.add(thisColumn, value);
                }
                break;
            case NumberRecord.sid: // 单元格为数字类型
                NumberRecord numrec = (NumberRecord) record;
                curRow = thisRow = numrec.getRow();
                thisColumn = numrec.getColumn();
                value = formatListener.formatNumberDateCell(numrec).trim();
                value = value.equals("") ? " " : value;
                // 向容器加入列值
                rowlist.add(thisColumn, value);
                break;
            default:
                break;
        }
        // 遇到新行的操作
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }
        // 空值的操作
        if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
            curRow = thisRow = mc.getRow();
            thisColumn = mc.getColumn();
            rowlist.add(thisColumn, " ");
        }
        // 更新行和列的值
        if (thisRow > -1)
            lastRowNumber = thisRow;
        if (thisColumn > -1)
            lastColumnNumber = thisColumn;
        // 行结束时的操作
        if (record instanceof LastCellOfRowDummyRecord) {
            if (minColumns > 0) {
                // 列值重新置空
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
            }
            lastColumnNumber = -1;
            // 每行结束时， 调用getRows() 方法
            getRows(sheetIndex, curRow, rowlist);
            // 清空容器
            rowlist.clear();
        }
    }

    /**
     * 获取行数据回调
     *
     * @param sheetIndex
     * @param curRow
     * @param rowList
     */
    private void getRows(int sheetIndex, int curRow, List<String> rowList) {
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
        if (!(o instanceof ReadExcelXlsUtil)) return false;
        ReadExcelXlsUtil that = (ReadExcelXlsUtil) o;
        return Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }
}