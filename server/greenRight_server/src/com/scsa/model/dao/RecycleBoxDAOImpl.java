package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.RecycleBox;

public class RecycleBoxDAOImpl implements RecycleBoxDAO {
	SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	

	@Override
	public List<RecycleBox> selectRecycleBoxList() {
		return sqlSession.selectList("recycleBox.selectRecycleBox");
	}

	@Override
	public List<RecycleBox> selectRecycleBoxListWithCurrentLocation(float latitude, float longitude) {
		return null;
	}

}
