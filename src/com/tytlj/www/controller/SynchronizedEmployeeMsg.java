package com.tytlj.www.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.tytlj.www.dao.IDao;
import com.tytlj.www.pojo.Employee;
import com.tytlj.www.util.HibernateSessionFactory;

/**
 * 
 * @author lilei
 * @see同步 员工信息
 */
@Controller
public class SynchronizedEmployeeMsg {

	private Logger logger = Logger.getLogger(SynchronizedEmployeeMsg.class);
	@Autowired
	private IDao baseDao;

	// 每天23点执行一次："0 0 23 * * ?"
	// 每天凌晨1点执行一次："0 0 1 * * ?"
	// @Scheduled(cron = "0 0 1 * * ?")
	public void taskCreateDutyExcel() {
		String webServiceUrl = "http://10.81.76.17/ty_cwd/ getstafflist?token=33dac39bfd057605ef44a6dbd08fa39f";
		Map<String, Employee> map = new HashMap<String, Employee>();
		try {
			Client client = Client.create();
			WebResource src = client.resource(webServiceUrl);
			// webService返回String类型的数据
			String webServiceResultString = src.get(String.class);
			SAXReader reader = new SAXReader();
			// 读取字符串
			InputStream ipt = new ByteArrayInputStream(
					webServiceResultString.getBytes());
			Document document = reader.read(new BufferedReader(
					new InputStreamReader(ipt, "UTF-8")));
			Element rootElement = document.getRootElement();
			Iterator<?> iterator = rootElement.elementIterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				Iterator<?> iterator1 = element.elementIterator();
				Employee employee = new Employee();
				while (iterator1.hasNext()) {
					Element element2 = (Element) iterator1.next();
					// statffcode员工编号
					if ("staff".equals(element2.getName())) {
						employee.setUserId(element2.getText());
						logger.info(element2.getText());
					}
					// staffname员工职位
					if ("zwzc".equals(element2.getName())) {
						employee.setPosition(element2.getText());
						logger.info(element2.getText());
					}
					// sp_deptname部门名称
					if ("sp_deptname".equals(element2.getName())) {
						employee.setDepartment(element2.getText());
					}
				}
				map.put(employee.getUserId(), employee);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			List<Employee> dataList = baseDao.entityList(session,
					Employee.class);
			Iterator<Employee> iterator = dataList.iterator();
			while (iterator.hasNext()) {
				Employee emp = iterator.next();
				Employee emp1 = map.get(emp.getUserId());
				if (emp1 != null) {
					int i = emp.compareTo(emp1);
					if (i == -1) {
						Employee employee = iterator.next();
						String sql = "UPDATE employee SET employee.`position`=? ,employee.`department` =? WHERE employee.`userId`=?";
						SQLQuery query = session.createSQLQuery(sql);
						query.setParameter(0, employee.getPosition());
						query.setParameter(1, employee.getDepartment());
						query.setParameter(2, employee.getUserId());
						query.executeUpdate();
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

}
