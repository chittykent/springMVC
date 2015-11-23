package com.app.simple.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 自动扫描FTP文件工具类
 * 需要定时执行
 */
public class ScanFtp {
	//服务器图片路径文件夹
	private String serverLocal = "D:/TOOLS/Tomcat 6.0/webapps/BCCCSM/modelforcast/";
	//图片上传文件夹存放路径,文件夹内应包含AGCM CSM ZS 3个子文件夹分别存放需要扫描到tomcat中的图片
	private String saveLocal = "D:/modelForcast/";
	/**
	 * 获得远程权限
	 * @return
	 */
	private void getFTPAdress(){
		//登陆成功
	}
	
	/**
	 * 开始扫描
	 * @throws IOException 
	 */
	private void scan() throws IOException {
		this.getFTPAdress();
		File file = new File(saveLocal + "AGCM");  //打开AGCM
		File[] array = file.listFiles();
	    String fileName;
	    File fileTemp;
	    for(int i = 0; i < array.length; i++){
	    	if(array[i].isFile()) {
	            fileTemp = array[i];
	            fileName = fileTemp.getName();//取出文件名
	            if (!fileName.equals("humbs.db")) {
	    			this.saveFile(fileTemp, 1);//分析每一个文件名字并存储
	    			System.out.println(fileName + " saved");
	            }
            }   
        }
	    
	    file = new File(saveLocal + "CSM");  //打开CSM
	    array = file.listFiles();
	    for(int i = 0; i < array.length; i++){
	    	if(array[i].isFile()) {
	            fileTemp = array[i];
	            fileName = fileTemp.getName();//取出文件名
	            if (!fileName.equals("humbs.db")) {
	    			this.saveFile(fileTemp, 2);//分析每一个文件名字并存储
	    			System.out.println(fileName + " saved");
	            }
            }   
        }
	    
	    file = new File(saveLocal + "ZS");  //打开ZS
	    array = file.listFiles();
	    for(int i = 0; i < array.length; i++){
	    	if(array[i].isFile()) {
	            fileTemp = array[i];
	            fileName = fileTemp.getName();//取出文件名
	            if (!fileName.equals("humbs.db")) {
	    			this.saveFile(fileTemp, 3);//分析每一个文件名字并存储
	    			System.out.println(fileName + " saved");
	            }
            }   
        }
	    

	}
	
	/**
	 * 开始执行
	 * @throws IOException 
	 */
	public void execute() throws IOException{
		scan();//开始扫描
	}
	
	/**
	 * 按类型存储
	 * @param file
	 * @param type
	 * @throws IOException 
	 */
	private void saveFile(File file, int type) throws IOException {
		String fileName = file.getName();
		//类型A C 和 指数3种
		String year = fileName.substring(1, 5);//获得发布年份
		String date = fileName.substring(5, 9);//获得发布日期包含月日
		String var = null;//获得变量名字
		String dir = serverLocal;//存储目录名字
		if (type == 1 ) {
			var = fileName.substring(11, 15);
			dir = dir + "AGCM/" + var + "/" + year + "/" + date;
		} else if(type == 2) {
			var = fileName.substring(11, 15);
			dir = dir + "CSM/" + var + "/" + year + "/" + date;
		} else {
			var = fileName.substring(11, 15);//指数的暂时没处理
			dir = dir + "ZS/" + var + "/" + year + "/" + date;
		}
		//判断是否存在这样的目录没有就自动创建
		File savePath = new File(dir);
		if(!savePath.exists()) {
			savePath.mkdirs();
		}
		File saveFile = new File(dir + "/" + fileName);
		if(!saveFile.exists()){//如果不存在,就存文件
			FileInputStream fis = null;//这里用本地复制暂时代替FTP
			FileOutputStream fos =null;
			BufferedInputStream bis =null;
			BufferedOutputStream bos =null;  
			int c;
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(dir + "/" + fileName);
			bos = new BufferedOutputStream(fos);
			while((c = bis.read())!= -1)
				bos.write(c);
			bos.flush();
		    if(bos != null) bos.close();
		    if(bis != null) bis.close();
		    if(fos != null) fos.close();
		    if(fis != null) fos.close();
		} else {
			System.out.println("文件已经存在,不进行存储,可清理当前文件.");
		}
	}
	
	
	/**
	 * 测试方法
	 * @param argv
	 * @throws IOException 
	 */
	public static void main(String argv[])  {
		ScanFtp s = new ScanFtp();
		try {
			s.scan();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
