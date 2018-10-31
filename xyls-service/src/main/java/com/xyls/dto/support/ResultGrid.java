package com.xyls.dto.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lihu on 2018/1/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultGrid {

    private int code;

    private String msg;

    private long count;


    private Object data;


}
