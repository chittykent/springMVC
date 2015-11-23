package com.app.simple.utils.gps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.codec.binary.Base64;


/**
 * com.dong.web.tools
 * create class GpsToBaidu.java
 * @author zhang bo
 * @description 
 *	gps坐标转百度坐标
 * @createTime 2014年5月4日  下午3:14:57
 */
public class GpsToBaidu {
	/**
	 * @description 
	 * 	转成百度坐标
	 * @Author zhang bo
	 * @createDate 2014年5月4日  下午3:16:43
	 * @param longitude
	 * @param latitude
	 * @return 格式:经度  纬度
	 */
	public static String getBaiduPoint(String gps_longitude,String gps_latitude){
        
        OutputStream out = null;
   	    BufferedReader br = null;
   	    Socket s = null;
       try { 
           s = new Socket("api.map.baidu.com", 80);  
           br = new BufferedReader(new InputStreamReader(  
                   s.getInputStream(), "UTF-8"));  
           out = s.getOutputStream();  
           StringBuffer sb = new StringBuffer(  
                   "GET /ag/coord/convert?from=0&to=4");  
           sb.append("&x=" + gps_longitude + "&y=" + gps_latitude);  
           sb.append("&callback=BMap.Convertor.cbk_3976 HTTP/1.1\r\n");  
           sb.append("User-Agent: Java/1.6.0_20\r\n");  
           sb.append("Host: api.map.baidu.com:80\r\n");  
           sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");  
           sb.append("Connection: Close\r\n");  
           sb.append("\r\n");  
           out.write(sb.toString().getBytes());  
           String json = "";  
           String tmp = "";  
           while ((tmp = br.readLine()) != null) {  
               json += tmp;  
           }  
 
           int start = json.indexOf("cbk_3976");  
           int end = json.lastIndexOf("}");  
           if (start != -1 && end != -1&& json.contains("\"x\":\"")) {  
               json = json.substring(start, end);  
               String[] point = json.split(",");  
               String x = point[1].split(":")[1].replace("\"", "");//base64加密后的百度坐标经度  
               String y = point[2].split(":")[1].replace("\"", "");//base64加密后的百度坐标纬度
               double lng = Double.parseDouble(new String(Base64.decodeBase64(x.getBytes())));
               double lat =  Double.parseDouble(new String(Base64.decodeBase64(y.getBytes())));
               return lng+" "+lat;
           } else {  
               System.out.println("gps坐标无效！！");  
           }  
           
       } catch (Exception e) {  
           e.printStackTrace();  
       }finally{
       	try {
       		out.close();  
               br.close();
               s.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
       	
       }  
       return "";
       
	}
	
	
	/**
	 * @description 
	 * 百度坐标转Gps坐标
	 * @Author zhang bo
	 * @createDate 2014年5月9日  上午10:36:57
	 * @return
	 */
	public static String BaiduToGpsPoint(String baidu_lng_old,String baidu_lat_old){
		//百度坐标转成GPS坐标
		//思想:先将百度坐标当成gps坐标用baiduApi算出新的百度坐标,再用新旧百度坐标算出Gps坐标
		String baiduPoint = getBaiduPoint(baidu_lng_old,baidu_lat_old);//转换前的百度坐标
		double gps_lng = 0;
		double gps_lat = 0;
		if(baiduPoint != null && !baiduPoint.isEmpty()){//转换成功
			double baidu_lng_new = Double.parseDouble(baiduPoint.split(" ")[0]);
			double baidu_lat_new = Double.parseDouble(baiduPoint.split(" ")[1]);
			//System.out.println(baidu_lng_new+" "+baidu_lat_new);//转换后的百度坐标:116.41004950566 39.916979519873
			
			gps_lng = 2*Double.parseDouble(baidu_lng_old)-baidu_lng_new;
			gps_lat = 2*Double.parseDouble(baidu_lat_old)-baidu_lat_new;
			//System.out.println(gps_lng+" "+gps_lat);//转换出来的GPS坐标:116.38480649434001 39.901480480127 
			return gps_lng+" "+gps_lat;
		}
		return "";
	}
	/**
	 * @description 
	 * 百度坐标转成GPS坐标,再验证
	 * @Author zhang bo
	 * @createDate 2014年5月9日  上午10:32:26
	 * @param args
	 */
	public static void main(String[] args){
		//百度坐标转成GPS坐标
		//思想:先将百度坐标当成gps坐标用baiduApi算出新的百度坐标,再用新旧百度坐标算出Gps坐标
		String baiduPoint = getBaiduPoint("116.397428","39.90923");//转换前的百度坐标
		double gps_lng = 0;
		double gps_lat = 0;
		if(baiduPoint != null && !baiduPoint.isEmpty()){//转换成功
			double baidu_lng = Double.parseDouble(baiduPoint.split(" ")[0]);
			double baidu_lat = Double.parseDouble(baiduPoint.split(" ")[1]);
			System.out.println(baidu_lng+" "+baidu_lat);//转换后的百度坐标:116.41004950566 39.916979519873
			
			gps_lng = 2*Double.parseDouble("116.397428")-baidu_lng;
			gps_lat = 2*Double.parseDouble("39.90923")-baidu_lat;
			System.out.println(gps_lng+" "+gps_lat);//转换出来的GPS坐标:116.38480649434001 39.901480480127 
		}
		
		//验证:将转成的gps坐标再用百度地图api转成百度坐标   与原有百度坐标对比 相差几米到十几米
		String new_baiduPoint = getBaiduPoint(Double.toString(gps_lng),Double.toString(gps_lat));
		if(new_baiduPoint!=null && !new_baiduPoint.isEmpty()){//转换成功
			String baidu_longitude = new_baiduPoint.split(" ")[0];//base64加密后的百度坐标经度
			String baidu_latitude = new_baiduPoint.split(" ")[1];//base64加密后的百度坐标纬度
			
			double baidu_lng = Double.parseDouble(new String(baidu_longitude));
			double baidu_lat = Double.parseDouble(new String(baidu_latitude));
			System.out.println(baidu_lng+" "+baidu_lat);//116.39743826208 39.909194650838
			
		}
	}
}
