package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Disposable;

public class DisposableDAOImpl implements DisposableDAO {

	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean insertDisposable(Disposable disposable) {
		boolean result = false;
		if(sqlSession.insert("disposable.insertDisposable", disposable) > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public Disposable selectDisposable(String dispId) {
		return sqlSession.selectOne("disposable.selectDisposable", dispId);
	}

	@Override
	public List<Disposable> selectDisposableList(String compId) {
		return sqlSession.selectList("disposable.selectDisposableList", compId);
	}

	@Override
	public boolean deleteDisposable(String dispId) {
		boolean result = false;
		if(sqlSession.delete("disposable.deleteDisposable", dispId) > 0) {
			result = true;
		}
		return result;
	}

}
