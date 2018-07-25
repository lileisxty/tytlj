package com.tytlj.www.util;

import java.util.LinkedHashMap;

/**
 * 
 * @author lilei
 * @seeShiro授权
 * 
 */
public class FilterChainDefinitionMapBuilder {

	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		// /login/**=anon
		// /login/logout=logout
		// /pages/**=authc
		map.put("/login/**", "anon");
		map.put("/login/logout", "logout");
		map.put("/pages/**", "authc");
		return map;
	}
}
