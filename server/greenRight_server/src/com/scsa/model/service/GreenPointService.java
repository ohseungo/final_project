package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.GreenPoint;

public interface GreenPointService {
	boolean addGreenPoint(GreenPoint point);
	List<GreenPoint> searchGreenPointListwithUserId(String userId);
	String searchGreenPointTotalwithUserId(String userId);
	List<GreenPoint> searchGreenPointStatuswithUserId(String userId);
}
