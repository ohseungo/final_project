package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.WalkService;
import com.scsa.model.vo.Walk;

@Controller
public class WalkController {
	private WalkService walkService;
	@Autowired
	public void setWalkService(WalkService walkService) {
		this.walkService = walkService;
	}
	
	@RequestMapping("/add_walk.do")
	public @ResponseBody boolean addWalk(Walk walk) {
		return walkService.addWalk(walk);
	}
	
	@RequestMapping("/view_walk.do")
	public @ResponseBody List<Walk> 
			showWalkListWithUserId(String userId) {
		return walkService.findWalkListWithUserId(userId);
	}
	
	
}
