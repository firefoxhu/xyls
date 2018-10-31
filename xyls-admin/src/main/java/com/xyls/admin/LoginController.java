package com.xyls.admin;

import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.enums.MenuType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
@Menu(name = "后台登录控制器", uri = "/admin/", isParent = true, type = MenuType.CONTROLLER, description = "后台登录控制器")
public class LoginController {

    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("index")
    @Menu(name = "管理首页页面", uri = "index", type = MenuType.MAIN_PAGE, description = "管理首页页面")
    public String index() {
        return "admin/index";
    }

    @GetMapping("main")
    @Menu(name = "管理首页的嵌套主页面", uri = "main", type = MenuType.SUB_PAGE, description = "管理首页的嵌套主页面")
    public String main() {
        return "admin/main";
    }

}
