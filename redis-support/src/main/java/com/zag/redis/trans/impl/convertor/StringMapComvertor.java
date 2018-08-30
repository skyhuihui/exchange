package com.zag.redis.trans.impl.convertor;

import com.google.common.collect.Lists;
import com.zag.redis.trans.ConvertorRegistry;
import com.zag.redis.trans.DataItem;
import com.zag.redis.trans.Translator;
import com.zag.redis.trans.impl.StringMap;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @date 2016/12/22
 */
public class StringMapComvertor extends AbstractMultiValueConvertor<StringMap> {

	public StringMapComvertor(ConvertorRegistry convertorRegistry) {
		super(convertorRegistry);
	}

	@Override
	public DataItem[] toRedisData(String key, StringMap val) {
		if(MapUtils.isEmpty(val)){
			return new DataItem[0];
		}
		List<DataItem> items = Lists.newArrayList();
		for (Map.Entry<String, String> entry : val.entrySet()) {
			DataItem[] temp = resolveEntry(key, entry);
			items.addAll(Arrays.asList(temp));
		}
		return items.toArray(new DataItem[items.size()]);
	}

	private DataItem[] resolveEntry(String key, Map.Entry<String, String> entry) {
		Object value = entry.getValue();
		if(value==null) return new DataItem[0];
		String propKey = entry.getKey();
		if(StringUtils.isNotBlank(key)){
			propKey = key + Translator.SEPERATOR + propKey;
		}
		return new DataItem[]{new DataItem(propKey,entry.getValue().getBytes())};
	}



	@Override
	public StringMap toValue(Class<StringMap> clazz, String prefix, DataItem[] redisData) {
		boolean usePrefix = StringUtils.isNotBlank(prefix);
		prefix = usePrefix ? prefix + Translator.SEPERATOR : prefix;
		StringMap map = new StringMap();
		for (DataItem item : redisData) {
			String k = item.getKey();
			k = usePrefix ? k.replace(prefix,"") : k;
			String val = new String(item.getData());
			map.put(k,val);
		}
		return map;
	}
}
