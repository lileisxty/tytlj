package com.tytlj.www.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.tytlj.www.dao.impl.BaseDao;
import com.tytlj.www.pojo.DayWork;
import com.tytlj.www.util.HibernateSessionFactory;

@Service
public class DayWorkService {

	@Resource
	private BaseDao baseDao;
	private Logger logger = Logger.getLogger(DayWorkService.class);

	/**
	 * 
	 * @param dayWork
	 * @see保存或者更新dayWork
	 */
	public void saveOrUpdateDayWork(DayWork dayWork) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			baseDao.entitySaveOrUpdate(session, dayWork);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}
