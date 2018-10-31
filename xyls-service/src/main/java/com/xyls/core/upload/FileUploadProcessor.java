package com.xyls.core.upload;

import com.xyls.core.support.FileResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadProcessor {


    FileResponse transfer(MultipartFile multipartFile, String type) throws FileUploadException;

    void download(ServletWebRequest request);


}
