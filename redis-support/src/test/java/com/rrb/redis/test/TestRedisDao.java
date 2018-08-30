package com.rrb.redis.test;

import org.springframework.stereotype.Component;

import com.zag.redis.ShardedJedisCurdCommonRedisDao;

@Component
public class TestRedisDao extends ShardedJedisCurdCommonRedisDao<TestRo,String> {

}
