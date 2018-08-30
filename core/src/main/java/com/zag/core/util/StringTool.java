package com.zag.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.MAX_RADIX;

public final class StringTool {

    public final static String MOBILE_REG =
        "^((14[0-9])|(13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    public final static String TEL_REG = "(^(\\d{3,4}-)?\\d{7,8})$";
    private static DecimalFormat df1 = new DecimalFormat("#.##");
    private static DecimalFormat ONE_DECEMAL_PLACES_FORMAT = new DecimalFormat("#.#");

    private StringTool() {
    }

    static public String[] stringToArray(String str, String delim) {
        StringTokenizer st = new StringTokenizer(str, delim);
        int count = st.countTokens();
        String[] strArr = new String[count];
        int i = 0;
        while (st.hasMoreTokens()) {
            strArr[i++] = st.nextToken();
        }
        return strArr;
    }

    static public String arrayToString(String[] array, String delim) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                sb.append(delim + array[i]);
            } else {
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }


	public static List<Integer> strToIntArray(String source){
		List<Integer> result = null;
		if(StringUtils.isNoneBlank(source)){
			String[] arr = source.split(",");
			if(arr!=null && arr.length>0){
				result = new ArrayList<Integer>();
				for(String s:arr){
					result.add(Integer.valueOf(s));
				}
			}
		}
		return result;
	}
    
    public static long ipToLong(String ip) {
        long result = 0;
        String[] ip_feild = ip.split("\\.");
        try {
            for (int i = 0; i < ip_feild.length; i++) {
                result += Long.parseLong(ip_feild[i]) << (8 * (3 - i));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String encodedByMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return byte2string(md.digest());
        } catch (Exception e) {
            return password;
        }
    }

    public static String encodedBySHA1(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(source.getBytes());
            return byte2string(md.digest());
        } catch (Exception e) {
            return null;
        }
    }

    public static String base64Encode(String s) {
        try {
            return Base64.encodeBase64String(s.getBytes(("utf-8")));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String base64Encode(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    public static String base64Decode(String s) {
        try {
            return new String(Base64.decodeBase64(s), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] base64DecodeB(String s) {
        return Base64.decodeBase64(s);
    }

    public static String encodedUrl(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static String byte2string(byte[] b) {
        StringBuffer hs = new StringBuffer(100);
        for (int n = 0; n < b.length; n++) {
            hs.append(byte2fex(b[n]));
        }
        return hs.toString();
    }

    public static String byte2fex(byte ib) {
        char[] Digit =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 添加手机验证
     *
     * @param mobile
     * @return maliang 2014-9-2上午11:34:23
     */
    public static boolean isMobileValid(String mobile) {
        return StringUtils.trimToEmpty(mobile).matches(MOBILE_REG);
    }

    public static boolean isTelValid(String tel) {
        return StringUtils.trimToEmpty(tel).matches(TEL_REG);
    }

    public static String contactWayFormat(String mobile, String tels) {
        if (StringUtils.isBlank(tels) && StringUtils.isBlank(mobile)) {
            return "";
        }
        String result = StringUtils.isBlank(mobile) ? "" : mobile + ",";
        tels = StringUtils.isBlank(tels) ? "" : tels;
        if (StringUtils.contains(tels, mobile)) {
            tels = StringUtils.remove(tels, mobile);
        }
        result += tels;
        result = result.replaceAll("(,)+", ",");
        if (result.endsWith(",")) {
            result = StringUtils.substring(result, 0, result.length() - 1);
        }
        return result;
    }

    /**
     * 根据userId 计算邀请码
     *
     * @param userId
     * @return
     */
    public static String userIdToInvitationCode(int userId) {
        return StringUtils.reverse(Integer.toString(userId, MAX_RADIX)).toUpperCase();
    }

    /**
     * 根据邀请码 反算userId
     *
     * @param invitationCode 邀请码
     * @return
     */
    public static Integer invitationCodeToUserId(String invitationCode) {
        try {
            return Integer.valueOf(StringUtils
                .reverse(org.codelogger.utils.StringUtils.trimAllWhitespace(invitationCode)),
                MAX_RADIX);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatPriceToYun(double value) {
        return df1.format(value / 100);
    }

    public static String formatPriceToYuanWithFen(int value) {
        int fen = value % 100;
        return value / 100 + "." + (fen < 10 ? "0" + fen : fen);
    }

    public static Integer formatPriceToFen(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Double doubleValue = null;
        try {
            doubleValue = Double.valueOf(value) * 100;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return doubleValue == null ? null : doubleValue.intValue();
    }

    /**
     * 获取字符串的字节长度
     *
     * @param s
     * @return
     * @author stone
     * @date 2015年7月8日
     */
    public static int getByteLength(String s) {
        if (s == null) {
            return 0;
        }
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }

    /**
     * 填充为空的数组元素
     *
     * @param chars
     * @param fill
     * @author stone
     * @date 2015年7月24日
     */
    public static void blankFill(String[] chars, String fill) {
        if (chars == null || chars.length == 0) {
            return;
        }
        for (int i = 0; i < chars.length; i++) {
            if (StringUtils.isBlank(chars[i])) {
                chars[i] = fill;
            }
        }
    }

    /**
     * 过滤字符串
     *
     * @param str
     * @param regex 需要过滤的字符串，例：\\s*|\t|\r|\n 去掉 空格、回车、换行符、制表符
     * @return
     * @author stone
     * @date 2015年9月7日
     */
    public static String filterInvalidChars(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String content = new String(str);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        String result = m.replaceAll("");
        return result;
    }

    public static String replaceUnvisiable(String source){
    	if(source != null){
    		//里面是全角空格
    		return source.replaceAll("[\\s　]+","");
    	}
    	return source ;
    }
    
    public static String mist(String conent) {
        if (StringUtils.isBlank(conent)) {
            return "*";
        }
        String mistedStr = String.valueOf(conent.charAt(0));
        mistedStr += conent.length() > 3 ?
            "**" + conent.charAt(conent.length() - 1) :
            conent.length() > 2 ? "*" + conent.charAt(conent.length() - 1) : "*";
        return mistedStr;
    }

    /**
     * 获取折扣,例： 9.7折
     *
     * @param currentPrice
     * @param originalPrice
     * @return
     * @author stone
     * @date 2015年11月27日
     */
    public static String getDiscountChars(Integer currentPrice, Integer originalPrice) {
        return discount(currentPrice, originalPrice) + "折";
    }

    public static double discount(Integer currentPrice, Integer originalPrice) {
        if (originalPrice == null || originalPrice == 0 || currentPrice == null
            || currentPrice == 0) {
            return 0.0d;
        }
        return Double.valueOf(ONE_DECEMAL_PLACES_FORMAT
            .format(currentPrice.doubleValue() / originalPrice.doubleValue() * 10d));
    }

    public static List<String> split(String source, String separator) {
        List<String> results = Lists.newArrayList();
        if (StringUtils.isBlank(source)) {
            return results;
        }
        String temp = source;
        if(!StringUtils.equals(" ", separator)){
        	temp = temp.replaceAll("( )*", "");
        }
        for (String segment : StringUtils.split(temp, separator)) {
            results.add(segment);
        }
        return results;
    }

    public static void main(String[] args) throws Exception {
    	String source = "\r\nthis is 　 test!!\r\n	;;;K1000568";
    	System.out.println(source.replaceAll("[\\s　]+",""));
    }

}
