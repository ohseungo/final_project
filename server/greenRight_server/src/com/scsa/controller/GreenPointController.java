package com.scsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.scsa.model.service.GreenPointService;

@Controller
public class GreenPointController {
	private GreenPointService greenPointService;
	
	@Autowired
	public void setGreenPointService(GreenPointService greenPointService) {
		this.greenPointService = greenPointService;
	}
	
	
	
	
}
