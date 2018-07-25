package com.tytlj.www.service;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tytlj.www.dao.impl.BaseDao;
import com.tytlj.www.pojo.StationPg;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see各站月派工总量
 * 
 */
@Service
public class StaticService {

	@Autowired
	private BaseDao baseDao;

	/**
	 * @see保存一个sationPg
	 */
	public void saveStationPgs(List<StationPg> stationPgs) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			for (StationPg stationPg : stationPgs) {
				baseDao.entitySave(session, stationPg);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param year
	 * @param mounth
	 * @see删除某年某月数据
	 */
	public void deleteStationPgs(String year, String mounth) {
		String sql = "DELETE FROM stationpg WHERE YEAR=? AND monthDay=?;";
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, year);
			query.setParameter(1, mounth);
			query.executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param condition查询条件
	 * @return
	 */
	public List<StationPg> getStations(Map<String, String> condition) {
		Session session = null;
		List<StationPg> list = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			list = baseDao.entityList(session, StationPg.class, condition);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		for (StationPg stationPg : list) {
			String value = stationPg.getMonth();
			String value1 = stationPg.getYear();
			stationPg.setMonth(value.substring(0, value.lastIndexOf(".")));
			stationPg.setYear(value1.substring(0, value1.lastIndexOf(".")));
		}
		return list;
	}
}
