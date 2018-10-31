package com.xyls.admin;

import com.xyls.dto.form.ResourceForm;
import com.xyls.dto.form.RoleForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.domain.SysResource;
import com.xyls.rbac.domain.SysRole;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.ResourceService;
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

@Controller
@RequestMapping("/admin/resource/")
@Menu(name = "系统资源管理", uri = "/admin/resource/", isParent = true, type = MenuType.CONTROLLER, description = "系统资源管理控制器")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("page/list")
    @Menu(name = "系统资源列表页", uri = "page/list", type = MenuType.MAIN_PAGE, description = "系统资源列表页")
    public String list() {
        return "admin/resource/list";
    }

    @GetMapping("page/add")
    @Menu(name = "系统资源添加页", uri = "page/add", type = MenuType.SUB_PAGE, description = "系统资源添加页")
    public String add() {
        return "admin/resource/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "系统资源编辑页", uri = "page/edit", type = MenuType.SUB_PAGE, description = "系统资源编辑页")
    public String edit(Model model, String id) {
        model.addAttribute("data", resourceService.query(id));
        return "admin/resource/edit";
    }


    @GetMapping("list")
    @ResponseBody
    @Menu(name = "系统资源列表数据", uri = "list", type = MenuType.DATA, description = "系统资源列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = new PageRequest(page - 1, limit);
            Page<SysResource> newsClassPage = resourceService.query(pageRequest);
            return new ResultGrid(0, "", Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())), newsClassPage.getContent());
        } catch (Exception e) {
            return new ResultGrid(0, e.getMessage(), 0, null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "系统资源添加", uri = "add", type = MenuType.BUTTON, description = "系统资源添加按钮")
    public ServerResponse add(@Valid ResourceForm resourceForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {
        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }
        try {
            UserInfo userInfo = (UserInfo) userDetails;
            resourceService.save(resourceForm, userInfo.getUsername());
            return ServerResponse.success("资源添加成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "系统资源编辑", uri = "edit", type = MenuType.BUTTON, description = "系统资源编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails, @Valid ResourceForm resourceForm, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
        } catch (UserFormException e) {
            return ServerResponse.fail(e.getCode(), e.getMessage());
        }

        try {
            UserInfo userInfo = (UserInfo) userDetails;
            resourceService.modify(resourceForm, userInfo.getUsername());
            return ServerResponse.success("资源修改成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "系统资源删除", uri = "remove", type = MenuType.BUTTON, description = "系统资源删除按钮")
    public ServerResponse remove(String ids) {
        try {
            resourceService.remove(ids);
            return ServerResponse.success("资源删除成功！");
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{id}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "id") String id) {

        try {
            return ServerResponse.success(resourceService.query(id));
        } catch (Exception e) {
            return ServerResponse.fail(e.getMessage());
        }
    }

}
