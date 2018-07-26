package com.tytlj.www.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tytlj.www.pojo.Department;
import com.tytlj.www.pojo.StationPg;
import com.tytlj.www.service.StaticService;
import com.tytlj.www.service.UserService;
import com.tytlj.www.util.GetSession;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.SessionKey;

/**
 * 
 * @author lilei
 * @see各站月派工总指标
 * 
 */
@Controller
@RequestMapping(value = "/postPg/")
public class StaticPgController {

	private Logger logger = Logger.getLogger(StaticPgController.class);

	@Autowired
	private GetSession getSession;
	@Autowired
	private UserService userService;
	@Autowired
	private StaticService stationService;

	/**
	 * 
	 * @param model
	 * @return
	 * @see打开各部门月派工页面
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String openPostPage(Model model) {
		Map<String, String> condition = new HashMap<String, String>();
		List<Department> departments = new ArrayList<Department>();
		// 从session中获取用户登录部门编码
		String deptCode = getSession.getSessionValue(SessionKey.deptCode);
		// 如果是管理员登录
		if ("admin".equals(deptCode)) {
			departments = userService.detpList();
		} else {
			// 把内存中的部门信息返回页面
			String departmentName = GlobalVariable.DEPARTMENTINFO.get(deptCode);
			Department department = new Department();
			department.setDepartment(departmentName);
			department.setDeptCode(deptCode);
			departments.add(department);
			// 将查询条件放到map中，根据部门名称查询
			condition.put("station", departmentName);
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		String year = sdf.format(date).toString() + ".0";
		String monthOld = sdf1.format(date).toString();
		String firstStr = monthOld.substring(0, 1);
		String month = null;
		if ("0".equals(firstStr)) {
			month = monthOld.substring(1) + ".0";
		} else {
			month = monthOld + ".0";
		}
		// 默认查询当月的站派工
		condition.put("year", year);
		condition.put("month", month);
		List<StationPg> list = stationService.getStations(condition);
		model.addAttribute("list", list);
		model.addAttribute("station", departments);
		return "postPage";
	}

	/**
	 * 
	 * @param stationPg
	 * @see保存stationPg记录
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFile(MultipartFile excelFile, Model model) {
		String deptCode = getSession.getSessionValue(SessionKey.deptCode);
		// 如果是管理操作则才执行文件解析
		if ("admin".equals(deptCode)) {
			String fileName = excelFile.getOriginalFilename();
			// 获得文件后缀
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!excelFile.isEmpty()) {

				// 获得文件后缀
				if (fileType.equals("xls") || fileType.equals("xlsx")) {
					List<StationPg> list = new ArrayList<StationPg>();
					try {
						HSSFWorkbook wb = new HSSFWorkbook(
								excelFile.getInputStream());
						Sheet sheet = wb.getSheetAt(0);
						int lastRowNum = sheet.getLastRowNum();
						Row row = null;
						Cell cell_1 = null;
						Cell cell_2 = null;
						Cell cell_3 = null;
						Cell cell_4 = null;
						Cell cell_5 = null;
						Cell cell_6 = null;
						Cell cell_7 = null;
						Cell cell_8 = null;
						Cell cell_9 = null;
						for (int i = 2; i < lastRowNum; i++) {
							StationPg stationPg = new StationPg();
							row = sheet.getRow(i);// 取得第i行
							// cell_0 = row.getCell(0);// 取得i行的第一列
							cell_1 = row.getCell(1);// 取得i行的第二列
							cell_2 = row.getCell(2);// 取得i行的第三列
							cell_3 = row.getCell(3);// 取得i行的第四列
							cell_4 = row.getCell(4);// 取得i行的第五列
							cell_5 = row.getCell(5);// 取得i行的第六列
							cell_6 = row.getCell(6);// 取得i行的第七列
							cell_7 = row.getCell(7);// 取得i行的第八列
							cell_8 = row.getCell(8);// 取得i行的第九列
							cell_9 = row.getCell(9);// 取得i行的第十列
							// double cellValue0 = cell_0.getNumericCellValue();
							double cellValue1 = cell_1.getNumericCellValue();
							double cellValue2 = cell_2.getNumericCellValue();
							String cellValue3 = cell_3.getStringCellValue()
									.trim();
							double cellValue4 = cell_4.getNumericCellValue();// 装车
							double cellValue5 = cell_5.getNumericCellValue();// 旅发
							double cellValue6 = cell_6.getNumericCellValue();// 运输
							double cellValue7 = cell_7.getNumericCellValue();// 客运
							double cellValue8 = cell_8.getNumericCellValue();// 货运
							double cellValue9 = cell_9.getNumericCellValue();// 办理
							stationPg.setId(UUID.randomUUID().toString());
							stationPg.setYear(String.valueOf(cellValue1));
							stationPg.setMonth(String.valueOf(cellValue2));
							stationPg.setStation(cellValue3);

							stationPg.setLoad(cellValue4);// 装车(车)
							stationPg.setBrigades(cellValue5);// 旅发人数(人)
							stationPg.setTransport(cellValue6);// 运输收入(万元)
							stationPg.setPassenger(cellValue7);// 客运收入(万元)
							stationPg.setFreight(cellValue8);// 货运收入(万元)
							stationPg.setTranslocation(cellValue9);// 办理列(列)
							list.add(stationPg);
						}
						stationService.deleteStationPgs(list.get(0).getYear(),
								list.get(0).getMonth());
						stationService.saveStationPgs(list);
					} catch (IOException e) { // TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return new ModelAndView("redirect:index");
	}
}
