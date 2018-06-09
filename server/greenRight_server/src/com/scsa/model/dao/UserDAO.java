package com.scsa.model.dao;

import com.scsa.model.vo.User;

public interface UserDAO {
	
	boolean insertUser(User user);
	User selectUser(String userId);
	User selectUser(String userId, String password);
	boolean deleteUser(String userId);
	boolean updateUser(User user);
	
}
