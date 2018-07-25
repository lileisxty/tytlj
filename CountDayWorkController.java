package com.tytlj.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tytlj.www.dao.impl.BaseDao;
import com.tytlj.www.pojo.CountDayWork;
import com.tytlj.www.service.CountDaoWorkService;
import com.tytlj.www.service.StaticService;
import com.tytlj.www.service.UserService;
import com.tytlj.www.util.GetSession;

/**
 * 
 * @author lilei
 * @see计工
 * 
 */
@Controller
@RequestMapping(value = "/countDayWork/")
public class CountDayWorkController {

	@Autowired
	private GetSession getSession;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private StaticService stationService;
	@Autowired
	private CountDaoWorkService countDaoWorkService;

	private Logger logger = Logger.getLogger(CountDayWorkController.class);

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String openCountDayWorkPage(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("oneDay", sdf.format(date).toString());
		return "countDayWork";
	}

	/**
	 * 
	 * @return
	 * @see从派工表中查询某天的某个班次
	 */
	@RequestMapping(value = "countDayWorkList", method = RequestMethod.GET)
	public ModelAndView openCountPage(String day, String banci,
			String deptCode, Model model) {
		Map<String, String> condition = new HashMap<String, String>();
		// 如果是管理员登录
		if ("admin".equals(deptCode)) {
			logger.info("admin用户登录所以查询所有部门");
		} else {
			// 将查询条件放到map中，部门编码查询
			condition.put("deptCode", deptCode);
		}
		// 将查询条件放到map中，班次查询
		condition.put("banci", banci);
		// 将查询条件放到map中，某一天
		condition.put("date", day);
		// 查询历史部门月派工情况
		List<CountDayWork> list = countDaoWorkService
				.getCountDayWorks(condition);
		model.addAttribute("list", list);
		model.addAttribute("oneDay", day);
		model.addAttribute("banci", banci);
		model.addAttribute("deptCode", deptCode);
		return new ModelAndView("countDayWork");
	}

	/**
	 * 
	 * @return
	 * @see保存或者更新CountDayWork类
	 */
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	public ModelAndView updateCountDayWork(CountDayWork countDayWork,
			RedirectAttributes attr) {
		// 保存记工信息
		countDaoWorkService.saveOrUpdateCountDayWork(countDayWork);
		String day = countDayWork.getDate();
		String banci = countDayWork.getBanci();
		attr.addAttribute("day", day);
		attr.addAttribute("banci", banci);
		return new ModelAndView(
				"redirect:/countDayWork/countDayWorkList?deptCode="
						+ countDayWork.getDeptCode());
	}

	/**
	 * 
	 * @param day
	 * @param banci
	 * @param model
	 * @return
	 * @修改某一个部门某一个班次某项记工的值
	 */
	@RequestMapping(value = "dayWorkUpdate", method = RequestMethod.GET)
	public ModelAndView updateOneBanci(String day, String banci, String item,
			double setValue, Model model, String deptCode,
			RedirectAttributes attr) {
		if ("riban".equals(banci)) {
			banci = "日班";
		}
		if ("qianye".equals(banci)) {
			banci = "前夜";
		}
		if ("houye".equals(banci)) {
			banci = "后夜";
		}
		if ("riye".equals(banci)) {
			banci = "日夜";
		}
		if ("yeban".equals(banci)) {
			banci = "夜班";
		}
		if ("rihou".equals(banci)) {
			banci = "日后";
		}
		countDaoWorkService.updateCountDayWork(deptCode, day, banci, item,
				setValue);
		attr.addAttribute("day", day);
		attr.addAttribute("banci", banci);
		attr.addAttribute("deptCode", deptCode);
		return new ModelAndView("redirect:/countDayWork/countDayWorkList");
	}
}
