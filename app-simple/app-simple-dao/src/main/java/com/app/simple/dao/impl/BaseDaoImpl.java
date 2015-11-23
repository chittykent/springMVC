package com.app.simple.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.scheduling.annotation.Async;

import com.app.simple.dao.BaseDao;
import com.app.simple.utils.ReflectUtil;
import com.app.simple.utils.StringUtil;
import com.app.tracker.model.annotation.Key;
import com.app.tracker.model.annotation.NotRecord;
import com.app.tracker.model.annotation.Table;
import com.app.tracker.model.common.PageModel;


public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	Logger log = Logger.getLogger(BaseDaoImpl.class);
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private Class<T> entityClass;
	private String tableName;
	private NamedParameterJdbcTemplate npjt;
	private List<String> keysList = new ArrayList<String>();
	private List<String> notRecords = new ArrayList<String>();
	private List<String> commonField = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	protected BaseDaoImpl() {
		this.entityClass = null;
		@SuppressWarnings("rawtypes")
		Class<? extends BaseDaoImpl> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}

		Field[] fiels = this.entityClass.getDeclaredFields();// 获得反射对象集合
		boolean t = this.entityClass.isAnnotationPresent(Table.class);// 获得类是否有注解
		if (t) {
			tableName = this.entityClass.getAnnotation(Table.class).name();
		} else {
			tableName = StringUtil.classToTableName(entityClass.getSimpleName());
		}
		setTableName(tableName);
		for (Field fl : fiels) {
			fl.setAccessible(true);// 开启支私有变量的访问权限
			if (fl.isAnnotationPresent(Key.class)) {// 判断是否存在主键
				keysList.add(fl.getName());
			} else if (fl.isAnnotationPresent(NotRecord.class)) {
				notRecords.add(fl.getName());
			} else {
				commonField.add(fl.getName());
			}
		}
	}

	
	private  String getQueryStr(String... field){
		if(field==null || field.length==0){
			return "*";
		}else{
			String temp="";
			for (String fd:field) {
				temp+=fd+",";
			}
			return temp.substring(0, temp.length()-1);
		}
	}
	
	public String getTableName() {
		return tableName;
	}

	private void setTableName(String tableName) {
		this.tableName = tableName;
	}

	// 用id查询很少用到
	public T get(PK id,String... field ) {

		String sql = "select "+getQueryStr(field)+" from " + tableName + " where id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(entityClass), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	// 用id查询很少用到
	public List<T> get(PK[] ids,String... field) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		String sql = "select "+getQueryStr(field)+" from " + tableName + "  where id in(:ids)";
		return getNamedParameterJdbcTemplate().query(sql, parameters, new BeanPropertyRowMapper<T>(entityClass));
	}

	@Override
	public T get(String condition,String... field) {
		if (condition == null) {
			condition = " 1=1 limit 1 ";
		}
		String sql = "select "+getQueryStr(field)+"  from " + tableName + " as model where  " + condition;
		
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(entityClass));
		} catch (EmptyResultDataAccessException e) {
			log.info(sql + ", no result");
		} catch (Exception e) {
			printStackTrace(e);
		}
		return null;
	}

	/**
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public T get(String propertyName, Object value, String condition) {
		if (condition == null) {
			condition = " 1=1 ";
		}
		String sql = "select *  from " + tableName + " as model where model." + propertyName + " = ? and " + condition;
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(entityClass), value);
		} catch (EmptyResultDataAccessException e) {
			log.info(sql + ", no result");

		} catch (Exception e) {
			printStackTrace(e);
		}
		return null;
	}

	
	/**
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public List<T> getAll(String condition, String... field) {
		if (condition == null) {
			condition = " 1=1 ";
		}
		String sql = "select "+getQueryStr(field)+"  from " + tableName + " where " + condition;
//		System.out.println(sql);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(entityClass));
	}

	/**
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public Long getTotalCount(String condition) {
		if (condition == null) {
			condition = " 1=1 ";
		}
		String sql = "select count(*) from " + tableName + " where " + condition;

		return jdbcTemplate.queryForLong(sql);
	}

	/**
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue, String condition) {
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue, condition);
		return (object == null);
	}

	/**
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public boolean isExist(String propertyName, Object value) {
		String condition=null;
		T object = get(propertyName, value, condition);
		return (object != null);
	}

	public PK save(T entity) {
		return saveAndReturnKey(entity, keysList.toArray(new String[] {}));
	}
	
	
	

	public void update(T entity) {

		String sql="update "+tableName +" set ";
			for (String rowName: commonField) {
				if(ReflectUtil.invokeGet(entity, rowName)!=null){
					sql+=rowName+"=:"+rowName+" , ";
				}
			}
			sql=sql.substring(0,sql.length()-2) + " where ";
			for (int i = 0; i < keysList.size(); i++) {
				if(i == keysList.size() -1){
					sql+=keysList.get(i)+"=:" + keysList.get(i);
				}else{
					sql+=keysList.get(i)+"=:" + keysList.get(i) + " and ";
				}
			}
//			sql+=" where id=:id";
			update(sql, entity);
			
	}

	public void update(String sql, T t) {
		//jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(t));
		getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(t));
		
	}

	

	/**
	 * 根据主键删除——不进行数据删除操作，一律将状态更新成9
	 */
	public void delete(PK id) {
		 String sql = "delete from " + tableName + " where "+keysList.get(0)+" = ?";
		 jdbcTemplate.update(sql, new Object[] { id });
	}

	/**
	 * 批量删除——不进行数据删除操作，一律将状态更新成9
	 */
	public void delete(PK[] ids) {
		for (PK id : ids) {
			delete(id);
		}
	}
	
	/**
	 * @Title: delete
	 * @Description: 自定义条件删除
	 * @param @param where
	 * @return void
	 * @throws
	 * @author: ZengZhuo
	 * @date 2014年7月24日 上午11:01:30
	 */
	public void delete(String where){
		String sql = "delete from " + tableName + " where "+where;
		 jdbcTemplate.execute(sql);
	}


	public PageModel<T> getListByPage(int pageNum, int pageSize, String condition) {
		PageModel<T> page = new PageModel<T>();
		if (StringUtils.isBlank(condition)) {
			condition = " 1=1 ";
		}
		String sql = "select * from " + tableName + " where " + condition + " limit " + pageSize * (pageNum - 1) + "," + pageSize;
		page.setList(jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(entityClass)));	
		long allCount=getTotalCount(condition);
		if(allCount%pageSize==0){
			page.setPageCount(allCount/pageSize);
		}else{
			page.setPageCount((allCount/pageSize)+1);
		}
		page.setAllCount(allCount);
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		return page;
	}

	/**
	 * 添加实体,返回主键
	 * 
	 * @param objForSave
	 * @param keyColumns
	 * @return
	 */
	private PK saveAndReturnKey(Object objForSave, String... keyColumns) {
		SimpleJdbcInsert sji = getSimpleJdbcInsert();
		sji.usingGeneratedKeyColumns(keyColumns);
		sji.setGeneratedKeyNames(keysList.toArray(new String[] {}));
		PK newId = (PK) sji.executeAndReturnKey(new BeanPropertySqlParameterSource(objForSave));
		return newId;
	}

	// ----tools
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		if (npjt == null) {
			npjt = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		}
		return npjt;
	}

	/**
	 * 根据条件更新单字段值
	 * 
	 * @param targetName
	 *            字段名
	 * @param targetVal
	 *            字段值
	 * @param condition
	 *            为查询附带的条件，如 " status !=9 "，如果没有可以写null
	 */
	public void updateSingleByCondition(String targetName, Object targetVal, String condition) {

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("update ").append(this.getTableName()).append(" set ").append(targetName).append(" = ? ");

		if (condition == null) {
			condition = " 1=1 ";
		}

		sqlBuffer.append(" where " + condition);

		jdbcTemplate.update(sqlBuffer.toString(), new Object[] { targetVal });
	}

	private void printStackTrace(Exception e) {
		StackTraceElement[] traces = e.getStackTrace();
		for (int i = 0; i < traces.length; i++) {
			log.error(traces[i]);
		}

	}

	/**
	 * "下一篇"功能的 SQL有问题，暂时不用
	 */
	public List<T> queryRand(int randCount, String rowName, String condition) {
		if (StringUtils.isBlank(condition)) {
			condition = " 1=1 ";
		}
		String sql = "SELECT * FROM " + tableName + " AS t1 JOIN (SELECT ROUND(RAND() * " + "((SELECT MAX(" + rowName + ") FROM " + tableName + ")-(SELECT MIN(" + rowName + ") FROM " + tableName
				+ "))" + " +(SELECT MIN(" + rowName + ") FROM " + tableName + ")) AS id) AS t2" + " WHERE t1." + rowName + " >= t2." + rowName + " and " + condition + " ORDER BY t1." + rowName
				+ " LIMIT " + randCount;

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(entityClass));
	}

	@Override
	public List<String> getColumns() {

		return commonField;
	}

	@Override
	public int updateSql(String sql) {
		return jdbcTemplate.update(sql);
	}

	@Override
	public boolean isExist(String propertyName, Object value, String condition) {
		T object = get(propertyName, value, condition);
		return (object != null);
	}

	@Override
	public void insert(T entity) {
		getNamedParameterJdbcTemplate().update(getInsertSql(), new BeanPropertySqlParameterSource(entity));
	}
	
	@Override
	public String isUnique(String condition, String... field) {
		String flag = null;
		T model = null;
		if (condition == null) {
			condition = " 1=1 limit 1 ";
		}else {
			condition += " limit 1 ";
		}
		String sql = "select "+getQueryStr(field)+"  from " + tableName + " as model where  " + condition;
		
		try {
			 model =  jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(entityClass));
			
		} catch (EmptyResultDataAccessException e) {
			log.info(sql + ", no result");
		} catch (Exception e) {
			printStackTrace(e);
		}
		flag = model == null ? "pass" : "retry";
		return flag; 
	}
	
	/**
	 * 生成插入的sql语句
	 * @param model
	 * @param tableName
	 * @return
	 */
	private  String getInsertSql() {
		StringBuffer sb=new StringBuffer();
		sb.append("insert into "+tableName +" (");
		for (int i = 0; i < commonField.size(); i++) {
			sb.append(commonField.get(i)+", ");
		}
		sb.deleteCharAt(sb.length()-2);
		sb.append(") values (");
		for (int i = 0; i < commonField.size(); i++) {
			sb.append(":"+commonField.get(i)+", ");
		}
		sb.deleteCharAt(sb.length()-2);
		sb.append(")");
		return sb.toString();
	}

	@Async
	public int updateSqlAsync(String sql) {
		
		return jdbcTemplate.update(sql);
	}

}
