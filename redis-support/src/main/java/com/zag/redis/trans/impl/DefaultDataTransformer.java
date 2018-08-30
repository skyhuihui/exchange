package com.zag.redis.trans.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.zag.redis.trans.DataTransformer;
import com.zag.redis.trans.DataItem;
import com.zag.redis.trans.Translator;
import org.apache.commons.collections.MapUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lei
 * @date 2016/12/22
 */
public class DefaultDataTransformer implements DataTransformer {
	@Override
	public DataItem[] redisData2Items(Map<byte[], byte[]> redisData) {
		if(MapUtils.isEmpty(redisData)){
			return new DataItem[0];
		}
		DataItem[] items = new DataItem[redisData.size()];
		Set<Map.Entry<byte[], byte[]>> entries = redisData.entrySet();
		int index = 0;
		for (Map.Entry<byte[], byte[]> entry : entries) {
			items[index++] = new DataItem(entry);
		}
		return items;
	}

	@Override
	public Map<String, DataItem[]> redisData2ItemsMap(Map<byte[], byte[]> redisData) {

		DataItem[] items = redisData2Items(redisData);

		Multimap<String, DataItem> multimap = MultimapBuilder.hashKeys().arrayListValues().build();
		for (DataItem item : items) {
			int index = item.getKey().indexOf(Translator.SEPERATOR);
			if(index >= 0){
				String key = item.getKey().substring(0,index);
				multimap.put(key,item);
			}else{
				multimap.put(item.getKey(),item);
			}
		}

		HashMap<String, DataItem[]> map = Maps.newHashMap();
		for (Map.Entry<String, Collection<DataItem>> entry : multimap.asMap().entrySet()) {
			map.put(entry.getKey(),entry.getValue().toArray(new DataItem[entry.getValue().size()]));
		}
		return map;

	}

	public static void main(String[] args) {
		System.out.println("name.id".substring(0,4));
	}

	@Override
	public Map<byte[], byte[]> items2RedisData(DataItem[] items) {
		Map<byte[],byte[]> result = Maps.newHashMap();
		for (DataItem item : items) {
			result.put(item.getKey().getBytes(),item.getData());
		}
		return result;
	}
}
