package com.xyls.core.properties;

import lombok.Data;

@Data
public class FileProperties {

    private String fileDir;

    private String url;

    //3M
    private int fileSize = 3;


    private String fileName = "fileName";

}
