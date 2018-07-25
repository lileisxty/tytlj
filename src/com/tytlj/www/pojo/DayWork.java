package com.tytlj.www.pojo;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see日派工实体表
 * 
 */
@Component
@XmlRootElement
public class DayWork {

	private String id;// 主键
	private String userId;// 工号
	private String username;// 姓名
	private String position;// 职位
	private String deptCode;// 部门编码

	private String banci;// 班次
	private String date;
	private int translocation = 0;// 转运办理列
	private int load = 0;// 装卸
	private int brigades = 0;// 旅发人数
	private double passenger = 0;// 客运收入
	private double freight = 0;// 货运收入
	private double transport = 0;// 运输收入
	private int isWork = 0;// 是否派工0表示未派工，1表示已经派工
	private String postWalk;// 派工岗位

	private String isHoliday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getBanci() {
		return banci;
	}

	public void setBanci(String banci) {
		this.banci = banci;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTranslocation() {
		return translocation;
	}

	public void setTranslocation(int translocation) {
		this.translocation = translocation;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public int getBrigades() {
		return brigades;
	}

	public void setBrigades(int brigades) {
		this.brigades = brigades;
	}

	public double getPassenger() {
		return passenger;
	}

	public void setPassenger(double passenger) {
		this.passenger = passenger;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public double getTransport() {
		return transport;
	}

	public void setTransport(double transport) {
		this.transport = transport;
	}

	public int getIsWork() {
		return isWork;
	}

	public void setIsWork(int isWork) {
		this.isWork = isWork;
	}

	public String getPostWalk() {
		return postWalk;
	}

	public void setPostWalk(String postWalk) {
		this.postWalk = postWalk;
	}

	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

	@Override
	public String toString() {
		return "DayWork [id=" + id + ", userId=" + userId + ", username="
				+ username + ", position=" + position + ", deptCode="
				+ deptCode + ", banci=" + banci + ", date=" + date
				+ ", translocation=" + translocation + ", load=" + load
				+ ", brigades=" + brigades + ", passenger=" + passenger
				+ ", freight=" + freight + ", transport=" + transport
				+ ", isWork=" + isWork + ", postWalk=" + postWalk + "]";
	}

}
