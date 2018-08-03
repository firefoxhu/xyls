package com.xyls.admin;
import com.xyls.dto.form.VideoForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/post/")
@Menu(name = "帖子管理",uri = "/admin/post/",isParent = true,type = MenuType.CONTROLLER,description = "帖子管理控制器")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("page/list")
    @Menu(name = "帖子列表页",uri = "page/list",type = MenuType.MAIN_PAGE,description = "帖子列表页")
    public String  list(){
        return "admin/post/list";
    }

    @GetMapping("list")
    @ResponseBody
    @Menu(name = "帖子列表数据",uri = "list",type = MenuType.DATA,description = "帖子列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, String title){
        try {
            return   postService.list(page-1,limit,title);
        }catch (Exception e){
            return new ResultGrid(0,e.getMessage(),0,null);
        }
    }



    @PostMapping("top")
    @ResponseBody
    @Menu(name = "帖子顶置",uri = "top",type = MenuType.BUTTON,description = "帖子顶置按钮")
    public ServerResponse top(String postId){

        try{
            postService.top(postId);
            return  ServerResponse.success("帖子顶置修改成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }

    @PostMapping("status")
    @ResponseBody
    @Menu(name = "帖子状态",uri = "status",type = MenuType.BUTTON,description = "帖子状态按钮")
    public ServerResponse status(String postId){

        try{
            postService.status(postId);
            return  ServerResponse.success("帖子状态修改成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }




}
