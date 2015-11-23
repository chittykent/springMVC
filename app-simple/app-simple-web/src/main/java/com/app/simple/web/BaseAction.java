package com.app.simple.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.simple.web.common.Global;

/**
* 传参异常处理
* @author zheng.guo
* @date 2015年9月22日 下午2:12:35    
* @description  
*/
public class BaseAction {
	private static Logger logger=LoggerFactory.getLogger(BaseAction.class);
	@ExceptionHandler
	@ResponseBody
    public Map<String, Object> exp(HttpServletRequest request, Exception ex) {  
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("status",Global.FAILED);
        String errMsg="传参错误.";
        //JSONObject.fromObject(request.getParameterMap()).toString()
        System.out.println(ex.toString());
        if(ex instanceof TypeMismatchException) {  
        	errMsg="类型转换错误,请检查传参值是否正确.";
        } else {  
        }  
        map.put("errMsg", errMsg);
        logger.error(errMsg+".params:"+JSONObject.fromObject(request.getParameterMap()).toString());
        return map;
    }  
}
