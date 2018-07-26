package com.tytlj.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tytlj.www.pojo.StationCountWork;
import com.tytlj.www.service.CountDaoWorkService;
import com.tytlj.www.service.CountStationService;
import com.tytlj.www.util.GetSession;
import com.tytlj.www.util.GlobalVariable;

/**
 * 
 * @author lilei
 * @see车站统计查询
 * 
 */
@Controller
@RequestMapping(value = "/countQueryStation/")
public class StationQueryController {

	@Autowired
	private GetSession getSession;
	@Autowired
	private CountStationService countStationService;
	@Autowired
	private CountDaoWorkService countDaoWorkService;

	/**
	 * 
	 * @param model
	 * @return
	 * @see打开车站统计查询页面
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String indexStationQuery(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf.format(date).toString();
		// 页面回显示开始时间结束时间
		model.addAttribute("startDay", day);
		model.addAttribute("endDay", day);
		return "stationCountDayWork";
	}

	/**
	 * 
	 * @param startDay
	 * @param endDay
	 * @param model
	 * @return
	 * @see车站统计查询，根据开始时间结束时间查询
	 */
	@RequestMapping(value = "queryData", method = RequestMethod.POST)
	public String loadData(String startDay, String endDay, String deptCode,
			Model model) {
		// 从session中获取用户登录部门名称
		List<StationCountWork> list;
		// 如果是管理员登录
		if ("admin".equals(deptCode)) {
			// 查询全部
			list = countStationService.getStationCountWork(startDay, endDay);
		} else {
			// 查询本部门
			list = countStationService.getStationCountWork(startDay, endDay,
					deptCode);
			if (list.size() > 0) {
				list.get(0).setStation(
						GlobalVariable.DEPARTMENTINFO.get(deptCode));
			}
		}

		model.addAttribute("list", list);
		model.addAttribute("startDay", startDay);
		model.addAttribute("endDay", endDay);
		model.addAttribute("deptCode", deptCode);
		return "stationCountDayWork";
	}
}
