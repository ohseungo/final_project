package com.scsa.model.service;

import com.scsa.model.dao.UserDAO;
import com.scsa.model.vo.User;

public class UserServiceImpl implements UserService {

	UserDAO userDao;
	
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean addUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User searchUser(String userId) {
		return userDao.selectUser(userId);
	}

	@Override
	public User loginUser(String userId, String password) {
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

}
