package com.xyls.exception;
/**
 * 用户填写表单不合法
 */
public class UserFormException extends UserException {



    public UserFormException(String code, String msg){
        super();
        this.code=code;
        this.message=msg;
    }
}
