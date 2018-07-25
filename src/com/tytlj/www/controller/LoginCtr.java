package com.tytlj.www.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tytlj.www.pojo.AccountInfo;
import com.tytlj.www.service.UserService;
import com.tytlj.www.util.MD5Util;
import com.tytlj.www.util.SessionKey;

/**
 * 
 * @author lilei
 * @see登录验证controller
 * 
 */
@Controller
public class LoginCtr extends BaseController {

	private Logger logger = Logger.getLogger(LoginCtr.class);
	@Resource
	private MD5Util md5Util;
	@Resource
	private UserService userService;

	/**
	 * 
	 * @return返回登录页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}

	/**
	 * 
	 * @param user
	 * @param bindingResult验证输入数据是否正确
	 * @param model
	 * @return
	 * @see验证用户登录信息，如果登录错误返回登录页面。如果用户名密码正确返回系统home页面
	 */
	@RequestMapping(value = "/code/checkUser", method = RequestMethod.POST)
	public ModelAndView LoginCheckCtr(AccountInfo account) {
		/*
		 * String pwdMd5 = null; try { pwdMd5 =
		 * MD5Util.EncoderByMd5(custom.getPassword()); } catch
		 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码封装为UsernamePasswordToken
			UsernamePasswordToken token = new UsernamePasswordToken(
					account.getAccount(), account.getPassword());
			try {
				// 执行登录
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				ae.printStackTrace();
				token.clear();
				return new ModelAndView("redirect:/index");
			}

		}
		if (currentUser.isPermitted("adminPermission")) {
			AccountInfo accountInfo = userService.CustomUniqueResult(account
					.getAccount());
			// 设置session
			Session session = currentUser.getSession();
			session.setAttribute(SessionKey.account, accountInfo.getAccount());
			session.setAttribute(SessionKey.department,
					accountInfo.getDepartment());
			session.setAttribute(SessionKey.accountType,
					accountInfo.getAccountType());
			session.setAttribute(SessionKey.deptCode, accountInfo.getDeptCode());
			session.setAttribute(SessionKey.name, accountInfo.getName());
			session.setAttribute(SessionKey.company, accountInfo.getCompany());
		} else {
			return new ModelAndView("redirect:/index");
		}
		return new ModelAndView("home");
	}

	/**
	 * 
	 * @return
	 * @see登录注销
	 */
	@RequestMapping(value = "/login/logout", method = RequestMethod.GET)
	public ModelAndView Logout() {
		Subject currentUser = SecurityUtils.getSubject();
		// String sessionUser=(String)
		// currentUser.getSession().getAttribute(SessionKey.sessionKey);
		currentUser.logout();
		return new ModelAndView("redirect:/index");
	}
}
