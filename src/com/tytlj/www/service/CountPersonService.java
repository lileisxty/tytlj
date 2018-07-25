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
import org.springframework.stereotype.Component;

import com.tytlj.www.pojo.CalculationWork;
import com.tytlj.www.pojo.PersonCountWork;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see个人，车站统计查询
 * 
 */
@Component
public class CountPersonService {

	private Map<String, CalculationWork> mapjg;
	private Map<String, CalculationWork> mappg;

	private void queryPersonCount(String startDay, String endDay,
			String deptCode) {
		// 记工统计查询
		String sqljg = "SELECT countdaywork.`userId`,SUM(countdaywork.`translocation`),SUM(countdaywork.`aload`),SUM(countdaywork.`brigades`),SUM(countdaywork.`passenger`),SUM(countdaywork.`freight`),SUM(countdaywork.`transport`),countdaywork.`username`,countdaywork.`position` "
				+ "FROM countdaywork WHERE (countdaywork.`deptCode`=? AND countdaywork.`oneDay` BETWEEN ? AND ?) GROUP BY countdaywork.`userId`;";
		String sqlpg = "SELECT daywork.`userId`,SUM(daywork.`translocation`),SUM(daywork.`aload`),SUM(daywork.`brigades`),SUM(daywork.`passenger`),SUM(daywork.`freight`),SUM(daywork.`transport`) ,daywork.`username`,daywork.`position`"
				+ "FROM daywork WHERE (daywork.`deptCode`=? AND daywork.`oneDay` BETWEEN ? AND ? )GROUP BY daywork.`userId`;";
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
				calculationWork.setUserName(obj[7].toString());
				calculationWork.setPosition(obj[8].toString());
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
				calculationWork.setUserName(obj[7].toString());
				calculationWork.setPosition(obj[8].toString());
				mappg.put(obj[0].toString(), calculationWork);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public List<PersonCountWork> getPersonCountWork(String startDay,
			String endDay, String deptCode) {
		queryPersonCount(startDay, endDay, deptCode);
		List<PersonCountWork> list = new ArrayList<PersonCountWork>();
		// 先从派map结合中找
		Iterator<String> iterator = mappg.keySet().iterator();
		while (iterator.hasNext()) {
			PersonCountWork personCountWork = new PersonCountWork();
			String key = iterator.next();
			CalculationWork calculationWorkjg = mapjg.get(key);
			CalculationWork calculationWorkpg = mappg.get(key);
			// 如果派的工能从记工里面找到这执行计算
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

				personCountWork.setTranslocation_j(Integer
						.valueOf(translocation_j));

				personCountWork.setLoad_j(Integer.valueOf(load_j));

				personCountWork.setBrigades_j(Integer.valueOf(brigades_j));

				personCountWork.setPassenger_j(Double.valueOf(passenger_j));

				personCountWork.setFreight_j(Double.valueOf(freight_j));

				personCountWork.setTransport_j(Double.valueOf(transport_j));
				// ///////////////////////////////////////////////////////////////////////////
				personCountWork.setTranslocation_p(Integer
						.valueOf(translocation_p));

				personCountWork.setLoad_p(Integer.valueOf(load_p));

				personCountWork.setBrigades_p(Integer.valueOf(brigades_p));

				personCountWork.setPassenger_p(Double.valueOf(passenger_p));

				personCountWork.setFreight_p(Double.valueOf(freight_p));

				personCountWork.setTransport_p(Double.valueOf(transport_p));
				// ////////////////////////////////////////////////////////
				personCountWork.setTranslocation_b(translocation);

				personCountWork.setLoad_b(load);

				personCountWork.setBrigades_b(brigades);

				personCountWork.setPassenger_b(passenger);

				personCountWork.setFreight_b(freight);

				personCountWork.setTransport_b(transport);
				// ///////////////////////////////////////////
				personCountWork.setUserId(calculationWorkpg.getId());
				personCountWork.setUsername(calculationWorkpg.getUserName());
				personCountWork.setPosition(calculationWorkpg.getPosition());
				list.add(personCountWork);
			} else {
				continue;
			}
		}
		return list;
	}

	private void queryPersonCount(String startDay, String endDay) {
		// 记工统计查询
		String sqljg = "SELECT countdaywork.`userId`,SUM(countdaywork.`translocation`),SUM(countdaywork.`aload`),SUM(countdaywork.`brigades`),SUM(countdaywork.`passenger`),SUM(countdaywork.`freight`),SUM(countdaywork.`transport`),countdaywork.`username`,countdaywork.`position` "
				+ "FROM countdaywork WHERE countdaywork.`oneDay` BETWEEN ? AND ? GROUP BY countdaywork.`userId`;";
		String sqlpg = "SELECT daywork.`userId`,SUM(daywork.`translocation`),SUM(daywork.`aload`),SUM(daywork.`brigades`),SUM(daywork.`passenger`),SUM(daywork.`freight`),SUM(daywork.`transport`) ,daywork.`username`,daywork.`position`"
				+ "FROM daywork WHERE daywork.`oneDay` BETWEEN ? AND ? GROUP BY daywork.`userId`;";
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
				calculationWork.setUserName(obj[7].toString());
				calculationWork.setPosition(obj[8].toString());
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
				calculationWork.setUserName(obj[7].toString());
				calculationWork.setPosition(obj[8].toString());
				mappg.put(obj[0].toString(), calculationWork);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public List<PersonCountWork> getPersonCountWork(String startDay,
			String endDay) {
		queryPersonCount(startDay, endDay);
		List<PersonCountWork> list = new ArrayList<PersonCountWork>();
		// 先从派map结合中找
		Iterator<String> iterator = mappg.keySet().iterator();
		while (iterator.hasNext()) {
			PersonCountWork personCountWork = new PersonCountWork();
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

			personCountWork
					.setTranslocation_j(Integer.valueOf(translocation_j));

			personCountWork.setLoad_j(Integer.valueOf(load_j));

			personCountWork.setBrigades_j(Integer.valueOf(brigades_j));

			personCountWork.setPassenger_j(Double.valueOf(passenger_j));

			personCountWork.setFreight_j(Double.valueOf(freight_j));

			personCountWork.setTransport_j(Double.valueOf(transport_j));
			// ///////////////////////////////////////////////////////////////////////////
			personCountWork
					.setTranslocation_p(Integer.valueOf(translocation_p));

			personCountWork.setLoad_p(Integer.valueOf(load_p));

			personCountWork.setBrigades_p(Integer.valueOf(brigades_p));

			personCountWork.setPassenger_p(Double.valueOf(passenger_p));

			personCountWork.setFreight_p(Double.valueOf(freight_p));

			personCountWork.setTransport_p(Double.valueOf(transport_p));
			// ////////////////////////////////////////////////////////
			personCountWork.setTranslocation_b(translocation);

			personCountWork.setLoad_b(load);

			personCountWork.setBrigades_b(brigades);

			personCountWork.setPassenger_b(passenger);

			personCountWork.setFreight_b(freight);

			personCountWork.setTransport_b(transport);
			// ///////////////////////////////////////////
			personCountWork.setUserId(calculationWorkpg.getId());
			personCountWork.setUsername(calculationWorkpg.getUserName());
			personCountWork.setPosition(calculationWorkpg.getPosition());
			list.add(personCountWork);
		}
		return list;
	}
}
