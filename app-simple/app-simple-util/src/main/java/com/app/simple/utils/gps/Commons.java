package com.app.simple.utils.gps;


/**
 * com.dong.util
 * create class Commons.java
 * @author zhang bo
 * @description 
 *	系统公共变量
 * @createTime 2014年5月15日  下午6:51:01
 */
public class Commons {
	//登录类型
	public static final String LOGIN_TYPE_TERMINAL = "1";//终端登录
	public static final String LOGIN_TYPE_WEB = "2";//终端登录
	//登陆方式
	public static final String LOGIN_WAY_LOGIN = "1";//登录
	public static final String LOGIN_WAY_EXIT = "2";//退出
	//报文类型
	//日志类型
	//日志操作
	public static final String LOG_TYPE_QUERY = "查询"; 
	public static final String LOG_TYPE_TERMINAL_SENT = "终端上报";
	public static final String LOG_TYPE_SERVER_SENT = "服务器下发"; 
	public static final String LOG_TYPE_DELETE = "删除"; 
	public static final String LOG_TYPE_UPDATE = "更新"; 
	public static final String LOG_TYPE_INSERT = "插入"; 
	public static final String LOG_TYPE_STATISTIC = "统计";
	public static final String LOG_TYPE_CLEANUP = "清理";
	//是否在线
	public static final int ONLINE_OK = 1;//在线
	public static final int ONLINE_NO = 0;//离线
	
	//终端报文返回状态
	public static final String ALREADY_BACK = "1";//已返回
	public static final String NOT_BACK = "2";//未返回
	//web端轮询超时时间
	public static final long WEB_POLLING_TIMEOUT = 25*1000;//25秒
	//定时清理webResponse_Array响应队列时间
	public static final long RESPONSE_CLEANUP_INTERVAL = 1*60*1000;//1分钟
	//清理webResponse_Array响应队列中过时的响应数据
	public static final long RESPONSE_CLEANUP_TIMEOUT = 30*1000;//30秒
	
	//用户访问标记 与 终端应答的报文匹配程度
	public static final int FIT_LEVEL_VERYHIGH = 4;//非常高
	public static final int FIT_LEVEL_HIGH = 3;//高
	public static final int FIT_LEVEL_MIDDLE = 2;//中
	public static final int FIT_LEVEL_LOW = 1;//低
	
	//成功,失败
	public static final String SUCCESS = "1";//成功
	public static final String FAILURE = "0";//失败
	
	/**########### IoSession attribute ############################*/
	/**终端手机号*/
	public static final String TERMINAL_CELLPHONE_NUMBER = "terminal_cellphone_number";
	/**终端车牌号*/
	public static final String TERMINAL_TARGET_NUMBER = "terminal_target_number";
	
	/**########### memcached key ############################*/
	/**终端在线数*/
	public static final String TERMINAL_ONLINE_NUM = "totalOnLineUser_terminal";
	/**########### memcached key prefix ############################*/
}
