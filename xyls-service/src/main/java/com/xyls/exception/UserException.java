package com.xyls.exception;
import lombok.Data;

@Data
public class UserException extends RuntimeException {

    public String code;

    public String message;

    public UserException(){
        super();
    }
}
