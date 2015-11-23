package com.app.simple.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 用来处理GB2312/BIG5码字符互相转换的类.
 * 需要两个码表文件:/zeal/util/gb-big5.table,/zeal/util/big5-gb.table.
 * 这两个码表可以根据具体情况补充映射不正确的码.
 * <p>
 * Title: GB<=>Big5
 * </p>
 * <p>
 * Description: Deal with the convertion between gb2312 and big5 charset
 * Strings.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: NewmenBase
 * </p>
 * 
 * @author Zeal Li
 * @version 1.0
 * @see zeal.util.StreamConverter
 */
public class GB2Big5Map {
	private static GB2Big5Map pInstance = null;

	private String s_big5TableFile = null;

	private String s_gbTableFile = null;

	private byte[] b_big5Table = null;

	private byte[] b_gbTable = null;

	/** 指定两个码表文件来进行初始化 */
	private GB2Big5Map(String sgbTableFile, String sbig5TableFile)
			throws NullPointerException {
		s_big5TableFile = sbig5TableFile;
		s_gbTableFile = sgbTableFile;
		if (null == b_gbTable) {
			b_gbTable = getBytesFromFile(sgbTableFile);
		}
		if (null == b_big5Table) {
			b_big5Table = getBytesFromFile(sbig5TableFile);
		}
		if (null == b_gbTable) {
			throw new NullPointerException("No gb table can be load");
		}
		if (null == b_big5Table) {
			throw new NullPointerException("No big5 table can be load");
		}
	}

	public static synchronized GB2Big5Map getInstance() {
	
			String filepath1="gb-big5.table";
	        String filepath2="big5-gb.table";
		
		return getInstance(filepath1,
				filepath2);
	}

	public static synchronized GB2Big5Map getInstance(String sgbTableFile,
			String sbig5TableFile) {
		if (null == pInstance) {
			try {
				pInstance = new GB2Big5Map(sgbTableFile, sbig5TableFile);
			} catch (Exception e) {
				System.err.println(e.toString());
				pInstance = null;
			}
		}

		return pInstance;
	}

	/**
	 * 把gbChar对应的big5字符替换掉，用来更新码表文件. 一般当发现字符映射不正确的时候可以通过这个方法来校正.
	 */
	protected synchronized void resetBig5Char(String gbChar, String big5Char)
			throws Exception {
		byte[] Text = new String(gbChar.getBytes(), "GBK").getBytes("GBK");
		byte[] TextBig5 = new String(big5Char.getBytes(), "BIG5")
				.getBytes("BIG5");
		int max = Text.length - 1;
		int h = 0;
		int l = 0;
		int p = 0;
		int b = 256;
		byte[] big = new byte[2];
		for (int i = 0; i < max; i++) {
			h = (int) (Text[i]);
			if (h < 0) {
				h = b + h;
				l = (int) (Text[i + 1]);
				if (l < 0) {
					l = b + (int) (Text[i + 1]);
				}
				if (h == 161 && l == 64) {
					; // do nothing
				} else {
					p = (h - 160) * 510 + (l - 1) * 2;
					b_gbTable[p] = TextBig5[i];
					b_gbTable[p + 1] = TextBig5[i + 1];
				}
				i++;
			}
		}

		BufferedOutputStream pWriter = new BufferedOutputStream(
				new FileOutputStream(s_gbTableFile));
		pWriter.write(b_gbTable, 0, b_gbTable.length);
		pWriter.close();
	}

	/**
	 * 把big5Char对应的gb字符替换掉，用来更新码表文件. 一般当发现字符映射不正确的时候可以通过这个方法来校正.
	 */
	protected synchronized void resetGbChar(String big5Char, String gbChar)
			throws Exception {
		byte[] TextGb = new String(gbChar.getBytes(), "gb2312")
				.getBytes("gb2312");
		byte[] Text = new String(big5Char.getBytes(), "BIG5").getBytes("BIG5");
		int max = Text.length - 1;
		int h = 0;
		int l = 0;
		int p = 0;
		int b = 256;
		byte[] big = new byte[2];
		for (int i = 0; i < max; i++) {
			h = (int) (Text[i]);
			if (h < 0) {
				h = b + h;
				l = (int) (Text[i + 1]);
				if (l < 0) {
					l = b + (int) (Text[i + 1]);
				}
				if (h == 161 && l == 64) {
					; // do nothing
				} else {
					p = (h - 160) * 510 + (l - 1) * 2;
					b_big5Table[p] = TextGb[i];
					b_big5Table[p + 1] = TextGb[i + 1];
				}
				i++;
			}
		}

		BufferedOutputStream pWriter = new BufferedOutputStream(
				new FileOutputStream(s_big5TableFile));
		pWriter.write(b_big5Table, 0, b_big5Table.length);
		pWriter.close();
	}

	/** 把gb2312编码的字符串转化成big5码的字节流 */
	public byte[] gb2big5(String inStr) throws Exception {
		if (null == inStr || inStr.length() <= 0) {
			return "".getBytes();
			// return "";
		}

		byte[] Text = new String(inStr.getBytes(), "GBK").getBytes("GBK");
		int max = Text.length - 1;
		int h = 0;
		int l = 0;
		int p = 0;
		int b = 256;
		byte[] big = new byte[2];
		for (int i = 0; i < max; i++) {
			h = (int) (Text[i]);
			if (h < 0) {
				h = b + h;
				l = (int) (Text[i + 1]);
				if (l < 0) {
					l = b + (int) (Text[i + 1]);
				}
				if (h == 161 && l == 64) {
					big[0] = big[1] = (byte) (161 - b);
				} else {
					p = (h - 160) * 510 + (l - 1) * 2;
					try {
						big[0] = (byte) (b_gbTable[p] - b);
					} catch (Exception e) {
						big[0] = 45;
					}
					try {
						big[1] = (byte) (b_gbTable[p + 1] - b);
					} catch (Exception e) {
						big[1] = 45;
					}
				}
				Text[i] = big[0];
				Text[i + 1] = big[1];
				i++;
			}

		}

		return Text;

	}

	/** 把big5码的字符串转化成gb2312码的字符串 */
	public String big52gb(String inStr) throws Exception {
		StringBuffer stbf = new StringBuffer();
		if (null == inStr || inStr.length() <= 0) {
			return "";
		}
		for (int j = 0; j < inStr.length(); j++) {

			String str = String.valueOf(inStr.charAt(j));
			if (new String(str.getBytes(), "gb2312").equals(str)) {
				stbf.append(str);
			} else {
				byte[] Text = new String(str.getBytes(), "BIG5")
						.getBytes("BIG5");
				int max = Text.length - 1;
				int h = 0;
				int l = 0;
				int p = 0;
				int b = 256;
				byte[] big = new byte[2];
				for (int i = 0; i < max; i++) {
					h = (int) (Text[i]);

					if (h > 0) {

						h = b + h;
						l = (int) (Text[i + 1]);
						if (l < 0) {

							l = b + (int) (Text[i + 1]);
						}
						if (h == 161 && l == 161) {

							big[0] = (byte) (161 - b);
							big[1] = (byte) (64 - b);
						} else {

							p = (h - 160) * 510 + (l - 1) * 2;
							System.out.println("----->" + p);

							try {

								big[0] = (byte) (b_big5Table[p] - b);

							} catch (Exception e) {
								big[0] = 45;
							}
							try {
								big[1] = (byte) (b_big5Table[p + 1] - b);
							} catch (Exception e) {
								big[1] = 45;
							}
						}
						Text[i] = big[0];
						Text[i + 1] = big[1];
						i++;
					}
					stbf.append(new String(Text));
				}

			}
		}

		return stbf.toString();
	}

	/** 把文件读入字节数组，读取失败则返回null */
	private static byte[] getBytesFromFile(String inFileName) {
		try {
			InputStream in = GB2Big5Map.class.getResourceAsStream(inFileName);
			byte[] sContent = StreamConverter.toByteArray(in);
			in.close();
			return sContent;

			/*
			 * java.io.RandomAccessFile inStream = new java.io.RandomAccessFile(
			 * inFileName,"r"); byte[] sContent = new byte[ (int)
			 * (inStream.length())]; inStream.read(sContent); inStream.close();
			 * return sContent;
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 将简体字符串转换成繁体
	 */
	public static String ConvertSimpleCHToComplexCH(String simp) {
		String restustr = "";
		GB2Big5Map converter = GB2Big5Map.getInstance();
		try {
			byte[] b = converter.gb2big5(simp);
			restustr = new String(b, "BIG5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restustr;
	}

	/*
	 * 将传入的既含有简体和繁体的字符串，并将其中的简体部分强转化成繁体
	 */
	public static String ParseTheCompleAndSimple(String str)
	{
		StringBuffer instr = new StringBuffer();
        if((str==null) || (str.length() <= 0)) return "";
	     try
	     {
			GB2Big5Map converter = GB2Big5Map.getInstance();
			for (int i = 0; i < str.length(); i++) {
				String st = str.valueOf(str.charAt(i));
				String temp = st;
				if (new String(st.getBytes("gb2312")).equals(temp)) {
					st = new String(converter.gb2big5(st), "BIG5");
					instr.append(st);
				} else {

					instr.append(st);
				}
			}
	     }
		 catch(Exception e)
		 {
			
		 }
		return instr.toString();
	}
	
	public static void main(String[] args) throws Exception {
		/*if (args.length < 2) {
			args = new String[3];
			args[0] = "zeal.util.GB2Big5";
			args[1] = "-gb";
			// args[2] = "中国活动旅游---中华人民共和国--分类--复杂--专题地图--输入--回归纪念--中环--学校--汽车";
			args[2] = "中國活動旅游中華分類";
			// System.out.println(
			// "Usage: zeal.util.GB2Big5 [-gb | -big5] inputstring");
			// System.exit(1);
			// return;
		}

		boolean bIsGB = true;
		String inStr = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-gb")) {
				bIsGB = true;
			} else if (args[i].equalsIgnoreCase("-big5")) {
				bIsGB = false;
			} else {
				inStr = args[i];
			}
		}*/

		GB2Big5Map pTmp = GB2Big5Map.getInstance();
		String result=pTmp.ParseTheCompleAndSimple("號碼");
		String result1=pTmp.ParseTheCompleAndSimple("错误");
		System.out.println(result);
		System.out.println(result1);
		/*String outStr = "";
		if (bIsGB) {
			outStr = pTmp.big52gb(inStr);
		} else {
			outStr = new String(pTmp.gb2big5(inStr), "BIG5");
		}

		System.out.println("String [" + inStr + "] converted into:\n[" + outStr
				+ "]");*/
	}
}
