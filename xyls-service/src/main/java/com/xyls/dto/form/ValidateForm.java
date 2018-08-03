package com.xyls.dto.form;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Locale;

public class ValidateForm {

    public static void validate(BindingResult result, MessageSource messageSource){
        if(result.hasErrors()){
            StringBuffer msg=new StringBuffer();
            //获取错误文字集合
            List<FieldError> fieldErrors=result.getFieldErrors();
            //获取本地local,zh_CN
            Locale currentLocale= LocaleContextHolder.getLocale();
            //遍历错误字段信息
            for(FieldError fieldError:fieldErrors){
                //获取错误信息
                String errorMessage=messageSource.getMessage(fieldError,currentLocale);
                //添加到错误消息集合内
                msg.append(fieldError.getField()+":"+errorMessage).append("-");
                break;
            }

            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),StringUtils.substring(msg.toString(),0,msg.toString().length()-1));
        }


    }
}
