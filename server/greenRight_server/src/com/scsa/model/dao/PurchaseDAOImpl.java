package com.scsa.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.Purchase;

public class PurchaseDAOImpl implements PurchaseDAO {
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public boolean insertPurchase(Purchase purchase) {
		boolean result = false;
		if(sqlSession.insert("purchase.insertPurchase", purchase) > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public List<Purchase> selectPurchaseListWithMultipleCondition(Map<String, String> id) {
		return sqlSession.selectList("purchase.selectPurchaseListWithMultipleCondition",id);
	}

	@Override
	public Purchase selectPurchase(String purchaseId) {
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) {
	}

	@Override
	public void deletePurchase(String purchaseId) {
	}
	
	@Override
	public List<Purchase> searchPurchaseList(Purchase purchase) {
		return sqlSession.selectList("purchase.searchPurchaseList", purchase);
	}
}
