package com.app.simple.dao;

import java.io.Serializable;
import java.util.List;

import com.app.tracker.model.common.PageModel;


public interface BaseDao<T, PK extends Serializable>{
	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id, String... field);

	/**
	 * 根据ID数组获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids, String... field);

	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param condition
	 *            属性名称
	 * @return 实体对象
	 */
	public T get(String condition, String... field);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll(String condition, String... field);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<String> getColumns();

	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount(String condition);

	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue, String condition);

	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value, String condition);

	/**
	 * 插入数据
	 * 
	 * @param entity
	 */
	public void insert(T entity);

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param t
	 */
	public void update(String sql, T t);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param t
	 */
	public int updateSqlAsync(String sql);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param t
	 */
	public int updateSql(String sql);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);
	
	/**
	 * @Title: delete
	 * @Description: 自定义条件删除
	 * @param @param where
	 * @return void
	 * @throws
	 * @author: ZengZhuo
	 * @date 2014年7月24日 上午11:16:32
	 */
	public void delete(String where);

	/**
	 * 简单的分页获取数据
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageModel<T> getListByPage(int pageNum, int pageSize,
			String condition);

	/**
	 * 随机查询
	 * 
	 * @param randCount
	 * @param rowName
	 * @return
	 */
	public List<T> queryRand(int randCount, String rowName, String condition);

	/**
	 * 判断某一字段是否唯一
	 * 
	 * @Description: 判断某一字段是否唯一
	 * @author xujiang.yi
	 * @date 2014年6月19日 下午3:46:13
	 * @param condition 条件
	 * @return 通过或者重新输入
	 */
	public String isUnique(String condition, String... field);

}
