package com.app.simple.utils.gps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

/**
 * com.sim.tracker.utils.gps
 * create class ReverseAddressResolution.java
 * @author zhang bo
 * @description 
 *	逆地址解析
 * @createTime 2014年6月18日  下午5:52:42
 */
public class ReverseAddressResolution {
	
	/**
	 * @description 
	 * 获取经纬度对应的地址
	 * @Author zhang bo
	 * @createDate 2014年6月18日  下午5:53:47
	 * @param baidu_lng 百度经度
	 * @param baidu_lat 百度纬度
	 * @return	
	 */
	public static String getAddressByBaiduPoint(double baidu_lng,double baidu_lat){
		OutputStream out = null;
	   	BufferedReader br = null;
	   	Socket s = null;
       try { 
           s = new Socket("api.map.baidu.com", 80);  
           br = new BufferedReader(new InputStreamReader(  
                   s.getInputStream(), "UTF-8"));  
           out = s.getOutputStream();  
           StringBuffer sb = new StringBuffer(  
                   "GET /geocoder?location="+baidu_lat+","+baidu_lng+"&output=json&key=6eea93095ae93db2c77be9ac910ff311");  
           sb.append(" HTTP/1.1\r\n");  
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
           if(StringUtils.isNotEmpty(json)){
        	   int index_start = json.indexOf("{");  
               int index_end = json.lastIndexOf("}");
               if(index_start>0 && index_end>0){
            	   json = json.substring(index_start, index_end+1);
               }
           }
           
           return json;
           /*
            * 返回结果
           //HTTP/1.1 200 OKHTTP_X_BD_LOGID: 3672276852HTTP_X_BD_LOGID64: 16717349088644817168Set-Cookie: 
            * BAIDUID=E9624D9C29C2050CA5C73F0FDE4632D8:FG=1; max-age=31536000; expires=Thu, 18-Jun-15 10:12:25 GMT; 
            * domain=.baidu.com; path=/; version=1P3P: CP=" OTI DSP COR IVA OUR IND COM "Content-Type: text/javascript;
            * charset=utf-8Transfer-Encoding: chunkedConnection: closeDate: Wed, 18 Jun 2014 10:12:25 GMTServer: apache1e0
           {	"status":"OK",    
        	   	"result":{        
	        	    "location":{            
	        	   		"lng":113.252432,            
	        	   		"lat":22.564152        
	        	   	},       
	        	   	"formatted_address":"广东省中山市中山市市辖区新丰西路",        
	        	   	"business":"",        
	        	   	"addressComponent":{            
	        	   		"city":"中山市",            
	        	   		"district":"中山市市辖区",            
	        	   		"province":"广东省",            
	        	   		"street":"新丰西路",            
	        	   		"street_number":""        
	        	   	},        
	        	   	"cityCode":187    
	        	}
           }0*/
       } catch (Exception e) {
	       	System.err.println(e);
	       	return null;
       }finally{
       	try {
	   		out.close();  
	        br.close();
	        s.close();
		} catch (Exception e2) {}
       }  
	}
	
	public static void main(String[] args){
		double lng = Double.parseDouble("113.252432");
		double lat = Double.parseDouble("22.564152");
		getAddressByBaiduPoint(lng,lat);
	}
}
