package com.xyls.rbac.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ls_user")
public class User implements Serializable{

    @Id
    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String userOpenId;

    @Column(length = 8)
    private String userAccountId;

    @Column(length = 16)
    private String userName;

    @Column(length = 32)
    private String userNickName;

    @Column(length = 64)
    private String userPassword;

    @Column(length = 18)
    private String userCardNumber;

    @Column(length = 512)
    private String userPersonalSignature;

    @Column(length = 64)
    private String userHeaderImage;

    @Column(length = 8)
    private String userProvince;

    @Column(length = 8)
    private String userCity;

    @Column(length = 8)
    private String userArea;

    @Column(length = 8)
    private String userStreet;

    @Column(length = 11)
    private String phone;

    @Column(length = 16)
    private String email;

    @Column(length = 2)
    private String sex;

    @Column(length = 32)
    private String age;

    @Column(length = 32)
    private String birthday;

    @Column(length = 1)
    private String status;

    @Column(length = 32)
    private String createPerson;

    @Column(length = 32)
    private String createTime;

    @Column(length = 32)
    private String createDescription;

    @Column(length = 32)
    private String modifyDescription;

    @Column(length = 32)
    private String modifyPerson;

    @Column(length = 32)
    private String modifyTime;

}