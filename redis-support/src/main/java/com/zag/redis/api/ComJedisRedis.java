package com.zag.redis.api;

import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ComJedisRedis<T> {

    public Pool<T> getPool();

    /**
     * Returns the value associated with field in the hash stored at key.
     */
    byte[] hget(String key, String field);

    /**
     * Removes the specified fields from the hash stored at key.
     * Specified fields that do not exist within this hash are ignored.
     * If key does not exist, it is treated as an empty hash and this command returns 0.
     */
    Long hdel(String key, String field);

    /**
     * Sets field in the hash stored at key to value.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists in the hash, it is overwritten.
     */
    long hset(String key, String field, byte[] value);

    /**
     * Sets field in the hash stored at key to value, only if field does not yet exist.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists, this operation has no effect.
     */
    boolean hsetnx(String key, String field, byte[] value);

    /**
     * Sets the specified fields to their respective values in the hash stored at key.
     * This command overwrites any existing fields in the hash.
     * If key does not exist, a new key holding a hash is created.
     */
    String hmset(String key, Map<byte[], byte[]> hash);

    /**
     * Returns the values associated with the specified fields in the hash stored at key.
     */
    List<byte[]> hmget(String key, byte[]... fields);

    /**
     * Returns all fields and values of the hash stored at key.
     * In the returned value, every field name is followed by its value, so the length of the reply is twice the size of the hash.
     */
    Map<byte[], byte[]> hgetAll(String key);

    /**
     * Returns if field is an existing field in the hash stored at key.
     */
    boolean hexists(String key, String field);

    /**
     * Add the specified members to the set stored at key.
     * Specified members that are already a member of this set are ignored.
     * If key does not exist, a new set is created before adding the specified members.
     * An error is returned when the value stored at key is not a set.
     */
    Long sadd(String key, byte[] value);

    /**
     * Returns if member is a member of the set stored at key.
     */
    boolean sismember(String key, byte[] member);

    /**
     * Returns all the members of the set value stored at key.
     * This has the same effect as running SINTER with one argument key.
     */
    Set<byte[]> smembers(String key);

    /**
     * Remove the specified members from the set stored at key.
     * Specified members that are not a member of this set are ignored.
     * If key does not exist, it is treated as an empty set and this command returns 0.
     * An error is returned when the value stored at key is not a set.
     */
    Long srem(String key, byte[] members);

    /**
     * Removes the specified keys. A key is ignored if it does not exist.
     */
    Long del(String key);

    /**
     * Returns if key exists.
     */
    boolean exists(String key);

    /**
     * Adds all the specified members with the specified scores to the sorted set stored at key.
     */
    boolean zadd(String key, double score, byte[] member);

    /**
     * Removes the specified members from the sorted set stored at key. Non existing members are ignored.
     */
    Long zrem(String key, byte[]... members);

    /**
     * {@linkplain #zrem(String, byte[]...)}
     */
    Long zrem(String key, String member);

	/**
	 * {@linkplain #zrem(String, byte[]...)}
	 */
	Long zrem(String key, String... members);

	/**
	 * Returns the specified range of elements in the sorted set stored at key.
	 * The elements are considered to be ordered from the lowest to the highest score.
	 * Lexicographical order is used for elements with equal score.
	 */
	Set<byte[]> zRange(String key, long start, long end);

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the highest to the lowest score.
     * Descending lexicographical order is used for elements with equal score.
     * Apart from the reversed ordering, ZREVRANGE is similar to {@linkplain #zRange(String, long, long)}.
     */
    Set<byte[]> zrevrange(String key, long start, long end);

    Set<Tuple> zrevrangeWithScore(String key, long start, long end);

    /**
     * Returns all the elements in the sorted set at key with a score between min and max (including elements with score equal to min or max).
     * The elements are considered to be ordered from low to high scores.
     * The elements having the same score are returned in lexicographical order (this follows from a property of the sorted set implementation in Redis and does not involve further computation).
     * <ul>
     * <li>
     * <span>ZRANGEBYSCORE zset (1 5</span><br/>
     * <span>Will return all elements with 1 < score <= 5 while:</span>
     * </li>
     * <li>
     * <span>ZRANGEBYSCORE zset (5 (10</span></br>
     * <span>Will return all the elements with 5 < score < 10 (5 and 10 excluded).</span>
     * </li>
     * </ul>
     */
    Set<byte[]> zangeByScore(String key, String min, String max);

    Set<Tuple> zrangeWithScores(String key, long start, long end);

    //获取列表元素总数
    Long zCard(String key);

    //获取sort set score
    Double zscore(String key, byte[] member);

    Integer zscoreToInt(String key, byte[] member);

    Long zscoreToLong(String key, byte[] member);

    Long zrank(String key, byte[] member);

    Double zincrby(String key, double score, byte[] member);

    Long rpush(String key, byte[] value);

    Long rpush(String key, byte[]... strings);

    Long lpush(String key, byte[]... strings);

    Long lpush(String key, String... strings);

    Long rpush(String key, String... strings);

    String ltrim(String key, long start, long end);

    List<byte[]> lrange(String key, long start, long end);

    long llen(String key);

    byte[] lpop(String key);

    Set<byte[]> zrevrangeByScore(String key, byte[] max, byte[] min);

    Set<byte[]> zrevrangeByScore(String key, String max, String min);

    Set<byte[]> zrevrangeByScore(String key, String max, String min, int offset, int count);

    long zcount(String key, String min, String max);

    /**
     * 根据评分删除数据
     */
    Long zremrangeByScore(String key, String start, String end);

    /**
     * 根据sorted-set中的排位删除数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author lei
     * @date 2017年8月17日
     */
    Long zremrangeByRank(String key, long start, long end);

    /**
     * 通过score范围获取成员数量
     */
    Long zcount(String key, double min, double max);

    Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

    Set<String> zrangeByScore(String key, String min, String max);

    Set<String> zrangeByScore(String key, long min, long max);

    void expire(String key, int seconds);

    void expireAt(String key, Long unixTime);

    List<Object> pipeHgetall(List<String> ids);

    void pipeSadd(String key, String... members);

    void pipeZadd(String key, double score, List<String> members);

    List<Object> pipeZcard(List<String> ids);

    /**
     * 管道存入hash
     *
     * @param key
     * @param hashs
     */
    void pipeHmset(String key, List<Map<byte[], byte[]>> hashs);

    /**
     * Increments the number stored at field in the hash stored at key by increment.
     * If key does not exist, a new key holding a hash is created.
     * If field does not exist the value is set to 0 before the operation is performed.
     */
    Long hincrBy(String key, byte[] field, long value);

    /**
     * Get the value of key.
     * If the key does not exist the special value nil is returned.
     * An error is returned if the value stored at key is not a string, because GET only handles string values.
     */
    byte[] get(String key);

    /**
     * Set key to hold the string value.
     * If key already holds a value, it is overwritten, regardless of its type.
     * Any previous time to live associated with the key is discarded on successful SET operation.
     */
    void set(String key, byte[] value);

    /**
     * Set key to hold the string value and set key to timeout after a given number of seconds.
     * This command is equivalent to executing the following commands:
     * <p>SET mykey value<br/>
     * EXPIRE mykey seconds</p>
     */
    void setex(String key, int seconds, byte[] value);

    /**
     * Set key to hold string value if key does not exist.
     * In that case, it is equal to SET.
     * When key already holds a value, no operation is performed.
     * SETNX is short for "SET if Not eXists".
     */
    boolean setnx(String key, byte[] value);

    /**
     * Returns the remaining time to live of a key that has a timeout.
     * This introspection capability allows a Redis client to check how many seconds a given key will continue to be part of the dataset.
     *
     * @return TTL in seconds, or a negative value.
     */
    Long getTTL(String key);

    /**
     * Increments the number stored at key by one.
     * If the key does not exist, it is set to 0 before performing the operation.
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     */
    Long incr(String key);

    /**
     * Returns the rank of member in the sorted set stored at key, with the scores ordered from high to low.
     * The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
     * Use {@linkplain #zrank(String, byte[])} to get the rank of an element with the scores ordered from low to high.
     */
    Long zrevrank(String key, byte[] member);


    /**
     * @param key
     * @param size
     * @return
     * @author lei
     * @date 2017年9月6日
     */
    List<Object> pipeRpop(byte[] key, long size);

    /**
     * 从list中移除value
     *
     * @param key
     * @param count
     * @param value
     * @return
     * @author lei
     * @date 2017年9月14日
     */
    Long lrem(String key, long count, byte[] value);

    Long incrby(String key, long increment);

    List<Object> pipeGet(List<String> keys);

    List<Object> pipeHget(Collection<String> keys, String field);

    void pipeHincrBy(Map<String, Integer> keyToCounts, String field);

    void pipeZrem(String key, Collection<String> keys);

    void pipeSet(List<String> keys, List<byte[]> values);

    void pipeHSet(Map<String, String> keyToValue, String field);

}
