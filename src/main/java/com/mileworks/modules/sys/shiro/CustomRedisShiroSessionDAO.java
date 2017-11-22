package com.mileworks.modules.sys.shiro;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mileworks.common.utils.RedisKeys;

/**
 * 由于RedisShiroSessionDAO 是在shiro配置专门使用操作session中的类方法，SysSessionListener required a single bean
 * 而且本身方法都是protected，这里重写RedisShiroSessionDAO其中的方法，
 * 方便在业务类中去操作redis，并且不直接调用redisTemplate
 * 
 * @author long-laptop 
 * 2017.11.10
 */

@Component
public class CustomRedisShiroSessionDAO {

	@Autowired
	private RedisTemplate redisTemplate;

	// 创建session
	public Serializable doCreate(Session session) {
		final String key = RedisKeys.getShiroSessionKey(session.getId().toString());
		setShiroSession(key, session);
		return session.getId() ;
	}

	// 获取session
	public Session doReadSession(Serializable sessionId) {
		final String key = RedisKeys.getShiroSessionKey(sessionId.toString());
		return getShiroSession(key);
	}

	// 更新session
	public void doUpdate(Session session) {
		final String key = RedisKeys.getShiroSessionKey(session.getId().toString());
		setShiroSession(key, session);
	}

	// 删除session
	public void doDelete(Session session) {
		final String key = RedisKeys.getShiroSessionKey(session.getId().toString());
		redisTemplate.delete(key);
	}

	private Session getShiroSession(String key) {
		return (Session) redisTemplate.opsForValue().get(key);
	}

	private void setShiroSession(String key, Session session) {
		redisTemplate.opsForValue().set(key, session);
		// 60分钟过期
		redisTemplate.expire(key, 60, TimeUnit.MINUTES);
	}

}
