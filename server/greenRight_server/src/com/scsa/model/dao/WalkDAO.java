package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Walk;

public interface WalkDAO {
	
	List<Walk> selectWalkListWithUserId(String userId);
	boolean insertWalk(Walk walk);
	
}
