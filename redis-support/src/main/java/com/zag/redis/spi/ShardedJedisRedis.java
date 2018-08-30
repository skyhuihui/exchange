package com.zag.redis.spi;

import com.zag.redis.api.ComJedisRedis;
import com.zag.redis.util.RedisUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.codelogger.utils.ArrayUtils;
import org.codelogger.utils.ValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

/**
 * @author stone(by lei)
 * @date 2017年8月12日
 * @reviewer
 */
@SuppressWarnings("deprecation")
public class ShardedJedisRedis implements ComJedisRedis<ShardedJedis> {

    @Autowired(required = false)
    protected ShardedJedisPool shardedJedisPool;

    @Override
    public Pool<ShardedJedis> getPool() {
        return shardedJedisPool;
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hget(java.lang.String, java.lang.String)
     */
    @Override
    public byte[] hget(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hget(key.getBytes(), field.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long incrby(String key, long increment) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.incrBy(key, increment);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hdel(java.lang.String, java.lang.String)
	 */
    @Override
    public Long hdel(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hdel(key.getBytes(), field.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hset(java.lang.String, java.lang.String, byte[])
	 */
    @Override
    public long hset(String key, String field, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hset(key.getBytes(), field.getBytes(), value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hsetnx(java.lang.String, java.lang.String, byte[])
	 */
    @Override
    public boolean hsetnx(String key, String field, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hsetnx(key.getBytes(), field.getBytes(), value).intValue() == 1;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hmset(java.lang.String, java.util.Map)
	 */
    @Override
    public String hmset(String key, Map<byte[], byte[]> hash) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hmset(key.getBytes(), hash);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hmget(java.lang.String, byte)
	 */
    @Override
    public List<byte[]> hmget(String key, byte[]... fields) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hmget(key.getBytes(), fields);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hgetAll(java.lang.String)
	 */
    @Override
    public Map<byte[], byte[]> hgetAll(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hgetAll(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#hexists(java.lang.String, java.lang.String)
	 */
    @Override
    public boolean hexists(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hexists(key.getBytes(), field.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#sadd(java.lang.String, byte[])
	 */
    @Override
    public Long sadd(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.sadd(key.getBytes(), value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#sismember(java.lang.String, byte[])
	 */
    @Override
    public boolean sismember(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.sismember(key.getBytes(), member);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#smembers(java.lang.String)
	 */
    @Override
    public Set<byte[]> smembers(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.smembers(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#srem(java.lang.String, byte[])
	 */
    @Override
    public Long srem(String key, byte[] members) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.srem(key.getBytes(), members);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#del(java.lang.String)
	 */
    @Override
    public Long del(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.del(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#exists(java.lang.String)
	 */
    @Override
    public boolean exists(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.exists(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#zadd(java.lang.String, double, byte[])
	 */
    @Override
    public boolean zadd(String key, double score, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long result = jedis.zadd(key.getBytes(), score, member);
            return result == 1l || result == 0l;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#zrem(java.lang.String, byte)
	 */
    @Override
    public Long zrem(String key, byte[]... members) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrem(key.getBytes(), members);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrem(java.lang.String, java.lang.String)
	 */
    @Override
    public Long zrem(String key, String member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrem(key.getBytes(), member.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    @Override
    public Long zrem(String key, String... members) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrem(key, members);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
         * @see com.redis.spi.a#zRange(java.lang.String, long, long)
         */
    @Override
    public Set<byte[]> zRange(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrange(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrange(java.lang.String, long, long)
	 */
    @Override
    public Set<byte[]> zrevrange(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrevrange(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrangeWithScore(java.lang.String, long, long)
	 */
    @Override
    public Set<Tuple> zrevrangeWithScore(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrevrangeWithScores(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<byte[]> zangeByScore(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrangeWithScores(java.lang.String, long, long)
	 */
    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrangeWithScores(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    //获取列表元素总数
    /* (non-Javadoc)
	 * @see com.redis.spi.a#zCard(java.lang.String)
	 */
    @Override
    public Long zCard(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zcard(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    //获取sort set score
    /* (non-Javadoc)
	 * @see com.redis.spi.a#zscore(java.lang.String, byte[])
	 */
    @Override
    public Double zscore(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zscore(key.getBytes(), member);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zscoreToInt(java.lang.String, byte[])
	 */
    @Override
    public Integer zscoreToInt(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.intValue();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.a#zscoreToLong(java.lang.String, byte[])
	 */
    @Override
    public Long zscoreToLong(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.longValue();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrank(java.lang.String, byte[])
	 */
    @Override
    public Long zrank(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrank(key.getBytes(), member);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zincrby(java.lang.String, double, byte[])
	 */
    @Override
    public Double zincrby(String key, double score, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zincrby(key.getBytes(), score, member);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.a#rpush(java.lang.String, byte[])
	 */
    @Override
    public Long rpush(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.rpush(key.getBytes(), value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#rpush(java.lang.String, byte)
	 */
    @Override
    public Long rpush(String key, byte[]... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.rpush(key.getBytes(), strings);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#lpush(java.lang.String, byte)
     */
    @Override
    public Long lpush(String key, byte[]... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.lpush(key.getBytes(), strings);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long lpush(String key, String... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.lpush(key, strings);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long rpush(String key, String... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.rpush(key, strings);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public String ltrim(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.ltrim(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#lrange(java.lang.String, long, long)
	 */
    @Override
    public List<byte[]> lrange(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.lrange(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#llen(java.lang.String)
	 */
    @Override
    public long llen(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.llen(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#lpop(java.lang.String)
	 */
    @Override
    public byte[] lpop(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.lpop(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrangeByScore(java.lang.String, byte[], byte[])
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, byte[] max, byte[] min) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max, min);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min, int offset,
                                        int count) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis
                    .zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes(), offset, count);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zcount(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public long zcount(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long count = jedis.zcount(key.getBytes(), min.getBytes(), max.getBytes());
            return count == null ? 0 : count;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zremrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zremrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zremrangeByRank(key.getBytes(), start, end);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zcount(java.lang.String, double, double)
	 */
    @Override
    public Long zcount(String key, double min, double max) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zcount(key.getBytes(), min, max);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrangeByScore(java.lang.String, double, double, int, int)
	 */
    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrangeByScore(key, min, max, offset, count);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrangeByScore(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrangeByScore(java.lang.String, long, long)
	 */
    @Override
    public Set<String> zrangeByScore(String key, long min, long max) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#expire(java.lang.String, int)
	 */
    @Override
    public void expire(String key, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.expire(key.getBytes(), seconds);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#expireAt(java.lang.String, java.lang.Long)
	 */
    @Override
    public void expireAt(String key, Long unixTime) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.expireAt(key.getBytes(), unixTime);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#pipeHgetall(java.util.List)
	 */
    @Override
    public List<Object> pipeHgetall(List<String> ids) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.hgetAll(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#pipeZadd(java.lang.String, java.util.String...)
     */
    @Override
    public void pipeSadd(String key, String... members) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            jedisPipeline.sadd(key, members);
            jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
     * @see com.redis.spi.a#pipeZadd(java.lang.String, double, java.util.List)
     */
    @Override
    public void pipeZadd(String key, double score, List<String> members) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String member : members) {
                jedisPipeline.zadd(key.getBytes(), score, member.getBytes());
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    /* (non-Javadoc)
	 * @see com.redis.spi.a#pipeZcard(java.util.List)
	 */
    @Override
    public List<Object> pipeZcard(List<String> ids) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.zcard(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#pipeHmset(java.lang.String, java.util.List)
	 */
    @Override
    public void pipeHmset(String key, List<Map<byte[], byte[]>> hashs) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (Map<byte[], byte[]> hash : hashs) {
                jedisPipeline.hmset(key.getBytes(), hash);
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#hincrBy(java.lang.String, byte[], long)
	 */
    @Override
    public Long hincrBy(String key, byte[] field, long value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.hincrBy(key.getBytes(), field, value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#get(java.lang.String)
	 */
    @Override
    public byte[] get(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.get(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#set(java.lang.String, byte[])
	 */
    @Override
    public void set(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key.getBytes(), value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#setex(java.lang.String, int, byte[])
	 */
    @Override
    public void setex(String key, int seconds, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.setex(key.getBytes(), seconds, value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#setnx(java.lang.String, byte[])
	 */
    @Override
    public boolean setnx(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long r = jedis.setnx(key.getBytes(), value);
            return r != null && r.intValue() == 1;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#getTTL(java.lang.String)
	 */
    @Override
    public Long getTTL(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.ttl(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#incr(java.lang.String)
	 */
    @Override
    public Long incr(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.incr(key.getBytes());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /* (non-Javadoc)
	 * @see com.redis.spi.a#zrevrank(java.lang.String, byte[])
	 */
    @Override
    public Long zrevrank(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.zrevrank(key.getBytes(), member);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeRpop(byte[] key, long size) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (int i = 0; i < size; i++) {
                jedisPipeline.rpop(key);
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long lrem(String key, long count, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            return jedis.lrem(key.getBytes(), count, value);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeGet(List<String> keys) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String key : keys) {
                jedisPipeline.get(key.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<Object> pipeHget(Collection<String> keys, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String key : keys) {
                jedisPipeline.hget(key, field);
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeHincrBy(Map<String, Integer> keyToCounts, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (Map.Entry<String, Integer> entry : keyToCounts.entrySet()) {
                if (ValueUtils.getValue(entry.getValue()) != 0) {
                    jedisPipeline.hincrBy(entry.getKey(), field, entry.getValue());
                }
            }
            jedisPipeline.sync();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeZrem(String key, Collection<String> members) {
        if (CollectionUtils.isEmpty(members)) return;
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            jedisPipeline.zrem(key, ArrayUtils.toArray(members));
            jedisPipeline.sync();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }


    @Override
    public void pipeSet(List<String> keys, List<byte[]> values) {
        assert CollectionUtils.isNotEmpty(keys) && CollectionUtils.isNotEmpty(values) && keys.size() == values.size();
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                jedisPipeline.set(RedisUtil.toByteArray(key), values.get(i));
            }
            jedisPipeline.sync();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void pipeHSet(Map<String, String> keyToValue, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (Map.Entry<String, String> entry : keyToValue.entrySet()) {
                jedisPipeline.hset(entry.getKey(), field, entry.getValue());
            }
            jedisPipeline.sync();
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }
}
