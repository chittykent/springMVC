package com.app.simple.utils.gps;

/**
 * com.dong.tools
 *
 * create class ProtocolCommon.java
 *
 * @author zhang bo
 *
 * @description 
 *	协议查询参数
 * @createTime 2014年5月7日  下午4:57:26
 *
 */
public enum ProtocolQueryParamater {
	/**仅可查询参数******************/
	SOFTVERSION,//软件版本号
	GSM,//GSM信号强度
	GPS,//GPS接收的卫星编号和强度
	VBAT,//电池电压，充电接口电压
	VIN,//外接电源输入电压
	LOGIN,//是否已经连接到后台
	IMSI,//终端内SIM卡的IMSI号码
	IMEI,//终端设备的IMSI号码
	ACC,//ACC的当前状态
	
	/**可配置参数也可查询******************/
	PSW,//防盗器密码,一般为6位数字
	DOMAIN,//服务器地址和端口。如：DOMAIN=WWW.CHEWEISHI.CN:8500
	FREQ,//GPS定位信息的上报频率。单位：秒；默认15秒;可设置范围:5~30
	PULSE,//正常供电下终端的心跳时间。单位：秒；默认120秒;可设置范围:60~150
	PHONE,//防盗器内SIM卡号码
	USER,//设置车主号码
	RADIUS,//GPS围栏告警距离，>=300米
	VIB,//非法移动告警短信，1表示开启，0表示关闭，默认为开启状态
	VIBL,//非法移动告警感应灵敏度0~15 , 0为最高灵敏度，15为最低灵敏度
	POF,//断电和低电告警开关，1表示开启，0表示关闭
	LBV,//低电量告警阈值，默认为3700000，即3.7v
	SLEEP,//开启、关闭休眠节电模式，如果为1，则防盗器在60分钟内没有发生振动，则进入休眠
			//节电状态；一旦进入休眠模式可通过以下方式激活：（1）发生振动，（2）自动唤醒
	WAKEUPT,//决定休眠后多久自动唤醒。单位：分钟；默认60分钟
	WAKEUP,//决定是否可通过WAKEUPT定时唤醒终端；默认WAKEUP=1，当WAKEUP=1时，终端可在
			//WAKEUPT时间后定时唤醒，同时也可通过震动唤醒，当WAKEUP=0时，终端不会定时唤醒，只可通过震动唤醒
	//VIBGPS,//开启、关闭GPS过滤功能。如果为1，则在5分钟内未检测到振动，则进入静止状态，
			//静止状态时过滤所有GPS漂移点
	SPEED,//超速报警设置。对于电动车，缺省为60，对于汽车，缺省关闭。电动车不允许用户设
			//定，但可以通过短信超级密码修改。对于汽车，则允许用户自由修改;可设置范围:40~60
	VIBCALL,//振动呼叫功能，1表示每次振动会直接呼叫车主，0表示关闭振动拨打电话功能。前提
			//是一定要设防。同VIB参数
	VIBCHK,//VIBCHK = X:Y，配置在X秒时间内产生了Y次震动，才产生震动告警。可设置范围:10:1~7;示例: 10:1,10:2,10:5,10:7
	POFT,//决定acc off后等待多久开启断电告警功能。单位：秒；默认0秒
	SLEEPT,//决定ACC断电后多长时间进入休眠；或休眠唤醒后工作多久重新进入休眠。单位：分钟；默认2分钟
	ACCLT,//决定acc off后等待多久进入设防。单位：秒；默认120秒。可设置范围:60~300;
	ACCLOCK;//是否开启根据ACC状态自动设防功能，ACCLOCK=1表示开启根据ACC状态
			//自动设防功能，ACC=0表示关闭自动设防功能


	/**
	 * @description 
	 * 	判断协议中是否存在传入的可查询参数
	 * @Author zhang bo
	 * @createDate 2014年5月8日  上午10:50:46
	 * @param paramName	配置参数名
	 * @return
	 */
	public static boolean hasParam(String queryParameter) {
		ProtocolQueryParamater[] paramArray = ProtocolQueryParamater.values();
		for(ProtocolQueryParamater pp : paramArray){
			if(pp.name().equals(queryParameter)){
				return true;
			}
		}
		return false;
	}
}
