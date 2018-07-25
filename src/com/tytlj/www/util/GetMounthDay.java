package com.tytlj.www.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 * @see获取年某月有多少天
 */
@Component
public class GetMounthDay {

	/**
	 * 
	 * @param date输入一个yyyy
	 *            -MM格式的日期
	 * @return返回某年某月有几天
	 */
	public int getDay(String date) {
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date monthDate = null;
		try {
			monthDate = sdfMonth.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		return day;
	}

	/**
	 * 
	 * @return返回某月的第一天
	 */
	public String getFirstDayOfMonth(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date monthDate = null;
		try {
			monthDate = sdfMonth.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置1号为当前日期即为本月第一天
		String firstDay = format.format(calendar.getTime());
		return firstDay;
	}

	/**
	 * 
	 * @return返回某月的最后一天
	 */
	public String getLastDayOfMonth(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date monthDate = null;
		try {
			monthDate = sdfMonth.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = format.format(calendar.getTime());
		return lastDay;
	}
}
