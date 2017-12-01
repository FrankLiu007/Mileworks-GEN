package com.mileworks.modules.sys.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mileworks.modules.sys.entity.SysUserEntity;
import com.mileworks.modules.sys.shiro.ShiroUtils;

/**
 * 登录相关操作
 * 
 * @author long-laptop 2017.11.9
 */
public class LoginSessionFilter extends AccessControlFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		// 获取当前用户信息
		SysUserEntity entity = ShiroUtils.getUserEntity();

		if (null != entity || isLoginRequest(request, response)) {
			return Boolean.TRUE;
		}
		if (ShiroUtils.isAjax(request)) {// ajax请求
			Map<String, String> resultMap = new HashMap<String, String>();
			logger.debug("当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");// 当前用户没有登录！
			ShiroUtils.out(response, resultMap);
		}

		return false;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

}
