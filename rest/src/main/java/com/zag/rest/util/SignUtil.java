package com.zag.rest.util;

import com.zag.core.util.StringTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SignUtil {
	//给客户端的用来计算sin的Token
	public static final String TOKEN = "1234567890";
	private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

	public static final String SIGN = "sign";
	public static final String DATA = "data";
	public static final String SHOPSTATUS = "shopStatus";
	public static final String USER_ID = "userId";

	/**
	 * 黑名单方式
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author stone
	 */
	public static boolean verifySign1(HttpServletRequest request) throws UnsupportedEncodingException {

		Set<String> keyValuePair = new TreeSet<String>();
		for (Map.Entry<String, String[]> paramKeyValues : request.getParameterMap().entrySet()) {
			String paramKey = paramKeyValues.getKey();
			if(SIGN.compareTo(paramKey)!=0 && DATA.compareTo(paramKey)!=0 && SHOPSTATUS.compareTo(paramKey)!=0){
				String parameterValue = request.getParameter(paramKey);
				if (parameterValue != null) {
					keyValuePair.add(paramKey + "=" + paramKeyValues.getValue()[0]);
				}
			}
		}
		String md5Source = StringUtils.join(keyValuePair, "&");
		md5Source += getToken(request.getParameter(USER_ID));

		String data = request.getParameter(DATA);
		if (data != null) {
			md5Source += data;
		}
		String postSign = request.getParameter(SIGN);
		String md5 = StringTool.encodedByMD5(md5Source);

		if (log.isDebugEnabled()) {
			log.debug("服务端参数: {};", md5Source);
			log.debug("签名比对: s-{};c-{}", md5, postSign);
		}
		return StringUtils.equals(postSign, md5);
	}

	private static String getToken(String userId) {
		return TOKEN;
	}


	/**
	 * 白名单方式
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author stone
	 */
	public static boolean verifySign(HttpServletRequest request) throws UnsupportedEncodingException {


		StringBuilder sb = new StringBuilder();
		sb.append("apn=").append(request.getParameter("apn"));
		sb.append("&deviceId=").append(request.getParameter("deviceId"));

		String imei = request.getParameter("imei");
		if (imei != null) {
			sb.append("&imei=").append(imei);
		}
		String imsi = request.getParameter("imsi");
		if (imsi != null) {
			sb.append("&imsi=").append(imsi);
		}

		String lat = request.getParameter("lat");
		if (lat != null) {
			sb.append("&lat=").append(lat);
		}
		String lon = request.getParameter("lon");
		if (lat != null) {
			sb.append("&lon=").append(lon);
		}

		String mac = request.getParameter("mac");
		if (mac != null) {
			sb.append("&mac=").append(mac);
		}


		sb.append("&os=").append(request.getParameter("os"));
		sb.append("&osVersion=").append(request.getParameter("osVersion"));
		sb.append("&partner=").append(request.getParameter("partner"));

		String routerMac = request.getParameter("routerMac");
		if (routerMac != null) {
			sb.append("&routerMac=").append(routerMac);
		}
		String station = request.getParameter("station");
		if (station != null) {
			sb.append("&station=").append(station);
		}

		sb.append("&sub=").append(request.getParameter("sub"));
		sb.append("&userAgent=").append(request.getParameter("userAgent"));
		String userId = request.getParameter("userId");
		if (userId != null) {
			sb.append("&userId=").append(userId);
		}
		sb.append("&ver=").append(request.getParameter("ver"));
		sb.append(getToken(userId));

		String data = request.getParameter("data");
		if (data != null) {
			sb.append(data);
		}
		String postSign = request.getParameter("sign");
		String md5Source = sb.toString();
		String md5 = StringTool.encodedByMD5(md5Source);

		if (log.isDebugEnabled()) {
			log.debug("服务端参数: {};", md5Source);
			log.debug("签名比对: s-{};c-{}", md5, postSign);
		}
		return StringUtils.equals(postSign, md5);

	}
}


