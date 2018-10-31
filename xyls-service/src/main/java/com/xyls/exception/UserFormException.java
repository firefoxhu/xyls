package com.xyls.exception;

import com.xyls.enums.ResponseEnum;

/**
 * 用户填写表单不合法
 */
public class UserFormException extends UserException {


    public UserFormException(String code, String msg) {
        super();
        this.code = code;
        this.message = msg;
    }

    public UserFormException(ResponseEnum responseEnum) {
        super();
        this.code = responseEnum.getCode();
        this.message =responseEnum.getDesc();
    }
}
