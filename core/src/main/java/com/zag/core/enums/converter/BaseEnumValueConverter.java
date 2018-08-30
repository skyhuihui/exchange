package com.zag.core.enums.converter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import javax.persistence.AttributeConverter;

import com.zag.core.exception.SystemException;
import com.zag.core.enums.EnumerableValue;

/**
 * 枚举属性转换器基类,将枚举的value转换为数据库的int值,使用方式见:
 * 
 * @see com.rrb.core.common.model.user.SommelierStatus
 * 
 * @param <E>
 *            枚举类
 * @author stone 2017年5月6日
 */
public abstract class BaseEnumValueConverter<E extends EnumerableValue> implements AttributeConverter<E, Integer> {

	private Class<E> clz;
	private Method method;

	@Override
	public Integer convertToDatabaseColumn(EnumerableValue attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public E convertToEntityAttribute(Integer dbData) {
		return valueOf(dbData);
	}

	@SuppressWarnings("unchecked")
	public BaseEnumValueConverter() {
		try {
			clz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			method = clz.getMethod("values");
		} catch (Exception e) {
			throw new SystemException("反射失败", e);
		}
	}

	/**
	 * 根据value获取对应枚举
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E valueOf(Integer value) {
		if (value == null) return null;
		try {
			method = clz.getMethod("values");
			for (E e : (E[]) method.invoke(null)) 
				if (e.getValue() == value) 
					return e;
			return null;
		} catch (Exception e) {
			throw new SystemException("反射失败", e);
		}
	}
}
