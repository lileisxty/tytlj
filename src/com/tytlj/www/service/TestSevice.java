package com.tytlj.www.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.tytlj.www.util.HibernateSessionFactory;

@Service
public class TestSevice {

	public void testUpdate(String userId, String salary) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tran = session.beginTransaction();
		String sql = "UPDATE employee SET salaryType='" + salary
				+ "' WHERE userId='" + userId + "';";
		session.createSQLQuery(sql).executeUpdate();
		tran.commit();
		session.close();
	}
}
