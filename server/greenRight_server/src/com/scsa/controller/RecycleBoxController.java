package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("/add_box.do")
	public boolean addRecycleBox(RecycleBox recycleBox) {
		return recycleBoxService.addRecycleBox(recycleBox);
	}
}
