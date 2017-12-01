package com.mileworks.modules.sys.shiro;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 由于在redis中存储session 的key 在存储的时候不方便带上userId相关信息，
 * 这里操作是单给独存放的key(userId) : value(session) 关系的缓存方便 状态标识
 * @author long-laptop
 * 2017.11.10
 */
public class RedisSessionTempDao {

	@Autowired
	private RedisTemplate redisTemplate;

	public void doCreate(String userId , LinkedHashMap<String, Serializable> map) {
		setShiroSession(userId, map);
	}
	
	public LinkedHashMap<String, Serializable> doRead(String userId) {
		return getShiroSession(userId);
	}
	
	private LinkedHashMap<String, Serializable> getShiroSession(String key) {
		return (LinkedHashMap<String, Serializable>) redisTemplate.opsForValue().get(key);
	}
	
	private void setShiroSession(String key, LinkedHashMap<String, Serializable> map) {
		redisTemplate.opsForValue().set(key, map);
		// 60分钟过期
		redisTemplate.expire(key, 60, TimeUnit.MINUTES);
	}
	
}
