package com.scsa.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.User;

public class UserDAOImpl implements UserDAO {

	private SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public boolean insertUser(User user) {
		return sqlSession.insert("user.insertUser", user) == 1;
	}

	@Override
	public User selectUser(String userId) {
		return sqlSession.selectOne("user.selectUser", userId);
	}

	@Override
	public User selectUser(String userId, String password) {
		return null;
	}

}
