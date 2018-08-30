package com.zag.redis.trans.impl.convertor;

import com.zag.redis.trans.ConvertorRegistry;
import com.zag.redis.trans.DataItem;
import com.zag.redis.trans.Translator;
import com.zag.redis.trans.ValueConvertor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lei
 * @date 2016/12/22
 */
public abstract class AbstractMultiValueConvertor<V> implements ValueConvertor<V> {
	private ConvertorRegistry convertorRegistry;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected ConvertorRegistry getConvertorRegistry() {
		return convertorRegistry;
	}
	public AbstractMultiValueConvertor(ConvertorRegistry convertorRegistry) {
		this.convertorRegistry = convertorRegistry;
	}

	protected DataItem[] resolveItems(String key, Object value){
		return this.resolveItems(null, key, value);
	}

	protected DataItem[] resolveItems(String prefix, String propKey, Object value) {
		if(StringUtils.isNotBlank(prefix)){
			propKey = prefix + Translator.SEPERATOR + propKey;
		}
		ValueConvertor<Object> convertor = getConvertorRegistry().findConvertor(value.getClass());
		if(convertor==null){
			logger.warn("prop[name={},type={}] convertor not found",propKey,value.getClass().getSimpleName());
		}
		return convertor.toRedisData(propKey, value);
	}

}
