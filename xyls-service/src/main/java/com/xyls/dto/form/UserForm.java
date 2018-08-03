package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


@Data
public class UserForm {

    private String userId;

    @NotEmpty(message = "用户名称（真实名称）不能为空！")
    private String userName;

    private String userPassword;

    private String reUserPassword;

    @NotEmpty(message = "身份证号不能为空！")
    private String userCardNumber;

    @NotEmpty(message = "用户所属省不能为空！")
    private String userProvince;

    @NotEmpty(message = "用户所属市不能为空！")
    private String userCity;

    @NotEmpty(message = "用户所属区不能为空！")
    private String userArea;

    @NotEmpty(message = "用户街道详情不能为空")
    private String userStreet;

    @NotEmpty(message = "用户手机号不能为空")
    private String phone;

    private String userPersonalSignature;

    private String userHeaderImage;

    private String email;

    private String sex;

    private String age;

    private String birthday;

    private String status;

    private String roles;

    private String passwordInvalid;

    private String userNickName;

}
