package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Step;

public interface StepDAO {
	
	List<Step> selectStepListWithUserId(String userId);
	
}
