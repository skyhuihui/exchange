package com.zag.redis.trans;

/**
 * @author lei
 * @date 2016/12/21
 */
public interface ConvertorRegistry {


	/**
	 * 注册一个类型的值转换器,并同时注册一个转换器匹配策略
	 * @param convertor
	 * @param <V>
	 */
	<V> void registerConvertor(ConvertorMatcher matcher, ValueConvertor<V> convertor);

	/**
	 * 注册一个类型的值转换器,使用默认的转换器匹配策略(类型直接匹配),重复注册同一个 class,后一个注册的会覆盖前一个
	 * @param clazz
	 * @param convertor
	 * @param <V>
	 */
	<V> void registerConvertor(Class<V> clazz, ValueConvertor<V> convertor);

	/**
	 * 根据类型寻找对应的 convertor,先找默认匹配策略,再找自定义匹配策略
	 * @param clazz
	 * @return
	 */
	ValueConvertor<Object> findConvertor(Class clazz);

	/**
	 * 判断当前类型的属性是否受到转换器支持
	 * @param clazz 属性类型
	 * @return
	 */
	boolean isSupportedPropertyType(Class clazz);


}
