package com.zag.redis.spi;

import com.zag.redis.util.RedisUtil;
import com.zag.redis.api.ComJedisRedis;
import org.apache.commons.collections.CollectionUtils;
import org.codelogger.utils.ArrayUtils;
import org.codelogger.utils.ValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author stone(by lei)
 * @date 2017年8月12日
 * @reviewer
 */
@SuppressWarnings("deprecation")
public class JedisRedis implements ComJedisRedis<Jedis> {

    @Autowired(required = false)
    private JedisPool jedisPool;

    @Override
    public Pool<Jedis> getPool() {
        return jedisPool;
    }

    /* (non-Javadoc)
     * @see com.redis.spi.ComJedisRedis#hget(java.lang.String, java.lang.String)
     */
    @Override
    public byte[] hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.ComJedisRedis#hdel(java.lang.String, java.lang.String)
	 */
    @Override
    public Long hdel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hset(java.lang.String, java.lang.String, byte[])
	 */
    @Override
    public long hset(String key, String field, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key.getBytes(), field.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hsetnx(java.lang.String, java.lang.String, byte[])
	 */
    @Override
    public boolean hsetnx(String key, String field, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hsetnx(key.getBytes(), field.getBytes(), value).intValue() == 1;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hmset(java.lang.String, java.util.Map)
	 */
    @Override
    public String hmset(String key, Map<byte[], byte[]> hash) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            System.out.println(key + ":" + hash);
            return jedis.hmset(key.getBytes(), hash);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hmget(java.lang.String, byte)
	 */
    @Override
    public List<byte[]> hmget(String key, byte[]... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key.getBytes(), fields);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hgetAll(java.lang.String)
	 */
    @Override
    public Map<byte[], byte[]> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hexists(java.lang.String, java.lang.String)
	 */
    @Override
    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#sadd(java.lang.String, byte[])
	 */
    @Override
    public Long sadd(String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#sismember(java.lang.String, byte[])
	 */
    @Override
    public boolean sismember(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#smembers(java.lang.String)
	 */
    @Override
    public Set<byte[]> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long incrby(String key, long increment) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incrBy(key, increment);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#srem(java.lang.String, byte[])
	 */
    @Override
    public Long srem(String key, byte[] members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key.getBytes(), members);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#del(java.lang.String)
	 */
    @Override
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#exists(java.lang.String)
	 */
    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zadd(java.lang.String, double, byte[])
	 */
    @Override
    public boolean zadd(String key, double score, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long result = jedis.zadd(key.getBytes(), score, member);
            return result == 1l || result == 0l;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrem(java.lang.String, byte)
	 */
    @Override
    public Long zrem(String key, byte[]... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key.getBytes(), members);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrem(java.lang.String, java.lang.String)
	 */
    @Override
    public Long zrem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key.getBytes(), member.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrem(java.lang.String, java.lang.String...)
	 */
    @Override
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key, members);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
         * @see com.redis.spi.ComJedisRedis#zRange(java.lang.String, long, long)
         */
    @Override
    public Set<byte[]> zRange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrange(java.lang.String, long, long)
	 */
    @Override
    public Set<byte[]> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrangeWithScore(java.lang.String, long, long)
	 */
    @Override
    public Set<Tuple> zrevrangeWithScore(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeWithScores(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<byte[]> zangeByScore(String key, String min, String max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrangeWithScores(java.lang.String, long, long)
	 */
    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeWithScores(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    //获取列表元素总数
    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zCard(java.lang.String)
	 */
    @Override
    public Long zCard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    //获取sort set score
    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zscore(java.lang.String, byte[])
	 */
    @Override
    public Double zscore(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zscoreToInt(java.lang.String, byte[])
	 */
    @Override
    public Integer zscoreToInt(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.intValue();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zscoreToLong(java.lang.String, byte[])
	 */
    @Override
    public Long zscoreToLong(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.longValue();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrank(java.lang.String, byte[])
	 */
    @Override
    public Long zrank(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrank(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zincrby(java.lang.String, double, byte[])
	 */
    @Override
    public Double zincrby(String key, double score, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key.getBytes(), score, member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#rpush(java.lang.String, byte[])
	 */
    @Override
    public Long rpush(String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#rpush(java.lang.String, byte)
	 */
    @Override
    public Long rpush(String key, byte[]... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key.getBytes(), strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#lpush(java.lang.String, byte)
	 */
    @Override
    public Long lpush(String key, byte[]... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key.getBytes(), strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long lpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long rpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#lrange(java.lang.String, long, long)
	 */
    @Override
    public List<byte[]> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ltrim(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#llen(java.lang.String)
	 */
    @Override
    public long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#lpop(java.lang.String)
	 */
    @Override
    public byte[] lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrangeByScore(java.lang.String, byte[], byte[])
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, byte[] max, byte[] min) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max, min);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min, int offset,
                                        int count) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis
                    .zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes(), offset, count);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zcount(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public long zcount(String key, String min, String max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long count = jedis.zcount(key.getBytes(), min.getBytes(), max.getBytes());
            return count == null ? 0 : count;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zremrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zcount(java.lang.String, double, double)
	 */
    @Override
    public Long zcount(String key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcount(key.getBytes(), min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrangeByScore(java.lang.String, double, double, int, int)
	 */
    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max, offset, count);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrangeByScore(java.lang.String, long, long)
	 */
    @Override
    public Set<String> zrangeByScore(String key, long min, long max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#expire(java.lang.String, int)
	 */
    @Override
    public void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key.getBytes(), seconds);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#expireAt(java.lang.String, java.lang.Long)
	 */
    @Override
    public void expireAt(String key, Long unixTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expireAt(key.getBytes(), unixTime);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#pipeHgetall(java.util.List)
	 */
    @Override
    public List<Object> pipeHgetall(List<String> ids) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.hgetAll(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.ComJedisRedis#pipeZadd(java.lang.String, java.util.String...)
     */
    @Override
    public void pipeSadd(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            jedisPipeline.sadd(key, members);
            jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
         * @see com.redis.spi.ComJedisRedis#pipeZadd(java.lang.String, double, java.util.List)
         */
    @Override
    public void pipeZadd(String key, double score, List<String> members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (String member : members) {
                jedisPipeline.zadd(key.getBytes(), score, member.getBytes());
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#pipeZcard(java.util.List)
	 */
    @Override
    public List<Object> pipeZcard(List<String> ids) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.zcard(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#pipeHmset(java.lang.String, java.util.List)
	 */
    @Override
    public void pipeHmset(String key, List<Map<byte[], byte[]>> hashs) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (Map<byte[], byte[]> hash : hashs) {
                jedisPipeline.hmset(key.getBytes(), hash);
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#hincrBy(java.lang.String, byte[], long)
	 */
    @Override
    public Long hincrBy(String key, byte[] field, long value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key.getBytes(), field, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#get(java.lang.String)
	 */
    @Override
    public byte[] get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#set(java.lang.String, byte[])
	 */
    @Override
    public void set(String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#setex(java.lang.String, int, byte[])
	 */
    @Override
    public void setex(String key, int seconds, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key.getBytes(), seconds, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#setnx(java.lang.String, byte[])
	 */
    @Override
    public boolean setnx(String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long r = jedis.setnx(key.getBytes(), value);
            return r != null && r.intValue() == 1;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#getTTL(java.lang.String)
	 */
    @Override
    public Long getTTL(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#incr(java.lang.String)
	 */
    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.ComJedisRedis#zrevrank(java.lang.String, byte[])
	 */
    @Override
    public Long zrevrank(String key, byte[] member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrank(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByRank(key, start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeRpop(byte[] key, long size) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (int i = 0; i < size; i++) {
                jedisPipeline.rpop(key);
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long lrem(String key, long count, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrem(key.getBytes(), count, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeGet(List<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (String key : keys) {
                jedisPipeline.get(key.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeHget(Collection<String> keys, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (String key : keys) {
                jedisPipeline.hget(key, field);
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeHincrBy(Map<String, Integer> keyToCounts, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (Map.Entry<String, Integer> entry : keyToCounts.entrySet()) {
                if (ValueUtils.getValue(entry.getValue()) != 0) {
                    jedisPipeline.hincrBy(entry.getKey(), field, entry.getValue());
                }
            }
            jedisPipeline.sync();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeZrem(String key, Collection<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            jedisPipeline.zrem(key, ArrayUtils.toArray(keys));
            jedisPipeline.sync();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeSet(List<String> keys, List<byte[]> values) {
        assert CollectionUtils.isNotEmpty(keys) && CollectionUtils.isNotEmpty(values) && keys.size() == values.size();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                jedisPipeline.set(RedisUtil.toByteArray(key), values.get(i));
            }
            jedisPipeline.sync();
        } finally {
            jedisPool.returnResource(jedis);
        }

    }

    @Override
    public void pipeHSet(Map<String, String> keyToValue, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline jedisPipeline = jedis.pipelined();
            for (Map.Entry<String, String> entry : keyToValue.entrySet()) {
                jedisPipeline.hset(entry.getKey(), field, entry.getValue());
            }
            jedisPipeline.sync();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}
