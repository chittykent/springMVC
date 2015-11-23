package com.app.simple.web.control.Login;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.simple.service.LoginService;
import com.app.simple.web.BaseAction;
import com.app.simple.web.common.Global;

@Controller
@RequestMapping("/login")
public class LoginAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);
	@Autowired
	private LoginService loginService;
	
	
	/***
	 * 用户登陆
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> login(@RequestParam String userName,
			@RequestParam String password) {
		logger.info("login...{}",userName);
		System.out.println("username:"+userName+",password:"+password);
		Map<String, Object> map = new HashMap<String, Object>();
		String status = Global.FAILED, errMsg = null;
		try {
			boolean result = loginService.login(userName, password);
			if (result) {
				status = Global.SUCCESS;
			} else {
				errMsg = "用户名或密码错误.";
			}
		} catch (Exception ex) {
			errMsg = "用户登陆异常.";
			logger.error("login exception:{}", ex.getMessage());
		}
		map.put("status", status);
		if (status.equals(Global.FAILED)) {
			map.put("errMsg", errMsg);
		}
		return map;
	}
}
