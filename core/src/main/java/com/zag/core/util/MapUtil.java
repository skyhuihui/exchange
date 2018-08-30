package com.zag.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	/**
	 * 将Oject对象转换成Map<String, String>
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> objectToMap(Object obj) {
		try {
			if (obj == null) {
	            return null;
	        }
	        Map<String, String> map = new HashMap<String, String>();
	        Field[] declaredFields = obj.getClass().getDeclaredFields();
	        for (Field field : declaredFields) {
	            field.setAccessible(true);
	            map.put(field.getName(), field.get(obj).toString());
	        }
	        return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }
}
