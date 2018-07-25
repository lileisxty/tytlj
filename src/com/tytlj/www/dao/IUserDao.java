package com.tytlj.www.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tytlj.www.pojo.Employee;

@Service
public interface IUserDao {

	/**
	 * 
	 * @return
	 * @see返回所有用户姓名
	 */
	public List<String> UserRealNameSet(Session session);

	/**
	 * 
	 * @param userName账户
	 * @return
	 * @see返回账户实体类
	 */
	public Employee userRealName(Session session, String userName);

}
