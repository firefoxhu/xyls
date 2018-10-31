package com.xyls.dto.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String userName;

    private String userNickName;

    private String userPersonalSignature;

    private String userHeaderImage;

    private String userProvice;

    private String userCity;

    private String userStreet;

    private String phone;

    private String email;

    private String sex;

    private String age;

    private String birthday;

    private String status;

    //
    private String userCenIntegral = "0";

    private String userCenLevel;

    //
    private boolean sign;
}
