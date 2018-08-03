package com.xyls.admin;
import com.xyls.dto.form.ShopForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserFormException;
import com.xyls.model.Shop;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.ShopService;
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
@RequestMapping("/admin/shop/")
@Menu(name = "门店管理",uri = "/admin/shop/",isParent = true,type = MenuType.CONTROLLER,description = "门店管理控制器")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("page/list")
    @Menu(name = "门店列表",uri = "page/list",type = MenuType.MAIN_PAGE,description = "门店列表页")
    public String  list(){
        return "admin/shop/list";
    }

    @GetMapping("page/add")
    @Menu(name = "添加门店",uri = "page/add",type = MenuType.SUB_PAGE,description = "添加门店页面")
    public String  add(){
        return "admin/shop/add";
    }

    @GetMapping("page/edit")
    @Menu(name = "编辑门店",uri = "page/edit",type = MenuType.SUB_PAGE,description = "编辑门店页面")
    public String  edit(Model model, String id){
        model.addAttribute("data",shopService.query(id));
        return "admin/shop/edit";
    }


    @GetMapping("list")
    @ResponseBody
    @Menu(name = "门店列表数据",uri = "list",type = MenuType.DATA,description = "门店列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageRequest pageRequest = new PageRequest(page-1,limit);
            Page<Shop> newsClassPage=shopService.query(pageRequest);
            return new ResultGrid(0,"",Integer.parseInt(String.valueOf(newsClassPage.getTotalElements())),newsClassPage.getContent());
        }catch (Exception e){
            return new ResultGrid(0,e.getMessage(),0,null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "门店列数据添加",uri = "add",type = MenuType.BUTTON,description = "门店数据添加")
    public ServerResponse add(@Valid ShopForm shopForm, @AuthenticationPrincipal UserDetails userDetails, BindingResult result){
        try {
            ValidateForm.validate(result,messageSource);
        }catch (UserFormException e){
            return ServerResponse.fail(e.getCode(),e.getMessage());
        }
        try{
            UserInfo userInfo = (UserInfo)userDetails;
            shopService.save(shopForm,userInfo.getUsername());
            return  ServerResponse.success("门店添加成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }




    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "门店数据编辑",uri = "edit",type = MenuType.BUTTON,description = "门店数据编辑")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails,@Valid ShopForm shopForm, BindingResult result){

        try {
            ValidateForm.validate(result,messageSource);
        }catch (UserFormException e){
            return ServerResponse.fail(e.getCode(),e.getMessage());
        }

        try{
            UserInfo userInfo = (UserInfo)userDetails;
            shopService.modify(shopForm,userInfo.getUsername());
            return  ServerResponse.success("门店的修改成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "门店数据删除",uri = "remove",type = MenuType.BUTTON,description = "门店数据删除")
    public ServerResponse  remove(String ids){
        try{
            shopService.remove(ids);
            return  ServerResponse.success("门店删除成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{id}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "id")String id){

        try{
            return ServerResponse.success(shopService.query(id));
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }
}
