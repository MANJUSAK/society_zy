package com.goodsoft.society_zy.domain.entity.result;

/**
 * function 系统响应状态提示枚举类
 * <p>
 * Created by 严彬荣 on 2017/7/24.
 * version v1.0
 */
public enum StatusEnum {
    SUCCESS(0, "数据清洗成功"),
    SUCCESS_MSG(0, "数据清洗成功，但未全部成功，为你找到的错误数据已导出到文件，请下载查看！"),
    SERVER_ERROR(500, "发生不可预知的错误"),
    EXCEL_ERROR(500, "数据清洗失败，可能原因是数据重复导致失败或其他未知错误"),
    EXCEL_NO_DATA(404, "解析excel表格成功，但不是有效数据"),
    NO_EXCEL(606, "excel表格文件数据为空"),
    DEFEAT(500, "失败"),
    UNKONW_ERROR(501, "未知错误"),
    ERROR(502, "错误操作"),
    NO_DATA(404, "无数据"),
    NO_EXCEL_DATA(404, "无可用数据导出"),
    NO_PDF_DATA(404, "无可用数据导出"),
    NO_URL(404, "无效请求"),
    NO_RIGHTS(401, "该用户无法操作此功能"),
    CHECKUSER(404, "用户名与密码不匹配"),
    USERNAME(402, "用户名已被注册"),
    USERTEL(402, "手机号已被注册"),
    CHECK_DATA(407, "请正确填写信息后重试"),
    FILE_UPLOAD(600, "文件上传失败，请重试"),
    FILE_SIZE(601, "文件大小不符合要求"),
    FILE_FORMAT(603, "文件格式不正确"),
    NO_FILE(604, "文件不能为空"),
    CHECKCODE(402, "验证码不正确"),
    NO_PRAM(400, "参数错误");


    private final int CODE;
    private final String EXPLAIN;

    StatusEnum(int CODE, String EXPLAIN) {
        this.CODE = CODE;
        this.EXPLAIN = EXPLAIN;
    }

    public int getCODE() {
        return CODE;
    }

    public String getEXPLAIN() {
        return EXPLAIN;
    }

}
