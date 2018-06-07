package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.GreenPointService;
import com.scsa.model.vo.GreenPoint;

@Controller
public class GreenPointController {
	private GreenPointService greenPointService;
	
	@Autowired
	public void setGreenPointService(GreenPointService greenPointService) {
		this.greenPointService = greenPointService;
	}
	
	@RequestMapping("/add_point.do")
	public @ResponseBody boolean addGreenPoint(GreenPoint point) {
		return greenPointService.addGreenPoint(point);
	}
	
	@RequestMapping("/view_point.do")
	public @ResponseBody List<GreenPoint> searchGreenPointListwithUserId(String userId) {
		return greenPointService.searchGreenPointListwithUserId(userId);
	}
	
	@RequestMapping("/total_point.do")
	public @ResponseBody String searchGreenPointTotalwithUserId(String userId) {
		return greenPointService.searchGreenPointTotalwithUserId(userId);
	}

	@RequestMapping("/status_point.do")
	public @ResponseBody List<GreenPoint> searchGreenPointStatuswithUserId(String userId) {
		return greenPointService.searchGreenPointStatuswithUserId(userId);
	}
	
	
	
}
