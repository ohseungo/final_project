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

	@RequestMapping("/list_box.do")
	public @ResponseBody List<RecycleBox> findRecycleBoxList() {
		return recycleBoxService.findRecycleBoxList();
	}


	public List<RecycleBox> findRecycleBoxListWithCurrentLocation(float latitude, float longitude) {
		return null;
	}
}
