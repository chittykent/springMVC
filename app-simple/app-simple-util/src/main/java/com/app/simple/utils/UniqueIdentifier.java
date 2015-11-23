package com.app.simple.utils;

import java.util.UUID;

/**
 * com.sim.tracker.utils
 * create class UniqueIdentifier.java
 * @author zhang bo
 * @description 
 *	创建唯一标识符
 * @createTime 2014年6月20日  下午2:23:57
 */
public class UniqueIdentifier {
	
	/**
	 * @description 
	 * 应用标识
	 * @Author zhang bo
	 * @createDate 2014年6月20日  下午2:32:14
	 * @return
	 */
	public static String applicationId(){
		return commonId().replace("-", "");
	}
	
	/**
	 * @description 
	 * 普通标识
	 * @Author zhang bo
	 * @createDate 2014年6月20日  下午2:32:52
	 * @return
	 */
	public static String commonId(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args) {
		String uniqueIdentifier = commonId();
		System.out.println(uniqueIdentifier);
		System.out.println(uniqueIdentifier.replace("-", ""));
		
		System.out.println(commonId());
		System.out.println(commonId());
		
		//long timestamp = UUID.randomUUID().timestamp();
		//System.out.println(timestamp);
	}
}
