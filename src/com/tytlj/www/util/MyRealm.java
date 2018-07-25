package com.tytlj.www.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.tytlj.www.dao.impl.ShiRoUserDaoImpl;
import com.tytlj.www.pojo.AccountInfo;

public class MyRealm extends AuthorizingRealm {

	private Logger logger = Logger.getLogger(MyRealm.class);
	@Resource
	private ShiRoUserDaoImpl shiRoUserDao;

	public ShiRoUserDaoImpl getShiRoUserDao() {
		return shiRoUserDao;
	}

	public void setShiRoUserDao(ShiRoUserDaoImpl shiRoUserDao) {
		this.shiRoUserDao = shiRoUserDao;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 2.从UsernamePasswordToken中获取username
		String account = upToken.getUsername();
		AccountInfo accountInfo = shiRoUserDao.queryUser(account);
		if (accountInfo == null) {
			throw new UnknownAccountException();
		}
		// } else if (!custom.getPassword().equals(token.getPrincipal())) {
		// throw new IncorrectCredentialsException();// ���벻��
		// }// else if(0 == user.getEnable()) {
		// throw new LockedAccountException(); // �ʺ���
		// }

		Object principal = accountInfo.getAccount();
		Object credentials = accountInfo.getPassword();
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				principal, credentials, this.getName());
		// 盐值
		// ByteSource
		// credentialsSalt=ByteSource.Util.bytes(user.getUserName());//使用用户名作为盐
		// authenticationInfo = new SimpleAuthenticationInfo(principal,
		// credentials, credentialsSalt, getName());
		return authenticationInfo;
	}

	// 授权需要实现的方法,根据角色授权

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		// 1.从principals中来获取登录用的信息
		Object principal = principals.getPrimaryPrincipal();
		// 2.利用登录用户的信息，查询当前用户的角色 Set<String>
		// String username = (String) principals.getPrimaryPrincipal();
		// System.out.println("用户名：" + username);
		Set<String> roles = new HashSet<>();
		roles.add("normal");
		// 管理员账号赋admin权限
		if ("ldrsk036".equals(principal)) {
			roles.add("admin");
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// User user = userDao.QueryUser(username);
		authorizationInfo.addStringPermission("adminPermission");
		authorizationInfo.addRoles(roles);
		return authorizationInfo;
	}
}
