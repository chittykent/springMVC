package com.app.simple.utils;


/**
 * <p>公共方法类</p>
 * <p>字符集工具类</p>
 * @version 1.0
 *
 */

public class CharSetUtil
{
	public static final String FILE_ENCODING = getFileEncoding();
//	public static final String FILE_ENCODING = "UTF-8";
	public CharSetUtil()
	{
	}
	/**
	 * 字符串经过从字符集iso-8859-1到字符集gb2312的转换，所有从字符集为
	 * iso-8859-1的系统中取得的字符串都必须经过这样的转换才能正常显示在
	 * jsp页面上，否则中文显示会有乱码出现。
	 * @param strValue String待转换的字符串
	 * @return 转换过的字符串
	 */
	public static String getCNString(String strValue)
	{
		if (strValue == null)
		{
			return "";
		}
		try
		{
			return new String(strValue.trim().getBytes("ISO-8859-1"), "GB2312");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 字符串经过从字符集gb2312到字符集iso-8859-1的转换，所有要传输到字符集为
	 * iso-8859-1的系统中的字符串都必须经过这样的转换才能以正常的格式传到系统中
	 * jsp页面上，否则传输过去的中文显示会有乱码。
	 * @param strValue String待转换的字符串
	 * @return 转换过的字符串
	 */
	public static String getUNCNStr(String strValue)
	{
		if (strValue == null)
		{
			return "";
		}
		try
		{
			return new String(strValue.trim().getBytes("GB2312"), "ISO-8859-1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到文件系统的字符集描述，若该文件系统的字符集描述为GBK则返回GB2312,
	 * 若字符集描述为ISO8859_1则返回ISO-8859-1。 
	 * @return 字符集描述
	 * */
	public static String getFileEncoding()
	{
		String str = System.getProperty("file.encoding");
		if (null == str)
		{
			return "";
		}
		str = str.trim().toUpperCase();
		if (str.equals("GBK"))
		{
			str = "GB2312";
		}
		else if (str.equals("ISO8859_1"))
		{
			str = "ISO-8859-1";
		}
		return str;
	}

	/**
	* 根据操作系统把字符串转换成GB2312字符码
	* @param strValue String 待转换的字符串
	* @return String GB2312字符码的字符串
	* */
	public static String getSysGBKStr(String strValue)
	{
		if (strValue == null)
		{
			return null;
		}
		if (FILE_ENCODING.toUpperCase().equals("GB2312"))
		{
			return strValue;
		}
		try
		{
			strValue = new String(strValue.getBytes(FILE_ENCODING), "GB2312");
		}
		catch (Exception ex)
		{
			Common.printLog("getSysGBKStr Exception: " + ex.getMessage());
		}
		return strValue;
	}
	/**
	* 根据操作系统把字符串转换成UTF-8字符码
	* @param strValue String 待转换的字符串
	* @return String GB2312字符码的字符串
	* */
	public static String getSysUTF8Str(String strValue)
	{
		if (strValue == null)
		{
			return null;
		}
		if (FILE_ENCODING.toUpperCase().equals("UTF-8"))
		{
			return strValue;
		}
		try
		{
			strValue = new String(strValue.getBytes(FILE_ENCODING), "UTF-8");
		}
		catch (Exception ex)
		{
			Common.printLog("getSysGBKStr Exception: " + ex.getMessage());
		}
		return strValue;
	}

	/**
	 * 把UTF-8字符码字符串转换成操作系统编码的字符串
	 * @param strValue String 待转换的字符串
	 * @return String 操作系统编码的字符串
	 * */
	public static String getUTF8SysStr(String strValue)
	{
		if (strValue == null)
		{
			return null;
		}
		if (FILE_ENCODING.toUpperCase().equals("UTF-8"))
		{
			return strValue;
		}
		try
		{
			strValue = new String(strValue.getBytes("UTF-8"), FILE_ENCODING);
		}
		catch (Exception ex)
		{
			Common.printLog("getSysGBKStr Exception: " + ex.getMessage());
		}
		return strValue;
	}

	/**
	 * 把GB2312字符码字符串转换成操作系统编码的字符串
	 * @param strValue String 待转换的字符串
	 * @return String 操作系统编码的字符串
	 * */
	public static String getGBKSysStr(String strValue)
	{
		if (strValue == null)
		{
			return null;
		}
		if (FILE_ENCODING.toUpperCase().equals("GB2312"))
		{
			return strValue;
		}
		try
		{
			strValue = new String(strValue.getBytes("GB2312"), FILE_ENCODING);
		}
		catch (Exception ex)
		{
			Common.printLog("getSysGBKStr Exception: " + ex.getMessage());
		}
		return strValue;
	}
	/**
	 * 字符集转换
	 * @param strIn 欲转换的字符串
	 * @param inCharset 原字符集
	 * @param outCharset 新字符集
	 * @return String 转换后的字符集
	 */
	public static String getCharsetConvert(String strIn, String inCharset, String outCharset)
	{
		String strOut = null;
		if (strIn == null || (strIn.trim()).equals(""))
		{
			return strIn;
		}
		try
		{
			byte[] b = strIn.getBytes(inCharset);
			strOut = new String(b, outCharset);
		}
		catch (Exception e)
		{
		}
		return strOut;
	}
	public static void main(String[] args)
	{
		String testStr = "中文中文";
		//String sysCode = CharSetUtil.getFileEncoding();
		String sysCode = FILE_ENCODING;
		System.out.println("sysCode     " + sysCode);
		System.out.println("sys         " + testStr);
//				testStr = CharSetUtil.getSysUTF8Str(testStr);
//				testStr = CharSetUtil.getCharsetConvert(testStr, sysCode, "UTF-8");

		testStr = CharSetUtil.getUNCNStr(testStr);
		System.out.println("sys-->utf8  " + testStr);
//				testStr = CharSetUtil.getUTF8SysStr(testStr);
		//		testStr = CharSetUtil.getCharsetConvert(testStr, "UTF-8", sysCode);
		testStr = CharSetUtil.getCNString(testStr);
		System.out.println("utf8-->sys  " + testStr);
	}
}
