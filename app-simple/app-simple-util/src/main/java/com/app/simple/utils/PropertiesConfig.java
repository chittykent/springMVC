package com.app.simple.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
/**
 * 读取Config文件工具类
 * @version 1.0
 * @since JDK 1.6
 */
public class PropertiesConfig {  
	  
    /** 
     * 获取整个配置文件中的属性
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     */  
    public static Properties readData(String filePath) {  
    	filePath = getRealPath(filePath);
        Properties props = new Properties();  
        try {  
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
            props.load(in);  
            in.close();  
            return props;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    private static String getRealPath(String filePath) {
    	//获取绝对路径 并截掉路径的”file:/“前缀  
    	return PropertiesConfig.class.getResource("/" + filePath).toString().substring(6);
    }
}  
