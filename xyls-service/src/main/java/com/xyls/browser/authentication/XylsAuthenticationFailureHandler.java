/**
 *
 */
package com.xyls.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xyls.core.properties.LoginResponseType;
import com.xyls.core.properties.SecurityProperties;
import com.xyls.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 浏览器环境下登录失败的处理器
 *
 * @author zhailiang
 */
@Component("xylsAuthenticationFailureHandler")
public class XylsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
            //response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.failure("登录失败！[" + exception.getMessage() + "]")));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
