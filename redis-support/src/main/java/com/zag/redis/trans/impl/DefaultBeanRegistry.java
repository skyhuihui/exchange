package com.zag.redis.trans.impl;

import com.zag.core.property.PropertyDescriptorExtends;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zag.redis.trans.ConvertorRegistry;
import com.zag.redis.trans.BeanRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @date 2016/12/22
 */
public class DefaultBeanRegistry implements BeanRegistry {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ConvertorRegistry convertorRegistry;

	private Map<Class,List<PropertyDescriptorExtends>> properties = Maps.newConcurrentMap();

	@Override
	public List<PropertyDescriptorExtends> findProperties(Class beanClazz) {
		List<PropertyDescriptorExtends> result = properties.get(beanClazz);
		if(result==null){
			synchronized (beanClazz){
				registerObject(beanClazz);
				result = properties.get(beanClazz);
			}
		}
		return result;
	}

	@Override
	public <RO> RO newBeanInstance(Class<RO> beanClazz) {
		try {
			return beanClazz.newInstance();
		} catch (InstantiationException|IllegalAccessException e) {
			ReflectionUtils.handleReflectionException(e);
			//make compiler happy
			return null;
		}
	}

	@Override
	public void registerObject(Class beanClazz) {
		List<PropertyDescriptorExtends> result = Lists.newArrayList();
		try {
			BeanInfo bi = Introspector.getBeanInfo(beanClazz, Object.class);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				if(getConvertorRegistry().isSupportedPropertyType(pd.getPropertyType())){
					result.add(new PropertyDescriptorExtends(pd,beanClazz));
				}else{
					logger.warn("ro[clazz={}] prop[name={},type={}] is unsupported", beanClazz,pd.getName(),pd.getPropertyType());
				}
			}
		} catch (IntrospectionException e) {

		}
		properties.put(beanClazz, result);
	}

	@Override
	public ConvertorRegistry getConvertorRegistry() {
		return convertorRegistry;
	}

	public void setConvertorRegistry(ConvertorRegistry convertorRegistry) {
		this.convertorRegistry = convertorRegistry;
	}
}
