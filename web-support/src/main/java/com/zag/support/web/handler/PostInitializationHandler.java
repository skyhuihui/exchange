package com.zag.support.web.handler;

import com.zag.core.exception.SystemException;
import com.zag.support.web.assist.IRequestVo;
import com.zag.support.web.BuildRequestHandler;
import com.zag.support.web.assist.PostInitialization;

import javax.servlet.http.HttpServletRequest;

/**
 * 后置初始化处理器,如果请求对象实现了PostInitialization接口,调用该对象的postInitializing()方法
 *
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月16日
 */
public class PostInitializationHandler implements BuildRequestHandler {

    @Override
    public void handle(HttpServletRequest request, IRequestVo requestVo) throws SystemException {
        if (requestVo instanceof PostInitialization) {
            ((PostInitialization) requestVo).postInitializing();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
