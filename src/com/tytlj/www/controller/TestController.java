package com.tytlj.www.controller;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.tytlj.www.pojo.StationPg;
import com.tytlj.www.service.TestSevice;

@Controller
@RequestMapping("/test/")
public class TestController {

	@Autowired
	private TestSevice testService;

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void makeData(MultipartFile excelFile) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(excelFile.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;
			Cell cell_1 = null;
			Cell cell_2 = null;
			for (int i = 0; i < lastRowNum; i++) {
				StationPg stationPg = new StationPg();
				row = sheet.getRow(i);// 取得第i行
				// cell_0 = row.getCell(0);// 取得i行的第一列
				cell_1 = row.getCell(0);// 取得i行的第二列
				cell_2 = row.getCell(1);// 取得i行的第三列
				// double cellValue0 = cell_0.getNumericCellValue();
				String cellValue1 = cell_1.getStringCellValue();
				String cellValue2 = cell_2.getStringCellValue();
				testService.testUpdate(cellValue1, cellValue2);
				System.out.println("cellValue1:" + cellValue1
						+ "----cellValue2:" + cellValue2);
			}
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
