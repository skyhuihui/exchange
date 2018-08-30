package com.zag.redis.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;

/**
 * load ShardedJedisPool
 * @author stone(by lei)
 * @date 2017年8月13日
 * @reviewer 
 */
public class JedisPoolLoad {
	
    private static final Logger log = LoggerFactory.getLogger(JedisPoolLoad.class);
	private static JedisPool jedisPool;
    
    synchronized public static JedisPool jedisPool() {
        return jedisPool;
    }

    /**
     * 获取redis 连接池, 支持多host主机列表配置
     */
    synchronized public static JedisPool jedisPool(String localConfigPath) {
        if (jedisPool == null) {
            log.debug("开始初始化redis池对象");
            PropertiesConfiguration config = null;
            try {
                    // 从classpath加载配置文件
                    config = new PropertiesConfiguration(localConfigPath);

            } catch (Exception e) {
            	e.printStackTrace();
            	log.error("加载redis配置出错,-->", e.getMessage());
            }

            GenericObjectPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(config.getInt("redis.maxTotal"));
            poolConfig.setMaxIdle(config.getInt("redis.maxIdle"));
            poolConfig.setTimeBetweenEvictionRunsMillis(config.getLong("redis.timeBetweenEvictionRunsMillis"));
            poolConfig.setMinEvictableIdleTimeMillis(config.getLong("redis.minEvictableIdleTimeMillis"));
            poolConfig.setTestOnBorrow(config.getBoolean("redis.testOnBorrow"));

            log.debug("开始加载redis主机列表");
            String hosts = config.getString("redis.host"); 
            String ports = config.getString("redis.port"); 
            String [] hostArr = hosts.split("@");
            String [] portArr = ports.split("@");
            jedisPool = new JedisPool(poolConfig, hostArr[0].trim(), Integer.parseInt(portArr[0].trim()));
        }
        return jedisPool;
    }
}
