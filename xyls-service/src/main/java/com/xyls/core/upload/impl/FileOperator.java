package com.xyls.core.upload.impl;

import com.xyls.core.properties.SecurityProperties;
import com.xyls.core.support.FileResponse;
import com.xyls.core.upload.FileUploadException;
import com.xyls.core.upload.FileUploadProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class FileOperator implements FileUploadProcessor {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public FileResponse transfer(MultipartFile multipartFile, String type) {

        if (multipartFile.isEmpty()) {
            throw new FileUploadException("参数异常后端并没有受到文件信息！请刷新重新上传！");
        }


        if (StringUtils.isEmpty(securityProperties.getFile().getFileDir())) {
            throw new FileUploadException("请配置上传的文件路径[xyls.secuirty.file.fileDir] [默认的传输文件大小3M如需要请修改相应的配置]");
        }


        if (multipartFile.getSize() / (1024 * 1024 * 1024) > securityProperties.getFile().getFileSize()) {
            throw new FileUploadException("当前上传的文件超过配置文件的大小[" + securityProperties.getFile().getFileSize() + "] 如需要请重新配置");
        }

        String filePath = securityProperties.getFile().getFileDir();

        File canWrite = new File(filePath);

        //检查目录写权限
        if (!canWrite.canWrite()) {
            throw new FileUploadException("系统的当前配置没有写的权限 请放宽权限！");
        }

        //前端传来的参数 目前暂时不做通用处理
        String dirName = type;

        String saveUrl = "";

        filePath += dirName + "/";

        File saveDirFile = new File(filePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        saveUrl += dirName + "/";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        filePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(filePath);


        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        //重构文件名称
        String originName = multipartFile.getOriginalFilename();

        String extendName = originName.substring(originName.lastIndexOf("."));

        String fileName = String.valueOf(System.currentTimeMillis()).concat(extendName);


        //构建文件路径对象
        File file = new File(filePath, fileName);

        try {

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

            throw new FileUploadException(e.getMessage());

        }

        return new FileResponse(securityProperties.getFile().getUrl().concat(saveUrl).concat(fileName), multipartFile.getSize(), securityProperties.getFile().getFileDir());
    }

    @Override
    public void download(ServletWebRequest request) {
        String fileName = null;
        try {

            fileName = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "fileName");

        } catch (ServletRequestBindingException e) {
            throw new FileUploadException("下载的文件名不存在！请配置[xyls.security.file.fileName] 必须传值fileName字段");
        }

        HttpServletResponse response = request.getResponse();
        try (InputStream inputStream = new FileInputStream(new File(securityProperties.getFile().getFileDir(), fileName));
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileUploadException("你下载的文件已过期！" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadException("未知异常！" + e.getMessage());
        }
    }
}
