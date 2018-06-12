package com.scsa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.scsa.model.service.PurchaseService;
import com.scsa.model.vo.Purchase;

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
		for(Purchase a: purchaseService.searchPurchaseListWithMultipleCondition(id)) {
			System.out.println(id.get("compId"));
			System.out.println(a);
		}
		return "purchaseList.jsp";
	}
	

	@RequestMapping("list_order.do")
	public @ResponseBody List<Purchase> selectPurchaseListWithUserId(String userId) {
		Map<String, String> id=new HashMap<String, String>();
		id.put("userId",(String) userId);
		
		return purchaseService.searchPurchaseListWithMultipleCondition(id);
	}
	
	@RequestMapping("search.do")
	public String searchPurcaseList(Purchase purchase, Model model, HttpSession session) {
		purchase.setCompId((String) session.getAttribute("compId"));
		model.addAttribute("purchaseList", purchaseService.searchPurchaseList(purchase));
		for(Purchase a: purchaseService.searchPurchaseList(purchase)) {
			System.out.println(a);
		}
		return "purchaseList.jsp";
	}
}
