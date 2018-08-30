package com.zag.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zag.core.exception.BusinessException;
import com.zag.core.util.HttpServletHelper;
import com.zag.core.util.WebUtil;
import com.zag.exception.Exceptions;
import com.zag.rest.util.JwtUnsign;
import com.zag.rest.util.NetUtils;
import com.zag.support.web.BuildRequestHandler;
import com.zag.support.web.assist.GlobalParams;
import com.zag.support.web.assist.GlobalParamsAware;
import com.zag.support.web.assist.IPAddressAware;
import com.zag.support.web.assist.IRequestVo;
import com.zag.support.web.handler.JSONResult;
import com.zag.vo.BaseRequestVo;
import com.zag.vo.ex.user.resp.JwtUser;
import com.zag.vo.ex.user.resp.UserRespVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 控制器基类,要求所有面向手机端的api都要继承此类
 *
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月3日
 */
@RestController
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private List<BuildRequestHandler> handlers = new ArrayList<>();

    /**
     * @param request          http请求
     * @param clazz            请求vo的class
     * @param validationGroups 用于jsr303验证的group class
     * @return
     */
    protected <V extends IRequestVo> V buildVo(HttpServletRequest request, Class<V> clazz, Class<?>... validationGroups) {
        String data = NetUtils.getHttpRequestBody(request).trim();
        logger.debug("请求参数:\n{}", data);
        if(Strings.isNullOrEmpty(data)){
            return null;
        }
        V vo;
        try {
            if (StringUtils.isNotBlank(data)) {
                // 有业务参数, 使用json反序列化
                vo = JSONObject.parseObject(data, clazz);
            } else {
                // 无业务参数, 默认构建
                vo = clazz.newInstance();
            }
        } catch (Exception e) {
            logger.error("解析接口业务参数出错", e);
            throw new BusinessException(Exceptions.Global.PARAMETER_ERROR, e);
        }

        if (vo instanceof IPAddressAware) {
            IPAddressAware ipvo = (IPAddressAware) vo;
            ipvo.setIp(HttpServletHelper.getClientIP(request));
        }
        if (vo instanceof GlobalParamsAware) {
            if(!Strings.isNullOrEmpty(request.getHeader("jwtToken"))){
                JwtUser jwtUser = new JwtUser();
                jwtUser = JwtUnsign.unsign(request.getHeader("jwtToken"), jwtUser.getClass());
                // 初始化全局参数
                GlobalParams globalParams = new GlobalParams(jwtUser.getId(), jwtUser.getName(), jwtUser.getType().toString(), jwtUser.getUserExamineEnums().toString());
                vo.setGlobalParams(globalParams);
                if (vo instanceof BaseRequestVo) {
                    ((BaseRequestVo) vo).setUserId(jwtUser.getId());
                    ((BaseRequestVo) vo).setName(jwtUser.getName());
                    ((BaseRequestVo) vo).setType(jwtUser.getType());
                    ((BaseRequestVo) vo).setUserExamineEnums(jwtUser.getUserExamineEnums());
                }
            }
        }

        Class<?>[] groups = null;
        if (validationGroups != null && validationGroups.length > 0) {
            groups = validationGroups;
        }
        try {
            validate(vo, groups);
        } catch (ValidationException e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                throw (BusinessException) cause;
            }
            throw e;
        }
        for (BuildRequestHandler handler : handlers) {
            handler.handle(request, vo);
        }

        return vo;
    }

    @ExceptionHandler(Exception.class)
    protected JSONResult exceptionHandler(Exception ex, HttpServletRequest request) throws IOException {
        ex.printStackTrace();
        if (ex instanceof BusinessException) {
            logger.info("business-exception:{}", ex);
            return new JSONResult((BusinessException) ex);
        } else {
            logger.error("system-exception:{}", ex);
            return new JSONResult(new BusinessException(Exceptions.Global.SERVER_EXCEPTION, WebUtil.writeEscapedXml
                    (ex.getMessage())));
        }
    }

    /**
     * 解析全局参数
     * <p/>
     * <p>
     * 解析出错时, 返回null, 打印错误日志
     * </p>
     *
     * @param httpRequest
     * @author zhujun
     * @date 2015-2-3
     */
    private GlobalParams parseGlobalParams(HttpServletRequest httpRequest) {
        GlobalParams params = null;
        try {
            GlobalParams p = new GlobalParams();
            params = p;
        } catch (Exception e) {
            logger.error("解析全局参数出错", e);
        }
        return params;
    }

    protected static void validate(Object obj, Class<?>... groups) throws ValidationException {
        Set<ConstraintViolation<Object>> constraintViolations = null;
        if (groups == null || groups.length == 0) {
            constraintViolations = VALIDATOR.validate(obj);
        } else {
            constraintViolations = VALIDATOR.validate(obj, groups);
        }
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            StringBuilder errorInfo = new StringBuilder();
            for (ConstraintViolation<Object> cv : constraintViolations) {
                errorInfo
                        // .append(cv.getPropertyPath()).append(", ")
                        .append(cv.getMessage() + "\r\n");
            }
            throw new BusinessException(Exceptions.Global.PARAMETER_ERROR, errorInfo.toString());
        }
    }
}
