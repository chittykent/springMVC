package com.app.simple.utils;

/**
 * 可插入数据库的
 * @author joe
 * @date  2011-10-24 上午10:50:27
 */
public interface Saveable {

	public String getTableName();
	//主键字段
	public String[] getKeyColumns();
}
