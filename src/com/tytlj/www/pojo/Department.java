package com.tytlj.www.pojo;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement
public class Department {

	private String deptCode;// 部门编号
	private String department;// 部门名称
	private int orderId;// 排序 序号从小到大排序

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "Department [deptCode=" + deptCode + ", department="
				+ department + ", orderId=" + orderId + "]";
	}

}
