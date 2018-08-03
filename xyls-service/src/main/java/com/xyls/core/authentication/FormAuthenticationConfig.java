/**
 * 
 */
package com.xyls.core.authentication;

import com.xyls.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 * 
 * @author zhailiang
 */
@Component
public class FormAuthenticationConfig {

	@Autowired
	protected AuthenticationSuccessHandler xylsAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler xylsAuthenticationFailureHandler;
	
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
			.successHandler(xylsAuthenticationSuccessHandler)
			.failureHandler(xylsAuthenticationFailureHandler);
	}
	
}
