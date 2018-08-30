package com.zag.db.redis.repository;

import com.zag.redis.spi.ShardedJedisRedis;
import com.zag.redis.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * 用于操作简单数据的redis-dao,例如序列号池,token等
 * @author stone
 * @since 2017年8月20日
 * @usage 
 * @reviewer
 */
@Repository
public class SimpleDataRedisDao extends ShardedJedisRedis {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected String getset(String key, String newVal) {
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			return jedis.getSet(key, newVal);
		}
	}
	protected List<Long> bytesSet2LongList(Collection<byte[]> set){
		if(set == null)return Collections.emptyList();
		List<Long> result = new ArrayList<>(set.size());
		for (byte[] bs : set) {
			result.add(RedisUtil.byteArrayToLong(bs));
		}
		return result;
	}
}
