package com.app.simple.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.app.simple.dao.UserDao;
import com.app.simple.model.UserModel;
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserModel,Serializable> implements UserDao{

}
