package com.scsa.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Company;

public class CompanyDAOImpl implements CompanyDAO {

	SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}


	@Override
	public Company selectCompanyWithCompId(String compId) {
		return sqlSession.selectOne("company.selectCompanyWithCompId", compId);
	}

}
