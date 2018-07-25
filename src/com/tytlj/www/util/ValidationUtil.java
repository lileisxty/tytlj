package com.tytlj.www.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ValidationUtil {

	private Logger logger = Logger.getLogger(ValidationUtil.class);

	public Map<String, String> validate(HandlerMethod handlerMethod,
			HttpServletRequest request) {
		// errorsMap保存验证错误信息结果
		Map<String, String> errorsMap = new HashMap<String, String>();
		// validateKey Validations.properties配置文件中的key
		String validateKey = handlerMethod.getBean().getClass().getSimpleName()
				+ "." + handlerMethod.getMethod().getName() + ".rules";
		String validateValue = ValidationResourceUtil.getValueMsg(
				handlerMethod, validateKey);
		// 如果获取到validation.properties配置文件的value
		if (!"".equals(validateValue)) {
			String[] ruleParameters = validateValue.split("\\|");
			for (String ruleParameter : ruleParameters) {
				String[] parameters = ruleParameter.split(":");
				String parameterName = parameters[0];
				String parameterValue = parameters[1];
				// 获取请求参数的值
				String requestParameter = request.getParameter(parameterName);
				logger.info("**属性名**" + parameterName + "**属性类型**"
						+ parameterValue + "**请求参数值**" + requestParameter);
				switch (parameterValue) {
				case "int": {
					if (!ValidationRules.isInt(requestParameter)) {
						String errorMsg = "不是一个int类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.int.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				case "double": {
					if (!ValidationRules.isDouble(requestParameter)) {
						String errorMsg = "这不是一个double类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.double.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				case "String": {
					if (!ValidationRules.isString(requestParameter)) {
						String errorMsg = "这不是一个String类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.String.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				case "date": {
					if (!ValidationRules.isDate(requestParameter)) {
						String errorMsg = "这不是一个date类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.date.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				case "datetime": {
					if (!ValidationRules.isTime(requestParameter)) {
						String errorMsg = "这不是一个datetime类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.datetime.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				case "rand": {
					if (!ValidationRules.isRand(request, requestParameter)) {
						String errorMsg = "这不是一个验证码类型";
						errorMsg = ValidationResourceUtil.getValueMsg(
								handlerMethod, "validation.rand.msg");
						errorsMap.put(parameterName, errorMsg);
						logger.info(errorMsg);
					}
					break;
				}
				}
			}
		}
		return errorsMap;
	}
}
