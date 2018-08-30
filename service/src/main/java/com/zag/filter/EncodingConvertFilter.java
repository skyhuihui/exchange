package com.zag.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zag.core.util.WebUtil;

/**
 * Filter - URL携带中文参数编码格式转换
 * @author stone
 * @since 2017年11月29日
 * @usage 
 * @reviewer
 */
public class EncodingConvertFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 原编码格式 */
	private String fromEncoding = "ISO-8859-1";

	/** 目标编码格式 */
	private String toEncoding = "UTF-8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(StringUtils.isNotEmpty(request.getQueryString())){
			Map<String, List<String>> getParames = splitQueryStringParameters(request.getQueryString());
			Map<String, String[]> parames = request.getParameterMap();
			for(Iterator<Entry<String, List<String>>> iter = getParames.entrySet().iterator();iter.hasNext();){
				Entry<String, List<String>> entry = iter.next();
				parames.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
			}
		}
		filterChain.doFilter(request, response);
	}

	private Map<String,List<String>> splitQueryStringParameters(String queryStr) throws IOException{
		logger.info("query string:{}",queryStr);
		String[] keyValues = queryStr.split("&");
		Map<String,List<String>> result = new HashMap<String,List<String>>();
		for(String keyValue: keyValues){
			String[] kv = keyValue.split("=");
			if(kv.length!=2)continue;
			if(StringUtils.isEmpty(kv[0])||StringUtils.isEmpty(kv[1]))continue;
			List<String> values = result.get(kv[0]);
			if(values==null){
				values = new ArrayList<String>();
			}
			values.add(WebUtil.writeEscapedXml(new String(kv[1].getBytes(fromEncoding), toEncoding)));
		}
		return result;
	}
	
	/**
	 * 获取原编码格式
	 * 
	 * @return 原编码格式
	 */
	public String getFromEncoding() {
		return fromEncoding;
	}

	/**
	 * 设置原编码格式
	 * 
	 * @param fromEncoding
	 *            原编码格式
	 */
	public void setFromEncoding(String fromEncoding) {
		this.fromEncoding = fromEncoding;
	}

	/**
	 * 获取目标编码格式
	 * 
	 * @return 目标编码格式
	 */
	public String getToEncoding() {
		return toEncoding;
	}

	/**
	 * 设置目标编码格式
	 * 
	 * @param toEncoding
	 *            目标编码格式
	 */
	public void setToEncoding(String toEncoding) {
		this.toEncoding = toEncoding;
	}

}