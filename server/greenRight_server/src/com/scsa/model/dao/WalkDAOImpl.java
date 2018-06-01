package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Walk;



public class WalkDAOImpl implements WalkDAO {

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Walk> selectWalkListWithUserId(String userId) {
		return sqlSession.selectList("step.selectWalkListWithUserId", userId);
	}

}
