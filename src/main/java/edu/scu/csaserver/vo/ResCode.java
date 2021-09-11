package edu.scu.csaserver.vo;


/**
 * 响应状态码
 */
public enum ResCode {
    // success resultcode
    // 根据业务逻辑声明一些常用的状态码
    SUCCESS(200, "请求成功"),
    DELETE_SUCCESS(205, "删除成功"),
    DELETE_FAIL(405, "删除失败"),
    FAIL(400, "请求失败");
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
