package com.tytlj.www.controller;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tytlj.www.pojo.Salary;
import com.tytlj.www.service.SalaryService;
import com.tytlj.www.util.GetMounthDay;
import com.tytlj.www.util.GetSession;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.MakeMajorKey;

/**
 * 
 * @author lilei
 * @see工资计算
 * 
 */
@Controller
@RequestMapping("/distribution/")
public class DistributionController {

	@Autowired
	private GetSession getSession;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private GetMounthDay getMounthDay;
	@Autowired
	private MakeMajorKey makeMajorKey;

	private Logger logger = Logger.getLogger(DistributionController.class);

	/**
	 * 
	 * @return
	 * @see打开工资计算页面
	 */
	@RequestMapping(value = "pieceworkliquidation", method = RequestMethod.GET)
	public String pieceworkliquidation(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		model.addAttribute("month", sdf.format(date).toString());
		return "salary";
	}

	/**
	 * 
	 * @param month
	 * @param deptCode
	 * @param model
	 * @return根据时间，部门编码查询部门的人员工资信息
	 */
	@RequestMapping(value = "salaryList", method = RequestMethod.GET)
	public String getSalaryList(String month, String deptCode, Model model) {
		String firstDay = getMounthDay.getFirstDayOfMonth(month);
		String lastDay = getMounthDay.getLastDayOfMonth(month);
		List<Salary> list = salaryService.getDeptSalary(deptCode, firstDay,
				lastDay, month);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("list", list);
		model.addAttribute("month", month);
		return "salary";
	}

	/**
	 * 
	 * @param salary
	 * @param model
	 * @return
	 * @see打开工资详情页面
	 */
	@PostMapping("salaryDetail")
	public String detailSalay(Salary salary, Model model) {
		model.addAttribute("salary", salary);
		return "salaryDetail";
	}

	/**
	 * 
	 * @param employeeId
	 * @param employeeName
	 * @param employeeJob
	 * @param baseSalary
	 * @param coefficient
	 * @param tran_price
	 * @param translocation
	 * @param load_price
	 * @param load
	 * @param brig_price
	 * @param brigades
	 * @param pass_price
	 * @param passenger
	 * @param frei_price
	 * @param freight
	 * @param trans_price
	 * @param transport
	 * @param examine
	 * @param assessment
	 * @param payroll
	 * @param month
	 * @param remark
	 * @param deptCode
	 * @param model
	 * @return
	 * @see保存工资信息
	 */
	@PostMapping("saveSalary")
	public ModelAndView saveSalayDetail(String employeeId, String employeeName,
			String employeeJob, String baseSalary, String coefficient,
			String tran_price, String translocation, String load_price,
			String load, String brig_price, String brigades, String pass_price,
			String passenger, String frei_price, String freight,
			String trans_price, String transport, String examine,
			String assessment, String payroll, String month, String remark,
			String deptCode, Model model, int bgRed, int red, int yellow,
			double assessmentValue, String examineType) {
		Salary salary = new Salary();
		salary.setId(makeMajorKey.Majorkey());
		salary.setEmployeeId(employeeId);
		salary.setEmployeeName(employeeName);
		salary.setEmployeeJob(employeeJob);
		salary.setBaseSalary(Integer.valueOf(baseSalary));
		salary.setCoefficient(Double.valueOf(coefficient));
		salary.setTran_price(Double.valueOf(tran_price));
		salary.setTranslocation(Double.valueOf(translocation));
		salary.setLoad_price(Double.valueOf(load_price));
		salary.setLoad(Double.valueOf(load));
		salary.setBrig_price(Double.valueOf(brig_price));
		salary.setBrigades(Double.valueOf(brigades));
		salary.setPass_price(Double.valueOf(pass_price));
		salary.setPassenger(Double.valueOf(passenger));
		salary.setFrei_price(Double.valueOf(frei_price));
		salary.setFreight(Double.valueOf(freight));
		salary.setTrans_price(Double.valueOf(trans_price));
		salary.setTransport(Double.valueOf(transport));
		salary.setExamineType(examineType);
		salary.setBgRed(bgRed);
		salary.setRed(red);
		salary.setYellow(yellow);
		if ("".equals(examine)) {
			examine = "0";
		}
		salary.setExamine(Integer.valueOf(examine));
		salary.setAssessment(Double.valueOf(assessment));
		salary.setAssessmentValue(assessmentValue);
		salary.setPayroll(Double.valueOf(payroll));
		salary.setMonth(month);
		salary.setDeptCode(deptCode);
		salary.setRemark(remark);
		salary.setIsPost(1);
		salaryService.saveSalary(salary);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("month", month);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("salary");
		return mv;
	}

	/**
	 * 
	 * @return
	 * @see打开薪资审核页面
	 */
	@GetMapping("salaryExamine")
	public ModelAndView openExamine(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		model.addAttribute("month", sdf.format(date).toString());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("salaryExamine");
		return mv;
	}

	/**
	 * 
	 * @param month
	 * @param deptCode
	 * @param model
	 * @return
	 * @see
	 */
	@RequestMapping(value = "salaryExamineList", method = RequestMethod.GET)
	public String getSalarysList(String month, String deptCode, Model model) {
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("month", month);
		condition.put("deptCode", deptCode);
		List<Salary> list = salaryService.getSalarys(condition);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("list", list);
		model.addAttribute("month", month);
		return "salaryExamine";
	}

	/**
	 * 
	 * @param month
	 * @param employeeId
	 * @return
	 * @see退回审核不通过的工资
	 */
	@PostMapping("salaryDetailDelete")
	public String deleteSalaryDetail(String id, String month, String deptCode,
			Model model) {
		salaryService.deleteSalary(id);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("month", month);
		return "salaryExamine";
	}

	@GetMapping("exportSalary")
	public ResponseEntity<byte[]> exportSalary(HttpServletRequest request,
			@RequestParam String month, @RequestParam String deptCode) {
		System.out.println("month:" + month + "deptCode:" + deptCode);
		ByteArrayOutputStream ops = null;
		String department = GlobalVariable.departmentInfo.get(deptCode);
		String fileName = department + month + "应发工资明细导出.xlsx";
		XSSFWorkbook wb = null;
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("month", month);
		condition.put("deptCode", deptCode);
		try {
			List<Salary> list = salaryService.getSalarys(condition);
			ops = new ByteArrayOutputStream();
			wb = new XSSFWorkbook();
			Sheet sheet1 = wb.createSheet();
			Row row1 = sheet1.createRow(0);
			row1.createCell(0).setCellValue("工号");
			row1.createCell(1).setCellValue("姓名");
			row1.createCell(2).setCellValue("部门");
			row1.createCell(3).setCellValue("职务");
			row1.createCell(4).setCellValue("绩效薪标准");
			row1.createCell(5).setCellValue("系数");
			row1.createCell(6).setCellValue("运转办理列");

			row1.createCell(7).setCellValue("运转办理列(单价)");
			row1.createCell(8).setCellValue("装卸车数");
			row1.createCell(9).setCellValue("装卸车数(单价)");
			row1.createCell(10).setCellValue("旅发人数");
			row1.createCell(11).setCellValue("旅发人数(单价)");
			row1.createCell(12).setCellValue("客运收入");
			row1.createCell(13).setCellValue("客运收入(单价)");
			row1.createCell(14).setCellValue("货运收入");
			row1.createCell(15).setCellValue("货运收入(单价)");
			row1.createCell(16).setCellValue("运输收入");
			row1.createCell(17).setCellValue("运输收入(单价)");

			row1.createCell(18).setCellValue("基本上线");
			row1.createCell(19).setCellValue("基本上线扣除");
			row1.createCell(20).setCellValue("大红");
			row1.createCell(21).setCellValue("红");
			row1.createCell(22).setCellValue("黄");

			row1.createCell(23).setCellValue("日常考核");
			row1.createCell(24).setCellValue("日常考核扣除");
			row1.createCell(25).setCellValue("应发工资");
			Iterator<Salary> iterator = list.iterator();
			int flag = 1;
			while (iterator.hasNext()) {
				Salary salary = iterator.next();
				Row insertRow = sheet1.createRow(flag);
				insertRow.createCell(0).setCellValue(salary.getEmployeeId());
				insertRow.createCell(1).setCellValue(salary.getEmployeeName());
				insertRow.createCell(2).setCellValue(department);
				insertRow.createCell(3).setCellValue(salary.getEmployeeJob());
				insertRow.createCell(4).setCellValue(salary.getBaseSalary());
				insertRow.createCell(5).setCellValue(salary.getCoefficient());

				insertRow.createCell(6).setCellValue(salary.getTranslocation());
				insertRow.createCell(7).setCellValue(salary.getTran_price());
				insertRow.createCell(8).setCellValue(salary.getLoad());
				insertRow.createCell(9).setCellValue(salary.getLoad_price());
				insertRow.createCell(10).setCellValue(salary.getBrigades());
				insertRow.createCell(11).setCellValue(salary.getBrig_price());
				insertRow.createCell(12).setCellValue(salary.getPassenger());
				insertRow.createCell(13).setCellValue(salary.getPass_price());
				insertRow.createCell(14).setCellValue(salary.getFreight());
				insertRow.createCell(15).setCellValue(salary.getFrei_price());
				insertRow.createCell(16).setCellValue(salary.getTransport());
				insertRow.createCell(17).setCellValue(salary.getTrans_price());

				insertRow.createCell(18).setCellValue(salary.getExamineType());
				insertRow.createCell(19).setCellValue(salary.getExamine());
				insertRow.createCell(20).setCellValue(salary.getBgRed());
				insertRow.createCell(21).setCellValue(salary.getRed());
				insertRow.createCell(22).setCellValue(salary.getYellow());

				insertRow.createCell(23).setCellValue(
						salary.getAssessment() + "%");
				insertRow.createCell(24).setCellValue(
						salary.getAssessmentValue());
				insertRow.createCell(25).setCellValue(salary.getPayroll());
				flag++;
			}
			wb.write(ops);
			wb.close();
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		// 下载文件
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(ops.toByteArray(), headers,
				HttpStatus.CREATED);
	}
}
