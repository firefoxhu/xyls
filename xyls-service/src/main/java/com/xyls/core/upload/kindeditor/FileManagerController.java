package com.xyls.core.upload.kindeditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyls.core.properties.SecurityProperties;
import com.xyls.core.upload.kindeditor.impl.NameComparator;
import com.xyls.core.upload.kindeditor.impl.SizeComparator;
import com.xyls.core.upload.kindeditor.impl.TypeComparator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/kindeditor/")
public class FileManagerController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    @GetMapping("kindeditor")
    public String index() {
        return "kindeditor";
    }


    @GetMapping("manager")
    public void manager(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        String rootPath = securityProperties.getFile().getFileDir();
        //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = securityProperties.getFile().getUrl();
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if (!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)) {
                out.println("Invalid Directory name.");
                return;
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        //排序形式，name or size or type
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        //目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            out.println("Directory does not exist.");
            return;
        }

        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        Map<String, Object> result = new HashedMap();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        response.setContentType("application/json; charset=UTF-8");
        out.println(objectMapper.writeValueAsString(result));
    }


    @PostMapping("upload")
    public void upload(MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response) throws IOException {


        PrintWriter out = response.getWriter();

        response.setContentType("text/html; charset=UTF-8");

        if (imgFile.isEmpty()) {
            out.println(getError("请选择文件!"));
            return;
        }

        //定义允许上传的文件扩展名
        Map<String, String> extMap = new HashMap();
        extMap.put("image", ".gif,.jpg,.jpeg,.png,.bmp");
        extMap.put("flash", ".swf,.flv");
        extMap.put("media", ".swf,.flv,.mp3,.wav,.wma,.wmv,.mid,.avi,.mpg,.asf,.rm,.rmvb");
        extMap.put("file", ".doc,.docx,.xls,.xlsx,.ppt,.htm,.html,.txt,.zip,.rar,.gz,.bz2");

        //最大文件大小
        long maxSize = 1000000;

        //文件保存目录路径
        String savePath = securityProperties.getFile().getFileDir();

        //文件保存目录URL
        String saveUrl = "";

        //检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            out.println(getError("上传目录不存在。【请配置好上传的文件主目录】"));
            return;
        }

        //检查目录写权限
        if (!uploadDir.canWrite()) {
            out.println(getError("上传目录没有写权限。"));
            return;
        }
        //前端传来的参数
        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        //匹配是否有image文件目录
        if (!extMap.containsKey(dirName)) {
            out.println(getError("目录名不正确。"));
            return;
        }

        //创建文件夹 根据当天时间创建一个文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }


        if (imgFile.getSize() > maxSize) {
            out.println(getError("上传文件大小超过限制。"));
            return;
        }
        //获取文件的源名称
        String originalName = imgFile.getOriginalFilename();
        //获取文件的后缀名
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        //判断后缀是否合法
        if (!Arrays.asList(extMap.get(dirName).split(",")).contains(suffix)) {
            out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
            return;
        }

        String fileName = String.valueOf(System.currentTimeMillis()).concat(suffix);

        File target = new File(savePath, fileName);

        try {
            imgFile.transferTo(target);
        } catch (IOException e) {
            e.printStackTrace();
            out.println(getError("上传文件失败。"));
            return;
        }

        Map<String, Object> obj = new HashedMap();
        obj.put("error", 0);
        obj.put("url", securityProperties.getFile().getUrl().concat(saveUrl) + fileName);
        out.println(objectMapper.writeValueAsString(obj));
    }


    private String getError(String message) {
        Map<String, Object> obj = new HashedMap();
        obj.put("error", 1);
        obj.put("message", message);
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
