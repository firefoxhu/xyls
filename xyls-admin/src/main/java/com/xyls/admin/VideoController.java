package com.xyls.admin;

import com.xyls.core.support.SimpleResponse;
import com.xyls.core.upload.impl.FileOperator;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.form.VideoForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.NewsClassService;
import com.xyls.service.NewsTypeService;
import com.xyls.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/video/")
@Menu(name = "视频管理", uri = "/admin/video/", isParent = true, type = MenuType.CONTROLLER, description = "视频管理控制器")
public class VideoController {


    @Autowired
    private VideoService videoService;

    @Autowired
    private NewsTypeService newsTypeService;


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NewsClassService newsClassService;

    @GetMapping("page/list")
    @Menu(name = "视频列表页", uri = "page/list", type = MenuType.MAIN_PAGE, description = "视频列表页")
    public String list() {
        return "admin/video/list";
    }

    @GetMapping("page/add")
    @Menu(name = "视频添加页", uri = "page/add", type = MenuType.SUB_PAGE, description = "视频添加页")
    public String add(Model model, String classId) {
        model.addAttribute("type", newsTypeService.findCategoryByClassId(classId));
        model.addAttribute("class", newsClassService.queryAll());
        return "admin/video/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "视频编辑页", uri = "page/edit", type = MenuType.SUB_PAGE, description = "视频编辑页")
    public String edit(Model model, String id, String classId) {
        model.addAttribute("type", newsTypeService.findCategoryByClassId(classId));
        model.addAttribute("class", newsClassService.queryAll());
        model.addAttribute("data", videoService.findOne(id));
        return "admin/video/edit";
    }

    @GetMapping("list")
    @ResponseBody
    @Menu(name = "视频列表数据", uri = "list", type = MenuType.DATA, description = "视频列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, String title) {
        try {
            return videoService.list(page - 1, limit, title);
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }

    @PostMapping("add")
    @ResponseBody
    @Menu(name = "视频添加", uri = "add", type = MenuType.BUTTON, description = "视频添加按钮")
    public ServerResponse add(@Valid VideoForm videoForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            videoService.save(videoForm, userInfo.getUsername());
            return ServerResponse.success("视频添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "视频编辑", uri = "edit", type = MenuType.BUTTON, description = "视频编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid VideoForm videoForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            videoService.modify(videoForm, userInfo.getUsername());
            return ServerResponse.success("视频修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "视频删除", uri = "remove", type = MenuType.BUTTON, description = "视频删除按钮")
    public ServerResponse remove(String ids) {
        try {
            videoService.remove(ids);
            return ServerResponse.success("删除视频成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }

    @PostMapping("upload")
    @ResponseBody
    @Menu(name = "视频上传", uri = "upload", type = MenuType.BUTTON, description = "视频按钮")
    public SimpleResponse upload(@RequestParam("file") MultipartFile file) {
        try {
            return SimpleResponse.success(fileOperator.transfer(file, "video"));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.failure(e.getMessage());
        }
    }

    @Autowired
    private FileOperator fileOperator;

}
