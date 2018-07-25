package com.tytlj.www.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class ValidationRules {

	/**
	 * 
	 * @param str
	 * @return
	 * @see String验证
	 */
	public static boolean isString(String str) {
		if (str == null || "".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @see Int验证
	 */
	public static boolean isInt(String str) {
		if (isString(str)) {
			return str.matches("\\d+");
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @see Double验证
	 */
	public static boolean isDouble(String str) {
		if (isString(str)) {
			return str.matches("\\d+(\\.\\d+)?");
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @see Date验证
	 */
	public static boolean isDate(String str) {
		if (isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @see dateTime验证
	 */
	public static boolean isTime(String str) {
		if (isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false;
	}

	/**
	 * 
	 * @param request
	 * @param str输入页面验证码
	 * @return
	 * @see验证码验证
	 */
	public static boolean isRand(HttpServletRequest request, String str) {
		if (isString(str)) {
			String rand = (String) request.getSession().getAttribute("rand");
			if (isString(rand)) {
				return rand.equalsIgnoreCase(str);
			}
		}
		return false;
	}
}
