package com.tytlj.www.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tytlj.www.pojo.CountDayWork;
import com.tytlj.www.pojo.DayWork;
import com.tytlj.www.service.CountDaoWorkService;
import com.tytlj.www.service.DayWorkService;
import com.tytlj.www.service.GetWebServiceDayWork;

/**
 * 
 * @author lilei
 * @see派工
 * 
 */
@Controller
@RequestMapping("/pic/")
public class PicController extends BaseController {

	private Logger logger = Logger.getLogger(PicController.class);
	@Autowired
	private GetWebServiceDayWork getWebServiceDayWork;
	@Autowired
	private DayWorkService dayWorkService;
	@Autowired
	private CountDaoWorkService countDaoWorkService;

	/**
	 * 
	 * @return日派工页面
	 */
	@RequestMapping(value = "dayWork", method = RequestMethod.GET)
	public String dayWorkIndex(Model model) {
		// 把当前时间回显示
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("oneDay", sdf.format(date).toString());
		return "dayWork";
	}

	/**
	 * @see保存提交的派工信息
	 */
	@RequestMapping(value = "dayWork/save", method = RequestMethod.POST)
	public String dayWorkSave(DayWork dayWork, Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String id = sdf.format(date);
		// 如果主键为空则生成主键，第一次保存的时候是没有主键的，update的时候是有主键
		if (null == dayWork.getId() || "".equals(dayWork.getId())) {
			// 设置主键id
			dayWork.setId(id);
			// 设置为1表示已经派工
		}
		dayWork.setIsWork(1);
		// 同步把派工的信息也保存到记工表中
		dayWorkService.saveOrUpdateDayWork(dayWork);
		CountDayWork countDayWork = new CountDayWork();
		countDayWork.setId(dayWork.getId());
		countDayWork.setBanci(dayWork.getBanci());
		countDayWork.setDate(dayWork.getDate());
		countDayWork.setUserId(dayWork.getUserId());
		countDayWork.setUsername(dayWork.getUsername());
		countDayWork.setPosition(dayWork.getPosition());
		countDayWork.setPostWalk(dayWork.getPostWalk());
		countDayWork.setDeptCode(dayWork.getDeptCode());
		countDayWork.setPassenger_p(dayWork.getPassenger());
		countDayWork.setLoad_p(dayWork.getLoad());
		countDayWork.setBrigades_p(dayWork.getBrigades());
		countDayWork.setFreight_p(dayWork.getFreight());
		countDayWork.setTranslocation_p(dayWork.getTranslocation());
		countDayWork.setTransport_p(dayWork.getTransport());
		countDaoWorkService.saveOrUpdateCountDayWork(countDayWork);
		// 回显数据
		List<DayWork> dayWorks = new ArrayList<DayWork>();
		// 第一次打开页面默认日班
		dayWorks = getWebServiceDayWork.getDayWorkList(dayWork.getDeptCode(),
				dayWork.getDate());
		if (dayWorks != null) {
			model.addAttribute("list", dayWorks);
		}
		// 回显示某一天时间
		model.addAttribute("oneDay", dayWork.getDate());
		// 回显示部门
		model.addAttribute("deptCode", dayWork.getDeptCode());
		return "dayWork";
	}

	/**
	 * 
	 * @return
	 * @see日派工页面加载table数据，后台接口获取当天考勤系统中可派工人员列表,回显示查询时间
	 * @param deptCode部门编码
	 * @param day某一天日期
	 */
	@RequestMapping(value = "dayWorkList", method = RequestMethod.POST)
	public ModelAndView getDayWork(String deptCode, String day, Model model) {
		List<DayWork> dayWorks = new ArrayList<DayWork>();
		dayWorks = getWebServiceDayWork.getDayWorkList(deptCode, day);
		if (dayWorks != null) {
			model.addAttribute("list", dayWorks);
		}
		model.addAttribute("oneDay", day);
		model.addAttribute("deptCode", deptCode);
		return new ModelAndView("dayWork");
	}

	/**
	 * 
	 * @return
	 * @seeajax获取某月某部门某班次的派工情况
	 */
	@RequestMapping(value = "banciWork", method = RequestMethod.GET)
	public @ResponseBody
	DayWork ajaxGetDayWork(String day, String banci, String deptCode) {
		DayWork dayWorks = getWebServiceDayWork.getClassesGrades(day, deptCode,
				banci);
		return dayWorks;
	}
}
