package com.zag.redis.trans.impl.convertor;

import com.zag.redis.trans.DataItem;
import com.zag.redis.trans.ValueConvertor;

import java.util.Objects;

/**
 * @author lei
 * @date 2016/12/21
 */
public abstract class AbstractSimpleValueConvertor<V> implements ValueConvertor<V> {


	@Override
	public DataItem[] toRedisData(String key, V val) {
		/**
		 * null 值将写入为空字符串
		 */
		if(val == null) return new DataItem[]{new DataItem(key,"".getBytes())};
		return new DataItem[]{new DataItem(key,val2String(val).getBytes())};
	}

	@Override
	public V toValue(Class<V> clazz, String key, DataItem[] items) {
		if(items==null||items.length==0)return null;
		if(items.length>1){
			throw new IllegalArgumentException("简单值数据项数组长度只能为1");
		}
		/**
		 * 空字符串将转换为 null
		 */
		String data = new String(items[0].getData());
		if(Objects.equals(data,"")) return null;
		return parseValue(data);
	}

	protected abstract V parseValue(String data);
	protected abstract String val2String(V val);

}
