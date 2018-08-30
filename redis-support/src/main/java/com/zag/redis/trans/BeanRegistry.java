package com.zag.redis.trans;

import com.zag.core.property.PropertyDescriptorExtends;

import java.util.List;

/**
 * @author lei
 * @date 2016/12/22
 */
public interface BeanRegistry {

	/**
	 * 根据ro对象获取其属性描述符扩展,首先从缓存中查找,如果没有找到,则注册一次并缓存,然后获取
	 * @param beanClazz
	 * @return
	 */
	List<PropertyDescriptorExtends> findProperties(Class  beanClazz);

	/**
	 *  创建一个 ro 新对象
	 * @param beanClazz
	 * @param <RO>
	 * @return
	 */
	<RO> RO newBeanInstance(Class<RO> beanClazz);

	/**
	 * 注册 ro 类,使其属性描述符得到缓存
	 * @param beanClazz
	 */
	void registerObject(Class beanClazz);


	ConvertorRegistry getConvertorRegistry();

}
