package com.zag.support.web.handler;


import com.zag.core.exception.SystemException;
import com.zag.core.util.HttpServletHelper;
import com.zag.support.web.BuildRequestHandler;
import com.zag.support.web.assist.IPAddressAware;
import com.zag.support.web.assist.IRequestVo;

import javax.servlet.http.HttpServletRequest;

/**
 * ip地址处理器,如果请求对象实现了IIPVo接口,将请求者ip写入该对象
 *
 * @author lei
 * @usage
 * @reviewer
 * @since 2017年8月16日
 */
public class IpAddressHandler implements BuildRequestHandler {

    @Override
    public void handle(HttpServletRequest request, IRequestVo requestVo) throws SystemException {
        if (requestVo instanceof IPAddressAware) {
            IPAddressAware ipvo = (IPAddressAware) requestVo;
            ipvo.setIp(HttpServletHelper.getClientIP(request));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
