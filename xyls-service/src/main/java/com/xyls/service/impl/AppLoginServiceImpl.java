package com.xyls.service.impl;

import com.xyls.dto.form.UserForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserException;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.domain.User;
import com.xyls.rbac.repository.UserRepository;
import com.xyls.service.AppLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AppLoginServiceImpl implements AppLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String,Object> findUserByAccount(String username, String password, String code) {

        String redisCacheCode = (String) redisTemplate.opsForValue().get(image_code+username);

        if(redisCacheCode == null || redisCacheCode.length() == 0) {
            throw new UserFormException(ResponseEnum.SYSTEM_CODE_NOT_EXIST);
        }

        if(code == null || code.length() != 4) {
            throw new UserFormException(ResponseEnum.SYSTEM_VALIDATE_ERROR);
        }

        if(!redisCacheCode.equals(code.trim())) {
            throw new UserFormException(ResponseEnum.SYSTEM_CODE_NOT_EQUAL);
        }

        User user = userRepository.findByUserName(username);

        if(user == null) {
            throw new UserFormException(ResponseEnum.LOGIN_USER_NOT_EXIST);
        }

       if(!passwordEncoder.encode(password).equals(user.getUserPassword())){
           throw new UserFormException(ResponseEnum.LOGIN_USER_PASSWORD_ERROR);
        }
        String cookie = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set(session_user+cookie,user);

        Map<String,Object> result = new HashMap<>();
        result.put("username",user.getUserName());
        result.put("userId",user.getUserId());
        result.put("sex",user.getSex());
        result.put("cookie",cookie);
        return result;
    }


    // TODO 生成图片验证码


}
