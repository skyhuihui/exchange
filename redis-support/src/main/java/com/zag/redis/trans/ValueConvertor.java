package com.zag.redis.trans;

/**
 * @author lei
 * @date 2016/12/21
 */
public interface ValueConvertor<V> {

	/**
	 * 将值转换为数据项,key 为属性名
	 * 例如,对象结构为:
	 *
	 * {
	 *     "name":"zhangsan",
	 *     "role":{
	 *         "text":"admin",
	 *         "id":1
	 *     }
	 * }
	 * 对应传入本方法的参数为:(两个不同的 Convertor 实现)
	 *      "name","zhangsan"           =>      {["name"="zhangsan"]}
	 *      "role",{
	 *          "text":"admin",
	 *          "id":1
	 *      }                           =>      {["role.id"=1,"role.text"="admin"]}
	 *
	 *
	 * @param key
	 * @param val
	 * @return
	 */
	DataItem[] toRedisData(String key, V val);


	/**
	 * 将数据项转换为对应类型的值
	 * 例如,有如下数据项:
	 *
	 *      "name","zhangsan"
	 *      "role.text","admin"
	 *      "role.id",1
	 * 它应该分为两次调用本方法,
	 * 一次传递参数:"",{["name"="zhangsan"]}                      =>      "zhangsan"
	 * 一次传递参数:"role",{["role.id"=1,"role.text"="admin"]}    =>      {
	 *                                                                      "id":1
	 *                                                                      "text":"admin"
	 *                                                                  }
	 *
	 * @param clazz
	 * @param prefix
	 * @param redisData
	 * @return
	 */
	V toValue(Class<V> clazz, String prefix, DataItem[] redisData);
//
//	/**
//	 * 返回当前转换器和属性类型之间的匹配策略,如果返回 null,则使用简单匹配策略
//	 * @return
//	 */
//	ConvertorMatcher getMatcher();

}
