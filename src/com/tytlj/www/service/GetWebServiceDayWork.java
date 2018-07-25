package com.tytlj.www.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.tytlj.www.dao.IDao;
import com.tytlj.www.pojo.DayWork;
import com.tytlj.www.pojo.StationPg;
import com.tytlj.www.util.GetMounthDay;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.HibernateSessionFactory;
import com.tytlj.www.variable.GloblaParam;

@Transactional
@Service
public class GetWebServiceDayWork {

	private Logger logger = Logger.getLogger(GetWebServiceDayWork.class);
	@Autowired
	private IDao baseDao;
	@Autowired
	private GetMounthDay getMounthDay;

	/**
	 * 
	 * @param deptCode部门编码
	 * @param day某一天日期
	 * @param classes班类型
	 * @return
	 * @seewebservice接口获取某天可一派工人员信息
	 */
	public List<DayWork> getDayWorkList(String deptCode, String day) {
		String webServiceUrl = "http://10.81.76.17/ty_cwd/getdatdlist?token=33dac39bfd057605ef44a6dbd08fa39f&deptcode="
				+ deptCode + "&ytd=" + day;
		List<DayWork> list = new ArrayList<DayWork>();
		try {
			Client client = Client.create();
			WebResource src = client.resource(webServiceUrl);
			// webService返回String类型的数据
			String webServiceResultString = src.get(String.class);
			SAXReader reader = new SAXReader();
			// 读取字符串
			InputStream ipt = new ByteArrayInputStream(
					webServiceResultString.getBytes());
			// Scanner scanner = new Scanner(ipt);
			// StringBuffer stringBuf = new StringBuffer();
			// while (scanner.hasNext()) {
			// stringBuf.append(scanner.next());
			// }
			// ipt.close();
			// String result = stringBuf.toString();
			// String result1 = result.substring(36);
			// System.out.println("页面返回的结果" + result1);
			// InputStream ipt1 = new ByteArrayInputStream(result.getBytes());
			//
			// Document document = reader.read(ipt1);

			Document document = reader.read(new BufferedReader(
					new InputStreamReader(ipt, "GB2312")));

			// Document document = reader.read(new BufferedReader(
			// new InputStreamReader(new FileInputStream(new File(
			// "D:/daywork.xml")), "UTF-8")));
			Element rootElement = document.getRootElement();
			Iterator<?> iterator = rootElement.elementIterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				Iterator<?> iterator1 = element.elementIterator();
				DayWork dayWork = new DayWork();

				while (iterator1.hasNext()) {
					Element element2 = (Element) iterator1.next();
					if ("shiftcyclecon".equals(element2.getName())) {
						dayWork.setIsHoliday(element2.getText());
					}
					// statffcode员工编号
					if ("staffcode".equals(element2.getName())) {
						dayWork.setUserId(element2.getText());
					}
					// staffname员工名称
					if ("stafftname".equals(element2.getName())) {
						dayWork.setUsername(element2.getText());
					}
					// staffname员工职位
					if ("staffzwzc".equals(element2.getName())) {
						dayWork.setPosition(element2.getText());
					}
				}
				list.add(dayWork);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 从集合中去掉没法派工的人员
		List<DayWork> newList = new ArrayList<DayWork>();
		for (int i = 0; i < list.size(); i++) {
			if (!GlobalVariable.setParam().contains(list.get(i).getIsHoliday())) {
				newList.add(list.get(i));
			}
		}
		// 先从webservice获取当天部门的考勤人员信息
		return compareListDayWork(newList, day, deptCode);
	}

	/**
	 * 
	 * @param list从webservice接口中获取的数据
	 *            ，day某天日期
	 * @return
	 */
	public List<DayWork> compareListDayWork(List<DayWork> list, String day,
			String deptCode) {
		// 默认日班是选中，从各站月总派工量中查询是否所有本部门的派工信息
		DayWork dayWork1 = getClassesGrades(day, deptCode, "日班");
		// 判断所在部门是否有月总派工量
		if (dayWork1 != null) {
			for (DayWork dayWork : list) {
				String userId = dayWork.getUserId();// 员工编号
				Map<String, String> condition = new HashMap<String, String>();
				condition.put("date", day);
				condition.put("userId", userId);
				Session session = null;
				DayWork dW = null;
				try {
					session = HibernateSessionFactory.getSession();
					Transaction tran = session.beginTransaction();
					dW = (DayWork) baseDao.entity(session, DayWork.class,
							condition);
					tran.commit();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();
				}
				// 判断webservice同步过来的数据在当天的计工表中是否已经计工，如果已经计工则把数据同步到集合中
				// dw不为空表示数据库中有数据，说明已经派过工，则把派工数据返回
				// dw为空说明没有派工，则把接口中的数据返回页面
				if (dW != null) {
					dayWork.setId(dW.getId());
					dayWork.setDeptCode(dW.getDeptCode());
					dayWork.setPosition(dW.getPosition());
					dayWork.setDate(dW.getDate());
					dayWork.setBrigades(dW.getBrigades());
					dayWork.setFreight(dW.getFreight());
					dayWork.setIsWork(dW.getIsWork());
					dayWork.setLoad(dW.getLoad());
					dayWork.setPassenger(dW.getPassenger());
					dayWork.setTranslocation(dW.getTranslocation());
					dayWork.setTransport(dW.getTransport());
					dayWork.setPostWalk(dW.getPostWalk());
					dayWork.setBanci(dW.getBanci());
				} else {
					dayWork.setDeptCode(deptCode);
					dayWork.setDate(day);
					dayWork.setBrigades(dayWork1.getBrigades());
					dayWork.setFreight(dayWork1.getFreight());
					dayWork.setIsWork(0);
					dayWork.setLoad(dayWork1.getLoad());
					dayWork.setPassenger(dayWork1.getPassenger());
					dayWork.setTranslocation(dayWork1.getTranslocation());
					dayWork.setTransport(dayWork1.getTransport());
				}

			}
			// 如果能从月总派工中查到信息则返回信息，否则返回空
			return list;
		} else {
			return null;
		}

	}

	/**
	 * @see根据班次，部门，年月，计算某班次的 translocation = 0;// 转运办理列 load = 0;// 装卸 brigades=
	 *                       0;// 旅发人数 passenger = 0;// 客运收入 freight = 0;//货运收入
	 *                       transport = 0;// 运输收入 postWalk;// 派工岗位
	 */
	public DayWork getClassesGrades(String day, String deptCode, String classes) {
		// 某月有多少天
		int dayNum = getMounthDay.getDay(day);
		// 获取日期的年月
		String[] strs = day.split("-");
		double year = Double.valueOf(strs[0]);
		double mounth = Double.valueOf(strs[1]);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("month", String.valueOf(mounth));
		map1.put("year", String.valueOf(year));
		StationPg stationPg = null;
		Map<String, String> map = GlobalVariable.stationRelation;
		String fatherDept = map.get(deptCode);
		// 如果在车站对应关系中能找到父站编码则使用父站编码
		if (fatherDept != null) {
			// 第一步根据部门编码查询部门名称，例如部门编码：89000，某一天格式2018-05-10
			// 获取部门名称
			String deptName = GlobalVariable.departmentInfo.get(fatherDept);
			String selfDeptName = GlobalVariable.departmentInfo.get(deptCode);
			map1.put("station", deptName);
			// 获取某个站某月的派工总量
			Session session = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tran = session.beginTransaction();
				stationPg = (StationPg) baseDao.entity(session,
						StationPg.class, map1);
				tran.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			// 如果是行车：派工：运转，装卸，货运收入
			if (selfDeptName.indexOf("行车") != -1) {
				stationPg.setBrigades(0.0);
				stationPg.setPassenger(0.0);
				stationPg.setTransport(0.0);
			}
			// 如果是客运：派工：旅发，客运收入
			if (selfDeptName.indexOf("客运") != -1) {
				stationPg.setTranslocation(0.0);
				stationPg.setLoad(0.0);
				stationPg.setFreight(0.0);
				stationPg.setTransport(0.0);
			}
			// 如果是货运：派工：装卸，货运收入
			if (selfDeptName.indexOf("货运") != -1) {
				stationPg.setTranslocation(0.0);
				stationPg.setBrigades(0.0);
				stationPg.setPassenger(0.0);
				stationPg.setTransport(0.0);
			}
			// 如果是装卸：派工：装卸
			if (selfDeptName.indexOf("装卸") != -1) {
				stationPg.setTranslocation(0.0);
				stationPg.setBrigades(0.0);
				stationPg.setPassenger(0.0);
				stationPg.setFreight(0.0);
				stationPg.setTransport(0.0);
			}
			// 如果是晋中站联合班组：派工：运转
			if (selfDeptName.equals("晋中站联合班组")) {
				stationPg.setLoad(0.0);
				stationPg.setBrigades(0.0);
				stationPg.setPassenger(0.0);
				stationPg.setFreight(0.0);
				stationPg.setTransport(0.0);
			}
		} else {
			// 第一步根据部门编码查询部门名称，例如部门编码：89000，某一天格式2018-05-10
			// 获取部门名称
			String deptName = GlobalVariable.departmentInfo.get(deptCode);
			map1.put("station", deptName);
			// 获取某个站某月的派工总量
			Session session = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tran = session.beginTransaction();
				stationPg = (StationPg) baseDao.entity(session,
						StationPg.class, map1);
				tran.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		// 判断stationPg不为空的情况，查询某个站某月总的派工量，根据总派工量计算某个站某个班次的派工量
		DayWork dayWork = null;
		if (stationPg != null) {
			dayWork = new DayWork();
			double coefficient = 0.0;
			if ("日班".equals(classes)) {
				coefficient = GloblaParam.riban;

			} else if ("前夜".equals(classes)) {
				coefficient = GloblaParam.qianye;

			} else if ("后夜".equals(classes)) {
				coefficient = GloblaParam.houye;

			} else if ("日夜".equals(classes)) {
				coefficient = GloblaParam.riye;

			} else if ("夜班".equals(classes)) {
				coefficient = GloblaParam.yeban;

			} else if ("日后".equals(classes)) {
				coefficient = GloblaParam.rihou;

			}
			double brigades = stationPg.getBrigades();// 旅发人数
			dayWork.setBrigades(doubleToInt(brigades / dayNum * coefficient));
			double freight = stationPg.getFreight();// 货运收入
			dayWork.setFreight(doubleToInt(freight / dayNum * coefficient));
			double load = stationPg.getLoad();// 装卸
			dayWork.setLoad(doubleToInt(load / dayNum * coefficient));
			double passenger = stationPg.getPassenger();// 客运收入
			dayWork.setPassenger(doubleToInt(passenger / dayNum * coefficient));
			double translocation = stationPg.getTranslocation();// 转运办理列
			dayWork.setTranslocation(doubleToInt(translocation / dayNum
					* coefficient));
			double transport = stationPg.getTransport();// 运输收入
			dayWork.setTransport(doubleToInt(transport / dayNum * coefficient));
		}
		return dayWork;
	}

	/**
	 * 
	 * @param number
	 * @return
	 * @see把double类型转换为int类型
	 */
	private int doubleToInt(double number) {
		BigDecimal bd = new BigDecimal(number).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		return Integer.parseInt(bd.toString());
	}

}
