package com.tytlj.www.pojo;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see个人工作统计，统计派工记工累计，完成百分比
 */
@Component
public class PersonCountWork {

	private String userId;// 工号
	private String username;// 姓名
	private String position;// 职位

	private int translocation_j = 0;// 转运办理列
	private int load_j = 0;// 装卸
	private int brigades_j = 0;// 旅发人数
	private double passenger_j = 0;// 客运收入
	private double freight_j = 0;// 货运收入
	private double transport_j = 0;// 运输收入

	private int translocation_p = 0;// 转运办理列
	private int load_p = 0;// 装卸
	private int brigades_p = 0;// 旅发人数
	private double passenger_p = 0;// 客运收入
	private double freight_p = 0;// 货运收入
	private double transport_p = 0;// 运输收入

	private double translocation_b = 0;// 转运办理列
	private double load_b = 0;// 装卸
	private double brigades_b = 0;// 旅发人数
	private double passenger_b = 0;// 客运收入
	private double freight_b = 0;// 货运收入
	private double transport_b = 0;// 运输收入

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

	public int getTranslocation_j() {
		return translocation_j;
	}

	public void setTranslocation_j(int translocation_j) {
		this.translocation_j = translocation_j;
	}

	public int getLoad_j() {
		return load_j;
	}

	public void setLoad_j(int load_j) {
		this.load_j = load_j;
	}

	public int getBrigades_j() {
		return brigades_j;
	}

	public void setTransport_j(int transport_j) {
		this.transport_j = transport_j;
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

	public double getPassenger_j() {
		return passenger_j;
	}

	public void setPassenger_j(double passenger_j) {
		this.passenger_j = passenger_j;
	}

	public double getFreight_j() {
		return freight_j;
	}

	public void setFreight_j(double freight_j) {
		this.freight_j = freight_j;
	}

	public double getTransport_j() {
		return transport_j;
	}

	public void setTransport_j(double transport_j) {
		this.transport_j = transport_j;
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

	public double getTranslocation_b() {
		return translocation_b;
	}

	public void setTranslocation_b(double translocation_b) {
		this.translocation_b = translocation_b;
	}

	public double getLoad_b() {
		return load_b;
	}

	public void setLoad_b(double load_b) {
		this.load_b = load_b;
	}

	public double getBrigades_b() {
		return brigades_b;
	}

	public void setBrigades_b(double brigades_b) {
		this.brigades_b = brigades_b;
	}

	public void setBrigades_b(int brigades_b) {
		this.brigades_b = brigades_b;
	}

	public void setBrigades_j(int brigades_j) {
		this.brigades_j = brigades_j;
	}

	public double getPassenger_b() {
		return passenger_b;
	}

	public void setPassenger_b(double passenger_b) {
		this.passenger_b = passenger_b;
	}

	public double getFreight_b() {
		return freight_b;
	}

	public void setFreight_b(double freight_b) {
		this.freight_b = freight_b;
	}

	public double getTransport_b() {
		return transport_b;
	}

	public void setTransport_b(double transport_b) {
		this.transport_b = transport_b;
	}

	@Override
	public String toString() {
		return "PersonCountWork [userId=" + userId + ", username=" + username
				+ ", position=" + position + ", translocation_j="
				+ translocation_j + ", load_j=" + load_j + ", brigades_j="
				+ brigades_j + ", passenger_j=" + passenger_j + ", freight_j="
				+ freight_j + ", transport_j=" + transport_j
				+ ", translocation_p=" + translocation_p + ", load_p=" + load_p
				+ ", brigades_p=" + brigades_p + ", passenger_p=" + passenger_p
				+ ", freight_p=" + freight_p + ", transport_p=" + transport_p
				+ ", translocation_b=" + translocation_b + ", load_b=" + load_b
				+ ", brigades_b=" + brigades_b + ", passenger_b=" + passenger_b
				+ ", freight_b=" + freight_b + ", transport_b=" + transport_b
				+ "]";
	}

}
