package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.Step;

public interface StepService {
	
	List<Step> findStepListWithUserId(String userId);

}
