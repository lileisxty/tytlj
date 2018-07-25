package com.tytlj.www.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tytlj.www.dao.IDao;
import com.tytlj.www.dao.impl.BaseDao;
import com.tytlj.www.pojo.Department;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see把數據從數據庫中加載到內存中
 */
@Service
@Transactional
public class InitDataService {

	private Logger logger = Logger.getLogger(InitDataService.class);

	public Map<String, String> mapDepartmentInfo() {
		Map<String, String> map = new HashMap<String, String>();
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			IDao idao = new BaseDao();
			List<Department> list = idao.entityList(session, Department.class);
			Iterator<Department> iterator = list.iterator();
			while (iterator.hasNext()) {
				Department department = iterator.next();
				map.put(department.getDeptCode(), department.getDepartment());
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return map;
	}

	public Map<String, String> getDeptRelation() {
		Map<String, String> departRelation = new HashMap<String, String>();
		departRelation.put("107011", "107011,1070111001,1070111002,1070111003");
		departRelation.put("107014", "107014,1070141001,1070141002,1070141003");
		departRelation.put("107016",
				"107016,107016001,107016002,107016003,107016004");
		departRelation.put("20000", "20000,21000,22000");
		departRelation.put("23000", "23000,24000");
		departRelation.put("25000", "25000,26000,27000,28000");
		departRelation.put("30000", "30000,31000,32000");
		departRelation.put("34000", "34000,35000,36000,37000");
		departRelation.put("39000", "39000,41000,42000");
		departRelation.put("45000", "45000,46000,47000");
		departRelation.put("48000", "48000,49000");
		departRelation.put("55000", "55000,56000,57000");
		departRelation.put("61000", "61000,62000,63000,64000,65000");
		departRelation.put("69000", "69000,71000,72000");
		departRelation.put("74000", "74000,75000,76000");
		departRelation.put("77000", "77000,78000,79000,81000");
		departRelation.put("82000", "82000,83000,84000,85000");
		departRelation.put("86000", "86000,86500");
		departRelation.put("89000", "89000,90000,91500");
		departRelation.put("95000", "95000,95500");
		return departRelation;
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @see返回一个map key是子车站deptCode，value是父车站deptCode
	 */
	public Map<String, String> getStationRelation() {
		Map<String, String> stationMap = new HashMap<String, String>();
		Map<String, String> map = getDeptRelation();
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = map.get(key);
			String[] depts = value.split(",");
			for (int i = 0; i < depts.length; i++) {
				stationMap.put(depts[i], key);
			}
		}
		return stationMap;
	}
}
