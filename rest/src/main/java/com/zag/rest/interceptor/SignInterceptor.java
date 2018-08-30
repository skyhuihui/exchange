package com.zag.rest.interceptor;

import com.zag.exception.Exceptions;
import com.zag.core.asserts.BusinessAsserts;
import com.zag.rest.base.Constants;
import com.zag.rest.util.SignUtil;
import com.zag.service.SystemConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名校验拦截器
 *
 * @author lei
 * @date 2017年11月11日
 * @reviewer
 * @see
 */
public class SignInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SignInterceptor.class);

    private SystemConfig systemConfig = null;

    private String excludedUri;

    private String excludedReferer = "";

    private Boolean manualEnable = true;


    /**
     * 使用URI 匹配不需要签名的正则表达式
     */
    public boolean withoutSign(String uri, String referer) {
        return uri.trim().matches(excludedUri) || referer != null && referer.length() > 0 && referer.trim().matches
                (excludedReferer);
    }

    /**
     * 签名验证实现
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        logger.debug("preHandle {}", request.getRequestURI());

        Object reqSignFlag = request.getAttribute(Constants.SIGN_FLAG_REQ_ATTR);
        Boolean enabled = manualEnable;
        if (reqSignFlag != null) {
            enabled = (Boolean.TRUE.equals(reqSignFlag) || "true".equals(reqSignFlag));
        } else if (manualEnable) {
            //默认开启加载，sign验证
            enabled = systemConfig == null || systemConfig.getOpenSign() == null ? true : systemConfig.getOpenSign();
        }
        if (enabled) {
            // 排除不需要签名的uri
            if (!withoutSign(request.getRequestURI(), request.getHeader("referer"))) {
                BusinessAsserts.isTrue(SignUtil.verifySign1(request), Exceptions.Global.SIGN_ERROR);
            }
        }
        return true;
    }


    public void setExcludedUri(String excludedUri) {
        this.excludedUri = excludedUri;
    }


    public void setExcludedReferer(String excludedReferer) {
        this.excludedReferer = excludedReferer;
    }


    public void setEnabled(boolean enabled) {
        this.manualEnable = enabled;
    }

}