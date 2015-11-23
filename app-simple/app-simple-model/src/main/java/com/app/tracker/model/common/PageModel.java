package com.app.tracker.model.common;

import java.util.List;

/** 
 * @author 陆卫东 E-mail: 770141401@qq.com
 * @version 创建时间：2013-8-30 
 *  类说明 
 */
public class PageModel<T> {
	
	private int pageNum= 1; // 当前页
	private int pageSize = 10; // 页大小	
	private long pageCount = 0; // 总页数
	private long allCount=0;
	
	private List<T> list;// 数据List
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public long getAllCount() {
		return allCount;
	}
	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}
	
	
}
