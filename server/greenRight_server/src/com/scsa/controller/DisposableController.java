package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.DisposableService;
import com.scsa.model.service.ProductService;
import com.scsa.model.vo.Disposable;
import com.scsa.model.vo.Product;

@Controller
public class DisposableController {
	
	private DisposableService disposableService;
	
	@Autowired
	public void setDisposableService(DisposableService disposableService) {
		this.disposableService = disposableService;
	}
	

	@RequestMapping("/add_disposable.do")
	public @ResponseBody boolean addDisposable(Disposable disposable) {
		return disposableService.addDisposable(disposable);
	}
	

	@RequestMapping("/select_disposable.do")
	public @ResponseBody Disposable selectDisposable(String dispId) {
		return disposableService.selectDisposable(dispId);
	}
	

	@RequestMapping("/view_disposable.do")
	public @ResponseBody List<Disposable> selectDisposableList() {
		return disposableService.selectDisposableList();
	}
	

	@RequestMapping("/delete_disposable.do")
	public @ResponseBody boolean deleteDisposable(String dispId) {
		return disposableService.deleteDisposable(dispId);
	}
	
}
