package com.tytlj.www.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	@Resource
	private MessageSource msgSource;

	public String getValue(String msgKey, Object... obj) {
		return msgSource.getMessage(msgKey, obj, Locale.getDefault());
	}
}
