package com.xyls.admin;

import com.xyls.dto.form.NewsTypeForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.model.NewsType;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.NewsClassService;
import com.xyls.service.NewsTypeService;
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
@RequestMapping("/admin/newsType/")
@Menu(name = "新闻分类管理", uri = "/admin/newsType/", isParent = true, type = MenuType.CONTROLLER, description = "新闻分类管理控制器")
public class NewsTypeController {


    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NewsClassService newsClassService;


    @GetMapping("page/list")
    @Menu(name = "新闻分类列表页", uri = "page/list", type = MenuType.MAIN_PAGE, description = "新闻分类列表页")
    public String list() {
        return "admin/newsType/list";
    }

    @GetMapping("page/add")
    @Menu(name = "新闻分类添加页", uri = "page/add", type = MenuType.SUB_PAGE, description = "新闻分类添加页")
    public String add(Model model) {
        model.addAttribute("class", newsClassService.queryAll());
        return "admin/newsType/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "新闻分类编辑页", uri = "page/edit", type = MenuType.SUB_PAGE, description = "新闻分类编辑页")
    public String edit(Model model, String id) {
        model.addAttribute("class", newsClassService.queryAll());
        model.addAttribute("data", newsTypeService.query(id));
        return "admin/newsType/edit";
    }


    @GetMapping("list")
    @ResponseBody
    @Menu(name = "新闻分类列表数据", uri = "list", type = MenuType.DATA, description = "新闻分类列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = new PageRequest(page - 1, limit);
            Page<NewsType> newsClassPage = newsTypeService.query(pageRequest);
            return new ResultGrid(0, "", Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())), newsClassPage.getContent());
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "新闻分类添加", uri = "add", type = MenuType.BUTTON, description = "新闻分类添加按钮")
    public ServerResponse add(@Valid NewsTypeForm newsTypeForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            newsTypeService.save(newsTypeForm, userInfo.getUsername());
            return ServerResponse.success("类型添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "新闻分类编辑", uri = "edit", type = MenuType.BUTTON, description = "新闻分类编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid NewsTypeForm newsTypeForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            newsTypeService.modify(newsTypeForm, userInfo.getUsername());
            return ServerResponse.success("类型的修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "新闻分类删除", uri = "remove", type = MenuType.BUTTON, description = "新闻分类删除按钮")
    public ServerResponse remove(String ids) {
        try {
            newsTypeService.remove(ids);
            return ServerResponse.success("类型删除成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{id}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "id") String id) {

        try {
            return ServerResponse.success(newsTypeService.query(id));
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("all")
    public ServerResponse all() {
        try {
            return ServerResponse.success(newsTypeService.queryAll());
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


}
