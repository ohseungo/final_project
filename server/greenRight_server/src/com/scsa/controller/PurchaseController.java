package com.scsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.scsa.model.service.PurchaseService;

@Controller
public class PurchaseController {
	
	private PurchaseService purchaseService;
	@Autowired
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	
}
