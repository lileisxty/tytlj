package com.tytlj.www.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
public final class GlobalVariable {

	// key deptCode value department
	public static Map<String, String> DEPARTMENTINFO;

	public static Map<String, String> DEPARTRELATION;// 部门关系，key是一级部门，value是二级部门以逗号隔开

	/**
	 * @see在获取考勤系统的时候去掉包含这个值
	 */
	public static String[] STRVALUES = { "年", "差", "学", "助", "星", "休", "节",
			"补", "病", "事", "婚", "产", "护", "儿", "伤", "旷", "未" };

	public static Set<String> setParam() {
		Set<String> set = new TreeSet<>();
		for (int i = 0; i < STRVALUES.length; i++) {
			set.add(STRVALUES[i]);
		}
		return set;
	}

	// 车站对应关系，key为子站，value为父站
	public static Map<String, String> STATIONRELATION;

}
