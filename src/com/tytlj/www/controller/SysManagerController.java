package com.tytlj.www.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tytlj.www.pojo.Department;
import com.tytlj.www.service.UserService;
import com.tytlj.www.util.GetSession;
import com.tytlj.www.util.GlobalVariable;
import com.tytlj.www.util.MD5Util;
import com.tytlj.www.util.SessionKey;

/**
 * 
 * @author lilei
 * @see一级菜单：系统管理。二级菜单：管理机构工作人员。
 * 
 */
@Controller
@RequestMapping("/sysManager/")
public class SysManagerController {
	private Logger logger = Logger.getLogger(SysManagerController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private GetSession getSession;

	/**
	 * 
	 * @return
	 * @see打开系统管理一级菜单页面
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String sysManagerPage() {
		return "sysManager";
	}

	/**
	 * 
	 * @return
	 * @see打开管理机构人员页面，后台查询数据默认显示第一页
	 * @param cp当前页
	 *            ，ls每页显示条数，col显示的列，kw查询关键字
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Map getAllPerson(int cp, int ls, String col, String kw, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		// 保存查询返回的结果集
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if ("".equals(kw)) {
			String deptCode = getSession.getSessionValue(SessionKey.deptCode);
			// 如果不是admin登录
			if (!deptCode.equals("admin")) {
				// 1先根据登录用户的部门编码找对应的部门名称
				String deptName = GlobalVariable.DEPARTMENTINFO.get(deptCode);
				// 2将查询条件部门名称放到map中，根据部门名称查询
				map.put("department", deptName);
			}
		} else {
			// 1先根据登录用户的部门编码找对应的部门名称
			String deptName = GlobalVariable.DEPARTMENTINFO.get(kw);
			// 2将查询条件部门名称放到map中，根据部门名称查询
			map.put("department", deptName);
		}
		resultMap = userService.EmployeeList(cp, ls, map);
		return resultMap;
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @see返回部门信息下拉列表框
	 */
	@RequestMapping(value = "position", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	List getPosition() {
		List<Department> departments = new ArrayList<Department>();
		// 从session中获取用户登录部门编码
		String deptCode = getSession.getSessionValue(SessionKey.deptCode);
		// 如果不是管理员登录
		if ("admin".equals(deptCode)) {
			departments = userService.detpList();
		} else {
			Map<String, String> map = GlobalVariable.DEPARTRELATION;
			Set<String> set = map.keySet();
			// 如果是有上级部门信息则显示上下级部门
			if (set.contains(deptCode)) {
				String deptCodeStr = map.get(deptCode);
				String[] deptCodes = deptCodeStr.split(",");
				for (int i = 0; i < deptCodes.length; i++) {
					String departmentName = GlobalVariable.DEPARTMENTINFO
							.get(deptCodes[i]);
					Department department = new Department();
					department.setDepartment(departmentName);
					department.setDeptCode(deptCodes[i]);
					departments.add(department);
				}
			} else {
				// 把内存中的部门信息返回页面
				String departmentName = GlobalVariable.DEPARTMENTINFO
						.get(deptCode);
				Department department = new Department();
				department.setDepartment(departmentName);
				department.setDeptCode(deptCode);
				departments.add(department);
			}

		}
		return departments;
	}

	/**
	 * 
	 * @return
	 * @see打开修改密码页面
	 */
	@RequestMapping(value = "changePwdPage", method = RequestMethod.GET)
	public String changePwdPage(Model model) {
		return "changePwd";

	}

	/**
	 * 
	 * @return
	 * @see保存新密码
	 */
	@RequestMapping(value = "savePwd", method = RequestMethod.POST)
	public String saveNewPwd(@RequestParam("newPassword") String newPassword) {
		String userId = getSession.getSessionValue(SessionKey.account);
		String md5Pwd = null;
		try {
			md5Pwd = MD5Util.EncoderByMd5(newPassword);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userService.updatePassword(userId, md5Pwd);
		return "successSavePwd";
	}
}
