package com.app.simple.utils.gps;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.app.simple.utils.DateUtil;



public class ServerResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger(ServerResponse.class);
	
	/**
	 * 
	 * @param phone 终端手机号
	 * @return
	 */
	public static String responsePhone(String phone){
		
		return serverResponse("S6","PHONE=" + phone);
	}
	/**
	 * 
	 * @param phone 用户手机号
	 * @return
	 */
	public static String responseUserNo(String phone){
		
		return serverResponse("S6","USER=" + phone);
	}
	
	/**
	 * server端返回的数据
	 * @param strings
	 * @return
	 */
	public static String serverResponse(String ...strings){
		
		String reValue = "[";
		if(strings.length > 0){
			if("S1".equals(strings[0])||"S6".equals(strings[0])){
				reValue += DateUtil.getDateSecondFormat();
				logger.info("S1报文下发时间: "+reValue);
				for (int i = 0; i < strings.length; i++) {
					reValue += "," + strings[i];
				}
			}else{
				reValue += strings[0];
				for (int i = 1; i < strings.length; i++) {
					reValue += "," + strings[i];
				}
			}
			reValue += "]";
		}
		return reValue;
	}
}
