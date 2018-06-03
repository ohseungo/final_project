package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.GreenPoint;

public class GreenPointDAOImpl implements GreenPointDAO {
	SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean insertGreenPoint(GreenPoint point) {
		return sqlSession.insert("greenPoint.insertGreenPoint", point) !=0;
	}

	@Override
	public List<GreenPoint> selectGreenPointListwithUserId(String userId) {
		return sqlSession.selectList("greenPoint.selectGreenPointListwithUserId", userId);
	}

	@Override
	public int selectGreenPointTotalwithUserId(String userId) {
		return sqlSession.selectOne("greenPoint.selectGreenPointTotalwithUserId", userId);
	}

	@Override
	public List<GreenPoint> selectGreenPointStatuswithUserId(String userId) {
		return sqlSession.selectList("greenPoint.selectGreenPointStatuswithUserId", userId);
	}

}
