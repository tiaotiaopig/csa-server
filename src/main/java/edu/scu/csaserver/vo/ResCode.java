package edu.scu.csaserver.vo;


/**
 * 响应状态码
 */
public enum ResCode {
    // success resultcode
    // 根据业务逻辑声明一些常用的状态码
    SUCCESS(200, "操作成功"),
    DELETE_SUCCESS(205, "删除成功"),
    ANALYZE_EMPTY_DATA(206,"分析成功，不存在当前协议数据或协议内容为空"),
    WAIT_A_MOMENT(301,"正在分析数据，请稍后再试"),
    FAIL(400, "请求失败"),
    UPLOAD_FAIL(401,"上传失败，请检查文件参数"),
    FILE_NOT_SUPPORT(402,"文件类型不支持，请选择正确的格式"),
    DIR_NOT_EXIST(403,"操作目录不存在，请联系管理员"),
    DELETE_FAIL(405, "删除失败"),
    FILE_NOT_EXIST(406,"文件不存在"),
    PROTOCOL_NOT_SUPPORT(407,"协议不支持"),
    PROTOCOL_NOT_EXIST(408,"不存在该协议信息"),
    ANALYZE_FAILED(409,"分析出错，请联系管理员");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String msg;

    ResCode (Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
