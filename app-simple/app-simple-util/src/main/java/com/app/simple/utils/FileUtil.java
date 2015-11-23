package com.app.simple.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileUtil {
	private static String message;

	public FileUtil() {
	}
	
	


	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		FileIsExist(filePathAndName);
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";

		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + "\r\n");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			es.printStackTrace();
		}
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (; st.hasMoreTokens();) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			message = "创建目录操作出错！";
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent,boolean cover,String encoding) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath,cover);
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent,
			String encoding) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "<br>删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			message = "复制整个文件夹内容操作出错";
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public String getMessage() {
		return FileUtil.message;
	}

	

	/**
	 * 文件不存在则创建文件
	 * 
	 * @param filename
	 */
	public static void FileIsExist(String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 文件是否存在 不创建文件
	 * 
	 * @param filename
	 */
	public static boolean IsExist(String filename) {
		File f = new File(filename);
		if (f.exists()) {
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param string
	 * @param hasSymbol
	 */
	public static void WriteFile(String filename, String content,boolean cover,String encoding) {
		writeStringToFile(filename, content, encoding, cover);
	}

	/**
	 * 
	 * @param string
	 * @param encoding
	 * @return
	 */
	public static List<String> ReadFileToListForString(String filename, String encoding) {
		List<String> array = new ArrayList<>();
		FileIsExist(filename);
		try {
			FileInputStream fs = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fs, encoding);
			br2 = new BufferedReader(isr);
			String data = "";
			while ((data = br2.readLine()) != null) {
                array.add(data);
            }
			
        }catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return array;
	}

	/**
	 * 在文件中追加文件
	 * @param filename
	 * @param content
	 */
	public static void FileAdd(String filename, String content) {
		FileIsExist(filename);
		BufferedWriter out = null;
		   try {
		    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
		    out.write(content);
		    out.write("\r\n");
		   } catch (Exception e) {
		    e.printStackTrace();
		   } finally {
		    try {
		     out.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }
	}
	
	
	public static boolean writeStringToFile(String fileName,String content,String enc,boolean cover) {
		File file = new File(fileName);
		
		try {
//			if(file.isFile()){
//				file.deleteOnExit();
//				file = new File(file.getAbsolutePath());
//			}
			OutputStreamWriter os = null;
			if(enc==null||enc.length()==0){
				os = new OutputStreamWriter(new FileOutputStream(file,cover));
			}else{
				os = new OutputStreamWriter(new FileOutputStream(file,cover),enc);
			}
			os.write(content);
			//os.write("\r\n");
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static List<String> checkRepeat(String filename,String encoding){
		List<String> array = new ArrayList<String>();
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fs = new FileInputStream(filename);
			isr = new InputStreamReader(fs, encoding);
			br = new BufferedReader(isr);
			String data = "";
			while ((data = br.readLine()) != null) {
				if(!array.contains(data) && !data.equals("")){
					array.add(data);
				}
                
            }
			
        }catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
			if(br!=null){
				
					br.close();
				
			}
			if(isr!=null){
				isr.close();
			}
			if(fs!=null){
				fs.close();
			}} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return array;
		
	}

	private static List<String> list=new ArrayList<>();

	private static BufferedReader br;

	private FileOutputStream fs;

	private static BufferedReader br2;
	public static void main(String[] args) {
		String str="C:/Users/zqkj/Desktop/所有群成员号12月";

		File file=new File(str);
		
		tempReadFile(file, "GBK");
		
		System.out.println(list.size());
		int j=0;
		for (String s : list) {
			s=s.replace("(", "");
			s=s.replace(")", "");
//			System.out.println(s);
			if(isNumeric(s)){				
				WriteFile("d://qqmail-12.txt", s+"@qq.com,", true, "gbk");
			}	else{
				j++;
			}
		}
		System.out.println(j);
//		String city="%E5%B8%B8%E5%B7%9E%E5%B8%82";
//		
//		System.out.println(URLDecoder.decode(city));
	}
	
	
	public static boolean isNumeric(String str){
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();   
	}
	
	
	public static void tempReadFile(File file,String encoding){
		
		if(file.isDirectory()){
			 File[] flist=file.listFiles();
			 for (File file2 : flist) {
				 tempReadFile(file2,encoding);
			}
		}else{
			try {
				FileInputStream fs = new FileInputStream(file.getPath());
				InputStreamReader isr;
				
				if (encoding.equals("")) {
					isr = new InputStreamReader(fs);
				} else {
					isr = new InputStreamReader(fs, encoding);
				}
				br = new BufferedReader(isr);
				try {
					String data = "";
					while ((data = br.readLine()) != null ) {
						if(data.contains("(")){
							if(!list.contains(data)){
								list.add(data);
							}
						}						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException es) {
				es.printStackTrace();
			}
		}
	}
}
