package com.xyls.admin;
import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.dto.dto.NewsDTO;
import com.xyls.exception.UserFormException;
import com.xyls.dto.form.NewsForm;
import com.xyls.dto.form.ValidateForm;
import com.xyls.rbac.enums.MenuType;
import com.xyls.service.NewsClassService;
import com.xyls.service.NewsService;
import com.xyls.dto.support.ResultGrid;
import com.xyls.dto.support.ServerResponse;
import com.xyls.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/admin/news/")
@Menu(name = "新闻管理",uri = "/admin/news/",isParent = true,type = MenuType.CONTROLLER,description = "新闻管理控制器")
public class NewsController {


    @Autowired
    private NewsService newsService;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private NewsClassService newsClassService;


    @GetMapping("page/list")
    @Menu(name = "新闻列表页",uri = "page/list",type = MenuType.MAIN_PAGE,description = "新闻列表页")
    public String  list(){
        return "admin/news/newsList";
    }

    @GetMapping("page/add")
    @Menu(name = "新闻添加页",uri = "page/add",type = MenuType.SUB_PAGE,description = "新闻添加页")
    public String  add(Model model,String classId){
        model.addAttribute("type",newsTypeService.findCategoryByClassId(classId));
        model.addAttribute("class",newsClassService.queryAll());
        return "admin/news/newsAdd";
    }

    @GetMapping("page/edit")
    @Menu(name = "新闻编辑页",uri = "page/edit",type = MenuType.SUB_PAGE,description = "新闻编辑页")
    public String  edit(Model model,String id,String classId){
        model.addAttribute("newsId",id);
        model.addAttribute("type",newsTypeService.findCategoryByClassId(classId));
        model.addAttribute("class",newsClassService.queryAll());
        return "admin/news/newsUpdate";
    }

    @GetMapping("list")
    @ResponseBody
    @Menu(name = "新闻列表数据",uri = "list",type = MenuType.DATA,description = "新闻列表数据")
    public ResultGrid list(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int limit,String title){
        try {
            return   newsService.list(page-1,limit,title);
        }catch (Exception e){
            return new ResultGrid(0,e.getMessage(),0,null);
        }
    }


    @PostMapping("add")
    @ResponseBody
    @Menu(name = "新闻添加",uri = "add",type = MenuType.BUTTON,description = "新闻添加按钮")
    public ServerResponse add(@Valid NewsForm newsForm, @AuthenticationPrincipal UserDetails userDetails,BindingResult result){
        try {
            ValidateForm.validate(result,messageSource);
        }catch (UserFormException e){
            return ServerResponse.fail(e.getCode(),e.getMessage());
        }
        try{
            UserInfo userInfo = (UserInfo)userDetails;
            newsService.save(newsForm,userInfo.getUsername());
            return  ServerResponse.success("新闻添加成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @Menu(name = "新闻编辑",uri = "edit",type = MenuType.BUTTON,description = "新闻编辑按钮")
    public ServerResponse edit(@AuthenticationPrincipal UserDetails userDetails,@Valid NewsForm newsForm, BindingResult result){

        try {
            ValidateForm.validate(result,messageSource);
        }catch (UserFormException e){
            return ServerResponse.fail(e.getCode(),e.getMessage());
        }

        try{
            UserInfo userInfo = (UserInfo)userDetails;
            newsService.modify(newsForm,userInfo.getUsername());
            return  ServerResponse.success("修改成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }


    @PostMapping("remove")
    @ResponseBody
    @Menu(name = "新闻删除",uri = "remove",type = MenuType.BUTTON,description = "新闻删除按钮")
    public ServerResponse  remove(String ids){
        try{
            newsService.remove(ids);
            return  ServerResponse.success("删除文章成功！");
        }catch (Exception  e){
            return ServerResponse.fail(e.getMessage());
        }
    }


    @GetMapping("{newsId}/preview")
    @ResponseBody
    public ServerResponse preview(@PathVariable(value = "newsId")String newsId) throws InvocationTargetException, IllegalAccessException {
        NewsDTO newsDTO=newsService.preview(newsId);
        return ServerResponse.success(newsDTO);
    }

}
