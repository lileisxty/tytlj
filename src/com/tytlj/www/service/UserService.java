package com.tytlj.www.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import com.tytlj.www.dao.IDao;
import com.tytlj.www.dao.IUserDao;
import com.tytlj.www.pojo.AccountInfo;
import com.tytlj.www.pojo.Department;
import com.tytlj.www.pojo.Employee;
import com.tytlj.www.util.HibernateSessionFactory;

@Component
public class UserService {

	@Resource
	private IDao baseDao;
	@Resource
	private IUserDao UserDaoImpl;

	/**
	 * 
	 * @param cls
	 * @param id
	 * @return
	 * @see查询一个用户信息
	 */
	public AccountInfo CustomUniqueResult(String id) {
		Session session = null;
		AccountInfo accountInfo = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			accountInfo = (AccountInfo) baseDao.entityUniqueResult(session,
					AccountInfo.class, id);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return accountInfo;
	}

	/**
	 * 
	 * @param Employee
	 * @see保存用户信息
	 */
	public void pojoSave(Object obj) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			baseDao.entitySave(session, obj);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param cp当前页
	 * @param ls每页显示条数
	 * @param col显示列
	 * @param kw查询关键字
	 * @return
	 */

	public Map EmployeeList(int cp, int ls, Map<String, String> condition) {
		Session session = null;
		Map map = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			map = baseDao.entityMap(session, Employee.class, cp, ls, condition);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return map;
	}

	/**
	 * 
	 * @param 部门名称
	 * @return
	 * @see查询返回系统所有部门
	 */
	public List<Department> detpList() {
		Session session = null;
		List<Department> list = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			Criteria criteria = HibernateSessionFactory.getSession()
					.createCriteria(Department.class);
			criteria.addOrder(Order.asc("orderId"));
			list = criteria.list();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public int updatePassword(String userId, String password) {
		Session session = null;
		int result = 0;
		String sql = "UPDATE accountinfo SET accountinfo.`password`=? WHERE accountinfo.`account`=?";
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter(0, password);
			sqlQuery.setParameter(1, userId);
			result = sqlQuery.executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
