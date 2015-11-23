package com.app.simple.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * 文件上传工具类
 *
 */
public class UploadUtil {
	private static final int BUFFER_SIZE = 16 * 1024;
	//保存图片
	public static synchronized void copy(File src, File newFile) {
		
		try {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				os = new BufferedOutputStream(new FileOutputStream(newFile),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (is.read(buffer) > 0) {
					os.write(buffer);
				}
			} finally {
				if (null != is) {
					is.close();
				}
				if (null != os) {
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回 年号+月号+天+时+分+秒+随机码
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static synchronized String getTime() {
		Calendar calendar = Calendar.getInstance();
		String year = calendar.get(calendar.YEAR) + "";
		String month = (calendar.get(calendar.MONTH) + 1) + "";
		String day = calendar.get(calendar.DAY_OF_MONTH) + "";
		String hour = calendar.get(calendar.HOUR_OF_DAY) + "";
		String minute = calendar.get(calendar.MINUTE) + "";
		String second = calendar.get(calendar.SECOND) + "";
		String milliSecond = calendar.get(calendar.MILLISECOND) + "";
		int r = (int)(Math.random()*100000);
		String random = String.valueOf(r);
		return year + month + day + hour + minute + second + milliSecond + random+"a";
	}

}
