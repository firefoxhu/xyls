package com.xyls.admin;

import com.xyls.dto.form.RoleForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.domain.SysRole;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.RoleService;
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
@RequestMapping("/admin/role/")
@Menu(name = "角色管理", uri = "/admin/role/", isParent = true, type = MenuType.CONTROLLER, description = "角色管理控制器")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("page/list")
    @Menu(name = "角色列表页", uri = "page/list", type = MenuType.MAIN_PAGE, description = "角色列表页")
    public String list() {
        return "admin/role/list";
    }

    @GetMapping("page/add")
    @Menu(name = "角色添加页", uri = "page/add", type = MenuType.SUB_PAGE, description = "角色添加页")
    public String add() {
        return "admin/role/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "角色编辑页", uri = "page/edit", type = MenuType.SUB_PAGE, description = "角色编辑页")
    public String edit(Model model, String id) {
        model.addAttribute("data", roleService.query(id));
        return "admin/role/edit";
    }

    @GetMapping("page/bind")
    @Menu(name = "角色绑定页", uri = "page/bind", type = MenuType.SUB_PAGE, description = "角色绑定页")
    public String bind(Model model, String id) throws InvocationTargetException, IllegalAccessException {
        model.addAttribute("data", roleService.roleBindResource(id));
        return "admin/role/bind";
    }

    @GetMapping("list")
    @ResponseBody
    @Menu(name = "角色列表数据", uri = "list", type = MenuType.DATA, description = "角色列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = new PageRequest(page - 1, limit);
            Page<SysRole> newsClassPage = roleService.query(pageRequest);
            return new ResultGrid(0, "", Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())), newsClassPage.getContent());
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "角色添加", uri = "add", type = MenuType.BUTTON, description = "角色加按钮")
    public ServerResponse add(@Valid RoleForm roleForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            roleService.save(roleForm, userInfo.getUsername());
            return ServerResponse.success("角色添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "角色编辑", uri = "edit", type = MenuType.BUTTON, description = "角色编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid RoleForm roleForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            roleService.modify(roleForm, userInfo.getUsername());
            return ServerResponse.success("角色修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "角色删除", uri = "remove", type = MenuType.BUTTON, description = "角色删除按钮")
    public ServerResponse remove(String ids) {
        try {
            roleService.remove(ids);
            return ServerResponse.success("角色删除成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("bind/resource")
    @ResponseBody
    @Menu(name = "角色绑定资源按钮", uri = "bind/resource", type = MenuType.BUTTON, description = "角色绑定资源按钮")
    public ServerResponse bind(String roleId, String resourceIds) {
        try {
            roleService.bind(roleId, resourceIds);
            return ServerResponse.success("ok");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }

    @PostMapping("unbind/resource")
    @ResponseBody
    @Menu(name = "角色解绑资源按钮", uri = "unbind/resource", type = MenuType.BUTTON, description = "角色解绑资源按钮")
    public ServerResponse unbind(String roleId, String resourceIds) {
        try {
            roleService.unbind(roleId, resourceIds);
            return ServerResponse.success("ok");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }
}
