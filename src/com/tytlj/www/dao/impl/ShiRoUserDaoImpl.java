package com.tytlj.www.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.tytlj.www.pojo.AccountInfo;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see专供给shiro框架提供用户数据查询
 * 
 */
public class ShiRoUserDaoImpl {
	private Logger logger = Logger.getLogger(ShiRoUserDaoImpl.class);
	@Resource
	private HibernateSessionFactory hibernateSessionFactory;

	/**
	 * 根据工号查询，返回一个AccountInfo对象
	 */
	public AccountInfo queryUser(String account) {
		HibernateSessionFactory.getSession().getTransaction().begin();
		Criteria criteria = HibernateSessionFactory.getSession()
				.createCriteria(AccountInfo.class);
		criteria.add(Restrictions.eq("account", account));
		AccountInfo accountInfo = (AccountInfo) criteria.uniqueResult();
		HibernateSessionFactory.getSession().getTransaction().commit();
		HibernateSessionFactory.getSession().close();
		return accountInfo;
	}
}
