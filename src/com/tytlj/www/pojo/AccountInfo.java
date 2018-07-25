package com.tytlj.www.pojo;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see账户信息表
 * 
 */
@XmlRootElement
@Component
public class AccountInfo {
	private String account;// 账户
	private String password;// 密码
	private String accountType;// 账户类型
	private String name;// 名字
	private String company;// 单位
	private String department;// 所属部门
	private String deptCode;// 部门编码
	private String role;// 角色

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AccountInfo [account=" + account + ", password=" + password
				+ ", accountType=" + accountType + ", name=" + name
				+ ", company=" + company + ", department=" + department
				+ ", deptCode=" + deptCode + ", role=" + role
				+ ", getAccount()=" + getAccount() + ", getPassword()="
				+ getPassword() + ", getAccountType()=" + getAccountType()
				+ ", getName()=" + getName() + ", getCompany()=" + getCompany()
				+ ", getDepartment()=" + getDepartment() + ", getDeptCode()="
				+ getDeptCode() + ", getRole()=" + getRole() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
