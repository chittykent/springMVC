package com.app.simple.web.common;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @description 日志
 * @author zheng.guo
 * @date 2015年7月26日 上午11:03:11
 * 
 */
@Aspect
public class LogAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String requestPath = null; // 请求地址
	private Map<?, ?> inputParamMap = null; // 传入参数
	private Object outParam = null;// 存放输出结果
	private long startTimeMillis = 0; // 开始时间
	private long endTimeMillis = 0; // 结束时间

	@Pointcut("execution(* com.app.simple.web.control.*.*.*(..))")
	public void init() {

	}

	/***
	 * 方法开始
	 * 
	 * @param joinPoint
	 */
	@Before(value = "init()")
	public void before(JoinPoint joinPoint) {
		startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
	}

	/***
	 * 方法结束
	 * 
	 * @param joinPoint
	 */
	@After(value = "init()")
	public void after(JoinPoint joinPoint) {
		endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
		writeLog();
	}

	/***
	 * 环绕
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "init()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			// 获取输入参数
			inputParamMap = request.getParameterMap();
			// 获取请求地址
			requestPath = request.getRequestURI();
			Object result = pjp.proceed();// result的值就是被拦截方法的返回值
			outParam = result;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("doAround error：{}", e.getMessage());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Global.SUCCESS);
		map.put("errMsg", "内部错误");
		return map;
	}

	private void writeLog() {
		String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(startTimeMillis);
		logger.info("url：" + requestPath + "; op_time：" + optTime
				+ " pro_time：" + (endTimeMillis - startTimeMillis) + "ms ;"
				+ " param：" + JSONObject.fromObject(inputParamMap).toString()
				+ ";" + "\n result:"
				+ JSONObject.fromObject(outParam).toString());
	}
}
