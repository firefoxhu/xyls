package com.xyls.service.impl;
import com.xyls.constant.TableConst;
import com.xyls.dto.form.UserForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.domain.User;
import com.xyls.rbac.domain.UserSysRole;
import com.xyls.rbac.repository.UserRepository;
import com.xyls.rbac.repository.UserSysRoleRepository;
import com.xyls.service.UserAdminService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSysRoleRepository userSysRoleRepository;

    @Override
    public Page<User> query(Pageable pageable) {
        Page<User>  page  = userRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public void save(UserForm userForm, String userId) throws Exception {

        User existUser= userRepository.findByUserName(userForm.getUserName());
        if(!StringUtils.isEmpty(existUser)){
            throw new Exception("该用户已经被占用！");
        }

        if(!org.apache.commons.lang3.StringUtils.equals(userForm.getUserPassword(),userForm.getReUserPassword())){
            throw new Exception("2次输入的密码不一致！");
        }

        User user = new User();
        BeanUtils.copyProperties(user,userForm);
        user.setUserId(GenKeyUtil.key());
        user.setStatus(TableConst.NORMAL);
        if(user.getUserHeaderImage() == null)
        user.setUserHeaderImage("http://www.luosen365.com/images/face.png");
        user.setCreatePerson(userId);
        user.setCreateTime(DateUtil.todayDateTime());
        user.setCreateDescription("后台增加用户！");
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);

        this.bindRole(userForm.getRoles(),userForm.getUserId());

    }

    @Override
    @Transactional
    public void remove(String ids) {
        if(org.apache.commons.lang3.StringUtils.isEmpty(ids)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        for(String item:ids.split(",") ){
            userRepository.delete(item);
            userSysRoleRepository.deleteByUserId(item);
        }


    }

    @Override
    @Transactional
    public void modify(UserForm userForm, String userId) throws Exception {
        if (StringUtils.isEmpty(userForm.getUserId())) {
            throw new Exception("参数非法请刷新页面重试！");
        }

        User user =userRepository.findOne(userForm.getUserId());

        String password = user.getUserPassword();

        BeanUtils.copyProperties(user,userForm);

        user.setModifyTime(DateUtil.todayDateTime());
        user.setModifyPerson(userId);
        user.setModifyDescription("修改门用户信息");
        user.setUserPassword(password);
        userRepository.save(user);

        userSysRoleRepository.deleteByUserId(userForm.getUserId());

        this.bindRole(userForm.getRoles(),userForm.getUserId());
    }

    @Override
    public User query(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserForm queryWithRoles(String id) throws InvocationTargetException, IllegalAccessException {

        User  user = this.query(id);

        if (StringUtils.isEmpty(user)) {
            try {
                throw new Exception("参数非法请刷新页面重试！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(userForm,user);
        List<UserSysRole> userSysRoles = userSysRoleRepository.findByUserId(id);
        String roleIds = "";
        for(UserSysRole userSysRole:userSysRoles){
            roleIds +=userSysRole.getRoleId();
        }
        userForm.setRoles(roleIds);

        return userForm;
    }


    private  void   bindRole(String roleStr,String userId){
        if(!StringUtils.isEmpty(roleStr)){
            String[] roles = roleStr.split(",");
            List<UserSysRole> userSysRoles = new ArrayList<>();

            for(String item:roles){
                UserSysRole  userSysRole = new UserSysRole();
                userSysRole.setUserRoleId(GenKeyUtil.key());
                userSysRole.setRoleId(item);
                userSysRole.setUserId(userId);
                userSysRoles.add(userSysRole);
            }
            userSysRoleRepository.save(userSysRoles);
        }
    }
}
