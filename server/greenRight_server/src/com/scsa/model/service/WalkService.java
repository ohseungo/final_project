package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.Walk;



public interface WalkService {
	
	List<Walk> findWalkListWithUserId(String userId);
	boolean addWalk(Walk walk);
	

}
