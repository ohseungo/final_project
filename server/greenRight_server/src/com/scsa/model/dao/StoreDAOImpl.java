package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Store;

public class StoreDAOImpl implements StoreDAO {
	SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Store> selectStoreList() {
		return sqlSession.selectList("store.selectStoreList");
	}

	@Override
	public List<Store> selectStoreListWithCurrentLocation(float latitude, float longitude) {
		return null;
	}

}
