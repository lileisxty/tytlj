package com.tytlj.www.util;

import org.springframework.stereotype.Component;

@Component
public class StringEncodingUtil {

	/**
	 * 
	 * @param str
	 * @return
	 * @see字符串编码格式转换
	 */
	public static String getString(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return new String(str.getBytes(encode), encode);
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return new String(str.getBytes(encode), encode);
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return new String(str.getBytes(encode), encode);
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return new String(str.getBytes(encode), encode);
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 十六进制转换字符串
	 * 
	 * @param String
	 *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String start) {
		String[] strArray1 = start.split(":");
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < strArray1.length; i++) {
			strBuffer.append(strArray1[i]);
		}
		String str = "0123456789abcdef";
		char[] hexs = strBuffer.toString().toCharArray();
		byte[] bytes = new byte[strBuffer.toString().length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
}
