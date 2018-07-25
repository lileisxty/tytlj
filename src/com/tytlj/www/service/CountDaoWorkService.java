package com.tytlj.www.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tytlj.www.dao.impl.BaseDao;
import com.tytlj.www.pojo.CountDayWork;
import com.tytlj.www.pojo.Department;
import com.tytlj.www.util.HibernateSessionFactory;

@Service
@Transactional
public class CountDaoWorkService {

	@Autowired
	private BaseDao baseDao;

	/**
	 * 
	 * @param dayWork
	 * @see保存或者更新dayWork
	 */
	public void saveOrUpdateCountDayWork(CountDayWork countDayWork) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			baseDao.entitySaveOrUpdate(session, countDayWork);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		HibernateSessionFactory.getSession().close();

	}

	public Department getDepartment(Map<String, String> condition) {
		Session session = null;
		Department dept = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			dept = (Department) baseDao.entity(session, Department.class,
					condition);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dept;
	}

	public List<CountDayWork> getCountDayWorks(Map<String, String> condition) {
		Session session = null;
		List<CountDayWork> list = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			list = baseDao.entityList(session, CountDayWork.class, condition);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * 
	 * @param code
	 * @param day
	 * @param banci
	 * @param colum
	 * @param value
	 * @see更新某一个部门某一天某班次的值
	 */
	public void updateCountDayWork(String deptCode, String day, String banci,
			String colum, double value) {
		String sqljg = "UPDATE countdaywork SET " + colum
				+ "=? WHERE oneday=? AND deptCode=? AND banci=?;";

		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			SQLQuery update = session.createSQLQuery(sqljg);
			update.setParameter(0, value);
			update.setParameter(1, day);
			update.setParameter(2, deptCode);
			update.setParameter(3, banci);
			update.executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param code
	 * @param day
	 * @param banci
	 * @param colum
	 * @param value
	 * @see 管理员专用更新某一个部门某一天某班次的值
	 */
	public void updateCountDayWork(String day, String banci, String colum,
			double value) {
		Session session = null;
		Transaction tran = null;
		String sqljg = "UPDATE countdaywork SET " + colum
				+ "=? WHERE oneday=? AND banci=?;";
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			SQLQuery update = session.createSQLQuery(sqljg);
			update.setParameter(0, value);
			update.setParameter(1, day);
			update.setParameter(2, banci);
			update.executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
