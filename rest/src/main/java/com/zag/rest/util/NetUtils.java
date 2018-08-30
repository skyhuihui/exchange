package com.zag.rest.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;


public class NetUtils {
	private static Logger logger = Logger.getLogger(NetUtils.class);
	
	public static String getHttpRequestBody(HttpServletRequest request){
		
		Object requestContext = request.getAttribute("requestContext");
		if(requestContext != null){
			return requestContext.toString();
		}
		String result = null;
		 try {
			request.setCharacterEncoding("UTF-8");
			
			int contentLen = request.getContentLength();
			if(contentLen > 0){
				InputStream is = request.getInputStream();
				byte[] message = new byte[contentLen];
				int readLen = 0;
				int readLengthThisTime = 0;
				while (readLen != contentLen) {
					 readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					 if (readLengthThisTime == -1){
						 break;
					 }
					 readLen += readLengthThisTime;
				}
				if(readLen >= contentLen){
					result = new String(message, "UTF-8");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		logger.info("request 信息：" + result); 
		request.setAttribute("requestContext", result);
		return result;
	}
}
