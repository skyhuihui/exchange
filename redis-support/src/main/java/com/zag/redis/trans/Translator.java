package com.zag.redis.trans;

import com.zag.redis.bean.BaseRedisObject;

import java.util.Map;

/**
 * @author lei
 * @date 2016/12/21
 */
public interface Translator {

	String SEPERATOR = ".";

	/**
	 * 将一个 redis hash 数据转换为一个 ro 对象
	 * @param redisData
	 * @return
	 */
	<RO> RO toObject(Class<RO> clazz, Map<byte[],byte[]> redisData);


	/**
	 * 将一个 redis hash 数据向对象中填充
	 * @param ro 被填充数据的对象
	 * @param redisData
	 * @return
	 */
	void fillObject(Object ro, Map<byte[],byte[]> redisData);



	/**
	 * 将一个 ro 对象转换为 redis hash 数据
	 * @param object
	 * @return
	 */
	Map<byte[],byte[]> toRedisData(BaseRedisObject object);


	DataTransformer getDataTransformer();

	ConvertorRegistry getConvertorRegistry();

	BeanRegistry getBeanRegistry();

}
