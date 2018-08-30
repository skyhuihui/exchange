package com.zag.support.web.handler;

import com.zag.core.exception.SystemException;
import com.zag.support.web.BuildRequestHandler;
import com.zag.support.web.assist.ClientType;
import com.zag.support.web.assist.ClientTypeAware;
import com.zag.support.web.assist.IRequestVo;
import com.zag.support.web.assist.GlobalParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 感知client type并与vo绑定
 *
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月17日
 */
@Component
public class ClientTypeHandler implements BuildRequestHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(HttpServletRequest request, IRequestVo requestVo) throws SystemException {
        if (requestVo instanceof ClientTypeAware) {
            ClientTypeAware aware = (ClientTypeAware) requestVo;
            // 实现了全局参数接口,client type从全局参数中取
            GlobalParams gp = requestVo.getGlobalParams();

            }
    }

    @Override
    public int getOrder() {
        return 10005;
    }


}
