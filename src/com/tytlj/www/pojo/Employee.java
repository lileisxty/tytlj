package com.tytlj.www.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 管理机构人员信息表
 */
@Component
@XmlRootElement
public class Employee implements Serializable, Comparable<Employee> {

	private static final long serialVersionUID = 1L;
	private String userId;// 工号
	private String username;// 姓名
	private String sex;// 性别
	private String position;// 职位
	private String department;// 部门名称
	private int salary;// 薪资
	private String salaryType;// 薪资计算类型

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	@Override
	public String toString() {
		return "Employee [userId=" + userId + ", username=" + username
				+ ", sex=" + sex + ", position=" + position + ", department="
				+ department + ", salary=" + salary + ", salaryType="
				+ salaryType + "]";
	}

	// 对比两个对象的用户iD,职位,部门信息是否相等
	@Override
	public int compareTo(Employee o) {
		// TODO Auto-generated method stub
		if (!this.userId.equals(o.getUserId())) {
			return -1;
		}
		if (!this.position.equals(o.getPosition())) {
			return -1;
		}
		if (!this.department.equals(o.getDepartment())) {
			return -1;
		}
		return 0;
	}

}