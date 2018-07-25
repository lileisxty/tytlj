package com.tytlj.www.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtil {

	@Resource
	private MessageSource msgSource;

	public String getValue(String msgKey, Object... obj) {
		return msgSource.getMessage(msgKey, obj, Locale.getDefault());
	}
}
