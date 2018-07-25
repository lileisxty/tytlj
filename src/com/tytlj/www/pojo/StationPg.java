package com.tytlj.www.pojo;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see各站月派工总量实体类
 * 
 */
@Component
public class StationPg {

	private String id;// 主键
	private String month;// 月
	private String year;// 年
	private String station;// 站名

	private double translocation = 0;// 办理列(列)
	private double load = 0;// 装车(车)
	private double brigades = 0;// 旅发人数(人)
	private double passenger = 0;// 客运收入(万元)
	private double freight = 0;// 货运收入(万元)
	private double transport = 0;// 运输收入(万元)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public double getTranslocation() {
		return translocation;
	}

	public void setTranslocation(double translocation) {
		this.translocation = translocation;
	}

	public double getLoad() {
		return load;
	}

	public void setLoad(double load) {
		this.load = load;
	}

	public double getBrigades() {
		return brigades;
	}

	public void setBrigades(double brigades) {
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

	@Override
	public String toString() {
		return "StationPg [id=" + id + ", month=" + month + ", year=" + year
				+ ", station=" + station + ", translocation=" + translocation
				+ ", load=" + load + ", brigades=" + brigades + ", passenger="
				+ passenger + ", freight=" + freight + ", transport="
				+ transport + "]";
	}

}
