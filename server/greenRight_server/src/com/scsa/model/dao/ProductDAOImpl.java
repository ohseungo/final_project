package com.scsa.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Product;
import com.scsa.model.vo.User;

public class ProductDAOImpl implements ProductDAO {
	
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}



	@Override
	public boolean insertProduct(Product product) {
		boolean result = false;
		if(sqlSession.insert("product.insertProduct", product) > 0) {
			result = true;
		}
		return result;
	}
	
	@Override
	public Product selectProduct(String productId) {
		return sqlSession.selectOne("product.selectProduct", productId);
	}
	
	@Override
	public boolean deleteProduct(String productId) {
		boolean result = false;
		if(sqlSession.delete("product.deleteProduct", productId) > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public List<Product> selectProductList(String compId) {
		return sqlSession.selectList("product.selectProductList", compId);
	}
	
}
