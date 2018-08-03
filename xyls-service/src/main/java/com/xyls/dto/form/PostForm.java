package com.xyls.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    private String  pid;

    private String content;

    private String  pic;

    private String  concat;

    private String ip;

    private String top;

    private String status;

    private String createDate;
}
