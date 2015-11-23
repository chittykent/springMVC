package com.app.simple.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

/**
 * <p>
 * 公共方法类
 * </p>
 * <p>
 * 通用功能
 * </p>
 * 
 * @version 1.0
 * 
 */

public class Common {
	public Common() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 替换SQL语句的危险字符串
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String getStrForOracle(String str) {
		if (str != null) {
			str = str.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||'&'||'").trim();
		} else {
			str = "";
		}
		return str;
	}

	/**
	 * Hashtable内通过值求键值
	 * 
	 * @param ht
	 *            Hashtable
	 * @param theStr
	 *            值
	 * @return String 键值
	 */
	public static String getKeyByValue(Hashtable ht, String theStr) {
		Enumeration en = ht.keys();
		String linStr = null;
		while (en.hasMoreElements()) {
			linStr = (String) en.nextElement();
			if (ht.get(linStr).equals(theStr)) {
				return linStr;
			}
		}
		return "";
	}

	/**
	 * 根据参数名称，从request对象中取出该参数，并把该参数转换成GB2312编码的字符集。
	 * 
	 * @param request
	 *            请求对象
	 * @param strParamName
	 *            参数名称
	 * @return String 转换后的参数值
	 * */
	public static String getStrFromReqParam(HttpServletRequest request, String strParamName) {
		String strStr = StringUtil.getNotNullStr(request.getParameter(strParamName));
		return strStr;
	}

	/**
	 * 根据参数名称，从request对象中取出该参数，并把该参数转换成中间件平台操作系统编码的字符集。
	 * 
	 * @param request
	 *            请求对象
	 * @param strParamName
	 *            参数名称
	 * @return String 转换后的参数值
	 * */
	public static String getISOStrFromReqParam(HttpServletRequest request, String strParamName) {
		String strISOStr = CharSetUtil.getUNCNStr(StringUtil.getNotNullStr(request.getParameter(strParamName)));
		return strISOStr;
	}

	/**
	 * 在不同的文件系统中打印根据该文件系统字符集转换而成的字符串日志。
	 * 
	 * @param strLog
	 *            String要打印的日志信息
	 * */
	public static void printLog(String strLog) {
		try {
			if (null == strLog) {
				System.out.println(strLog);
				return;
			}
			System.out.println(new String(strLog.getBytes("GB2312"), CharSetUtil.getFileEncoding()));
		} catch (Exception ex) {
			System.out.println("#########################");
			System.out.println(strLog);
		}
	}

	public static void printLog(Exception e) {
		e.printStackTrace(System.out);
	}

	/**
	 * 在不同的文件系统中打印根据该文件系统字符集转换而成的整数。
	 * 
	 * @param iLog
	 *            int
	 * */
	public static void printLog(int iLog) {
		printLog(new Integer(iLog).toString());
	}

	/**
	 * 在不同的文件系统中打印根据该文件系统字符集转换而成的长整数。
	 * 
	 * @param lLog
	 *            long
	 * */
	public static void printLog(long lLog) {
		printLog(new Long(lLog).toString());
	}

	/**
	 * 在不同的文件系统中打印根据该文件系统字符集转换而成的双精度数。
	 * 
	 * @param lLog
	 *            欲打印双精度数
	 * */
	public static void printLog(double lLog) {
		printLog(new Double(lLog).toString());
	}

	/**
	 * 把一个Object对象转换成Short类型，并返回short值，若转换出现错误则返回-1。
	 * 
	 * @param objValue
	 *            Object待转换的对象
	 * @return short 返回的short类型的值，-1表示出现错误
	 * */
	public static short getShortValue(Object objValue) {
		short nValue = -1;
		if (null == objValue) {
			return nValue;
		}
		nValue = StringUtil.getStrToShort(objValue.toString());
		return nValue;
	}

	/**
	 * 把一个Object对象转换成Integer类型，并返回int值，若转换出现错误则返回-1。
	 * 
	 * @param objValue
	 *            Object待转换的对象
	 * @return int 返回的int类型的值，-1表示出现错误
	 * */
	public static int getIntValue(Object objValue) {
		int nValue = -1;
		if (null == objValue) {
			return nValue;
		}
		nValue = StringUtil.getStrToInt(objValue.toString());
		return nValue;
	}

	/**
	 * 把一个Object对象转换成Long类型，并返回long值，若转换出现错误则返回-1。
	 * 
	 * @param objValue
	 *            Object待转换的对象
	 * @return long 返回的long类型的值，-1表示出现错误
	 * */
	public static long getLongValue(Object objValue) {
		long lValue = -1;
		if (null == objValue) {
			return lValue;
		}
		lValue = StringUtil.getStrToLong(objValue.toString());
		return lValue;
	}

	/***
	 * 根据xml可显示的规范对字符串进行编码
	 * 
	 * @param strXmlStr
	 *            欲处理的XML字符串
	 * @return String 处理后的XML字符串
	 * */
	public static String getXMLEncodeStr(String strXmlStr) {
		strXmlStr = strXmlStr.replaceAll("&", "&amp;");
		strXmlStr = strXmlStr.replaceAll("<", "&lt;");
		strXmlStr = strXmlStr.replaceAll(">", "&gt;");
		strXmlStr = strXmlStr.replaceAll("\"", "&quot;");
		strXmlStr = strXmlStr.replaceAll("\n", "<br>\n");
		strXmlStr = strXmlStr.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		strXmlStr = strXmlStr.replaceAll(" ", "&nbsp;");
		return strXmlStr;
	}

	/**
	 * 将传入的字符串格式化为SQL字符串,如:"131,2321" to "'131','2321'"
	 * 
	 * @param _arrayString
	 *            传入的分组字符串:"131,2321"
	 * @return String 处理后的SQL字符串
	 * @throws Exception
	 */
	public static String getSqlString(String _arrayString) throws Exception {
		String returnString = "";
		try {
			StringTokenizer st = new StringTokenizer(_arrayString, ",");
			while (st.hasMoreTokens()) {
				String temp = st.nextToken();
				returnString = returnString + "'" + temp + "',";
			}
			if (!returnString.equals("")) {
				returnString = returnString.substring(0, returnString.length() - 1);
				// 去除最后的","
			}
			return returnString;
		} catch (Exception e) {
			System.out.println("Error at PublicComp.java getSqlString():" + e.getMessage());
			throw e;
		}

	}

	/**
	 * ��传入的字符串补足相应的位数后返回指定长度的字符串;
	 * 
	 * @param _baseString
	 *            原字符串
	 * @param _length
	 *            指定长度
	 * @param _firstOrEnd
	 *            1:在前面补,2在后面补
	 * @param _char
	 *            用于补位的字符
	 * @return String 处理后的字符串
	 * @throws Exception
	 */
	public static String formatString(String _baseString, int _length, int _firstOrEnd, char _char) throws Exception {
		String formatString = "";
		String tempStr = "" + _char;
		int tempLength = 0;
		int i = 0;
		try {
			tempLength = _baseString.length();
			formatString = _baseString;
			if (tempLength >= _length) { // 如果比实际位数要长或正好相等,则返回原值;
				formatString = _baseString;
			} else {
				for (i = 1; i <= _length - tempLength; i++) {
					if (_firstOrEnd == 1) {
						formatString = tempStr + formatString;
					} else {
						formatString = formatString + tempStr;
					}
				}
			}

			return formatString;
		} catch (Exception e) {
			System.out.println("Error at PublicComp.java formatString():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 根据被除数和除数计算平均值,四舍五入
	 * 
	 * @param _dividend
	 *            被除数
	 * @param _divisor
	 *            除数
	 * @return long 计算结果
	 * @throws Exception
	 */
	public static long getAvg(long _dividend, long _divisor) throws Exception {
		long returnValue = 0;
		try {
			if (_dividend == 0) {
				return 0;
			}
			if (_divisor == 0) {
				return 0;
			}
			float temp = (float) _dividend / (float) _divisor;
			returnValue = (long) temp;
			float temp2 = Float.parseFloat("" + returnValue + ".5");
			if (temp >= temp2) {
				returnValue++;
			}
			return returnValue;
		} catch (Exception e) {
			System.out.println("Error at PublicComp.java getAvg():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 根据被除数和除数计算百分比值,如15.30,精确到小数点后两位
	 * 
	 * @param _dividend
	 *            被除数
	 * @param _divisor
	 *            除数
	 * @return double 计算结果
	 * @throws Exception
	 */
	public static double getPercent(double _dividend, double _divisor) throws Exception {
		double returnValue = (double) 0.01;
		try {
			if (_dividend == 0) {
				return (double) 0.00;
			}
			if (_divisor == 0) {
				return (double) 0.00;
			}
			returnValue = (_dividend / _divisor) * 100;
			String tempAll = "" + returnValue;
			// 取出小数点前的数
			String beginString = tempAll.substring(0, tempAll.indexOf("."));
			// 取出小数点后的3位
			String lastString = tempAll.substring(tempAll.indexOf(".") + 1, tempAll.length());
			int length = lastString.length();
			if (length >= 3) {
				// 处理四舍五入
				String temp1 = lastString.substring(0, 2);
				String temp2 = lastString.substring(2, 3);

				int all = Integer.parseInt(temp1);
				if (Integer.parseInt(temp2) >= 5) {
					all++;
				}
				lastString = "" + all;
			}
			returnValue = Double.parseDouble(beginString + "." + lastString);

			return returnValue;
		} catch (Exception e) {
			System.out.println("Error at PublicComp.java getPercent():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 从一个Properties文件中读取出一个键值
	 * 
	 * @param fileName
	 *            文件名
	 * @param keyName
	 *            键值
	 * @return String
	 */
	public static String getValueFromPropertyByKey(String fileName, String keyName) {
		// General gn=new General();
		Properties initProps = new Properties();
		String theValue = null;
		InputStream in = null;

		try {
			in = Common.class.getClassLoader().getResourceAsStream(fileName);
			initProps.load(in);
			theValue = initProps.getProperty(keyName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return theValue;
		}
	}

	/**
	 * 增加、删除List
	 * 
	 * @param newList
	 *            List
	 * @param oldList
	 *            List
	 * @param addList
	 *            List
	 * @param delList
	 *            List
	 */
	public static void doGetChgList(List newList, List oldList, List addList, List delList) {
		delList.addAll(oldList);

		Object curObj = null;
		for (int i = 0; i < newList.size(); i++) {
			curObj = newList.get(i);
			if (oldList.contains(curObj)) {
				delList.remove(curObj);
			} else {
				addList.add(curObj);
			}
		}
	}

	/**
	 * 根据指定的字符串将字符串分隔；返回List
	 * 
	 * @param str
	 *            str
	 * @param sep
	 *            sep
	 * @return List
	 */
	public static List getStrList(String str, String sep) {
		if (str != null && !str.equals("") && sep != null) {
			// if(str != null && sep != null) {
			int iSep = sep.length();
			Vector vct = new Vector();
			for (int pos = str.indexOf(sep); pos > -1;) {
				vct.add(str.substring(0, pos));
				str = str.substring(pos + iSep);
				pos = str.indexOf(sep);
			}
			vct.addElement(str);
			return vct;
		}
		return null;
	}

	/**
	 * 
	 * @desc
	 * @param count
	 *            随机取出位数
	 * @param type
	 *            类型 1.数字 2.字母 3.数字字母
	 * @return
	 * @author Hongbing.Zhang
	 */
	public static String getRandom(int count, int type) {
		String checkCode = null;
		switch (type) {
		case 1:
			checkCode = RandomStringUtils.random(count, "0123456789");
			break;
		case 2:
			checkCode = RandomStringUtils.random(count, "qwertyuiopasdfghjklzxcvbnm");
			break;
		case 3:
			checkCode = RandomStringUtils.random(count, "0123456789qwertyuiopasdfghjklzxcvbnm");
			break;
		default:
			checkCode = RandomStringUtils.randomNumeric(count);
			break;
		}
		return checkCode;
	}
	
	public static final String SERVICE_ORIGIN_JKFXPG = "健康风险评估" ;
	public static final String SERVICE_ORIGIN_DZFW = "定制服务" ;
	public static final String SERVICE_ORIGIN_SDLR = "手动录入" ;
}
