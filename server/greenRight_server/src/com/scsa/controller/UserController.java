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

//	@RequestMapping("/login.do")
//	public @ResponseBody boolean login(String userId, String password) {
//		User user = userService.searchUser(userId);
//		if (user!=null && user.getPassword().equals(password)) {
//			return true;
//		}
//		return false;
//	}
	
	
/*
	@RequestMapping("/login.do")
	public @ResponseBody String login(String userId, String password) {
		User user = userService.searchUser(userId);
		if (user!=null && user.getPassword().equals(password)) {
			if(userId.equals("starbucks") || userId.equals("wcafe")) {
				return "redirect:/company.jsp";
			}
			else if(userId.equals("�ְ�")) {
				return "redirect:/mall.jsp";
			}
		}
		return "redirect:/login.jsp";
	}
	@RequestMapping("/find_user.do")
	public @ResponseBody User login(String userId) {
		return userService.searchUser(userId);
	}
	
	*/
	
	@RequestMapping(method=RequestMethod.POST, value="/login.do")
	public String loginUser(@RequestParam String userId, String password
			,HttpSession session) {
		User user = userService.searchUser(userId);
		if (user!=null && user.getPassword().equals(password)) {//로그인에 성공하면
			if(user.getUserType() == 1) {//상품 판매자 웹페이지로 이동
				session.setAttribute("userId", userId);
				return "/WEB-INF/mall/mall.jsp";
			}
			else if (user.getUserType() == 2){//업체(컵관련) 웹페이지로 이동
				return "/WEB-INF/corporate/corporate.jsp";
			}
			else {//0번(일반고객일 경우)
				return "redirect:/error.jsp";
			}
		}else {//로그인에 실패할 경우 다시 로그인페이지로 가줌
			return "redirect:/login.jsp";
		}
	}
	
	@RequestMapping("/logout.do")
	//아이디나 유저정보 필요없으니까 session만 받으면 됨.
	public String logout(HttpSession session) {
		//session을 초기화하는놈. id로 로그인이 되어있으니까 걔를 session에서 지워주면 로그아웃하는 기능처럼 됨!
		session.invalidate();
		//session 클리어 한 후 다시 로그인페이지로 가라!
		return "redirect:/login.jsp";
	}
	
	@RequestMapping("/register_user.do")
	public @ResponseBody boolean addUser(User user) {
		return userService.addUser(user);
	}
	
}
