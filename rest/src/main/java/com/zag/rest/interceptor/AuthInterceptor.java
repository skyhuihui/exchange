package com.zag.rest.interceptor;

import com.google.common.base.Objects;
import com.zag.core.asserts.BusinessAsserts;
import com.zag.core.util.DebugUtil;
import com.zag.exception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private boolean enabled = false;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!isEnabled()){
			return true;
		}
		if(DebugUtil.isDebugEnabled() && Objects.equal("true", request.getParameter("debug"))){
			logger.debug("debug mode enabled, and parameter [debug] is 'true', pass");
			return true;
		}
		
		String token = request.getParameter("authToken");
		
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
