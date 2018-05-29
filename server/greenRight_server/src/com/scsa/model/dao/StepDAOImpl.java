package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Step;

public class StepDAOImpl implements StepDAO {

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Step> selectStepListWithUserId(String userId) {
		return sqlSession.selectList("step.selectStepListWithUserId", userId);
	}

}
