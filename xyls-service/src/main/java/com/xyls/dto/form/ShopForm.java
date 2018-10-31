package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ShopForm {

    private String id;

    @NotEmpty(message = "门店名称不能为空！")
    private String name;

    private String mobilePhone;

    private String telephone;

    @NotEmpty(message = "门店所在省不能为空！")
    private String province;

    @NotEmpty(message = "门店所在市不能为空！")
    private String city;

    @NotEmpty(message = "门店所在区不能为空！")
    private String area;

    @NotEmpty(message = "门店所在街道不能为空！")
    private String street;

    @NotEmpty(message = "门店所在坐标x不能为空！")
    private String positionX;

    @NotEmpty(message = "门店所在坐标y不能为空！")
    private String positionY;

    @NotEmpty(message = "门店描述不能为空！")
    private String description;

    /**
     * 0、上门服务
     * 其他 暂不上门
     */
    @NotEmpty(message = "门店是否上门服务不能为空！")
    private String goOut;

    /**
     * 门店图片展示用“，”分开
     */
    @NotEmpty(message = "门店图片展示不能为空！")
    private String picture;
}
