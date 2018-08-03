/**
 * 
 */
package com.xyls.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xyls.core.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 默认的session失效处理策略
 * 
 * @author zhailiang
 *
 */
public class XylsInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public XylsInvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
