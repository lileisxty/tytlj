package com.tytlj.www.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tytlj.www.dao.IDao;
import com.tytlj.www.pojo.CalculationWork;
import com.tytlj.www.pojo.Employee;
import com.tytlj.www.pojo.Pothook;
import com.tytlj.www.pojo.Salary;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.HibernateSessionFactory;

@Service
public class SalaryService {

	@Autowired
	private IDao baseDao;
	private Logger logger = Logger.getLogger(SalaryService.class);

	/**
	 * 
	 * @return查询所有的挂钩指标
	 */
	private List<Pothook> getAllPothook() {
		Session session = null;
		List<Pothook> list = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			list = baseDao.entityList(session, Pothook.class);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * @param list挂钩指标集合
	 * @return加工挂钩指标list集合，返回一个挂钩指标的map集合
	 */
	private Map<String, Pothook> makeMapPothook(List<Pothook> list) {

		Iterator<Pothook> iterator = list.iterator();
		Map<String, Pothook> map = new HashMap<String, Pothook>();
		while (iterator.hasNext()) {
			Pothook pothook = iterator.next();
			map.put(pothook.getPosition(), pothook);
		}
		return map;
	}

	/**
	 * 
	 * @return根据部门编码，查询一个部门的全部用户,如果此用户当月已经提交工资则不可以重新提交工资
	 */
	private List<Employee> getOneDeptCustom(String department, String month) {
		Session session = null;
		List<Employee> list = null;
		Map<String, String> condition = new HashMap<String, String>();
		Map<String, String> condition1 = new HashMap<String, String>();
		condition1.put("month", month);
		condition.put("department", department);
		List<Employee> employees = new ArrayList<Employee>();
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			list = baseDao.entityList(session, Employee.class, condition);
			int temp = list.size();
			for (int i = 0; i < temp; i++) {
				condition1.put("employeeId", list.get(i).getUserId());
				Salary salary = (Salary) baseDao.entity(session, Salary.class,
						condition1);
				// 如果有数据查询出来则说明此用户已经提交工资信息，则不可以重新提交，从计算工资列表中去掉
				if (salary == null) {
					employees.add(list.get(i));
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employees;
	}

	/**
	 * 
	 * @param startDay开始时间
	 * @param endDay结束时间
	 * @param deptCode部门编码
	 * @return返回一个map集合数据key是员工编号 value是员工信息记工统计实体类
	 */
	private Map<String, CalculationWork> queryPersonCount(String startDay,
			String endDay, String deptCode) {
		Map<String, CalculationWork> map = new HashMap<String, CalculationWork>();
		// 记工统计查询
		String sqljg = "SELECT countdaywork.`userId`,SUM(countdaywork.`translocation`),SUM(countdaywork.`aload`),SUM(countdaywork.`brigades`),SUM(countdaywork.`passenger`),SUM(countdaywork.`freight`),SUM(countdaywork.`transport`),countdaywork.`username`,countdaywork.`position` "
				+ "FROM countdaywork WHERE (countdaywork.`deptCode`=? AND countdaywork.`oneDay` BETWEEN ? AND ?) GROUP BY countdaywork.`userId`;";
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sqljg);
			query.setParameter(0, deptCode);
			query.setParameter(1, startDay);
			query.setParameter(2, endDay);
			List listjg = query.list();
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
				map.put(obj[0].toString(), calculationWork);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return map;

	}

	public void saveSalary(Salary salary) {
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			baseDao.entitySave(session, salary);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param deptCode
	 * @return返回部门员工工资信息list集合。第一步查询部门员工信息：工号，姓名，职位，绩效薪资，并计算系数。 
	 *                                                          第二步查询挂钩指标。第三步查询某员工当月的挂钩指标的累计完成数
	 *                                                          。
	 */
	public List<Salary> getDeptSalary(String deptCode, String startDay,
			String endDay, String month) {
		List<Salary> list = new ArrayList<Salary>();
		// 员工记工统计map key是员工编码,把部门员工的记工都统计出来
		Map<String, CalculationWork> mapCalculationWork = queryPersonCount(
				startDay, endDay, deptCode);
		// 挂钩指标 key是指标职位名称
		Map<String, Pothook> mapPothook = makeMapPothook(getAllPothook());
		// 部门全部员工信息
		List<Employee> employees = getOneDeptCustom(
				GlobalVariable.departmentInfo.get(deptCode), month);
		Iterator<Employee> iterator = employees.iterator();
		// 找出部门员工工资最大的数
		List<Integer> max = new ArrayList<Integer>();
		for (Employee employee : employees) {
			if (employee.getSalary() > 4000) {
				max.add(employee.getSalary());
			}
		}
		// 工资最小值
		int minSalary = Collections.min(max);
		// 查询本部门，同种类职位工资总和 返回结果集为Map<String,int> key为职位名称，value为工资总和
		Map<String, Integer> map = sumSalary(deptCode);

		// 循环员工信息
		while (iterator.hasNext()) {
			Salary salary = new Salary();
			Employee employee = iterator.next();
			// 当前员工的绩效薪资信息
			int pay = employee.getSalary();
			salary.setBaseSalary(employee.getSalary());// 基本工资
			salary.setEmployeeId(employee.getUserId());// 员工工号
			salary.setEmployeeJob(employee.getPosition());// 员工职务
			salary.setEmployeeName(employee.getUsername());// 员工姓名
			salary.setDeptCode(deptCode);
			salary.setMonth(month);// 月份

			// 根据职务取这个部门的这个职务所有人的工资总和
			int sumSalary = map.get(employee.getPosition());
			double coefficient = employee.getSalary() * 1.0 / minSalary;
			// 工资系数保留两位小数
			BigDecimal p = new BigDecimal(new Double(coefficient).toString());

			double coeff = p.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			// 如果系数小于1.0则按1.0计算
			if (coeff < 1.0) {
				salary.setCoefficient(1.0);
			} else {
				salary.setCoefficient(coeff);
			}
			// 找出员工月累计记工量
			CalculationWork calculationWork = mapCalculationWork.get(employee
					.getUserId());
			// 员工职位
			String position = employee.getPosition();
			// 根据职位找挂钩指标
			Pothook pothook = mapPothook.get(position);
			if (pothook != null) {
				// 找出员工采用哪个站挂钩，客运，客运货运，货运，其它站
				if (employee.getSalaryType().equals("其它站")) {
					// 取出挂钩指标
					// String translocation;运转办理列
					// 挂钩指标占百分比
					double tran = pothook.getTranslocation();
					// 从记工中取出月记工总数,如果不为空则说明这个员工有记工
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/月运转办理列总数
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
								salary.setPayroll(translocation);
							}
						}
					}
				}
				if (employee.getSalaryType().equals("客货运站")) {
					// 取出挂钩指标
					double load = pothook.getLoad();
					// 取出挂钩指标
					// String translocation;运转办理列
					// 挂钩指标占百分比
					double tran = pothook.getTranslocation();
					// 从记工中取出月记工总数,如果不为空则说明这个员工有记工
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}
						}
						if (calculationWork.getLoad() != null) {
							if (!"0.0".equals(calculationWork.getLoad())
									&& !"0".equals(calculationWork.getLoad())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getLoad()));
								// 运转办理列单价
								double load_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * load_price);
								double loadSalary = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setLoad(load_price);
								salary.setTran_price(load_price);
							}
						}
					}
				}
				if (employee.getSalaryType().equals("客运站")) {
					// 取出挂钩指标 运转办理列
					double tran = pothook.getTranslocation();// 挂钩指标占百分比
					if (calculationWork != null) {
						if (calculationWork.getLoad() != null) {
							if (!"0.0".equals(calculationWork.getLoad())
									&& !"0".equals(calculationWork.getLoad())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getLoad()));
								// 运转办理列单价
								double load_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * load_price);
								double loadSalary = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setLoad(load_price);
								salary.setTran_price(load_price);
							}
						}
					}

				}
				if (employee.getSalaryType().equals("货运站")) {
					// 取出挂钩指标 运转办理列，装卸车数
					double tran = pothook.getTranslocation();// 挂钩指标占百分比
					double load = pothook.getLoad();
					// 从记工中取出月记工总数,如果不为空则说明这个员工有记工
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}
						}
						if (calculationWork.getLoad() != null) {
							if (!"0.0".equals(calculationWork.getLoad())
									&& !"0".equals(calculationWork.getLoad())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* tran
										/ coeff
										/ Double.valueOf(calculationWork
												.getLoad()));
								// 运转办理列单价
								double load_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * tran
										* coeff * load_price);
								double loadSalary = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setLoad(load_price);
								salary.setTran_price(load_price);
							}
						}
					}
				}
				// --------------------------没有找到职位对应详细挂钩指标数--------------------------------
			} else {
				// 找出员工采用哪个站挂钩，客运，客运货运，货运，其它站
				if (employee.getSalaryType().equals("其它站")) {
					// 从记工中取出月记工总数,如果不为空则说明这个员工有记工
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.7
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.7
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}
						}
						if (calculationWork.getTransport() != null) {
							if (!"0.0".equals(calculationWork.getTransport())
									&& !"0".equals(calculationWork
											.getTransport())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.3
										/ coeff
										/ Double.valueOf(calculationWork
												.getTransport()));
								// 运转办理列单价
								double trans_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.3
										* coeff * trans_price);
								double transport = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTransport(transport);
								salary.setTrans_price(trans_price);
							}
						}
					}
				}
				if (employee.getSalaryType().equals("客货运站")) {
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.1
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.1
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}
						}
						if (calculationWork.getTransport() != null) {
							if (!"0.0".equals(calculationWork.getTransport())
									&& !"0".equals(calculationWork
											.getTransport())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.6
										/ coeff
										/ Double.valueOf(calculationWork
												.getTransport()));
								// 运转办理列单价
								double trans_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.6
										* coeff * trans_price);
								double transport = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTransport(transport);
								salary.setTrans_price(trans_price);
							}
						}
						if (calculationWork.getLoad() != null) {
							if (!"0".equals(calculationWork.getLoad())
									&& !"0.0".equals(calculationWork.getLoad())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.1
										/ coeff
										/ Double.valueOf(calculationWork
												.getLoad()));
								// 装卸单价
								double load_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.6
										* coeff * load_price);
								double load = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setLoad(load);
								salary.setLoad_price(load_price);
							}
						}
					}
				}
				if (employee.getSalaryType().equals("客运站")) {
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.1
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.1
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}

						}
						if (calculationWork.getTransport() != null) {
							if (!"0.0".equals(calculationWork.getTransport())
									&& !"0".equals(calculationWork
											.getTransport())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.6
										/ coeff
										/ Double.valueOf(calculationWork
												.getTransport()));
								// 运转办理列单价
								double trans_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.6
										* coeff * trans_price);
								double transport = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTransport(transport);
								salary.setTrans_price(trans_price);
							}
						}
						if (calculationWork.getBrigades() != null) {
							if (!"0.0".equals(calculationWork.getBrigades())
									&& !"0".equals(calculationWork
											.getBrigades())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.3
										/ coeff
										/ Double.valueOf(calculationWork
												.getBrigades()));
								// 旅发单价
								double brig_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.3
										* coeff * brig_price);
								double brigades = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setBrigades(brigades);
								salary.setBrig_price(brig_price);
							}

						}
					}
				}
				if (employee.getSalaryType().equals("客货运站")) {
					if (calculationWork != null) {
						if (calculationWork.getTranslocation() != null) {
							if (!"0.0".equals(calculationWork
									.getTranslocation())
									&& !"0".equals(calculationWork
											.getTranslocation())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.1
										/ coeff
										/ Double.valueOf(calculationWork
												.getTranslocation()));
								// 运转办理列单价
								double tran_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.1
										* coeff * tran_price);
								double translocation = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTranslocation(translocation);
								salary.setTran_price(tran_price);
							}

						}
						if (calculationWork.getTransport() != null) {
							if (!"0.0".equals(calculationWork.getTransport())
									&& !"0".equals(calculationWork
											.getTransport())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.3
										/ coeff
										/ Double.valueOf(calculationWork
												.getTransport()));
								// 运转办理列单价
								double trans_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.3
										* coeff * trans_price);
								double transport = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setTransport(transport);
								salary.setTrans_price(trans_price);
							}

						}
						if (calculationWork.getBrigades() != null) {
							if (!"0.0".equals(calculationWork.getBrigades())
									&& !"0".equals(calculationWork
											.getBrigades())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.3
										/ coeff
										/ Double.valueOf(calculationWork
												.getBrigades()));
								// 旅发单价
								double brig_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.3
										* coeff * brig_price);
								double brigades = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 运转办理列工资
								salary.setBrigades(brigades);
								salary.setBrig_price(brig_price);
							}
						}
						if (calculationWork.getLoad() != null) {
							if (!"0.0".equals(calculationWork.getLoad())
									&& !"0".equals(calculationWork.getLoad())) {
								// 计算单价：单价=绩效薪/系数/运转办理列
								BigDecimal bd = new BigDecimal(sumSalary
										* 0.3
										/ coeff
										/ Double.valueOf(calculationWork
												.getLoad()));
								// 装卸单价
								double load_price = bd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								BigDecimal bd1 = new BigDecimal(pay * 0.3
										* coeff * load_price);
								double load = bd1.setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();

								// 装卸工资
								salary.setLoad(load);
								salary.setLoad_price(load_price);
							}
						}
					}
				}
			}
			double payroll = salary.getBrigades() + salary.getFreight()
					+ salary.getTranslocation() + salary.getTransport()
					+ salary.getLoad() + salary.getPassenger();
			salary.setPayroll(payroll);
			list.add(salary);
		}
		return list;
	}

	public List<Salary> getSalarys(Map<String, String> condition) {
		List<Salary> list = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			list = baseDao.entityList(session, Salary.class, condition);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * 
	 * @param month
	 * @param employeeId
	 * @see删除提交的工资信息
	 */
	public void deleteSalary(String id) {
		String sqljg = "DELETE FROM salary WHERE salary.`id`=?";
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sqljg);
			query.setParameter(0, id);
			query.executeUpdate();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Map<String, Integer> sumSalary(String deptCode) {
		String sql = "SELECT SUM(employee.`salary`),employee.`position` FROM employee WHERE employee.`department`=? GROUP BY employee.`position`";
		Session session = null;
		Transaction tran = null;
		String department = GlobalVariable.departmentInfo.get(deptCode);
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			session = HibernateSessionFactory.getSession();
			tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, department);
			List list = query.list();
			for (Object object : list) {
				Object[] obj = (Object[]) object;
				int value = Integer.valueOf(obj[0].toString());
				String key = obj[1].toString();
				map.put(key, value);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return map;
	}
}
