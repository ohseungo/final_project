package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.StepDAO;
import com.scsa.model.vo.Step;

public class StepServiceImpl implements StepService {
	StepDAO stepDao;
	
	public void setStepDao(StepDAO stepDao) {
		this.stepDao = stepDao;
	}
	

	@Override
	public List<Step> findStepListWithUserId(String userId) {
		return stepDao.selectStepListWithUserId(userId);
	}

}
