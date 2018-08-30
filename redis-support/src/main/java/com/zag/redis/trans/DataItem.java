package com.zag.redis.trans;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map.Entry;

/**
 * @author lei
 * @date 2016/12/21
 */
public class DataItem {

	private final String key;

	private final byte[] data;

	public DataItem(String key, byte[] data) {
		this.key = key;
		this.data = data;
	}

	public DataItem(Entry<byte[],byte[]> entry){
		this.key = new String(entry.getKey());
		this.data = entry.getValue();
	}

	public String getKey() {
		return key;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("key", key)
				.append("data", new String(data))
				.toString();
	}
}
