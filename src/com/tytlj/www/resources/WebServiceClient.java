package com.tytlj.www.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import jxl.common.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

@Component
public class WebServiceClient {

	private static Logger logger = Logger.getLogger(WebServiceClient.class);

	public static void main(String[] args) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							"D:/allMessage.xml")), "utf-8")));
			Element rootElement = document.getRootElement();
			Iterator<?> iterator = rootElement.elementIterator();
			int i = 1;
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				Iterator<?> iterator1 = element.elementIterator();
				while (iterator1.hasNext()) {
					Element element2 = (Element) iterator1.next();
					// statffcode员工编号
					if ("staff".equals(element2.getName())) {
						logger.info(element2.getText());
					}
					// staffname员工职位
					if ("zwzc".equals(element2.getName())) {
						logger.info(element2.getText());
					}
					// sp_deptname部门名称
					if ("sp_deptname".equals(element2.getName())) {
						logger.info(element2.getText());
					}

				}
				logger.info("----------------------------" + i++
						+ "-------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
