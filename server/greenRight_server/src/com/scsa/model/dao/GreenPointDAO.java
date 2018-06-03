package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.GreenPoint;

public interface GreenPointDAO {
	
	boolean insertGreenPoint(GreenPoint point);
	List<GreenPoint> selectGreenPointListwithUserId(String userId);
	
	
	int selectGreenPointTotalwithUserId(String userId);
	List<GreenPoint> selectGreenPointStatuswithUserId(String userId);

	
}
