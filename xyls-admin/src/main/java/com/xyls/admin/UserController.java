package com.xyls.admin;

import com.xyls.dto.form.UserForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.domain.User;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.RoleService;
import com.xyls.service.UserAdminService;
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
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/admin/user/")
@Menu(name = "用户中心", uri = "/admin/user/", isParent = true, type = MenuType.CONTROLLER, description = "用户中心控制器")
public class UserController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("page/list")
    @Menu(name = "用户列表", uri = "page/list", type = MenuType.MAIN_PAGE, description = "用户中心列表页")
    public String list() {
        return "admin/user/list";
    }

    @GetMapping("page/add")
    @Menu(name = "添加用户", uri = "page/add", type = MenuType.SUB_PAGE, description = "用户中心列添加页面")
    public String add(Model model) {
        model.addAttribute("data", roleService.findAll());
        return "admin/user/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "编辑用户", uri = "page/edit", type = MenuType.SUB_PAGE, description = "用户中心列修改页面")
    public String edit(Model model, String id) throws InvocationTargetException, IllegalAccessException {
        model.addAttribute("data", userAdminService.queryWithRoles(id));
        model.addAttribute("roles", roleService.findAll());
        return "admin/user/edit";
    }


    @GetMapping("list")
    @ResponseBody
    @Menu(name = "用户列表数据", uri = "list", type = MenuType.DATA, description = "用户中心列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = new PageRequest(page - 1, limit);
            Page<User> newsClassPage = userAdminService.query(pageRequest);
            return new ResultGrid(0, "", Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())), newsClassPage.getContent());
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "用户列数据添加", uri = "add", type = MenuType.BUTTON, description = "用户中心数据添加")
    public ServerResponse add(@Valid UserForm userForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            userAdminService.save(userForm, userInfo.getUsername());
            return ServerResponse.success("用户添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "用户列数据编辑", uri = "edit", type = MenuType.BUTTON, description = "用户中心数据编辑")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid UserForm userForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            userAdminService.modify(userForm, userInfo.getUsername());
            return ServerResponse.success("用户修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "用户列数据删除", uri = "remove", type = MenuType.BUTTON, description = "用户中心数据删除")
    public ServerResponse remove(String ids) {
        try {
            userAdminService.remove(ids);
            return ServerResponse.success("用户删除成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{id}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "id") String id) {

        try {
            return ServerResponse.success(userAdminService.query(id));
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }
}
