package com.zag.redis.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.zag.redis.util.RedisUtil;

import redis.clients.util.JedisByteHashMap;

/**
 * Redis对象map,使用序列化和反序列化Redis对象
 */
public class RedisObjMap {

    /**
     * 序列化Map
     */
    private JedisByteHashMap serialMap;

    /**
     * @return the serialMap
     */
    public JedisByteHashMap getSerialMap() {
        return serialMap;
    }

    /**
     * @param serialMap the serialMap to set
     */
    public void setSerialMap(JedisByteHashMap serialMap) {
        this.serialMap = serialMap;
    }

    public RedisObjMap() {
        this(null);
    }

    public RedisObjMap(Map<byte[], byte[]> map) {
        if (map == null) {
            serialMap = new JedisByteHashMap();
        } else {
            if (map instanceof JedisByteHashMap) {
                serialMap = (JedisByteHashMap) map;
            } else {
                serialMap = new JedisByteHashMap();
                serialMap.putAll(map);
            }
        }
    }

    // bytes
    protected void put(String key, byte[] value) {
        if (key != null) {
            serialMap.put(key.getBytes(), value);
        }
    }

    public byte[] getBytes(String key) {
        return key == null || serialMap == null ? null : serialMap.get(key.getBytes());
    }

    // boolean
    public void put(String key, Boolean value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Boolean getBoolean(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToBoolean(bytesValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Boolean booleanValue = getBoolean(key);
        return booleanValue == null ? defaultValue : booleanValue;
    }

    // byte
    public void put(String key, Byte value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Byte getByte(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToByte(bytesValue);
    }

    public byte getByte(String key, byte defaultValue) {
        Byte byteValue = getByte(key);
        return byteValue == null ? defaultValue : byteValue;
    }

    // short
    public void put(String key, Short value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Short getShort(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToShort(bytesValue);
    }

    public short getShort(String key, short defaultValue) {
        Short shortValue = getShort(key);
        return shortValue == null ? defaultValue : shortValue;
    }

    // int
    public void put(String key, Integer value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Integer getInt(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToInt(bytesValue);
    }

    public int getInt(String key, int defaultValue) {
        Integer integerValue = getInt(key);
        return integerValue == null ? defaultValue : integerValue;
    }

    // long
    public void put(String key, Long value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Long getLong(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToLong(bytesValue);
    }

    public long getLong(String key, long defaultValue) {
        Long longValue = getLong(key);
        return longValue == null ? defaultValue : longValue;
    }

    // date
    public void put(String key, Date value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public Date getDate(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToDate(bytesValue);
    }

    // String
    public void put(String key, String value) {
        put(key, RedisUtil.toByteArray(value));
    }

    public String getString(String key) {
        byte[] bytesValue = getBytes(key);
        return bytesValue == null ? null : RedisUtil.byteArrayToStr(bytesValue);
    }

    public String getString(String key, String defaultValue) {
        String value = getString(key);
        return value == null ? defaultValue : value;
    }

    // BigDecimal

    /**
     * 大数字使用long方式存储小数点左边的整数
     */
    public void put(String key, BigDecimal value) {
        put(key, value, 0);
    }

    /**
     * 大数字使用long方式存储小数点左边的整数
     */
    public void put(String key, BigDecimal value, int movePointRight) {
        if (value != null) {
            if (movePointRight != 0) {
                value = value.movePointRight(movePointRight);
            }
            put(key, value.longValue());
        } else {
            put(key, new byte[0]);
        }
    }

    /**
     * @param key
     * @return
     */
    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, 0);
    }

    /**
     * @param key
     * @return
     */
    public BigDecimal getBigDecimal(String key, int movePointLeft) {
        Long longValue = getLong(key);
        return longValue == null ? null : new BigDecimal(longValue).movePointLeft(movePointLeft);
    }

    /**
     * 设置 redisobj 子对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putObj(String typeKey, BaseRedisObject obj) {
        if (obj != null) {
            this.put(typeKey, obj.getClass().getName());
            serialMap.putAll(obj.toMap());
        }
    }

    /**
     * 获取子对象
     *
     * @param typeKey 对象类型存储的键
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public BaseRedisObject getObj(String typeKey) {
        BaseRedisObject obj = null;
        String classType = this.getString(typeKey);
        if (classType != null) {
            try {
                Class clazz = Class.forName(classType);
                obj = (BaseRedisObject) clazz.newInstance();
                obj.fromMap(serialMap);
            } catch (Exception e) {
                throw new RuntimeException("获取redis对象[" + classType + "]出错", e);
            }
        }
        return obj;
    }

}
