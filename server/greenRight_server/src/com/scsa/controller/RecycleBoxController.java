package com.scsa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.RecycleBoxService;
import com.scsa.model.vo.RecycleBox;

@Controller
public class RecycleBoxController {
	RecycleBoxService recycleBoxService;
	
	@Autowired
	public void setRecycleBoxService(RecycleBoxService recycleBoxService) {
		this.recycleBoxService = recycleBoxService;
	}

	//@RequestMapping("/list_box.do")
	public @ResponseBody List<RecycleBox> findRecycleBoxList() {
		return recycleBoxService.findRecycleBoxList();
	}

	@RequestMapping("/list_box.do")
	public @ResponseBody List<RecycleBox> findRecycleBoxListWithCurrentLocation(RecycleBox recycleBox) {
		return recycleBoxService.findRecycleBoxListWithCurrentLocation(recycleBox);
	}
	
	@RequestMapping("/add_recycle.do")
	public String addRecycleBox(Model model, RecycleBox recycleBox) {
		recycleBoxService.addRecycleBox(recycleBox);
		model.addAttribute("recycleBoxList", recycleBoxService.findRecycleBoxList());
		return "/recycle.jsp";
	}
	
	@RequestMapping("/recycle.do")
	public String selectProductList(Model model) {
		model.addAttribute("recycleBoxList", recycleBoxService.findRecycleBoxList());
		return "/recycle.jsp";
	}
	
	@RequestMapping("/delete_recycle.do")
	public String deleteRecycleBox(Model model, String recycleBoxId) {
		recycleBoxService.deleteRecycleBox(recycleBoxId);
		model.addAttribute("recycleBoxList", recycleBoxService.findRecycleBoxList());
		return "/recycle.jsp";
	}
}
