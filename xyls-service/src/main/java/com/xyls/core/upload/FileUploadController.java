package com.xyls.core.upload;

import com.xyls.core.support.SimpleResponse;
import com.xyls.core.upload.impl.FileOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file/")
public class FileUploadController {

    @Autowired
    private FileOperator fileOperator;

    @PostMapping("upload")
    public SimpleResponse upload(@RequestParam("file") MultipartFile file) {

        try {
            return SimpleResponse.success(fileOperator.transfer(file, "image"));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.failure(e.getMessage());
        }

    }


    @GetMapping("/download")
    public void download(HttpServletRequest request) {

        try {
            fileOperator.download(new ServletWebRequest(request));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
