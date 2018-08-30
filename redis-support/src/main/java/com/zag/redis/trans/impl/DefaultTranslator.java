package com.zag.redis.trans.impl;

import com.zag.core.property.PropertyDescriptorExtends;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zag.redis.bean.BaseRedisObject;
import com.zag.redis.trans.*;
import com.zag.redis.annotation.RedisWriteIgnore;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @date 2016/12/22
 */
public class DefaultTranslator implements Translator {

	private DataTransformer dataTransformer;

	private ConvertorRegistry convertorRegistry;

	private BeanRegistry beanRegistry;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public <RO> RO toObject(Class<RO> clazz, Map<byte[], byte[]> redisData) {
		BeanRegistry beanRegistry = getBeanRegistry();
		RO ro = beanRegistry.newBeanInstance(clazz);
		if(MapUtils.isEmpty(redisData)) {
			return ro;
		}
		fillObject(ro,redisData);

		return ro;
	}

	@Override
	public void fillObject(Object ro, Map<byte[], byte[]> redisData) {
		Map<String, DataItem[]> map = getDataTransformer().redisData2ItemsMap(redisData);
		if(logger.isTraceEnabled()){
			logger.trace("redis data src:");
			for (Map.Entry<String, DataItem[]> entry : map.entrySet()) {
				logger.trace("\t{} = {}",entry.getKey(),Arrays.toString(entry.getValue()));
			}
		}
		List<PropertyDescriptorExtends> pdes = beanRegistry.findProperties(ro.getClass());
		for (PropertyDescriptorExtends pde : pdes) {
			String name = pde.getName();
			DataItem[] vals = map.get(name);
			Class propertyType = pde.getPropertyType();
			Object value = getConvertorRegistry().findConvertor(propertyType).toValue(propertyType, name, vals);
			setValue(ro, pde, value);
		}
	}

	@Override
	public Map<byte[], byte[]> toRedisData(BaseRedisObject object) {

		if(object==null)return Maps.newHashMapWithExpectedSize(1);

		List<DataItem> data = resolveBean(object);
		return this.getDataTransformer().items2RedisData(data.toArray(new DataItem[data.size()]));
	}

	private List<DataItem> resolveBean(Object object) {
		List<PropertyDescriptorExtends> pdes = this.getBeanRegistry().findProperties(object.getClass());
		List<DataItem> data = Lists.newArrayList();
		for (PropertyDescriptorExtends pde : pdes) {
			Class<? extends Object> propertyType = pde.getPropertyType();
			Object val = getValue(object,pde);
			//约定值:void.class,特殊处理
			if(val == void.class)continue;
//			if(val==null)continue;
			String name = pde.getName();
			ValueConvertor<Object> convertor = this.getConvertorRegistry().findConvertor(propertyType);
			DataItem[] items = convertor.toRedisData(name, val);
			data.addAll(Arrays.asList(items));
		}
		return data;
	}

	@Override
	public DataTransformer getDataTransformer() {
		return dataTransformer;
	}

	@Override
	public ConvertorRegistry getConvertorRegistry() {
		return convertorRegistry;
	}

	@Override
	public BeanRegistry getBeanRegistry() {
		return beanRegistry;
	}

	public void setDataTransformer(DataTransformer dataTransformer) {
		this.dataTransformer = dataTransformer;
	}

	public void setConvertorRegistry(ConvertorRegistry convertorRegistry) {
		this.convertorRegistry = convertorRegistry;
	}

	public void setBeanRegistry(BeanRegistry beanRegistry) {
		this.beanRegistry = beanRegistry;
	}

	private void setValue(Object pojo, PropertyDescriptorExtends pde, Object value){
		if(value==null)return;
		Method writeMethod = pde.getWriteMethod();
		if(writeMethod==null){
			if(logger.isDebugEnabled()){
				logger.warn("ro[type={}] prop[name={}] not found setter",pojo.getClass(),pde.getName());
			}
			return;
		}
		ReflectionUtils.invokeMethod(writeMethod,pojo,value);
	}

	/**
	 * 约定:当返回 void.class 时 表示这个值不需要写入 redis
	 * @param pojo
	 * @param pde
	 * @return
	 */
	private Object getValue(Object pojo,PropertyDescriptorExtends pde) {
		Method readMethod = pde.getReadMethod();
		if (readMethod == null) {
			if (logger.isDebugEnabled()) {
				logger.warn("ro[type={}] prop[name={}] not found getter", pojo.getClass(), pde.getName());
			}
			return null;
		}
		RedisWriteIgnore ignore = pde.findAnnotation(RedisWriteIgnore.class);
		if(ignore==null){
			return ReflectionUtils.invokeMethod(readMethod, pojo);
		}

		if(ignore.type() == RedisWriteIgnore.IgnoreType.ALL){
			return void.class;//特殊的返回值
		}
		Object val = ReflectionUtils.invokeMethod(readMethod, pojo);
		if(ignore.type() == RedisWriteIgnore.IgnoreType.NULL && val == null){
			return void.class;//特殊的返回值
		}
		if(ignore.type() == RedisWriteIgnore.IgnoreType.NULL_OR_EMPTY_STRING && val instanceof String && StringUtils.isBlank((String)val)){
			return void.class;//特殊的返回值
		}
		return val;
	}



}
