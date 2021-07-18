package edu.scu.csaserver.vo;


/**
 * 响应状态码
 */
public enum ResCode {
    // success resultcode
    // 根据业务逻辑声明一些常用的状态码
    SUCCESS(200, "成功"),
    FAIL(400, "失败");
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
