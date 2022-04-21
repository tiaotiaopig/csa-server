package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务器给前端响应的通用模板
 * @author Lifeng
 * @date 2021/7/10 15:11
 */
@Data
public class Res<T> implements Serializable {
    private static final long serialVersionUID = 2570727641048946088L;
    /**
     * 响应状态码
     */
    @ApiModelProperty("响应状态码")
    private Integer code;
    /**
     * 响应信息
     */
    @ApiModelProperty("响应信息")
    private String msg;
    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    public Res(){}

    public Res(ResCode resCode) {
        this.code = resCode.getCode();
        this.msg = resCode.getMsg();
    }

    public Res(Integer code, String msg){
        this.msg = msg;
        this.code = code;
    }

    public Res(Integer code, String msg, T data){
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * 对成功的响应进行统一的封装
     * @param data 请求成功时响应的数据
     * @param <T>
     * @return
     */
    public static <T> Res<T> success(T data) {
        Res<T> res = new Res<>(ResCode.SUCCESS);
        res.setData(data);
        return res;
    }
//
//    public static <T> Res<T> success(ResCode resCode, T data) {
//        Res<T> res = new Res<>(resCode);
//        res.setData(data);
//        return res;
//    }
//
    public static <T> Res<T> fail(Integer code, String msg) {
        return new Res<>(code, msg);
    }

    public static <T> Res<T> genRes(ResCode code){
        return new Res<>(code);
    }
    public static <T> Res<T> genResWithData(ResCode code,T data){
        return new Res<>(code.getCode(),code.getMsg(),data);
    }
}
