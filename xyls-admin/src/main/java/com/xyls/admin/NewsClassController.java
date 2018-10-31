package com.xyls.admin;

import com.xyls.dto.form.NewsClassForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.model.NewsClass;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.NewsClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/newsClass/")
@Menu(name = "新闻栏目", uri = "/admin/newsClass/", isParent = true, type = MenuType.CONTROLLER, description = "新闻栏目控制器")
public class NewsClassController {

    @Autowired
    private NewsClassService newsClassService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("page/list")
    @Menu(name = "新闻栏目列表页", uri = "page/list", type = MenuType.MAIN_PAGE, description = "新闻栏目列表页")
    public String list() {
        return "admin/newsClass/list";
    }

    @GetMapping("page/add")
    @Menu(name = "新闻栏目添加页", uri = "page/add", type = MenuType.SUB_PAGE, description = "新闻栏目添加页")
    public String add() {
        return "admin/newsClass/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "新闻栏目编辑页", uri = "page/edit", type = MenuType.SUB_PAGE, description = "新闻栏目编辑页")
    public String edit(Model model, String id) {
        model.addAttribute("newsClass", newsClassService.query(id));
        return "admin/newsClass/edit";
    }


    @GetMapping("list")
    @ResponseBody
    @Menu(name = "新闻栏目列表数据", uri = "list", type = MenuType.DATA, description = "新闻栏目列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = new PageRequest(page - 1, limit);
            Page<NewsClass> newsClassPage = newsClassService.query(pageRequest);
            return new ResultGrid(0, "", Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())), newsClassPage.getContent());
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "新闻栏目添加", uri = "add", type = MenuType.BUTTON, description = "新闻栏目添加按钮")
    public ServerResponse add(@Valid NewsClassForm newsClassForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            newsClassService.save(newsClassForm, userInfo.getUsername());
            return ServerResponse.success("栏目添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "新闻栏目编辑", uri = "edit", type = MenuType.BUTTON, description = "新闻栏目编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid NewsClassForm newsClassForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            newsClassService.modify(newsClassForm, userInfo.getUsername());
            return ServerResponse.success("栏目修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "新闻栏目删除", uri = "remove", type = MenuType.BUTTON, description = "新闻栏目删除按钮")
    public ServerResponse remove(String ids) {
        try {
            newsClassService.remove(ids);
            return ServerResponse.success("栏目删除成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{newsClsId}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "newsClsId") String id) {

        try {
            return ServerResponse.success(newsClassService.query(id));
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }


    }


    @GetMapping("all")
    public ServerResponse all() {
        try {
            return ServerResponse.success(newsClassService.queryAll());
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }
}
