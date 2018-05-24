package com.scsa.model.service;

import com.scsa.model.vo.User;

public interface UserService {
	boolean addUser(User user);
	User searchUser(String userId);
	User loginUser(String userId, String password);
}
