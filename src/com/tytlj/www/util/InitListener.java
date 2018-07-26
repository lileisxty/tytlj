package com.tytlj.www.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.tytlj.www.service.InitDataService;

@Component
public class InitListener implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		initGlobalWebServiceClientInfo();
	}

	private void initGlobalWebServiceClientInfo() {
		InitDataService initDataService = new InitDataService();
		GlobalVariable.DEPARTMENTINFO = initDataService.mapDepartmentInfo();
		GlobalVariable.DEPARTRELATION = initDataService.getDeptRelation();
		GlobalVariable.STATIONRELATION = initDataService.getStationRelation();
	}

}
