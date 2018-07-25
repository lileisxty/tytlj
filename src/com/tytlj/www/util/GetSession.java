package com.tytlj.www.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tytlj.www.service.UserService;

@Component
public class GetSession {
	@Autowired
	private UserService userService;

	/**
	 * @see获取当前登录用的用户名
	 * @return
	 */
	public String getSessionValue(String sessionKey) {
		Subject currentUser = SecurityUtils.getSubject();
		String sessionUser = (String) currentUser.getSession().getAttribute(
				sessionKey);
		return sessionUser;
	}

	/**
	 * 
	 * @return
	 * @see取得登录用的真实姓名
	 */
	/*
	 * public String getRealName() { String userName = getSessionRealname();//
	 * 从httpSession中获取账户名 Custom user = userService.getUser(userName);//
	 * 根据账户名查询用户信息 return user.getUserdetail().getRealname(); }
	 */
}
