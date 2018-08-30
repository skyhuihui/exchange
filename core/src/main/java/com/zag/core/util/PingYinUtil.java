package com.zag.core.util;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音处理util
 * @author stone
 * @since 2017年11月16日
 * @usage 
 * @reviewer
 */
public class PingYinUtil {

	 /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * 
     * @param inputString
     * @return
     */
	public static String getPingYin(String inputString) {
		if(StringUtils.isEmpty(inputString)) return "";
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        StringBuffer pybf = new StringBuffer();
        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    pybf.append(temp[0]);
                } else
                	pybf.append(java.lang.Character.toString(input[i]));
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pybf.toString();
    }
    /**  
     * 获取汉字串拼音首字母，英文字符不变  
     * @param chinese 汉字串  
     * @return 汉语拼音首字母  
     */  
    public static String getFirstSpell(String chinese) {
		if(StringUtils.isEmpty(chinese)) return "";
        StringBuffer pybf = new StringBuffer();   
        char[] arr = chinese.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < arr.length; i++) {   
            if (arr[i] > 128) {   
                try {   
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
                    if (temp != null) {   
                        pybf.append(temp[0].charAt(0));   
                    }   
                } catch (BadHanyuPinyinOutputFormatCombination e) {   
                    e.printStackTrace();   
                }   
            } else {   
                pybf.append(arr[i]);   
            }   
        }   
        return pybf.toString().replaceAll("\\W", "").trim();   
    }   
    /**  
     * 获取汉字串拼音，英文字符不变  
     * @param chinese 汉字串  
     * @return 汉语拼音  
     */  
    public static String getFullSpell(String chinese) {   
    	if(StringUtils.isEmpty(chinese)) return "";
        StringBuffer pybf = new StringBuffer();   
        char[] arr = chinese.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < arr.length; i++) {   
            if (arr[i] > 128) {   
                try {   
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);   
                } catch (BadHanyuPinyinOutputFormatCombination e) {   
                    e.printStackTrace();   
                }   
            } else {   
                pybf.append(arr[i]);   
            }   
        }   
        return pybf.toString();   
    }
    
    /**
     * 获取字符串大写首字母
     * @author stone
     * @date 2017年11月16日
     * @param inputStr
     * @return
     */
    public static String getFirstLetter(String inputStr){
    	String str = getFirstSpell(inputStr);
    	return StringUtils.isNotEmpty(str)?String.valueOf(str.charAt(0)).toUpperCase():"";
    }
    
}
