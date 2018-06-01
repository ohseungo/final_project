package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.WalkDAO;
import com.scsa.model.vo.Walk;


public class WalkServiceImpl implements WalkService {
	WalkDAO walkDao;
	
	public void setStepDao(WalkDAO walkDao) {
		this.walkDao = walkDao;
	}
	

	@Override
	public List<Walk> findWalkListWithUserId(String userId) {
		return walkDao.selectWalkListWithUserId(userId);
	}

}
