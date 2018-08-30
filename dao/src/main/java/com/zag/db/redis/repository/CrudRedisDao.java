package com.zag.db.redis.repository;

import com.google.common.collect.Sets;
import com.zag.core.asserts.SystemAsserts;
import com.zag.redis.ShardedJedisCurdCommonRedisDao;
import com.zag.redis.bean.BaseRedisObject;
import com.zag.redis.util.RedisUtil;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.io.Serializable;
import java.util.*;

/**
 * 请继承此类
 * @author lei
 * @date 2017年8月13日
 * @reviewer 
 * @param <T>
 * @param <ID>
 */
public class CrudRedisDao<T extends BaseRedisObject<ID>, ID extends Serializable> extends ShardedJedisCurdCommonRedisDao<T, ID> {

	/**
	 * 将Set<bytes[]>转换为List<Long>
	 * @author stone
	 * @date 2017年8月17日
	 * @param set
	 * @return
	 */
	protected List<Long> bytesSet2LongList(Collection<byte[]> set){
		if(set == null)return Collections.emptyList();
		List<Long> result = new ArrayList<>(set.size());
		for (byte[] bs : set) {
			result.add(RedisUtil.byteArrayToLong(bs));
		}
		return result;
	}
	/**
	 * 将Set<bytes[]>的数据转换为Long后装入目标集合
	 * @author stone
	 * @date 2017年8月20日
	 * @param set
	 * @param target
	 * @return
	 */
	protected <TARGET extends Collection<Long>> TARGET bytesSet2TargetCollection(Collection<byte[]> set, TARGET target){
		SystemAsserts.notNull(target);
		if(set == null)return target;
		for (byte[] bs : set) {
			target.add(RedisUtil.byteArrayToLong(bs));
		}
		return target;
	}
	
	/**
	 * 以timstamp为score,将id放入sorted-set
	 * @author stone
	 * @date 2017年8月20日
	 * @param key
	 * @param id
	 * @return
	 */
	protected boolean zadd(String key, Date timestamp, Long id){
		return zadd(key, timestamp.getTime(), RedisUtil.toByteArray(id));
	}
	
	/**
	 * @Description: 以time为score 将string放入sorted-set
	 * @author mounan
	 * @time:2017年1月18日 下午4:58:39
	 * @param key
	 * @param timestamp
	 * @param str
	 * @return
	 */
	protected boolean zadd(String key, Date timestamp, String str){
		return zadd(key, timestamp.getTime(), RedisUtil.toByteArray(str));
	}
	
	
	/**
	 * 从一个保存Long值的sorted-set中,分页查询值,并转化为一个List<Long>返回,降序
	 * @author stone
	 * @date 2017年8月20日
	 * @param key
	 * @param page
	 * @param size
	 * @return
	 */
	protected List<Long> findIdListFromSortedSetRevrange(String key, Integer page, Integer size) {
		long start = page * size;//0
		long end = (page+1) * size -1;//10
		Set<byte[]> set = zrevrange(key, start , end);
		return bytesSet2LongList(set);
	}
	
	/**
	 * 配合pop使用
	 * @author stone
	 * @date 2017年9月17日
	 * @param listKey
	 * @param id
	 */
	protected void lpushToList(String listKey, ID id){
		//将足迹信息存入list,便于task持久化
		lpush(listKey, RedisUtil.toByteArray(id));
	}
	
	/**
	 * 配合push使用
	 * @author stone
	 * @date 2017年9月17日
	 * @param listKey
	 * @param size
	 * @return
	 */
	protected List<T> rpopFromList(String listKey, int size){
		long nowsize = llen(listKey);
		nowsize = Math.min(size, nowsize);
		List<Object> list = pipeRpop(listKey.getBytes(), nowsize);
		Set<byte[]> idset = Sets.newHashSet();
		for (Object o : list) {
			byte[] data = (byte[])o;
			idset.add(data);
		}
		return findByIds(idset);
	}
	
	protected List<Long> pipeLlen(List<String> keys){
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			ShardedJedisPipeline jedisPipeline = jedis.pipelined();
			for (String key : keys) {
				jedisPipeline.llen(key);
			}
			List lenth = jedisPipeline.syncAndReturnAll();
			return lenth;
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}
}

