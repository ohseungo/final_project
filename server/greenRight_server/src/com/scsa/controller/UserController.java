package com.scsa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.scsa.model.service.UserService;
import com.scsa.model.vo.User;

@Controller
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login.do")
	public @ResponseBody boolean login(String userId, String password) {
		User user = userService.searchUser(userId);
		if (user!=null && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	
/*
	@RequestMapping("/login.do")
	public @ResponseBody String login(String userId, String password) {
		User user = userService.searchUser(userId);
		if (user!=null && user.getPassword().equals(password)) {
			if(userId.equals("starbucks") || userId.equals("wcafe")) {
				return "redirect:/company.jsp";
			}
			else if(userId.equals("¾Ö°æ")) {
				return "redirect:/mall.jsp";
			}
		}
		return "redirect:/login.jsp";
	}
	
	*/
	@RequestMapping("/register_user.do")
	public @ResponseBody boolean addUser(User user) {
		return userService.addUser(user);
	}
}
