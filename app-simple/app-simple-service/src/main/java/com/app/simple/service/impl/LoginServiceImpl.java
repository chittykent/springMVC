package com.app.simple.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.simple.dao.BaseDao;
import com.app.simple.dao.UserDao;
import com.app.simple.model.UserModel;
import com.app.simple.service.LoginService;
import com.app.simple.utils.MD5;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private BaseDao<UserModel,Serializable> baseDao;
	
	@Override
	public boolean login(String userName, String password) {
		boolean result=false;
		System.out.println("加密："+MD5.encodeString(password));
		UserModel userModel = baseDao.get("username = '"+userName+"' and userPassword ='"+MD5.encodeString(password)+"' and isDelete='N'");
		System.out.println("userModel:"+userModel);
		if(userModel!=null)result=true;
		//result=userDao.checkLogin(userName, password);
		return result;
	}
	
}
