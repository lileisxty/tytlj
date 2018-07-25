package com.tytlj.www.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lilei
 */
@Component
public class MakeMajorKey {
	/**
	 * @see返回日期字符串主键精确年月日时分秒毫秒
	 * @return
	 */
	public String Majorkey() {
		Date currentTime = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String majorKey = sdFormat.format(currentTime);
		return majorKey;
	}

	/**
	 * 
	 * @return
	 * @see返回当天的时间年月日
	 */
	public String GetNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sdFormat.format(currentTime);
	}

	/**
	 * 
	 * @return
	 * @see返回当天的是时间年月日时分
	 */
	public String GetNowDateMinute() {
		Date currentTime = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdFormat.format(currentTime);
	}

	/**
	 * 
	 * @return
	 * @see返回昨天的时间年月日
	 */
	public String GetYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal
				.getTime());
		return yesterday;
	}
}
