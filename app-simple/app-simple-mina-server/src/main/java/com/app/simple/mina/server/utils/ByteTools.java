package com.app.simple.mina.server.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: ByteTools
 * @Description: 字节转换工具
 * @author ZengZhuo
 * @date 2014年8月13日 下午1:01:51
 *
 */
public class ByteTools {
	private static final Log log = LogFactory.getLog(ByteTools.class);
	final static Object lock = new Object();
	private static int BUFFER_SIZE = 8096;// 缓冲区大小

	public static void makeHtml(String page, String filePath) {
		makeHtml(page, filePath, "UTF-8");
	}

	public static String getName() {
		Random r = new Random();
		String datetime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		return datetime + r.nextInt(1000);
	}

	// 返回html代码
	public static String getHtmlCode(String httpUrl) {

		String htmlCode = "";
		try {
			InputStream in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(new InputStreamReader(in, "GBK"));
			String currentLine;
			while ((currentLine = breader.readLine()) != null) {
				htmlCode += currentLine + "\n";
				System.out.println(currentLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlCode;
	}

	public static void makeHtml(String page, String filePath, String chartset) {
		synchronized (lock) {
			HttpURLConnection hc = null;
			FileOutputStream fos = null;
			BufferedInputStream bis = null;
			byte[] buf = new byte[BUFFER_SIZE];
			int size = 0;
			URL url = null;
			try {
				url = new URL(page);
				hc = (HttpURLConnection) url.openConnection();
				// 连接指定的资源
				hc.connect();
				// 获取网络输入流
				bis = new BufferedInputStream(hc.getInputStream());
				// 建立文件

				fos = new FileOutputStream(filePath);

				// 保存文件
				while ((size = bis.read(buf)) != -1)
					fos.write(buf, 0, size);

				fos.close();
				bis.close();
				hc.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 根据正则表达式来匹配结果
	 * 
	 * @param pattern
	 * @param match
	 * @param i
	 * @return
	 */
	public static String[] analysis(String pattern, String match, int i) {
		Pattern sp = Pattern.compile(pattern);// 常见匹配的正则表达式模板
		Matcher matcher = sp.matcher(match); // 匹配器
		String[] content = new String[i];
		for (int i1 = 0; matcher.find(); i1++) {
			content[i1] = matcher.group(1);
		}
		// 下面一段是为了剔除为空的串
		int l = 0;
		for (int k = 0; k < content.length; k++) {
			if (content[k] == null) {
				l = k;
				break;
			}
		}
		String[] content2;
		if (l != 0) {
			content2 = new String[l];
			for (int n = 0; n < l; n++) {
				content2[n] = content[n];
			}
			return content2;
		} else {
			return content;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param strUrl
	 * @return
	 */
	public static int saveImage(String strUrl) {
		URLConnection uc = null;
		try {
			URL url = new URL(strUrl);
			uc = url.openConnection();
			uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			// uc.setReadTimeout(30000);
			// 获取图片长度
			// System.out.println("Content-Length: "+uc.getContentLength());
			// 获取文件头信息
			// System.out.println("Header"+uc.getHeaderFields().toString());
			if (uc == null)
				return 0;
			InputStream ins = uc.getInputStream();
			byte[] str_b = new byte[1024];
			int byteRead = 0;
			String[] images = strUrl.split("/");
			String imagename = images[images.length - 1];
			File fwl = new File(imagename);
			FileOutputStream fos = new FileOutputStream(fwl);
			while ((byteRead = ins.read(str_b)) > 0) {
				fos.write(str_b, 0, byteRead);
			}
			;
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取网页内容出错");
		} finally {
			uc = null;
		}
		return 1;
	}

	public static void p(String s) {
		System.out.println("输出： " + s);
	}

	public static void p(int s) {
		System.out.println("输出： " + s);
	}

	public static String getHref(String src) {
		return "<img src=\"/images/emoticons/" + src + ".gif\" alt=\"" + src + "\"  class=\"emote\" />";
	}


	public static String getContent(String temp) {
		String regEx = "\\[emote\\][a-z]{3,8}\\[/emote\\]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(temp);
		while (m.find()) {
			String _temp = m.group();
			p(_temp);
			String img = _temp.replace("[emote]", "");
			img = img.replace("[/emote]", "");
			String src = getHref(img);
			p(src);
			temp = temp.replace(_temp, src);
			p(temp);
		}
		return temp;
	}

	
	
	public static String getid(){
		return Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(),Character.MAX_RADIX);
	}
	
	 /**
     * 合并二维byte数组中的元素为一个一维的byte数组。合并的原则是：假设从左往右
     * 数，第一个下标为纵坐标，个数为M个，第二个下标为横坐标，个数为N个，那么合
     * 并后的数组为 (第一个)byte[n] + byte[n] + ... + (第m个)byte[n]，实际上，
     * N的个数，第m个和第m+1个不一定一样长，程序不会在意。
     *
     * @param bb 二维数组
     * @return 形式为(第一个)byte[n]+byte[n]+...+(第m个)byte[n]的byte数组。
     */
    public static byte[] combineByteArray(byte[][] bb) {
        int fullLen = 0;
        for (int i = 0; i < bb.length; i++) {
            fullLen += bb[i].length;

        }
        byte[] b = new byte[fullLen];
        int index = 0;
        for (int i = 0; i < bb.length; i++) {
            System.arraycopy(bb[i], 0, b, index, bb[i].length);
            index += bb[i].length;
        }

        return b;
    }

    /**
     * 合并两个byte数组为一个。
     *
     * @param b1 数组
     * @param b2 数组
     * @return 形式为b1+b2的byte数组。
     */
    public static byte[] combineByteArray(byte[] b1, byte[] b2) {
        byte[][] bb = new byte[2][];
        bb[0] = b1;
        bb[1] = b2;
        return combineByteArray(bb);
    }

    public static final byte[] splitBytes(byte[] b, int offset, int len) {
        byte[] ret = new byte[len];
        System.arraycopy(b, offset, ret, 0, len);
        return ret;
    }

    public static final byte[] short2bytes(short s, int o) {
        byte[] b = new byte[2]; // java中int长4byte
        if (o == Constants.LITTLE_ENDIAN) { //升序小端字节序存储时由右向左
            for (int count = 0; count < b.length; count++) {
                b[count] = (byte) s;
                s >>= 8;
            }
        }
        if (o == Constants.BIG_ENDIAN) { //降序大端字节序存储时由左到右
            for (int count = b.length - 1; count >= 0; count--) {
                b[count] = (byte) s;
                s >>= 8;
            }
        }
        return b;
    }

    public static final short bytes2short(byte[] b, int offset, int o) {
        short s = 0;
        int len = 2; //short是两字节。
        if (o == Constants.BIG_ENDIAN) { //升序小端字节序存储时由右向左
            for (int count = offset; count < len + offset; count++) {
                s |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != len + offset - 1) {
                    s <<= 8;
                }
            }
        }
        if (o == Constants.LITTLE_ENDIAN) { //降序大端字节序存储时由左到右
            for (int count = offset + len - 1; count >= offset; count--) {
                s |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != offset) {
                    s <<= 8;
                }
            }
        }
        return s;
    }

    public static final short bytes2short(byte[] b, int o) {
        return bytes2short(b, 0, o);
    }

    public static final byte[] int2bytes(int i, int o) {
        byte[] b = new byte[4]; // java中int长4byte
        if (o == Constants.LITTLE_ENDIAN) { //升序小端字节序存储时由右向左
            for (int count = 0; count < b.length; count++) {
                b[count] = (byte) i;
                i >>= 8;
            }
        }
        if (o == Constants.BIG_ENDIAN) { //降序大端字节序存储时由左到右
            for (int count = b.length - 1; count >= 0; count--) {
                b[count] = (byte) i;
                i >>= 8;
            }
        }
        return b;
    }

    public static final int bytes2int(byte[] b, int offset, int o) {
        int i = 0;
        int len = 4; //java中int是4字节。
        if (o == Constants.BIG_ENDIAN) { //升序小端字节序存储时由右向左
            for (int count = offset; count < len + offset; count++) {
                i |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != len + offset - 1) {
                    i <<= 8;
                }
            }
        }
        if (o == Constants.LITTLE_ENDIAN) { //降序大端字节序存储时由左到右
            for (int count = offset + len - 1; count >= offset; count--) {
                i |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != offset) {
                    i <<= 8;
                }
            }
        }
        return i;
    }

    public static final int bytes2int(byte[] b, int o) {
        return bytes2int(b, 0, o);
    }

    public static final byte[] long2bytes(long l, int o) {
        byte[] b = new byte[8]; // java中long长8byte
        if (o == Constants.LITTLE_ENDIAN) {
            for (int count = 0; count < b.length; count++) {
                b[count] = (byte) l;
                l >>= 8;
            }
        }
        if (o == Constants.BIG_ENDIAN) {
            for (int count = b.length - 1; count >= 0; count--) {
                b[count] = (byte) l;
                l >>= 8;
            }
        }
        return b;
    }
    
    //************** add by zhang bo ******************
    public static byte[] floatGetBytes(float data){  
        int intBits = Float.floatToIntBits(data);  
        return intGetBytes(intBits);  
    }
    
    public static byte[] intGetBytes(int data){  
        byte[] bytes = new byte[4];  
        bytes[0] = (byte) (data & 0xff);  
        bytes[1] = (byte) ((data & 0xff00) >> 8);  
        bytes[2] = (byte) ((data & 0xff0000) >> 16);  
        bytes[3] = (byte) ((data & 0xff000000) >> 24);  
        return bytes;  
    }
    
    public static float bytesGetFloat(byte[] bytes){  
        return Float.intBitsToFloat(bytesGetInt(bytes));  
    }
    
    public static int bytesGetInt(byte[] bytes){
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));  
    }
    
    public static byte[] doubleGetBytes(double data){  
        long intBits = Double.doubleToLongBits(data);  
        return longGetBytes(intBits);
    }
    
    public static byte[] longGetBytes(long data)  
    {  
        byte[] bytes = new byte[8];  
        bytes[0] = (byte) (data & 0xff);  
        bytes[1] = (byte) ((data >> 8) & 0xff);  
        bytes[2] = (byte) ((data >> 16) & 0xff);  
        bytes[3] = (byte) ((data >> 24) & 0xff);  
        bytes[4] = (byte) ((data >> 32) & 0xff);  
        bytes[5] = (byte) ((data >> 40) & 0xff);  
        bytes[6] = (byte) ((data >> 48) & 0xff);  
        bytes[7] = (byte) ((data >> 56) & 0xff);  
        return bytes;  
    }
    
    public static double bytesGetDouble(byte[] bytes){  
        long l = bytesGetLong(bytes);  
        System.out.println(l);  
        return Double.longBitsToDouble(l);  
    }
    
    public static long bytesGetLong(byte[] bytes){  
        return(0xffL & (long)bytes[0]) | (0xff00L & ((long)bytes[1] << 8)) | (0xff0000L & ((long)bytes[2] << 16)) | (0xff000000L & ((long)bytes[3] << 24))  
         | (0xff00000000L & ((long)bytes[4] << 32)) | (0xff0000000000L & ((long)bytes[5] << 40)) | (0xff000000000000L & ((long)bytes[6] << 48)) | (0xff00000000000000L & ((long)bytes[7] << 56));  
    }

    /**
     * @Title: bytes2HexString
     * @Description: 字节数组转16进制
     * @param @param src
     * @param @return
     * @return String
     * @throws
     * @author: ZengZhuo
     * @date 2014年8月13日 下午5:31:29
     */
    public static String bytes2HexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	} 
	
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexString2Bytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}
	
	/**
	 * @description 
	 * char转byte
	 * @Author zhang bo
	 * @createDate 2014年8月12日  上午9:34:36
	 * @param data
	 * @return
	 */
	public static byte[] charGetBytes(char data){  
        byte[] bytes = new byte[2];  
        bytes[0] = (byte) (data);  
        bytes[1] = (byte) (data >> 8);  
        return bytes;  
    }
	
	/**
	 * @description 
	 * byte转char
	 * @Author zhang bo
	 * @createDate 2014年8月12日  上午9:35:00
	 * @param bytes
	 * @return
	 */
	public static char bytesGetChar(byte[] bytes){  
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));  
    }

	 /**
	  * @description 
	  * 无符号short转int   
	  * @Author zhang bo
	  * @createDate 2014年8月11日  下午8:37:44
	  */
	public static int unsignedShort2Int(short unsignedShort){
		byte[] shortbyte = short2bytes(unsignedShort, Constants.LITTLE_ENDIAN);//[-95, -7]
		byte[] cellid2_bytes = new byte[4];
		System.arraycopy(shortbyte, 0, cellid2_bytes, 0, 2);//[-95, -7, 0, 0]
		int int_after = bytes2int(cellid2_bytes, Constants.LITTLE_ENDIAN);//63905
		return int_after;
	}
	
	/**
	 * @description 
	 * int转无符号short  java中short是有符号的,但认为是无符号的
	 * @Author zhang bo
	 * @createDate 2014年8月11日  下午8:45:26
	 * @param ordinaryInt
	 * @return
	 */
	public static short int2UnsignedShort(int ordinaryInt){
		byte[] byteShort = new byte[2];
		System.arraycopy(ByteTools.int2bytes(ordinaryInt, Constants.LITTLE_ENDIAN),0, byteShort, 0, 2);//[-95, -7]
		short unsignedShort = ByteTools.bytes2short(byteShort, Constants.LITTLE_ENDIAN);//-1631
		return unsignedShort;
	}
    //******* end *******
    
    
    public static final long bytes2long(byte[] b, int offset, int o) {
        long l = 0;
        int len = 8; //java中long是8字节。
        if (o == Constants.BIG_ENDIAN) { //升序小端字节序存储时由右向左
            for (int count = offset; count < len + offset; count++) {
                l |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != len + offset - 1) {
                    l <<= 8;
                }
            }
        }
        if (o == Constants.LITTLE_ENDIAN) { //降序大端字节序存储时由左到右
            for (int count = offset + len - 1; count >= offset; count--) {
                l |= b[count] < 0 ? b[count] + 256 : b[count];
                if (count != offset) {
                    l <<= 8;
                }
            }
        }
        return l;
    }

    public static final long bytes2long(byte[] b, int o) {
        return bytes2long(b, 0, o);
    }

    public static final byte[] string2WideChar(String s) {
        byte[] tmp = s.getBytes();
        byte[] ret = new byte[tmp.length * 2];
        for (int i = 0; i < ret.length; i++) {
            if (i % 2 == 0) {
                ret[i] = tmp[i / 2];
            } else {
                ret[i] = (byte) 0;
            }
        }
        return ret;
    }

    public static final String wideChar2string(byte[] b) {
        return wideChar2string(b, 0, b.length);
    }

    public static final String wideChar2string(byte[] b, int offset, int len) {
        byte[] ret = new byte[len / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = b[offset + i * 2];
        }
        return new String(ret);
    }

    public final static byte[] string2bytes(String s) {
        return s.getBytes();
    }

    public final static String bytes2string(byte[] b, int offset, int len) {
        return new String(b, offset, len);
    }

    public final static String bytes2string(byte[] b) {
        return new String(b);
    }

    public final static byte[] zeroBytes(int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[0] = (byte) 0;
        }
        return b;
    }

    public final static boolean isZeroBytes(byte[] b) {
        return isZeroBytes(b, 0, b.length);
    }

    public final static boolean isZeroBytes(byte[] b, int offset, int len) {
        boolean zero = true;
        for (int i = offset; i < offset + len; i++) {
            if (b[i] != (byte) 0) {
                zero = false;
            }
        }
        return zero;
    }

    public static String convertBytes(byte abyte0[], int j, int k) {
        if (k % 2 != 0) {
            return "";
        }
        int l = 0;
        int i1 = 1;
        if (k > 0) {
            StringBuffer stringbuffer = new StringBuffer();
            if (abyte0[j] == -1 && abyte0[j + 1] == -2) {
                j += 2;
                k -= 2;
            } else if (abyte0[j] == -2 && abyte0[j + 1] == -1) {
                j += 2;
                k -= 2;
                l = 1;
                i1 = 0;
            }
            for (int j1 = 0; j1 < k; j1 += 2) {
                char c1 = (char) (((char) abyte0[j + j1 + i1] & 0xff) << 8 | (char) abyte0[j + j1
                        + l] & 0xff);
                char c2 = 'A';
                if (c1 == 0) {
                    break;
                }
                if (c1 >= 'Ａ' && c1 <= 'Ｚ') {
                    c1 = (char) ((c1 - 65313) + 65);
                } else if (c1 >= 'ａ' && c1 <= 'ｚ') {
                    c1 = (char) ((c1 - 65345) + 97);
                } else if (c1 >= '⑴' && c1 <= '⑼') {
                    c1 = (char) ((c1 - 9332) + 49);
                } else if (c1 >= '⑽' && c1 <= '⒆') {
                    c2 = '1';
                    c1 = (char) ((c1 - 9341) + 48);
                } else if (c1 == '⒇') {
                    c2 = '2';
                    c1 = '0';
                }
                if (c2 == '1' || c2 == '2') {
                    stringbuffer.append(c2);
                }
                stringbuffer.append(c1);
            }

            return stringbuffer.toString();
        } else {
            return "";
        }
    }

    public static String convertBytes(byte abyte0[]) {
        return convertBytes(abyte0, 0, abyte0.length);
    }

   
    public static int setData(byte buf[], int off, short val) {
        buf[off++] = (byte) ((val >> 8) & 0xff);
        buf[off++] = (byte) (val & 0xff);
        return 2;
    }

    public static int setData(byte buf[], int off, int val) {
        buf[off++] = (byte) ((val >> 24) & 0xff);
        buf[off++] = (byte) ((val >> 16) & 0xff);
        buf[off++] = (byte) ((val >> 8) & 0xff);
        buf[off++] = (byte) (val & 0xff);
        return 4;
    }

    /**
     * Convert a byte array to string. The byte array should be in
     * UnicodeLittleEndianUnmarked encoding.
     *
     * Note: Not all machine support UTF16.
     *
     * @param data The byte array
     * @param off The start offset in the byte array
     * @param len The byte count of data
     * @return The result string
     * @exception IllegalArgumentException If the byte array has an odd length.
     */
    public static String bytesToString(byte[] data, int off, int len) {
        // int nBase = off;
        if (len % 2 != 0) {
            throw new IllegalArgumentException();
        }
        int firstByte = 0;
        int secondByte = 1;
        if (len > 0) {
            StringBuffer ret = new StringBuffer();
            if (data[off] == (byte) 0xFF && data[off + 1] == (byte) 0xFE) {
                off += 2;
                len -= 2;
            } else if (data[off] == (byte) 0xFE && data[off + 1] == (byte) 0xFF) {
                off += 2;
                len -= 2;
                firstByte = 1;
                secondByte = 0;
            }

            for (int i = 0; i < len; i += 2) {
                char tmpc = (char) ((((char) data[off + i + secondByte] & 0xFF) <<
                                     8) |
                                    ((char) data[off + i + firstByte] & 0xFF));
                char tmpc2 = 'A';
                if (tmpc == 0) {
                    break;
                }

                if (tmpc >= 'Ａ' && tmpc <= 'Ｚ') {
                    tmpc = (char) (tmpc - 'Ａ' + 'A');
                } else if (tmpc >= 'ａ' && tmpc <= 'ｚ') {
                    tmpc = (char) (tmpc - 'ａ' + 'a');
                } else if (tmpc >= '⑴' && tmpc <= '⑼') {
                    tmpc = (char) (tmpc - '⑴' + '1');
                } else if (tmpc >= '⑽' && tmpc <= '⒆') {
                    tmpc2 = '1';
                    tmpc = (char) (tmpc - '⑽' + '0');
                } else if (tmpc == '⒇') {
                    tmpc2 = '2';
                    tmpc = '0';
                }
                if (tmpc2 == '1' || tmpc2 == '2') {
                    ret.append(tmpc2);
                }
                ret.append(tmpc);
            }
            return ret.toString();
        } else {
            return "";
        }
    }

  
    public static String bytesToString(byte[] data) {
        return bytesToString(data, 0, data.length);
    }

    /**
     * Convert a string to a byte array in UnicodeLittleEndianUnmarked encoding.
     * @param str The string to convert
     * @return The result byte array.
     */
    public static byte[] stringToBytes(String str) {
        int len = str.length();
        byte[] ret = new byte[len << 1];
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            ret[(i << 1) + 1] = (byte) ((ch >> 8) & 0xFF);
            ret[i << 1] = (byte) (ch & 0xFF);
        }
        return ret;
    }

    /**
     * check if it's Pinyin shorten of a stock
     * @param str - the string to be checked
     * @return - if the length of the string is 4, and the 4 characters are all between 'A'
     * and 'Z', then return true, otherwise, return false
     */
    public static boolean isValidPinYin(String str) {
        if (str.length() == 0) { //check if the length of the string is 4
            return false;
        }

        for (int i = 0; i < str.length(); i++) { //check if the characters are between 'A' and 'Z'
            char c = str.charAt(i);
            if ((c < 'A') || (c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    public static long byte2Number(byte[] tmp, int off, int len) {
        long l = 0;
        for (int i = 0; i < len; i++) {
            l <<= 8;
            l += ((int)tmp[off + i]) & 0xff;
        }
        return l;
    }

 	 /**
    * 判断字符串的编码
    *
    * @param str
    * @return
    */  
  public static String getEncoding(String str) {   
       String encode = "UTF-8";   
      try {   
          if (str.equals(new String(str.getBytes(encode), encode))) {   
               String s = encode;   
              return s;   
           }   
       } catch (Exception e) {   
    	   e.printStackTrace();
    	   
       }   
       encode = "ISO-8859-1";   
      try {   
          if (str.equals(new String(str.getBytes(encode), encode))) {   
               String s1 = encode;   
              return s1;   
           }   
       } catch (Exception e) {   
    	   e.printStackTrace();
       }   
       encode = "GB2312";   
      try {   
          if (str.equals(new String(str.getBytes(encode), encode))) {   
               String s2 = encode;   
              return s2;   
           }   
       } catch (Exception e) { 
    	   e.printStackTrace();
       }   
       encode = "GBK";   
      try {   
          if (str.equals(new String(str.getBytes(encode), encode))) {   
               String s3 = encode;   
              return s3;   
           }   
       } catch (Exception e) {   
    	   e.printStackTrace();
       }   
      return "";   
   }   

  
  	/** 
      * MD5 加密 
      */  
      public static String getMD5Str(String str) {  
          MessageDigest messageDigest = null;  
    
          try {  
              messageDigest = MessageDigest.getInstance("MD5");  
    
              messageDigest.reset();  
    
              messageDigest.update(str.getBytes("UTF-8"));  
          } catch (NoSuchAlgorithmException e) {  
              System.out.println("NoSuchAlgorithmException caught!");  
              System.exit(-1);  
          } catch (UnsupportedEncodingException e) {  
              e.printStackTrace();  
          }  
    
          byte[] byteArray = messageDigest.digest();  
    
          StringBuffer md5StrBuff = new StringBuffer();  
   
          for (int i = 0; i < byteArray.length; i++) {              
              if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                  md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
              else  
                  md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
          }  
          return md5StrBuff.toString();  
      }  
      public static String getFile(String path) {
  		// get file list where the path has
  		File file = new File(path);
  		// get the folder list
  		return file.getName();

  		// File[] array = file.listFiles();
  		//
  		// for(int i=0;i<array.length;i++){
  		// if(array[i].isFile()){
  		// // only take file name
  		// System.out.println("^^^^^" + array[i].getName());
  		// // take file path and name
  		// System.out.println("#####" + array[i]);
  		// // take file path and name
  		// System.out.println("*****" + array[i].getPath());
  		// }else if(array[i].isDirectory()){
  		// getFile(array[i].getPath());
  		// }
  		// }
  	}

  	public static String getExtension(File f) {
  		return (f != null) ? getExtension(f.getName()) : "";
  	}

  	public static String getExtension(String filename) {
  		return getExtension(filename, "");
  	}


  	public static String getExtension(String filename, String defExt) {
  		if ((filename != null) && (filename.length() > 0)) {
  			int i = filename.lastIndexOf('.');

  			if ((i > -1) && (i < (filename.length() - 1))) {
  				return filename.substring(i);
  			}
  		}
  		return defExt;
  	}

  	public static String trimExtension(String filename) {
  		if ((filename != null) && (filename.length() > 0)) {
  			int i = filename.lastIndexOf('.');
  			if ((i > -1) && (i < (filename.length()))) {
  				return filename.substring(0, i);
  			}
  		}
  		return filename;
  	}
  	
  	/**
  	 * 判断上传图片是否为图片
  	 * @param uploadImageFileName 图片名字
  	 * @return
  	 */
  	public static boolean fileType(String uploadImageFileName){
		if (".jpg".equalsIgnoreCase(ByteTools.getExtension(uploadImageFileName)) 
				|| ".png".equalsIgnoreCase(ByteTools.getExtension(uploadImageFileName))
				||".gif".equalsIgnoreCase(ByteTools.getExtension(uploadImageFileName)) 
				||".bmp".equalsIgnoreCase(ByteTools.getExtension(uploadImageFileName))){
			return true;
		}else{
			return false;
		}
	}
  	 private static String getHexString(byte b) {   
         String hexStr = Integer.toHexString(b);   
         int m = hexStr.length();   
         if (m < 2) {   
             hexStr = "0" + hexStr;   
         } else {   
             hexStr = hexStr.substring(m - 2);   
         }   
         return hexStr;   
     }   
   
     private static String getBinaryString(int i) {   
         String binaryStr = Integer.toBinaryString(i);   
         int length = binaryStr.length();   
         for (int l = 0; l < 8 - length; l++) {   
             binaryStr = "0" + binaryStr;   
         }   
         return binaryStr;   
     }   
  	public static String gb2312ToUtf8(String str) throws UnsupportedEncodingException {

  		StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < str.length(); i++) {   
            String s = str.substring(i, i + 1);   
            if (s.charAt(0) > 0x80) {   
                byte[] bytes = s.getBytes("Unicode");   
                String binaryStr = "";   
                for (int j = 2; j < bytes.length; j += 2) {   
                    // the first byte   
                    String hexStr = getHexString(bytes[j + 1]);   
                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
                    binaryStr += binStr;   
                    // the second byte   
                    hexStr = getHexString(bytes[j]);   
                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
                    binaryStr += binStr;   
                }   
                // convert unicode to utf-8   
                String s1 = "1110" + binaryStr.substring(0, 4);   
                String s2 = "10" + binaryStr.substring(4, 10);   
                String s3 = "10" + binaryStr.substring(10, 16);   
                byte[] bs = new byte[3];   
                bs[0] = Integer.valueOf(s1, 2).byteValue();   
                bs[1] = Integer.valueOf(s2, 2).byteValue();   
                bs[2] = Integer.valueOf(s3, 2).byteValue();   
                String ss = new String(bs, "UTF-8");   
                sb.append(ss);   
            } else {   
                sb.append(s);   
            }   
        }   
        return sb.toString();   

  	}
  	
  	/**
  	 * @Title: isNumeric
  	 * @Description: 判断字符串是否为数字
  	 * @param @param str
  	 * @param @return
  	 * @return boolean
  	 * @throws
  	 * @author: ZengZhuo
  	 * @date 2014年8月14日 下午4:43:51
  	 */
  	public static boolean isNumeric(String str){ 
  	    Pattern pattern = Pattern.compile("[0-9]*"); 
  	    return pattern.matcher(str).matches();    
  	 }
  	
  	public byte[] getLowHighByte(byte[] data) 
  	{    
  	    byte[] lowHighByte=new byte[2];
  	    int dataLen = data.length;        
  	    lowHighByte[0] = (byte)(0xFF&dataLen);
  	    lowHighByte[1] = (byte)((0xFF00&dataLen)>>8);
  	    return lowHighByte;
  	}  
  	
	public static void main(String[] args) {
		//81,0,0,0,1,0,0,0
		
		
//		byte [] b=new byte[8];
//		b[0]=-69;
//		b[1]=124;
//		b[2]=2;
//		b[3]=99;
//		b[4]=71;
//		b[5]=1;
//		b[6]=0;
//		b[7]=0;
//		
//		System.out.println(bytes2long(b, Constants.LITTLE_ENDIAN));
//		String str16 = "A7";
//		byte [] byteArray =ByteTools.hexString2Bytes(str16);
//		System.out.println(byteArray[0]);
//		System.out.println(ByteTools.bytes2HexString(byteArray));
//		byte b = -125;
//		
//		System.out.println(ByteTools.bytes2HexString(new byte[]{b}));
//		
//		System.out.println(ByteTools.isNumeric("ss"));
		
		
//		String str16= "FB";
//		System.out.println(Integer.valueOf(str16, 16));
//		System.out.println(Integer.parseInt("53", 16));
//		
//		String str_01 = "FA";
//		int res_01 = Integer.parseInt(str_01, 16);
////		String str_02 = "3B";
////		int res_02 = Integer.parseInt(str_02, 16);
////		System.out.println("**" + (res_01 + res_06));
//		String str_03 = "3B";
//		int res_03 = Integer.parseInt(str_03, 16);
//		String str_04 = "10";
//		int res_04 = Integer.parseInt(str_04, 16);
//		String str_05 = "88";
//		int res_05 = Integer.parseInt(str_05, 16);
//		
//		
//		String str_06 = "53";
//		int res_06 = Integer.parseInt(str_06, 16);
////		System.out.println(53);
//		String str_07 = "01";
//		int res_07 = Integer.parseInt(str_07, 16);
//		String str_08 = "03";
//		int res_08 = Integer.parseInt(str_08, 16);
//		int res = res_01 + res_03 + res_04 + res_05+res_06 + res_07 +res_08;
//		System.out.println("求和:" + res);
//		String str_09 = "24";
//		System.out.println(Integer.parseInt(str_09,16));
//		
//		System.out.println(Integer.parseInt("5A", 16));
//		String str = "FA3B1088530103";
//		byte [] byteArray = str.getBytes();
//		System.out.println(ByteTools.bytes2int(byteArray,0, 0));
//		System.out.println(byteArray.length);
//		int res01 = ByteTools.bytes2int(byteArray, 0, 0);
//		int res02 = ByteTools.bytes2int(byteArray, 0, 0);
//		System.out.println(res01);
//		System.out.println(res02);
//		
//		String b_01 = "FA";
//		int r_01 = ByteTools.bytes2int(b_01.getBytes(), 0);
//		String b_02 = "3F";
//		int r_02 = ByteTools.bytes2int(b_02.getBytes(), 0);
//		String b_03 = "59";
//		int r_03 = ByteTools.bytes2int(b_03.getBytes(), 0);
//		String b_04 = "F7";
//		int r_04 = ByteTools.bytes2int(b_04.getBytes(), 0);
//		String b_05 = "38";
//		int r_05 = ByteTools.bytes2int(b_05.getBytes(), 0);
//		String b_06 = "F4";
//		int r_06 = ByteTools.bytes2int(b_06.getBytes(), 0);
//		String b_07 = "5F";
//		int r_07 = ByteTools.bytes2int(b_07.getBytes(), 0);
//		int total = r_01 + r_02 + r_03 + r_04 + r_05 + r_06 + r_07 ;
//		System.out.println(total);
		
//		String [] array = new String []{"FA","3F","59","F7","38","F4","5F"};
//		String [] array = new String []{"FA","3B","10","88","53","01","03"};
//		int total = 0;
//		for (int i = 0; i < array.length; i++) {
//			System.out.println(Integer.parseInt(array[i],16));
//			total = total + Integer.parseInt(array[i] ,16);
//		}
//		
//		System.out.println("合计："+total);
//		
//		
//		
//
//		String data_1 = "FA3F59F738F45F14";
//		String data_2 = "FA3B108853010324";
//		String data_3 = "FA3B1088FE0103CF";
//		String data_4 = "FA3F59F700000089";
//
//		byte[] data_1_byte = ByteTools.hexString2Bytes(data_4);//[-6, 63, 89, -9, 56, -12, 95, 20]
//		byte cc = (byte)(data_1_byte[0]+data_1_byte[1]);
//		byte test =0;
//		for(int i=0;i<data_1_byte.length-1;i++){
//			test += data_1_byte[i];
//		}
//		
//		System.out.println("test=" + test);
//		return;
		byte[] b1=new byte[]{-49};
		System.out.println(ByteTools.bytes2HexString(b1).toUpperCase());
//		String str="-53";
//		byte[] bArray=ByteTools.hexString2Bytes(str);
//		for(byte b:bArray)
//		System.out.println(b);
		
	}  	
	
	
	
	
	

 
}
