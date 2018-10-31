package com.xyls.dto.support;

import com.xyls.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//保证序列化json的时候,如果是null的对象,key也会消失
@Data
@AllArgsConstructor
public class ServerResponse<T> implements Serializable {

    private String code;
    private String msg;
    private T data;


    public static <T> ServerResponse<T> success(String msg) {
        return new ServerResponse<T>(ResponseEnum.SYSTEM_STATUS.getCode(), msg, null);
    }

    public static <T> ServerResponse<T> success(String code, String msg) {
        return new ServerResponse<T>(code, msg, null);
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<T>(ResponseEnum.SYSTEM_STATUS.getCode(), ResponseEnum.SYSTEM_STATUS.getDesc(), data);
    }

    public static <T> ServerResponse<T> success(ResponseEnum responseEnum, T data) {
        return new ServerResponse<T>(responseEnum.getCode(), responseEnum.getDesc(), data);
    }

    public static <T> ServerResponse<T> success(ResponseEnum responseEnum) {
        return new ServerResponse<T>(responseEnum.getCode(), responseEnum.getDesc(), null);
    }

    public static <T> ServerResponse<T> success(String code, String msg, T data) {
        return new ServerResponse<T>(code, msg, data);
    }


    public static <T> ServerResponse<T> fail(String msg) {
        return new ServerResponse<T>(ResponseEnum.SYSTEM_ERROR.getCode(), msg, null);
    }

    public static <T> ServerResponse<T> fail(String code, String msg) {
        return new ServerResponse<T>(code, msg, null);
    }

    public static <T> ServerResponse<T> fail(T data) {
        return new ServerResponse<T>(ResponseEnum.SYSTEM_ERROR.getCode(), ResponseEnum.SYSTEM_ERROR.getDesc(), data);
    }

    public static <T> ServerResponse<T> fail(ResponseEnum responseEnum, T data) {
        return new ServerResponse<T>(responseEnum.getCode(), responseEnum.getDesc(), data);
    }

    public static <T> ServerResponse<T> fail(ResponseEnum responseEnum) {
        return new ServerResponse<T>(responseEnum.getCode(), responseEnum.getDesc(), null);
    }

    public static <T> ServerResponse<T> fail(String code, String msg, T data) {
        return new ServerResponse<T>(code, msg, data);
    }

}

