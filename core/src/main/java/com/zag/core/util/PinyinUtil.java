package com.zag.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    private static Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    public static HanyuPinyinOutputFormat pinyinOutputFormat;

    static {
        pinyinOutputFormat = new HanyuPinyinOutputFormat();
        pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写
        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // WITH_TONE_NUMBER//第几声
        pinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    public static String prepareKeyword(final String keyword) {
        if (keyword != null) {
            return keyword.replaceAll("[\\s 　]*", "");
        } else {
            return null;
        }
    }

    public static Map<String, String> getPingYin(String src) {
        if (src != null) {
            String keyword = prepareKeyword(src);
            char[] charArray = keyword.toCharArray();
            String[] pinyinArray = null;
            Map<String, String> tempCharMap = new HashMap<String, String>();
            Map<String, String> tempStrPinyinMap = new HashMap<String, String>();

            Map<String, String> resultPinyinMap = new HashMap<String, String>();
            String key_char = null;
            String key_str = null;

            try {
                for (int i = 0; i < charArray.length; i++) {
                    tempCharMap = new HashMap<String, String>();
                    // 判断是否为汉字字符函数
                    if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinArray =
                            PinyinHelper.toHanyuPinyinStringArray(charArray[i], pinyinOutputFormat);
                        if (pinyinArray != null) {
                            for (int j = 0; j < pinyinArray.length; j++) {
                                tempCharMap.put(pinyinArray[j], Character.toString(charArray[i]));
                            }
                        }
                    } else {
                        tempCharMap.put(Character.toString(charArray[i]),
                            Character.toString(charArray[i]));
                    }

                    Iterator<String> it_str = null;
                    Iterator<String> it_char = tempCharMap.keySet().iterator();
                    while (it_char.hasNext()) {
                        key_char = it_char.next();

                        if (i == 0) {
                            resultPinyinMap.put(key_char, "");
                        } else {
                            it_str = tempStrPinyinMap.keySet().iterator();
                            while (it_str.hasNext()) {
                                key_str = it_str.next();
                                resultPinyinMap.remove(key_str);

                                key_str += key_char;
                                resultPinyinMap.put(key_str, "");
                            }
                        }
                    }

                    tempStrPinyinMap.clear();
                    tempStrPinyinMap.putAll(resultPinyinMap);
                }

            } catch (Exception e) {
                logger.error("转码错误", e);
            } finally {
                tempCharMap.clear();
                tempStrPinyinMap.clear();
            }
            return resultPinyinMap;

        } else {
            return null;
        }
    }

    /**
     * 汉语拼音全拼
     * <p>未处理多音字情况
     *
     * @param chinese
     * @param isReplace
     * @return
     * @date 下午2:17:06  2014年12月23日
     */
    public static String getFullSpell(String chinese) {
        if (StringUtils.isBlank(chinese))
            return null;
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
        return pybf.toString().replaceAll("u:", "v");
    }

    /**
     * 获取汉字拼音首字母
     * <p>未处理多音字情况
     *
     * @param chinese
     * @return
     */
    public static String getFirstSpell(String chinese) {
        if (StringUtils.isBlank(chinese))
            return null;
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
        return pybf.toString().replaceAll("\\W", "").trim().replaceAll("u:", "v");
    }

    public static Map<String, String> getFirstLetter(String src) {
        if (src != null) {
            String keyword = prepareKeyword(src);
            char[] charArray = keyword.toCharArray();
            String[] pinyinArray = null;
            Map<Character, String> tempCharMap = new HashMap<Character, String>();
            Map<String, String> tempStrPinyinMap = new HashMap<String, String>();

            Map<String, String> resultPinyinMap = new HashMap<String, String>();
            Character key_char = null;
            String key_str = null;

            try {
                for (int i = 0; i < charArray.length; i++) {
                    tempCharMap = new HashMap<Character, String>();
                    // 判断是否为汉字字符函数
                    if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinArray =
                            PinyinHelper.toHanyuPinyinStringArray(charArray[i], pinyinOutputFormat);
                        if (pinyinArray != null) {
                            for (int j = 0; j < pinyinArray.length; j++) {
                                tempCharMap.put(pinyinArray[j].charAt(0),
                                    Character.toString(charArray[i]));
                            }
                        }
                    } else {
                        tempCharMap.put(charArray[i], Character.toString(charArray[i]));
                    }

                    Iterator<String> it_str = null;
                    Iterator<Character> it_char = tempCharMap.keySet().iterator();
                    while (it_char.hasNext()) {
                        key_char = it_char.next();

                        if (i == 0) {
                            resultPinyinMap.put(Character.toString(key_char), "");
                        } else {
                            it_str = tempStrPinyinMap.keySet().iterator();
                            while (it_str.hasNext()) {
                                key_str = it_str.next();
                                resultPinyinMap.remove(key_str);

                                key_str += key_char;
                                resultPinyinMap.put(key_str, "");
                            }
                        }
                    }

                    tempStrPinyinMap.clear();
                    tempStrPinyinMap.putAll(resultPinyinMap);
                }

            } catch (Exception e) {
                logger.error("转码错误", e);
            } finally {
                tempCharMap.clear();
                tempStrPinyinMap.clear();
            }
            return resultPinyinMap;

        } else {
            return null;
        }
    }

    public static Map<String, String> getPingYinAndChinese(String src) {
        if (src != null) {
            String keyword = prepareKeyword(src);
            char[] charArray = keyword.toCharArray();
            String[] pinyinArray = null;
            Map<String, String> tempCharMap = new HashMap<String, String>();
            Map<String, String> tempStrPinyinMap = new HashMap<String, String>();

            Map<String, String> resultPinyinMap = new HashMap<String, String>();
            String key_char = null;
            String key_str = null;

            try {
                for (int i = 0; i < charArray.length; i++) {
                    tempCharMap = new HashMap<String, String>();
                    // 判断是否为汉字字符函数
                    if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinArray =
                            PinyinHelper.toHanyuPinyinStringArray(charArray[i], pinyinOutputFormat);
                        if (pinyinArray != null) {
                            for (int j = 0; j < pinyinArray.length; j++) {
                                tempCharMap.put(pinyinArray[j], Character.toString(charArray[i]));
                            }
                        }
                    }
                    tempCharMap
                        .put(Character.toString(charArray[i]), Character.toString(charArray[i]));


                    Iterator<String> it_str = null;
                    Iterator<String> it_char = tempCharMap.keySet().iterator();
                    while (it_char.hasNext()) {
                        key_char = it_char.next();

                        if (i == 0) {
                            resultPinyinMap.put(key_char, "");
                        } else {
                            it_str = tempStrPinyinMap.keySet().iterator();
                            while (it_str.hasNext()) {
                                key_str = it_str.next();
                                resultPinyinMap.remove(key_str);

                                key_str += key_char;
                                resultPinyinMap.put(key_str, "");
                            }
                        }
                    }

                    tempStrPinyinMap.clear();
                    tempStrPinyinMap.putAll(resultPinyinMap);
                }

            } catch (Exception e) {
                logger.error("转码错误", e);
            } finally {
                tempCharMap.clear();
                tempStrPinyinMap.clear();
            }
            return resultPinyinMap;

        } else {
            return null;
        }
    }

    public static void main(String[] args) {

//		System.out.println("成长a潜伏共长");
//		getPingYin("长成潜伏共");
//		Iterator<String> it = (new PinyinUtil()).getFirstLetter("长安").keySet().iterator();
//		while (it.hasNext()) {
//			System.out.println("" + it.next());
//		}
    }

}
