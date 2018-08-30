package com.zag.rest.base.handler;

import com.zag.core.exception.SystemException;
import com.zag.support.web.BuildRequestHandler;
import com.zag.support.web.assist.GlobalParams;
import com.zag.support.web.assist.IRequestVo;
import com.zag.vo.BaseRequestVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static com.zag.core.util.WebUtil.writeEscapedXml;

/**
 * 全局参数初始化处理器
 * @author stone
 * @since 2017年8月16日
 * @usage 
 * @reviewer
 */
@Component
public class GlobalParamsHandler implements BuildRequestHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(HttpServletRequest request, IRequestVo requestVo) throws SystemException {
		if (requestVo != null) {
			// 初始化全局参数
			GlobalParams globalParams = parseGlobalParams(request);
			requestVo.setGlobalParams(globalParams);
			if (requestVo instanceof BaseRequestVo) {

			}
		}
	}

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
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
