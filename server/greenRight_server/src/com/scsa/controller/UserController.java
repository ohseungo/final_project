package com.scsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@RequestMapping("/login.do")
	public @ResponseBody boolean login(String id, String pw) {
		if (id !=null && id.equals("test") &&
			pw != null && pw.equals("111")) return true;
		return false;
	}
}