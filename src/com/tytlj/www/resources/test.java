package com.tytlj.www.resources;

import java.math.BigDecimal;

public class test {

	public static void main(String[] args) {
		double f = 3.1315;
		BigDecimal b = new BigDecimal(new Double(f).toString());
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
	}
}
