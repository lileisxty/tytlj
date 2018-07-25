package com.tytlj.www.pojo;

public class Pothook {

	private String id;
	private String position;
	private double translocation;
	private double load;
	private double brigades;
	private double passenger;
	private double freight;
	private double transport;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
		return "Pothook [id=" + id + ", position=" + position
				+ ", translocation=" + translocation + ", load=" + load
				+ ", brigades=" + brigades + ", passenger=" + passenger
				+ ", freight=" + freight + ", transport=" + transport + "]";
	}

}
