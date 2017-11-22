package com.mileworks.modules.sys.shiro.filter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.mileworks.common.utils.Constant;
import com.mileworks.modules.sys.shiro.CustomRedisShiroSessionDAO;
import com.mileworks.modules.sys.shiro.RedisSessionTempDao;
import com.mileworks.modules.sys.shiro.ShiroUtils;

/**
 * 添加强制踢人操作过滤器
 * 
 * @author long-laptop 2017.11.9
 */
public class ForceKickSessionFilter extends AccessControlFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomRedisShiroSessionDAO customRedisShiroSessionDao;

	@Autowired
	private RedisSessionTempDao redisSessionTempDao;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		Serializable sessionId = session.getId(); // 获取最新的session
		String userId = ShiroUtils.getUserId().toString();

		Boolean marker = (Boolean) session.getAttribute(Constant.LoginStatus.FORCEKICK.getValue());
		if (null != marker && marker) {
			Map<String, String> resultMap = new HashMap<String, String>();
			if (ShiroUtils.isAjax(request)) {
				logger.debug("当前用户已经在其他地方登录，并且是Ajax请求！");
				resultMap.put("user_status", "300");
				resultMap.put("message", "您已经在其他地方登录，请重新登录！");
				ShiroUtils.out(response, resultMap);
			}
			return Boolean.FALSE;
		}

		// 从临时缓存中获取用户-Session信息 <UserId,SessionId>
		LinkedHashMap<String, Serializable> infoMap = redisSessionTempDao.doRead(userId);
		// 如果不存在，创建一个新的
		infoMap = null == infoMap ? new LinkedHashMap<String, Serializable>() : infoMap;

		// 如果已经包含当前Session，并且是同一个用户，跳过。
		if (infoMap.containsKey(userId) && infoMap.containsValue(sessionId)) {
			// 更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			redisSessionTempDao.doCreate(userId, infoMap);
			return Boolean.TRUE;
		}

		// 如果用户相同，Session不相同，那么就要处理了
		/**
		 * 如果用户Id相同,Session不相同 1.获取到原来的session，并且标记为踢出。 2.继续走
		 */
		if (infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
			Serializable oldSessionId = infoMap.get(userId);
			Session oldSession = customRedisShiroSessionDao.doReadSession(oldSessionId);
			if (null != oldSession) {
				// 标记session已经踢出
				oldSession.setAttribute(Constant.LoginStatus.FORCEKICK_STATUS, Boolean.TRUE);
				customRedisShiroSessionDao.doCreate(oldSession); // 更新
			} else {
				customRedisShiroSessionDao.doDelete(oldSession);
				infoMap.remove(userId);
				redisSessionTempDao.doCreate(userId, infoMap);
			}
			return Boolean.TRUE;
		}

		if (!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
			infoMap.put(userId, sessionId);
			redisSessionTempDao.doCreate(userId, infoMap);
		}

		return Boolean.TRUE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		subject.logout();
		WebUtils.getSavedRequest(request);
		// 重定向到login界面
		WebUtils.issueRedirect(request, response, "login");
		return false;
	}

}
