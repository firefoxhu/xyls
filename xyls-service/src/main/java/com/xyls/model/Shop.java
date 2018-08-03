package com.xyls.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ls_shop")
public class Shop {

    /**
     *新闻id
     */
    @Id
    @Column(length = 32)
    private String id;

    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String name;

    @Column(length = 32)
    private String telephone;

    @Column(length = 8)
    private String province;

    @Column(length = 8)
    private String city;

    @Column(length = 8)
    private String area;

    @Column(length = 64)
    private String street;

    @Column(length = 16)
    private String positionX;

    @Column(length = 16)
    private String positionY;

    @Column(length = 256)
    private String description;

    /**
     * 0、上门服务
     * 其他 暂不上门
     */
    @Column(length = 1)
    private String goOut;

    /**
     * 门店图片展示用“，”分开
     */
    @Column(length = 128)
    private String picture;

    @Column(length = 1)
    private String status;

    @Column(length = 32)
    private String createPerson;

    @Column(length = 32)
    private String createTime;

    @Column(length = 32)
    private String createDescription;

    @Column(length = 32)
    private String modifyPerson;

    @Column(length = 32)
    private String modifyTime;

    @Column(length = 32)
    private String modifyDescription;

}
