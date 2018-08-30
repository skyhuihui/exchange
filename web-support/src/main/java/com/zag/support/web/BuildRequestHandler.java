package com.zag.support.web;

import com.zag.core.exception.SystemException;
import com.zag.support.web.assist.IRequestVo;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;

/**
 * 构建请求处理链
 * 所有实现必须被mvc容器管理
 * 返回较小的order值的实现排在处理链更前方
 *
 * @author lei
 * @usage
 * @reviewer
 * @since 2017年8月17日
 */
public interface BuildRequestHandler extends Ordered {

    void handle(HttpServletRequest request, IRequestVo requestVo) throws SystemException;

}
