package com.scsa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping("/mall.do")
	public String selectDisposableList(Model model, String compId) {
		model.addAttribute("disposableList", disposableService.selectDisposableList(compId));
		return "/mall.jsp";
	}
	
//	@RequestMapping("/delete_disposable.do")
//	public @ResponseBody boolean deleteDisposable(String dispId) {
//		
//		return disposableService.deleteDisposable(dispId);
//	}
	
	@RequestMapping("/delete_disposable.do")
	public  String deleteDisposable(String dispId, HttpSession session) {
		String result = null;
		Disposable disposable = disposableService.selectDisposable(dispId);
		session.setAttribute("disposable", disposable);
		if(disposableService.deleteDisposable(dispId) == true) {
			session.setAttribute("compId", disposable.getCompId());
			result = "redirect:/corporate.do="+disposable.getCompId();
		}else {
			result = null;
		}
		return result;
	} 
	
}
