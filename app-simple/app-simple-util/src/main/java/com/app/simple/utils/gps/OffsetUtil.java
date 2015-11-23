package com.app.simple.utils.gps;

import java.util.HashMap;
import java.util.Map;



 /**
 *  Class Name: OffsetUtil.java
 *  @Description: TODO 
 *  @author jf.gong  DateTime 2014年5月6日 下午1:04:56    
 *  @version 1.0
 */
public class OffsetUtil {
	
	 
	 /**
	 *  @Title: getAreaPostion 
	 *  @Description: 获取秒纬度或秒经度
	 *  @author jf.gong  DateTime 2014年5月6日 下午1:42:33
	 *  @param coordinate 坐标的经度或纬度
	 *  @return double 返回坐标整数+小数部分的秒纬度或坐标整数+小数部分的秒经度
	 *  
	 *  更新:zhang bo  2014年6月10日  下午3:51:53
	 *  	提高经纬度坐标转换精度,缩小划分范围,每个范围有一个偏移向量
	 *  	经纬度*3600:精确到秒
	 *  	经纬度*3600*60:精确到毫秒
	 *  	因为纬度		1度=110公里	经纬度整数部分
	 *  				1分=1.85公里	经纬度小数部分前3位
	 *  				1秒=30.8米	经纬度小数部分第3,第4,第5位
	 *  	道路纠偏接口修改经纬度小数部分第五位会产生变化纠正到不同的点
	 *  	所以为提高精度,把范围缩小到毫秒内计算偏移
	 */
	public static String getAreaPostion(double coordinate){

		 double integralVal = (double)Math.floor(coordinate);
		 double decimalVal  = Math.floor((coordinate - integralVal)*3600*60) ;
		 String reVal = String.valueOf(integralVal).substring(0,String.valueOf(integralVal).indexOf(".")) + "_" 
				 		+ String.valueOf(decimalVal).substring(0,String.valueOf(decimalVal).indexOf("."));
         return reVal;

     }
	
	
	 /**
	 *  @Title: getBOffset 
	 *  @Description: 得到gps到百度地图偏移量与百度区域坐标
	 *  @author jf.gong  DateTime 2014年5月6日 下午5:46:15
	 *  @return Point 
	 *  @param GPSlng
	 *  @param GPSlat
	 *  @return
	 */
	public static Map<String, Object> getGps2BaiduOffset(double GPSlng,double GPSlat){
		
		Point point = Converter.b_api(GPSlng + "", GPSlat + "", 0);
		//返回值
		Map<String, Object> reMap = null;
		if(null != point){
			reMap = new HashMap<String, Object>();
			//获取GPS坐标区域
			String gps_arealng = getAreaPostion(GPSlng);
			String gps_arealat = getAreaPostion(GPSlat);
			//获取百度坐标区域
			String b_arealng = getAreaPostion(point.longitude);
			String b_arealat = getAreaPostion(point.latitude);
			//计算gps到百度的偏移量
			double bLngOffset = point.getLongitude() - GPSlng;
			double bLatOffset = point.getLatitude() - GPSlat;
			
			reMap.put("bLngOffset", bLngOffset);
			reMap.put("bLatOffset", bLatOffset);
			reMap.put("b_arealng", b_arealng);
			reMap.put("b_arealat", b_arealat);
			reMap.put("gps_arealng", gps_arealng);
			reMap.put("gps_arealat", gps_arealat);
		}
		return reMap;
    }
	
	
	 /**
	 *  @Title: getGOffset 
	 *  @Description: 得到Google到baidu偏移量 与百度区域坐标
	 *  @author jf.gong  DateTime 2014年5月6日 下午5:46:31
	 *  @return Point 
	 *  @param GPSlng
	 *  @param GPSlat
	 *  @return
	 */
	public static Map<String, Object> getGoogle2BaiduOffset(double Googlelng,double Googlelat){
		
		Point bPoint = Converter.b_api(Googlelng + "", Googlelat + "", 2); //Google 转百度
		//返回值
		Map<String, Object> reMap = new HashMap<String, Object>();
		if(null != bPoint){
			//获取google坐标区域
			String g_arealng = getAreaPostion(Googlelng);
			String g_arealat = getAreaPostion(Googlelat);
			//获取百度坐标区域
			String b_arealng = getAreaPostion(bPoint.longitude);
			String b_arealat = getAreaPostion(bPoint.latitude);
			//计算google到百度的偏移量
			double gLngOffset = bPoint.longitude-Googlelng;
			double gLatOffset =bPoint.latitude- Googlelat;
			
			reMap.put("gLngOffset", gLngOffset);
			reMap.put("gLatOffset", gLatOffset);
			reMap.put("b_arealng", b_arealng);
			reMap.put("b_arealat", b_arealat);
			reMap.put("g_arealng", g_arealng);
			reMap.put("g_arealat", g_arealat);
		}
		return reMap;
    }
}
