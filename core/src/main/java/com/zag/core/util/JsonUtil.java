package com.zag.core.util;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.codelogger.utils.MathUtils;
import org.codelogger.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用jackson
 * Created @ClassName: JsonUtil By @author stone
 * @date 2017年7月12日 上午10:58:35
 * @reviewer shutao.gong
 */ 
public class JsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T json2Obj(String jsonStr, Class<T> clazz) {
        if (jsonStr == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Json反序列化出错", e);
        }
    }

 	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static <T> T json2Obj(String content,Class<T> clazzItem, Class ... classes) {
         if (StringUtils.isBlank(content)) {
             return null;
         }
         JavaType javaType  = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);   
         try {
             return OBJECT_MAPPER.readValue(content, javaType);
         } catch (Exception e) {
             throw new RuntimeException("Json反序列化出错", e);
         }
 	}
 	
    public static String obj2Json(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Json序列化出错", e);
        }
    }

    public static void printObjectJsonDemo(Object obj) {
        fillValueToField(obj, true);
    }

    private static void fillValueToField(Object obj, Boolean isFirst) {
        if (obj == null) {
        	logger.debug("null");
        } else {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String fieldName = declaredField.getName();
                if (!Modifier.isFinal(declaredField.getModifiers())) {
                    declaredField.setAccessible(true);
                    Type genericType = declaredField.getGenericType();
                    try {
                        Object value = declaredField.get(obj);
                        if (value == null) {
                            if (genericType == Integer.class || genericType == int.class) {
                                value = MathUtils.randomInt();
                            } else if (genericType == Long.class || genericType == long.class) {
                                value = MathUtils.randomLong();
                            } else if (genericType == Double.class || genericType == double.class) {
                                value = MathUtils.randomDouble();
                            } else if (genericType == String.class) {
                                value = "valueOf" + StringUtils.firstCharToUpperCase(fieldName);
                            } else if (genericType == Boolean.class
                                || genericType == boolean.class) {
                                value = MathUtils.randomInt() % 2 == 0;
                            } else if (genericType == Byte.class || genericType == byte.class) {
                                value =
                                    String.valueOf(StringUtils.getRandomAlphabetic()).getBytes()[0];
                            } else if (genericType == Short.class || genericType == short.class) {
                                value = (short) MathUtils.randomInt(1, 9);
                            } else if (genericType == Character.class
                                || genericType == char.class) {
                                value = StringUtils.getRandomAlphabetic();
                            } else if (genericType == BigDecimal.class) {
                                value = new BigDecimal(MathUtils.randomInt());
                            } else if (genericType == List.class) {
                                value = newArrayList();
                            } else if (genericType == Set.class) {
                                value = newHashSet();
                            } else if (genericType == Map.class) {
                                value = newHashMap();
                            } else if (genericType == Queue.class) {
                                value = new ConcurrentLinkedQueue<>();
                            }
                            if (value == null && isFirst) {
                                try {
                                    value = Class.forName(genericType.toString().split("\\s")[1]).newInstance();
                                    fillValueToField(value, false);
                                } catch (Exception ignored) {
                                    ignored.printStackTrace();
                                }
                            }
                        } else if (isFirst) {
                            try {
                                fillValueToField(value, false);
                            } catch (Exception ignored) {
                                ignored.printStackTrace();
                            }
                        }
                        if (value != null) {
                            declaredField.set(obj, value);
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        }
    }

}
