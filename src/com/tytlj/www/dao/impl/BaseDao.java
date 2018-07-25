package com.tytlj.www.dao.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tytlj.www.dao.IDao;
import com.tytlj.www.util.HibernateSessionFactory;

@Component("baseDao")
public class BaseDao implements IDao {

	private Logger logger = Logger.getLogger(BaseDao.class);
	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;

	@Override
	public void entitySave(Session session, Object obj) {
		// TODO Auto-generated method stub
		session.save(obj);
	}

	/**
	 * @see cls实体类，currentPage当前页，maxResult每页显示条数，conditionMap查询条件 返回一个map结合
	 */
	@Override
	public Map<String, Object> entityMap(Session session, Class<?> cls,
			int currentPage, int maxResult, Map<String, String> conditionMap) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(cls);
		// 查询总页数
		Criteria criteria1 = HibernateSessionFactory.getSession()
				.createCriteria(cls);
		// 如果有当前页和每页显示多少条数据则设置查询
		if (currentPage != 0 || maxResult != 0) {
			criteria.setFirstResult((currentPage - 1) * maxResult);
			criteria.setMaxResults(maxResult);
		}
		// 有查询条件查询
		if (conditionMap.size() > 0) {
			// getFields()获得某个类的所有的公共（public）的字段，包括父类。
			// getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
			// 同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()。
			Field[] fileds = cls.getDeclaredFields();
			for (Field filed : fileds) {
				// 获取cls类属性名
				String conditionKey = filed.getName();
				// 根据属性名去条件中查询条件值
				String conditionValue = conditionMap.get(conditionKey);
				if (null != conditionValue) {
					criteria.add(Restrictions.eq(conditionKey, conditionValue));
					criteria1
							.add(Restrictions.eq(conditionKey, conditionValue));
				}
			}
		}
		criteria1.setProjection(Projections.rowCount());
		Long allRowsCount = (Long) criteria1.uniqueResult();
		List<?> list = criteria.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allRowsCount", allRowsCount);
		map.put("entityList", list);
		return map;
	}

	/**
	 * @see根据用户id查询用户信息
	 */
	@Override
	public Object entityUniqueResult(Session session, Class<?> cls, String id) {
		// TODO Auto-generated method stub
		return session.get(cls, id);
	}

	@Override
	public void entityUpdate(Session session, Object object) {
		session.update(object);

	}

	@Override
	public void entitySaveOrUpdate(Session session, Object obj) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(obj);
	}

	@Override
	public void entityClearTable(Session session, String tableName) {
		// TODO Auto-generated method stub
		String sql = "delete from " + tableName + "";
		session.createSQLQuery(sql).executeUpdate();
	}

	/**
	 * @see根据条件返回一个List<Object>集合
	 */
	@Override
	public List entityList(Session session, Class<?> cls,
			Map<String, String> condtion) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(cls);
		// 有查询条件查询
		if (condtion.size() > 0) {
			// getFields()获得某个类的所有的公共（public）的字段，包括父类。
			// getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
			// 同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()。
			Field[] fileds = cls.getDeclaredFields();
			for (Field filed : fileds) {
				// 获取cls类属性名
				String conditionKey = filed.getName();
				// 根据属性名去条件中查询条件值
				String conditionValue = condtion.get(conditionKey);
				if (null != conditionValue) {
					criteria.add(Restrictions.eq(conditionKey, conditionValue));
				}
			}
		}
		List<?> list = criteria.list();
		return list;
	}

	/**
	 * 
	 * @param cls
	 * @param condtion
	 * @return
	 * @see根据查询条件返回一个实体类
	 */
	public Object entity(Session session, Class<?> cls,
			Map<String, String> condtion) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(cls);
		// 有查询条件查询
		if (condtion.size() > 0) {
			// getFields()获得某个类的所有的公共（public）的字段，包括父类。
			// getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
			// 同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()。
			Field[] fileds = cls.getDeclaredFields();
			for (Field filed : fileds) {
				// 获取cls类属性名
				String conditionKey = filed.getName();
				// 根据属性名去条件中查询条件值
				String conditionValue = condtion.get(conditionKey);
				if (null != conditionValue) {
					criteria.add(Restrictions.eq(conditionKey, conditionValue));
				}
			}
		}
		return criteria.uniqueResult();
	}

	@Override
	public List entityList(Session session, Class<?> cls) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(cls);
		return criteria.list();
	}

}
