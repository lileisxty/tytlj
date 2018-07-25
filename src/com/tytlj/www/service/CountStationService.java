package com.tytlj.www.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tytlj.www.pojo.CalculationWork;
import com.tytlj.www.pojo.StationCountWork;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see个人，车站统计查询
 * 
 */
@Component
public class CountStationService {

	private Map<String, CalculationWork> mapjg;
	private Map<String, CalculationWork> mappg;

	@Autowired
	private CountDaoWorkService countDaoWorkService;

	private void queryStationCount(String startDay, String endDay,
			String deptCode) {
		// 记工统计查询
		String sqljg = "SELECT countdaywork.`deptCode`,SUM(countdaywork.`translocation`),SUM(countdaywork.`aload`),SUM(countdaywork.`brigades`),SUM(countdaywork.`passenger`),SUM(countdaywork.`freight`),SUM(countdaywork.`transport`)"
				+ "FROM countdaywork WHERE (countdaywork.`deptCode`=? AND countdaywork.`oneDay` BETWEEN ? AND ?) GROUP BY countdaywork.`deptCode`;";
		String sqlpg = "SELECT daywork.`deptCode`,SUM(daywork.`translocation`),SUM(daywork.`aload`),SUM(daywork.`brigades`),SUM(daywork.`passenger`),SUM(daywork.`freight`),SUM(daywork.`transport`)FROM daywork "
				+ "WHERE (daywork.`deptCode`=? AND daywork.`oneDay` BETWEEN ? AND ? )GROUP BY daywork.`deptCode`;";
		// 派工结果
		mapjg = new HashMap<String, CalculationWork>();
		// 记工结果
		mappg = new HashMap<String, CalculationWork>();
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			SQLQuery queryJg = session.createSQLQuery(sqljg);
			SQLQuery queryPg = session.createSQLQuery(sqlpg);
			queryJg.setParameter(0, deptCode);
			queryJg.setParameter(1, startDay);
			queryJg.setParameter(2, endDay);

			queryPg.setParameter(0, deptCode);
			queryPg.setParameter(1, startDay);
			queryPg.setParameter(2, endDay);
			List listjg = queryJg.list();
			List listpg = queryPg.list();
			for (Object object : listjg) {
				Object[] obj = (Object[]) object;
				CalculationWork calculationWork = new CalculationWork();
				calculationWork.setId(obj[0].toString());
				calculationWork.setTranslocation(obj[1].toString());
				calculationWork.setLoad(obj[2].toString());
				calculationWork.setBrigades(obj[3].toString());
				calculationWork.setPassenger(obj[4].toString());
				calculationWork.setFreight(obj[5].toString());
				calculationWork.setTransport(obj[6].toString());

				mapjg.put(obj[0].toString(), calculationWork);
			}
			for (Object object : listpg) {
				Object[] obj = (Object[]) object;
				CalculationWork calculationWork = new CalculationWork();
				calculationWork.setId(obj[0].toString());
				calculationWork.setTranslocation(obj[1].toString());
				calculationWork.setLoad(obj[2].toString());
				calculationWork.setBrigades(obj[3].toString());
				calculationWork.setPassenger(obj[4].toString());
				calculationWork.setFreight(obj[5].toString());
				calculationWork.setTransport(obj[6].toString());

				mappg.put(obj[0].toString(), calculationWork);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<StationCountWork> getStationCountWork(String startDay,
			String endDay, String deptCode) {
		queryStationCount(startDay, endDay, deptCode);
		List<StationCountWork> list = new ArrayList<StationCountWork>();
		// 先从派map结合中找
		Iterator<String> iterator = mappg.keySet().iterator();
		while (iterator.hasNext()) {
			StationCountWork stationCountWork = new StationCountWork();
			String key = iterator.next();
			CalculationWork calculationWorkjg = mapjg.get(key);
			CalculationWork calculationWorkpg = mappg.get(key);
			if (calculationWorkjg != null) {
				String translocation_j = calculationWorkjg.getTranslocation();
				String load_j = calculationWorkjg.getLoad();
				String brigades_j = calculationWorkjg.getBrigades();
				String passenger_j = calculationWorkjg.getPassenger();
				String freight_j = calculationWorkjg.getFreight();
				String transport_j = calculationWorkjg.getTransport();

				double translocation_jd = Double.valueOf(translocation_j);
				double load_jd = Double.valueOf(load_j);
				double brigades_jd = Double.valueOf(brigades_j);
				double passenger_jd = Double.valueOf(passenger_j);
				double freight_jd = Double.valueOf(freight_j);
				double transport_jd = Double.valueOf(transport_j);

				String translocation_p = calculationWorkpg.getTranslocation();
				String load_p = calculationWorkpg.getLoad();
				String brigades_p = calculationWorkpg.getBrigades();
				String passenger_p = calculationWorkpg.getPassenger();
				String freight_p = calculationWorkpg.getFreight();
				String transport_p = calculationWorkpg.getTransport();

				double translocation_pd = Double.valueOf(translocation_p);
				double load_pd = Double.valueOf(load_p);
				double brigades_pd = Double.valueOf(brigades_p);
				double passenger_pd = Double.valueOf(passenger_p);
				double freight_pd = Double.valueOf(freight_p);
				double transport_pd = Double.valueOf(transport_p);

				double translocation;
				double load;
				double brigades;
				double passenger;
				double freight;
				double transport;
				if (translocation_pd != 0.0) {
					translocation = (double) translocation_jd
							/ translocation_pd * 100;
					BigDecimal t = new BigDecimal(
							new Double(translocation).toString());
					translocation = t.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					translocation = 0.0;
				}
				if (load_pd != 0.0) {
					load = (double) load_jd / load_pd * 100;
					BigDecimal l = new BigDecimal(new Double(load).toString());
					load = l.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					load = 0.0;
				}
				if (brigades_pd != 0.0) {
					brigades = (double) brigades_jd / brigades_pd * 100;
					BigDecimal b = new BigDecimal(
							new Double(brigades).toString());
					brigades = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					brigades = 0.0;
				}
				if (passenger_pd != 0.0) {
					passenger = (double) passenger_jd / passenger_pd * 100;
					BigDecimal p = new BigDecimal(
							new Double(passenger).toString());
					passenger = p.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					passenger = 0.0;
				}
				if (freight_pd != 0.0) {
					freight = (double) freight_jd / freight_pd * 100;
					BigDecimal f = new BigDecimal(
							new Double(freight).toString());
					freight = f.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					freight = 0.0;
				}

				if (transport_pd != 0.0) {
					transport = (double) transport_jd / transport_pd * 100;
					BigDecimal tt = new BigDecimal(
							new Double(transport).toString());
					transport = tt.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					transport = 0.0;
				}

				stationCountWork.setTranslocation_j(Integer
						.valueOf(translocation_j));

				stationCountWork.setLoad_j(Integer.valueOf(load_j));

				stationCountWork.setBrigades_j(Integer.valueOf(brigades_j));

				stationCountWork.setPassenger_j(Double.valueOf(passenger_j));

				stationCountWork.setFreight_j(Double.valueOf(freight_j));

				stationCountWork.setTransport_j(Double.valueOf(transport_j));
				// ///////////////////////////////////////////////////////////////////////////
				stationCountWork.setTranslocation_p(Integer
						.valueOf(translocation_p));

				stationCountWork.setLoad_p(Integer.valueOf(load_p));

				stationCountWork.setBrigades_p(Integer.valueOf(brigades_p));

				stationCountWork.setPassenger_p(Double.valueOf(passenger_p));

				stationCountWork.setFreight_p(Double.valueOf(freight_p));

				stationCountWork.setTransport_p(Double.valueOf(transport_p));
				// ////////////////////////////////////////////////////////
				stationCountWork.setTranslocation_b(translocation);

				stationCountWork.setLoad_b(load);

				stationCountWork.setBrigades_b(brigades);

				stationCountWork.setPassenger_b(passenger);

				stationCountWork.setFreight_b(freight);

				stationCountWork.setTransport_b(transport);
				// ///////////////////////////////////////////
				stationCountWork.setStation(calculationWorkpg.getId());

				list.add(stationCountWork);
			} else {
				continue;
			}
		}
		return list;
	}

	private void queryStationCount(String startDay, String endDay) {
		// 记工统计查询
		String sqljg = "SELECT countdaywork.`deptCode`,SUM(countdaywork.`translocation`),SUM(countdaywork.`aload`),SUM(countdaywork.`brigades`),SUM(countdaywork.`passenger`),SUM(countdaywork.`freight`),SUM(countdaywork.`transport`)"
				+ "FROM countdaywork WHERE countdaywork.`oneDay` BETWEEN ? AND ? GROUP BY countdaywork.`deptCode`;";
		String sqlpg = "SELECT daywork.`deptCode`,SUM(daywork.`translocation`),SUM(daywork.`aload`),SUM(daywork.`brigades`),SUM(daywork.`passenger`),SUM(daywork.`freight`),SUM(daywork.`transport`)"
				+ "FROM daywork WHERE daywork.`oneDay` BETWEEN ? AND ? GROUP BY daywork.`deptCode`;";
		// 派工结果
		mapjg = new HashMap<String, CalculationWork>();
		// 记工结果
		mappg = new HashMap<String, CalculationWork>();
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			SQLQuery queryJg = session.createSQLQuery(sqljg);
			SQLQuery queryPg = session.createSQLQuery(sqlpg);
			queryJg.setParameter(0, startDay);
			queryJg.setParameter(1, endDay);

			queryPg.setParameter(0, startDay);
			queryPg.setParameter(1, endDay);
			List listjg = queryJg.list();
			List listpg = queryPg.list();
			for (Object object : listjg) {
				Object[] obj = (Object[]) object;
				CalculationWork calculationWork = new CalculationWork();
				calculationWork.setId(obj[0].toString());
				calculationWork.setTranslocation(obj[1].toString());
				calculationWork.setLoad(obj[2].toString());
				calculationWork.setBrigades(obj[3].toString());
				calculationWork.setPassenger(obj[4].toString());
				calculationWork.setFreight(obj[5].toString());
				calculationWork.setTransport(obj[6].toString());

				mapjg.put(obj[0].toString(), calculationWork);
			}
			for (Object object : listpg) {
				Object[] obj = (Object[]) object;
				CalculationWork calculationWork = new CalculationWork();
				calculationWork.setId(obj[0].toString());
				calculationWork.setTranslocation(obj[1].toString());
				calculationWork.setLoad(obj[2].toString());
				calculationWork.setBrigades(obj[3].toString());
				calculationWork.setPassenger(obj[4].toString());
				calculationWork.setFreight(obj[5].toString());
				calculationWork.setTransport(obj[6].toString());

				mappg.put(obj[0].toString(), calculationWork);
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
	 * @param startDay
	 * @param endDay
	 * @return
	 * @seeadmin管理员查询全部
	 */
	public List<StationCountWork> getStationCountWork(String startDay,
			String endDay) {
		queryStationCount(startDay, endDay);
		List<StationCountWork> list = new ArrayList<StationCountWork>();
		// 先从派map结合中找
		Iterator<String> iterator = mappg.keySet().iterator();
		while (iterator.hasNext()) {
			StationCountWork stationCountWork = new StationCountWork();
			String key = iterator.next();
			CalculationWork calculationWorkjg = mapjg.get(key);
			CalculationWork calculationWorkpg = mappg.get(key);
			String translocation_j = calculationWorkjg.getTranslocation();
			String load_j = calculationWorkjg.getLoad();
			String brigades_j = calculationWorkjg.getBrigades();
			String passenger_j = calculationWorkjg.getPassenger();
			String freight_j = calculationWorkjg.getFreight();
			String transport_j = calculationWorkjg.getTransport();

			double translocation_jd = Double.valueOf(translocation_j);
			double load_jd = Double.valueOf(load_j);
			double brigades_jd = Double.valueOf(brigades_j);
			double passenger_jd = Double.valueOf(passenger_j);
			double freight_jd = Double.valueOf(freight_j);
			double transport_jd = Double.valueOf(transport_j);

			String translocation_p = calculationWorkpg.getTranslocation();
			String load_p = calculationWorkpg.getLoad();
			String brigades_p = calculationWorkpg.getBrigades();
			String passenger_p = calculationWorkpg.getPassenger();
			String freight_p = calculationWorkpg.getFreight();
			String transport_p = calculationWorkpg.getTransport();

			double translocation_pd = Double.valueOf(translocation_p);
			double load_pd = Double.valueOf(load_p);
			double brigades_pd = Double.valueOf(brigades_p);
			double passenger_pd = Double.valueOf(passenger_p);
			double freight_pd = Double.valueOf(freight_p);
			double transport_pd = Double.valueOf(transport_p);

			double translocation;
			double load;
			double brigades;
			double passenger;
			double freight;
			double transport;
			if (translocation_pd != 0.0) {
				translocation = (double) translocation_jd / translocation_pd
						* 100;
				BigDecimal t = new BigDecimal(
						new Double(translocation).toString());
				translocation = t.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			} else {
				translocation = 0.0;
			}
			if (load_pd != 0.0) {
				load = (double) load_jd / load_pd * 100;
				BigDecimal l = new BigDecimal(new Double(load).toString());
				load = l.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			} else {
				load = 0.0;
			}
			if (brigades_pd != 0.0) {
				brigades = (double) brigades_jd / brigades_pd * 100;
				BigDecimal b = new BigDecimal(new Double(brigades).toString());
				brigades = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			} else {
				brigades = 0.0;
			}
			if (passenger_pd != 0.0) {
				passenger = (double) passenger_jd / passenger_pd * 100;
				BigDecimal p = new BigDecimal(new Double(passenger).toString());
				passenger = p.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			} else {
				passenger = 0.0;
			}
			if (freight_pd != 0.0) {
				freight = (double) freight_jd / freight_pd * 100;
				BigDecimal f = new BigDecimal(new Double(freight).toString());
				freight = f.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			} else {
				freight = 0.0;
			}

			if (transport_pd != 0.0) {
				transport = (double) transport_jd / transport_pd * 100;
				BigDecimal tt = new BigDecimal(new Double(transport).toString());
				transport = tt.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			} else {
				transport = 0.0;
			}

			stationCountWork.setTranslocation_j(Integer
					.valueOf(translocation_j));

			stationCountWork.setLoad_j(Integer.valueOf(load_j));

			stationCountWork.setBrigades_j(Integer.valueOf(brigades_j));

			stationCountWork.setPassenger_j(Double.valueOf(passenger_j));

			stationCountWork.setFreight_j(Double.valueOf(freight_j));

			stationCountWork.setTransport_j(Double.valueOf(transport_j));
			// ///////////////////////////////////////////////////////////////////////////
			stationCountWork.setTranslocation_p(Integer
					.valueOf(translocation_p));

			stationCountWork.setLoad_p(Integer.valueOf(load_p));

			stationCountWork.setBrigades_p(Integer.valueOf(brigades_p));

			stationCountWork.setPassenger_p(Double.valueOf(passenger_p));

			stationCountWork.setFreight_p(Double.valueOf(freight_p));

			stationCountWork.setTransport_p(Double.valueOf(transport_p));
			// ////////////////////////////////////////////////////////
			stationCountWork.setTranslocation_b(translocation);

			stationCountWork.setLoad_b(load);

			stationCountWork.setBrigades_b(brigades);

			stationCountWork.setPassenger_b(passenger);

			stationCountWork.setFreight_b(freight);

			stationCountWork.setTransport_b(transport);
			// ///////////////////////////////////////////

			// Map<String, String> map = new HashMap<String, String>();
			// map.put("deptCode", calculationWorkpg.getId());
			// Department dept = countDaoWorkService.getDepartment(map);
			stationCountWork.setStation(GlobalVariable.departmentInfo
					.get(calculationWorkpg.getId()));
			list.add(stationCountWork);
		}
		return list;
	}
}
