package com.tytlj.www.pojo;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see日计工实体类
 * 
 */
@Component
public class CountDayWork {

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

	private int translocation_p = 0;// 转运办理列(派工)
	private int load_p = 0;// 装卸(派工)
	private int brigades_p = 0;// 旅发人数(派工)

	private double passenger_p = 0;// 客运收入(派工)
	private double freight_p = 0;// 货运收入(派工)
	private double transport_p = 0;// 运输收入(派工)
	private String postWalk;// 派工岗位

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

	public int getTranslocation_p() {
		return translocation_p;
	}

	public void setTranslocation_p(int translocation_p) {
		this.translocation_p = translocation_p;
	}

	public int getLoad_p() {
		return load_p;
	}

	public void setLoad_p(int load_p) {
		this.load_p = load_p;
	}

	public int getBrigades_p() {
		return brigades_p;
	}

	public void setBrigades_p(int brigades_p) {
		this.brigades_p = brigades_p;
	}

	public double getPassenger_p() {
		return passenger_p;
	}

	public void setPassenger_p(double passenger_p) {
		this.passenger_p = passenger_p;
	}

	public double getFreight_p() {
		return freight_p;
	}

	public void setFreight_p(double freight_p) {
		this.freight_p = freight_p;
	}

	public double getTransport_p() {
		return transport_p;
	}

	public void setTransport_p(double transport_p) {
		this.transport_p = transport_p;
	}

	public String getPostWalk() {
		return postWalk;
	}

	public void setPostWalk(String postWalk) {
		this.postWalk = postWalk;
	}

	@Override
	public String toString() {
		return "CountDayWork [id=" + id + ", userId=" + userId + ", username="
				+ username + ", position=" + position + ", deptCode="
				+ deptCode + ", banci=" + banci + ", date=" + date
				+ ", translocation=" + translocation + ", load=" + load
				+ ", brigades=" + brigades + ", passenger=" + passenger
				+ ", freight=" + freight + ", transport=" + transport
				+ ", translocation_p=" + translocation_p + ", load_p=" + load_p
				+ ", brigades_p=" + brigades_p + ", passenger_p=" + passenger_p
				+ ", freight_p=" + freight_p + ", transport_p=" + transport_p
				+ ", postWalk=" + postWalk + "]";
	}

}
