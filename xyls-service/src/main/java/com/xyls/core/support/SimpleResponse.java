/**
 *
 */
package com.xyls.core.support;

import lombok.Data;

/**
 * 简单响应的封装类
 *
 * @author zhailiang
 */
@Data
public class SimpleResponse {

    private String code;

    private String message;

    private Object data;


    public static SimpleResponse success(Object data) {
        return new SimpleResponse("OK", "请求成功！", data);
    }

    public static SimpleResponse success(String message) {
        return new SimpleResponse("T", message);
    }

    public static SimpleResponse success(String message, Object data) {
        return new SimpleResponse("T", message, data);
    }


    public static SimpleResponse failure(String message) {
        return new SimpleResponse("F", message);
    }

    public static SimpleResponse failure(String message, Object data) {
        return new SimpleResponse("F", message, data);
    }


    public SimpleResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public SimpleResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public SimpleResponse(Object data) {
        this.message = message;
    }
}
