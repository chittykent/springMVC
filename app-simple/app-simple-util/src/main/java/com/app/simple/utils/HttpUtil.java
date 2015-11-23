package com.app.simple.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	
		/**
		 * 获得客户端IP地址
		 * @param request
		 * @return
		 */
		public static String getIpAddr(HttpServletRequest request)
		{
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getRemoteAddr();
			}
			if( StringUtils.isNotEmpty(ip)  )
			{
				if( ip.length() > 45 )
				{
					return ip.substring(0,45);
				}
				else
				{
					return ip;
				}
			}
			return ip;
		}
		
		/**
		 * 获得客户端操作系统和浏览器类型
		 * @param request
		 * @return
		 */
		public static int[] getAgent(HttpServletRequest request)
		{
			String strHeader = request.getHeader("User-Agent");
			String strSystem = null;
			String strExplorer = null;
			int[] arr = new int[2];
			
			//遨游：		Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; Maxthon; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)
			//IE6:		Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; Maxthon; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)
			//IE7:		Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; InfoPath.1)
			//Opera:	Opera/9.12 (Windows NT 5.1; U; en)
			//Mozilla:	Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.2) Gecko/20040804 Netscape/7.2 (ax)
			//FIREFOX: 	Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.1
			
			//判断客户端操作系统类型
			if( strHeader.indexOf( "Windows NT 5.0" ) >=0 )
			{
				strSystem="Windows 2000";
				arr[0]=1;
			}
			else if( strHeader.indexOf( "Windows NT 5.1" ) >=0 )
			{
				strSystem="Windows XP";
				arr[0]=2;
			}
			else if( strHeader.indexOf( "Windows NT 5.2" ) >=0 )
			{
				strSystem="Windows .NET/Windows 2003";
				arr[0]=3;
			}
			else
			{
				arr[0]=0;
			}
			
			//判断客户端浏览器类型
			if( strHeader.indexOf( "MSIE" ) >=0 )
			{
				strExplorer=strHeader.substring( strHeader.indexOf( "MSIE" ), strHeader.indexOf( ";", strHeader.indexOf( "MSIE" ) ) );
				arr[1]=1;
			}
			else if( strHeader.indexOf( "Firefox" ) >=0 )
			{
				strExplorer=strHeader.substring( strHeader.indexOf( "Firefox" ), strHeader.length() );
				arr[1]=5;
			}
			/*
			else if( strHeader.indexOf( "Netscape" ) >=0 )
			{
				strExplorer=strHeader.substring( strHeader.indexOf( "Netscape" ), strHeader.length() );
				arr[1]=3;
			}
			*/
			else
			{
				arr[1]=0;
			}
			
			return arr; 
			
			
		}
	
	//返回ip地址的十进制表示
	public static long getIpAddrLong(HttpServletRequest request){
		return HttpUtil.ipToLong(HttpUtil.getIpAddr(request));
	}
	//将点分十进制格式的IP地址转换成10进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIP){
         long[] ip=new long[4];
         int position1=strIP.indexOf(".");
         int position2=strIP.indexOf(".",position1+1);
         int position3=strIP.indexOf(".",position2+1);
         ip[0]=Long.parseLong(strIP.substring(0,position1));
         ip[1]=Long.parseLong(strIP.substring(position1+1,position2));
         ip[2]=Long.parseLong(strIP.substring(position2+1,position3));
         ip[3]=Long.parseLong(strIP.substring(position3+1));
         return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3]; 
    }

    //将10进制整数形式转换成点分十进制格式的IP地址
    public static String longToIP(long longIP){
         StringBuffer sb=new StringBuffer("");
         //直接右移24位
         sb.append(String.valueOf(longIP>>>24));
         sb.append(".");          
         //将高8位置0，然后右移16位
         sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16)); 
         sb.append(".");
         sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
         sb.append(".");
         sb.append(String.valueOf(longIP&0x000000FF));
         return sb.toString(); 
    } 
    /**
     * @param args
     */
    public static void main(String[] args) {
    	for(int i=100;i<111;i++){
		     String ipStr = "192.168.1."+i;
		     long ipLong = HttpUtil.ipToLong(ipStr);
		     System.out.println(ipStr +" 的整数形式为: " + ipLong);
		     //System.out.println("整数" + ipLong + "转化成字符串IP地址: "  + HttpUtil.longToIP(ipLong));
		     //IP地址转化成二进制形式输出
		     //System.out.println( ipStr +"的二进制形式为: "  + Long.toBinaryString(ipLong));
    	}
    }   

}
