/**
 * 
 */
package com.xyls.rbac.init;

import com.xyls.rbac.domain.*;
import com.xyls.rbac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 默认的系统数据初始化器，永远在其他数据初始化器之前执行
 * 
 * @author zhailiang
 *
 */
@Component
@Transactional
public class AdminDataInitializer extends AbstractDataInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SysRoleRepository sysRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSysRoleRepository userSysRoleRepository;

	@Autowired
	protected SysResourceRepository sysResourceRepository;

	@Autowired
	private SysRoleResourceRepository sysRoleResourceRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idea.core.spi.initializer.DataInitializer#getIndex()
	 */
	@Override
	public Integer getIndex() {
		return Integer.MIN_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idea.core.spi.initializer.AbstractDataInitializer#doInit()
	 */
	@Override
	protected void doInit() {

		String userId=UUID.randomUUID().toString().replaceAll("-","");

		User user = new User();
		user.setUserId(userId);
		user.setUserName("admin");
		user.setUserPassword(passwordEncoder.encode("P@ssw0rd"));
		userRepository.save(user);

		String roleId=UUID.randomUUID().toString().replaceAll("-","");
		SysRole sysRole=new SysRole();
		sysRole.setRoleId(roleId);
		sysRole.setRoleName("超级管理员");
		sysRoleRepository.save(sysRole);


		UserSysRole userSysRole = new UserSysRole();
		userSysRole.setUserRoleId(UUID.randomUUID().toString().replaceAll("-",""));
		userSysRole.setRoleId(roleId);
		userSysRole.setUserId(userId);
		userSysRoleRepository.save(userSysRole);

		String resourceId=UUID.randomUUID().toString().replaceAll("-","");

		SysResource root = new SysResource();
		root.setResourceId(resourceId);
		root.setResourceName("文章列表");
		root.setResourceIcon("icon-text");
		root.setResourceUrl("/admin/news/list");
		root.setResourceSpread("C");
		sysResourceRepository.save(root);

		SysRoleResource sysRoleResource =new SysRoleResource();
		sysRoleResource.setRoleResourceId(UUID.randomUUID().toString().replaceAll("-",""));
		sysRoleResource.setRoleId(roleId);
		sysRoleResource.setResourceId(resourceId);
		sysRoleResourceRepository.save(sysRoleResource);





		String userId2=UUID.randomUUID().toString().replaceAll("-","");

		User user2 = new User();
		user2.setUserId(userId2);
		user2.setUserName("guest");
		user2.setUserPassword(passwordEncoder.encode("123456"));
		userRepository.save(user2);

		String roleId2=UUID.randomUUID().toString().replaceAll("-","");
		SysRole sysRole2=new SysRole();
		sysRole2.setRoleId(roleId2);
		sysRole2.setRoleName("贵宾");
		sysRoleRepository.save(sysRole2);


		UserSysRole userSysRole2 = new UserSysRole();
		userSysRole2.setUserRoleId(UUID.randomUUID().toString().replaceAll("-",""));
		userSysRole2.setRoleId(roleId2);
		userSysRole2.setUserId(userId2);
		userSysRoleRepository.save(userSysRole2);

		String resourceId2=UUID.randomUUID().toString().replaceAll("-","");

		SysResource root2 = new SysResource();
		root2.setResourceId(resourceId2);
		root2.setResourceName("文章列表");
		root2.setResourceIcon("icon-text");
		root2.setResourceUrl("/admin/news/list");
		root2.setResourceSpread("C");
		sysResourceRepository.save(root2);

		SysRoleResource sysRoleResource2 =new SysRoleResource();
		sysRoleResource2.setRoleResourceId(UUID.randomUUID().toString().replaceAll("-",""));
		sysRoleResource2.setRoleId(roleId2);
		sysRoleResource2.setResourceId(resourceId2);
		sysRoleResourceRepository.save(sysRoleResource2);



	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idea.core.spi.initializer.AbstractDataInitializer#isNeedInit()
	 */
	@Override
	protected boolean isNeedInit() {
		return userRepository.count() == 0;
	}


}
