package com.app.tracker.model.common;

import net.sf.json.JSONObject;

/** 
 * @author 陆卫东 E-mail: 770141401@qq.com
 * @version 创建时间：2013-8-12 
 *  类说明 
 */
public class MessageModel {
	
	private boolean success;  // 消息状态
	private String msg;  // 消息提示
	private Object data; // 消息数据
	
	

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJson(){
		
		return JSONObject.fromObject(this).toString();
	}
	
}
