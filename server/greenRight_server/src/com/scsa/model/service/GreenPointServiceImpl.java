package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.GreenPointDAO;
import com.scsa.model.vo.GreenPoint;

public class GreenPointServiceImpl implements GreenPointService {
	GreenPointDAO greenPointDao;
	
	public void setGreenPointDao(GreenPointDAO greenPointDao) {
		this.greenPointDao = greenPointDao;
	}

	@Override
	public boolean addGreenPoint(GreenPoint point) {
		return greenPointDao.insertGreenPoint(point);
	}

	@Override
	public List<GreenPoint> searchGreenPointListwithUserId(String userId) {
		return greenPointDao.selectGreenPointListwithUserId(userId);
	}

	@Override
	public String searchGreenPointTotalwithUserId(String userId) {
		
		return greenPointDao.selectGreenPointTotalwithUserId(userId);
	}

	@Override
	public List<GreenPoint> searchGreenPointStatuswithUserId(String userId) {
		return greenPointDao.selectGreenPointStatuswithUserId(userId);
	}

}
