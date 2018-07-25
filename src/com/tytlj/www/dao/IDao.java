package com.tytlj.www.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public interface IDao {

	/**
	 * 
	 * @param obj保存对象
	 */
	public void entitySave(Session session, Object obj);

	/**
	 * 
	 * @param cls
	 * @param currentPage
	 *            当前页
	 * @param maxResult
	 *            每页条数
	 * @param condtionMap
	 *            查询条件
	 * @return
	 * @see根据条件分页查询结果集
	 */
	public Map<String, Object> entityMap(Session session, Class<?> cls,
			int currentPage, int maxResult, Map<String, String> condtionMap);

	/**
	 * 
	 * @param cls类
	 * @param condtionMap查询条件
	 * @return
	 * @see条件查询
	 */
	public List entityList(Session session, Class<?> cls,
			Map<String, String> condtionMap);

	/**
	 * 
	 * @param cls
	 * @return
	 * @see无条件查询
	 */
	public List entityList(Session session, Class<?> cls);

	public Object entityUniqueResult(Session session, Class<?> cls, String id);

	public void entityUpdate(Session session, Object object);

	public void entitySaveOrUpdate(Session session, Object obj);

	/**
	 * @see清楚表中的数据
	 */
	public void entityClearTable(Session session, String tableName);

	/**
	 * 
	 * @param cls
	 * @param condtion
	 * @return
	 * @see根据多个查询条件返回一个实体类
	 */
	public Object entity(Session session, Class<?> cls,
			Map<String, String> condtion);

}
