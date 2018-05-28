package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.StoreService;
import com.scsa.model.vo.Store;

@Controller
public class StoreController {
	StoreService storeService;
	@Autowired
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	@RequestMapping("/list_store.do")
	public @ResponseBody List<Store> findStoreList() {
		return storeService.findStoreList();
	}
}
