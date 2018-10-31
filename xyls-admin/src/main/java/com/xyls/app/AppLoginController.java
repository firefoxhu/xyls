package com.xyls.app;
import com.xyls.dto.support.ServerResponse;
import com.xyls.exception.UserException;
import com.xyls.service.AppLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/app")
@CrossOrigin("*")
public class AppLoginController {

    @Autowired
    private AppLoginService appLoginService;

    @PostMapping("/login")
    @ResponseBody
    public ServerResponse login(String username,String password,String code) {
        try {
            return ServerResponse.success(appLoginService.findUserByAccount(username,password,code));
        }catch (UserException u) {
            return ServerResponse.fail(u.getCode(),u.getMessage(),"认证失败！");
        }catch (Exception e){
            return ServerResponse.fail(e.getMessage());
        }
    }














}
