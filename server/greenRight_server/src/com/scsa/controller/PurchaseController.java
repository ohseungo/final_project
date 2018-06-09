package com.scsa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.scsa.model.service.PurchaseService;

@Controller
public class PurchaseController {
	
	private PurchaseService purchaseService;
	@Autowired
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@RequestMapping("orderList.do")
	public String selectPurchaseList(Model model, HttpSession session) {
		Map<String, String> id=new HashMap<String, String>();
		id.put("compId",(String) session.getAttribute("compId"));
		model.addAttribute("purchaseList", purchaseService.searchPurchaseListWithMultipleCondition(id));
		System.out.println(purchaseService.searchPurchaseListWithMultipleCondition(id));
		return "purchaseList.jsp";
	}
}
