package com.scsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.scsa.model.service.StepService;

@Controller
public class StepController {
	
	StepService stepService;
	@Autowired
	public void setStepService(StepService stepService) {
		this.stepService = stepService;
	}
	
	
}
