package com.xyls.service;
import java.util.Map;

public interface AppLoginService {

    String  image_code = "SESSION_CODE_";

    String session_user = "SESSION_USER_";

    Map<String,Object> findUserByAccount(String username, String password, String code);

}
