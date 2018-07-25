package com.tytlj.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tytlj.www.pojo.PersonCountWork;
import com.tytlj.www.service.CountDaoWorkService;
import com.tytlj.www.service.CountPersonService;
import com.tytlj.www.util.GetSession;

@Controller
@RequestMapping("/countQueryPerson/")
public class PersonQueryController {

	@Autowired
	private GetSession getSession;
	@Autowired
	private CountDaoWorkService countDaoWorkService;
	@Autowired
	private CountPersonService countPerson;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf.format(date).toString();
		model.addAttribute("startDay", day);
		model.addAttribute("endDay", day);
		return "personCountDayWork";
	}

	@RequestMapping(value = "queryData", method = RequestMethod.POST)
	public String loadData(String startDay, String endDay, String deptCode,
			Model model) {
		// 根据部门名称查询部门编码
		List<PersonCountWork> list;
		// 如果是管理员登录
		if ("admin".equals(deptCode)) {
			// 查询全部
			list = countPerson.getPersonCountWork(startDay, endDay);
		} else {
			// 查询本部门
			list = countPerson.getPersonCountWork(startDay, endDay, deptCode);
		}

		model.addAttribute("deptCode", deptCode);
		model.addAttribute("list", list);
		model.addAttribute("startDay", startDay);
		model.addAttribute("endDay", endDay);
		return "personCountDayWork";
	}
}
