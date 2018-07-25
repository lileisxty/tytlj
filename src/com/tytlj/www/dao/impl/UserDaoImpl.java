package com.tytlj.www.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tytlj.www.dao.IUserDao;
import com.tytlj.www.pojo.Employee;

@Service("userDaoImpl")
public class UserDaoImpl implements IUserDao {

	/**
	 * @see查询所有的账户信息
	 */
	@Override
	public List<String> UserRealNameSet(Session session) {
		// TODO Auto-generated method stub
		String hql = "select userName as userName from User";
		Query query = session.createQuery(hql);
		return query.list();
	}

	/**
	 * @see根据账户名查询账户信息返回一个账户的实体类
	 */
	@Override
	public Employee userRealName(Session session, String userId) {
		// TODO Auto-generated method stub
		Criteria critria = session.createCriteria(Employee.class);
		critria.add(Restrictions.eq("userId", userId));
		return (Employee) critria.uniqueResult();
	}
}
