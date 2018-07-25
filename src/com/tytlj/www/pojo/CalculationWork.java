package com.tytlj.www.pojo;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see用于计算过程中临时实体类
 * 
 */
@Component
public class CalculationWork {

	private String id;
	private String userName;
	private String position;

	private String translocation;// 转运办理列
	private String load;// 装卸
	private String brigades;// 旅发人数
	private String passenger;// 客运收入
	private String freight;// 货运收入
	private String transport;// 运输收入

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTranslocation() {
		return translocation;
	}

	public void setTranslocation(String translocation) {
		this.translocation = translocation;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getBrigades() {
		return brigades;
	}

	public void setBrigades(String brigades) {
		this.brigades = brigades;
	}

	public String getPassenger() {
		return passenger;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	@Override
	public String toString() {
		return "CalculationWork [id=" + id + ", userName=" + userName
				+ ", position=" + position + ", translocation=" + translocation
				+ ", load=" + load + ", brigades=" + brigades + ", passenger="
				+ passenger + ", freight=" + freight + ", transport="
				+ transport + "]";
	}

}
