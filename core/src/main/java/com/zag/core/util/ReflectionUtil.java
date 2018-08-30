package com.zag.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> findGenericParameter(Class<?> rawClz,int argIndex){
		Type[] clz = null;
		Type superType = rawClz.getGenericSuperclass();
		if(superType instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType)superType;
			clz = pt.getActualTypeArguments();
			if(clz.length-1 > argIndex){
				return (Class<T>)clz[argIndex];
			}
		}
		return null;
	}
	
	
}
