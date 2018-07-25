package com.tytlj.www.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ValidationResourceUtil {
	private static Logger logger = Logger
			.getLogger(ValidationResourceUtil.class);

	/**
	 * 
	 * @param handlerMethod
	 * @return
	 * @see数据验证返回的错误页面
	 */
	public static String getErrorPageValue(HandlerMethod handlerMethod) {
		String errorPageKey = handlerMethod.getBean().getClass()
				.getSimpleName()
				+ "." + handlerMethod.getMethod().getName() + ".rules.error";
		String pageUri = getValueMsg(handlerMethod, errorPageKey);
		if ("".equals(pageUri)) {
			pageUri = getValueMsg(handlerMethod, "CommonErrorPage");
		}
		return pageUri;
	}

	/**
	 * 
	 * @param handlerMethod
	 * @param keyMsg
	 * @return
	 * @see 获取properties配置文件value值
	 */
	public static String getValueMsg(HandlerMethod handlerMethod, String keyMsg) {
		Method method = null;
		String msg = null;
		try {
			// 获取getValue()方法
			method = handlerMethod.getBean().getClass()
					.getMethod("getValue", String.class, Object[].class);
			msg = method.invoke(handlerMethod.getBean(), keyMsg, null)
					.toString();
			logger.info("**" + msg + "**");
		} catch (Exception e) {
			msg = "";
			e.printStackTrace();
		}
		return msg;
	}
}
