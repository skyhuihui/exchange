package com.zag.redis.util;

import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

public final class RedisUtil {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static byte[] toByteArray(Serializable id) {
		return id.toString().getBytes();
	}
    
    public static byte[] currentTs() {
        return toByteArray(System.currentTimeMillis());
    }

    public static byte[] toByteArray(Boolean value) {
        return value == null ? new byte[0] : (value ? "1" : "0").getBytes();
    }

    public static byte[] toByteArray(Byte value) {
        return value == null ? new byte[0] : Byte.toString(value).getBytes();
    }

    public static byte[] toByteArray(Short value) {
        return value == null ? new byte[0] : Short.toString(value).getBytes();
    }

    public static byte[] toByteArray(Integer value) {
        return value == null ? new byte[0] : Integer.toString(value).getBytes();
    }

    public static byte[] toByteArray(Long value) {
        return value == null ? new byte[0] : Long.toString(value).getBytes();
    }

    public static byte[] toByteArray(java.util.Date value) {
        if (value == null)
            return null;
        return Long.toString(value.getTime()).getBytes();
    }

    public static byte[] toByteArray(String value) {
        if (value == null)
            return new byte[0];
        return value.getBytes(DEFAULT_CHARSET);
    }

    public static byte[] toByteArray6(BigDecimal value) {
        return value == null ? new byte[0] : toByteArray(value.movePointRight(6).intValue());
    }

    public static BigDecimal byteArrayToBigDecimal6(byte[] b) {
        int intValue = byteArrayToInt(b);
        return new BigDecimal(intValue).movePointLeft(6);
    }

    public static byte[][] toByteArray(int[] value) {
        byte[][] result = new byte[value.length][];
        for (int i = 0; i < value.length; i++) {
            result[i] = toByteArray(value[i]);
        }
        return result;
    }


    public static byte[][] toByteArray(List<Integer> list) {
        byte[][] result = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = toByteArray(list.get(i));
        }
        return result;
    }
    
    public static byte[][] ListBytetoByteArray(List<byte[]> list) {
        byte[][] result = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = toByteArray(list.get(i));
        }
        return result;
    }

    public static byte[][] toByteArray(long[] value) {
        byte[][] result = new byte[value.length][];
        for (int i = 0; i < value.length; i++) {
            result[i] = toByteArray(value[i]);
        }
        return result;
    }

    public static Integer byteArrayToInt(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Integer.parseInt(new String(b));
    }

    public static Long byteArrayToLong(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Long.parseLong(new String(b));
    }

    public static Short byteArrayToShort(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Short.parseShort(new String(b));
    }

    public static Byte byteArrayToByte(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Byte.parseByte(new String(b));
    }

    public static Boolean byteArrayToBoolean(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : "1".equals(new String(b));
    }

    public static String byteArrayToStr(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : new String(b, DEFAULT_CHARSET);
    }

//    public static byte[] toBitByteArray(long value) {
//        byte[] byteNum = new byte[8];
//        for (int ix = 0; ix < 8; ++ix) {
//            int offset = 64 - (ix + 1) * 8;
//            byteNum[ix] = (byte) ((value >> offset) & 0xff);
//        }
//        return byteNum;
//    }
//
//    public static byte[] toBitByteArray(int value) {
//        byte[] byteNum = new byte[4];
//        for (int ix = 0; ix < 4; ++ix) {
//            int offset = 32 - (ix + 1) * 8;
//            byteNum[ix] = (byte) ((value >> offset) & 0xff);
//        }
//        return byteNum;
//    }
//
//
//    public static int bitByteArrayToInt(byte[] b) {
//        int num = 0;
//        for (int ix = 0; ix < b.length; ++ix) {
//            num <<= 8;
//            num |= (b[ix] & 0xff);
//        }
//        return num;
//    }
//
//    public static long bitByteArrayToLong(byte[] b) {
//        long num = 0;
//        for (int ix = 0; ix < b.length; ++ix) {
//            num <<= 8;
//            num |= (b[ix] & 0xff);
//        }
//        return num;
//    }

    /**
     * @param bytesValue
     */
    public static Date byteArrayToDate(byte[] bytesValue) {
        long longValue = byteArrayToLong(bytesValue);
        return new Date(longValue);
    }

    /**
     * @param bytesSet
     * @return
     */
    public static int[] bytesSetToIntArray(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return null;
        }

        int[] userIdArr = new int[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            userIdArr[count] = byteArrayToInt(userIdBytes);
            count++;
        }
        return userIdArr;
    }

    public static List<Integer> bytesSetToIntList(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return newArrayList();
        }

        List<Integer> userIdArr = new ArrayList<>(bytesSet.size());
        for (byte[] userIdBytes : bytesSet) {
            userIdArr.add(byteArrayToInt(userIdBytes));
        }
        return userIdArr;
    }

    public static Long[] bytesSetToLongArray(Set<byte[]> bytesSet) {
        if (isEmpty(bytesSet)) {
            return null;
        }
        Long[] idArr = new Long[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            idArr[count] = byteArrayToLong(userIdBytes);
            count++;
        }
        return idArr;
    }

    public static List<Long> bytesSetToLongList(Set<byte[]> bytesSet) {
        if (isEmpty(bytesSet)) {
            return newArrayList();
        }
        List<Long> idList = new ArrayList<Long>(bytesSet.size());
        for (byte[] userIdBytes : bytesSet) {
            idList.add(byteArrayToLong(userIdBytes));
        }
        return idList;
    }

    public static List<String> bytesSetToStringList(Set<byte[]> bytesSet) {
        if (isEmpty(bytesSet)) {
            return new ArrayList<>();
        }
        List<String> strings = new ArrayList<>(bytesSet.size());
        for (byte[] bytes : bytesSet) {
            strings.add(byteArrayToStr(bytes));
        }
        return strings;
    }

    public static String[] bytesSetToStringArray(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return null;
        }

        String[] userIdArr = new String[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            userIdArr[count] = byteArrayToStr(userIdBytes);
            count++;
        }
        return userIdArr;
    }

    public static List<Long> stringSetToLongList(Set<String> stringSet) {
        if (isEmpty(stringSet)) {
            return null;
        }
        List<Long> idList = new ArrayList<Long>(stringSet.size());
        for (String s : stringSet) {
            idList.add(Long.valueOf(s));
        }
        return idList;
    }
    public static List<String> stringSetToStringList(Set<String> stringSet) {
    	if (isEmpty(stringSet)) {
    		return new ArrayList<>();
    	}
    	List<String> idList = new ArrayList<String>(stringSet.size());
    	for (String s : stringSet) {
    		idList.add(String.valueOf(s));
    	}
    	return idList;
    }

}
