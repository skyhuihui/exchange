package com.zag.core.util;

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

/**
 * 字符或字符串的常用操作封装
 * @author stone
 * @date 2017年7月31日P
 * @reviewer 
 */
public final class StringUtil {

    public final static String MOBILE_REG = "^((14[0-9])|(13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    public final static String TEL_REG = "(^(\\d{3,4}-)?\\d{7,8})$";
    private static DecimalFormat df1 = new DecimalFormat("#.##");
    private static DecimalFormat ONE_DECEMAL_PLACES_FORMAT = new DecimalFormat("#.#");
    
    // ------------------------------------------------------------------------------------------------
 	private static String MAX_INT_STRING = Integer.toString(Integer.MAX_VALUE);
 	private static String MIN_INT_STRING = Integer.toString(Integer.MIN_VALUE);
 	private static String MAX_LONG_STRING = Long.toString(Long.MAX_VALUE);
 	private static String MIN_LONG_STRING = Long.toString(Long.MIN_VALUE);
 	// -------------------------------------------------------------------------------------------------
 	

    private StringUtil() {
    }
    
	/**
	 * 判断字符串是否为空或空串
	 * @author stone
	 * @date 2017年7月31日
	 * @param string 需要判定的字符串对象
	 * @return  true: 空或空串; false: 非空或空串
	 */
	public static boolean isNullOrEmpty(String string)
	{
		return null == string || string.length() == 0;
	}

	/**
	 * 判定是否是Integer.MIN_VALUE ~ Integer.MAX_VALUE间的整数字符串
	 * @author stone
	 * @date 2017年7月31日
	 * @param string 需要判定的字符串
	 * @return  true: 是整数字符串; false: 不是整数字符串
	 */
	public static boolean isInteger(String string)
	{
		if (isNullOrEmpty(string)) return false;

		char f = string.charAt(0);
		if (f == '-')
		{
			if (string.length() > MIN_INT_STRING.length()) return false;

			for (int n = 1; n < string.length(); n++)
			{
				if (isNumberChar(string.charAt(n))) continue;

				return false;
			}

			if (string.length() < MIN_INT_STRING.length()) return true;
			for (int n = 1; n < string.length(); n++)
			{
				int mc = MIN_INT_STRING.charAt(n);
				int sc = string.charAt(n);

				if (sc > mc) return false;
			}

			return true;
		}
		else return isPositiveInteger(string);
	}

	/**
	 * 判定是否是正常0 ~ Integer.MAX_VALUE间的整数字符串
	 * @author stone
	 * @date 2017年7月31日
	 * @param string 需要判定的字符串
	 * @return true: 是整数字符串; false: 不是整数字符串
	 */
	public static boolean isPositiveInteger(String string)
	{
		if (isNullOrEmpty(string) || string.length() > MAX_INT_STRING.length()) return false;

		for (int n = 0; n < string.length(); n++)
		{
			if (isNumberChar(string.charAt(n))) continue;

			return false;
		}

		if (string.length() < MAX_INT_STRING.length()) return true;

		for (int n = 0; n < string.length(); n++)
		{
			int mc = MAX_INT_STRING.charAt(n);
			int sc = string.charAt(n);

			if (sc > mc) return false;
		}

		return true;
	}

	/**
	 * 判定是否是Long.MIN_VALUE ~ Long.MAX_VALUE间的长整数字符串
	 * @author stone
	 * @date 2017年7月31日
	 * @param string 需要判定的字符串
	 * @return true: 是长整数字符串; false: 不是长整数字符串
	 */
	public static boolean isLong(String string)
	{
		if (isNullOrEmpty(string)) return false;

		char f = string.charAt(0);
		if (f == '-')
		{
			if (string.length() > MIN_LONG_STRING.length()) return false;

			for (int n = 1; n < string.length(); n++)
			{
				if (isNumberChar(string.charAt(n))) continue;

				return false;
			}

			if (string.length() < MIN_LONG_STRING.length()) return true;
			for (int n = 1; n < string.length(); n++)
			{
				int mc = MIN_LONG_STRING.charAt(n);
				int sc = string.charAt(n);

				if (sc > mc) return false;
			}

			return true;
		}
		else return isPositiveLong(string);
	}

	/**
	 * 判定是否是正常0 ~ Long.MAX_VALUE间的长整数字符串
	 * @author stone
	 * @date 2017年7月31日
	 * @param string 需要判定的字符串
	 * @return true: 是长整数字符串; false: 不是长整数字符串
	 */
	public static boolean isPositiveLong(String string)
	{
		if (isNullOrEmpty(string) || string.length() > MAX_LONG_STRING.length()) return false;

		for (int n = 0; n < string.length(); n++)
		{
			if (isNumberChar(string.charAt(n))) continue;

			return false;
		}

		if (string.length() < MAX_LONG_STRING.length()) return true;

		for (int n = 0; n < string.length(); n++)
		{
			int mc = MAX_LONG_STRING.charAt(n);
			int sc = string.charAt(n);

			if (sc > mc) return false;
		}

		return true;
	}

	/**
	 * 是否是浮点数
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str)
	{
		if (StringUtil.isNullOrEmpty(str)) return false;

		int n = 0;
		if (str.charAt(0) == '-') n++;

		int pSize = 0;
		boolean p = false;
		for (; n < str.length(); n++)
		{
			char c = str.charAt(n);
			if (c == '.')
			{
				if (p) return false;
				p = true;
				continue;
			}

			if (!isNumberChar(c)) return false;

			if (!p) pSize++;
		}

		return pSize > 0;
	}

	/**
	 * 判定字符是否是数字
	 * @author stone
	 * @date 2017年7月31日
	 * @param c 字符
	 * @return true: 是数字; false: 不是数字
	 */
	public static boolean isNumberChar(char c)
	{
		return c >= '0' && c <= '9';
	}

	/**
	 * 是否是大写字母
	 * @author stone
	 * @date 2017年7月31日
	 * @param c 字符
	 * @return true: 大写字母; false: 不是大写字母
	 */
	public static boolean isUppercase(char c)
	{
		return c >= 'A' && c <= 'Z';
	}

	/**
	 * 是否是小写字母
	 * @author stone
	 * @date 2017年7月31日
	 * @param c 字符
	 * @return true: 小写字母; false：不是小写字母
	 */
	public static boolean isLowercase(char c)
	{
		return c >= 'a' && c <= 'z';
	}

	/**
	 * 是否是字母
	 * @author stone
	 * @date 2017年7月31日
	 * @param c
	 * @return
	 */
	public static boolean isLetter(char c)
	{
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	/**
	 * 是否是中文
	 * @author stone
	 * @date 2017年7月31日
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c)
	{
		return c >= 0x4e00 && c <= 0x9fa5;
	}

	/**
	 * 是否是特殊字符
	 * @author stone
	 * @date 2017年7月31日
	 * @param c
	 * @return
	 */
	public static boolean isSpecialChar(char c)
	{
		return !isLetter(c) && !isNumberChar(c) && !isChinese(c);
	}

	/**
	 * 
	 * 是否是到分钟的时间字符串，形如： HH:mm
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return true 是, false 不是(含空)
	 */
	public static boolean isTimeMinutesFormat(String str)
	{
		if (StringUtil.isNullOrEmpty(str)) return false;

		String[] split = str.split(":");
		if (split.length != 2) return false;

		String hourStr = split[0];// .trim();
		String minuStr = split[1];// .trim();
		if (!isPositiveInteger(hourStr) || !isPositiveInteger(minuStr)) return false;

		int hour = Integer.parseInt(hourStr);
		int minu = Integer.parseInt(minuStr);

		return hour >= 0 && hour <= 23 && minu >= 0 && minu <= 59;
	}

	/**
	 * 判断是否是11位手机号码
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return
	 */
	public static boolean isMobilePhoneNumber(String str)
	{
		if (isNullOrEmpty(str) || str.length() != 11 || !isPositiveLong(str)) return false;

		return str.charAt(0) == '1';
	}

	/**
	 * 获取一个字符串中的阿拉伯数字(含浮点型)
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return
	 */
	public static List<String> getAllRealNumberFromString(String str)
	{
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNullOrEmpty(str)) return list;

		StringBuffer buffer = new StringBuffer();
		for (int n = 0; n < str.length(); n++)
		{
			char c = str.charAt(n);

			if (isNumberChar(c))
			{
				buffer.append(c);
				continue;
			}

			if (c == '.')
			{
				if (buffer.length() == 0) continue;
				buffer.append(c);
				continue;
			}

			if (c == '-' && buffer.length() == 0)
			{
				buffer.append(c);
				continue;
			}

			addStrBuffNumberToList(buffer.toString(), list);
			buffer.delete(0, buffer.length());
		}

		addStrBuffNumberToList(buffer.toString(), list);
		return list;
	}
	
	private static void addStrBuffNumberToList(String strBuff, List<String> strList)
	{
		if (strBuff == null || strBuff.length() == 0) return;
		
		if (isDouble(strBuff)) strList.add(strBuff);
		else
		{
			String[] strs = strBuff.split("\\.");
			for(String strItem : strs)
			{
				if (isDouble(strItem)) strList.add(strItem);
			}
		}
	}
	
	/**
	 * 获取一个字符串中的第一组阿拉伯数字(含浮点型)
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return 第一组数字字符串，如果不存在这样的字符串，则返回空
	 */
	public static String getFirstRealNumberFromString(String str)
	{
		if (StringUtil.isNullOrEmpty(str)) return null;
		StringBuffer buffer = new StringBuffer();
		for (int n = 0; n < str.length(); n++)
		{
			char c = str.charAt(n);
			if (isNumberChar(c))
			{
				buffer.append(c);
				continue;
			}
			if (c == '.')
			{
				if (buffer.length() == 0) continue;
				buffer.append(c);
				continue;
			}
			if (c == '-' && buffer.length() == 0)
			{
				buffer.append(c);
				continue;
			}
			String numstr = getStrBuffNumberFirst(buffer.toString());
			if(numstr != null) return numstr;
			buffer.delete(0, buffer.length());
		}
		String numstr = getStrBuffNumberFirst(buffer.toString());
		if(numstr != null) return numstr;
		return null;
	}
	
	private static String getStrBuffNumberFirst(String strBuff)
	{
		if (strBuff == null || strBuff.length() == 0) return null;
		if (isDouble(strBuff)) return strBuff;
		else
		{
			String[] strs = strBuff.split("\\.");
			for(String strItem : strs)
			{
				if (isDouble(strItem)) return strItem;
			}
		}
		return null;
	}

	/**半角转全角
	 * @author stone
	 * @date 2017年7月31日
	 * @param input
	 * @return
	 */
	public static String toSBC(String input)
	{
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == ' ')
			{
				c[i] = '\u3000';
			}
			else if (c[i] < '\177')
			{
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * @author stone
	 * @date 2017年7月31日
	 * @param input
	 * @return
	 */
	public static String toDBC(String input)
	{
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == '\u3000')
			{
				c[i] = ' ';
			}
			else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
			{
				c[i] = (char) (c[i] - 65248);

			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 判断字符串包不包含中文字符
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return
	 */
	public static boolean containsChinese(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			char ic = str.charAt(i);
			if (isChinese(ic)) return true;
		}
		return false;
	}

	/**
	 * 判断字符串是不是全中文字符
	 * @author stone
	 * @date 2017年7月31日
	 * @param str
	 * @return
	 */
	public static boolean isAllChinese(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			char ic = str.charAt(i);
			if (!isChinese(ic)) return false;
		}
		return true;
	}

	/**
	 * 判断字符串是不是全英文字母
	 * @author stone
	 * @date 2017年7月31日
	 * @param letters
	 * @return
	 */
	public static boolean isLetters(String letters)
	{
		if (StringUtil.isNullOrEmpty(letters)) return false;

		for (int n = 0; n < letters.length(); n++)
		{
			char c = letters.charAt(n);
			if (!isLetter(c)) return false;
		}

		return true;
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
    
    /**
     * 过滤字符串中的特殊字符
     * 
     * @param str
     * @return
     * @author Nian.Li
     * <br>2017年9月23日
     */
    public static String patternMatcherStr(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";     
        Pattern p = Pattern.compile(regEx);        
        Matcher m = p.matcher(str);    
        
        return m.replaceAll("").trim();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getDiscountChars(266, 1000));
    }

}
