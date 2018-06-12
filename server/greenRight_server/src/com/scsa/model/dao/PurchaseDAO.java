package com.scsa.model.dao;

import java.util.List;
import java.util.Map;

import com.scsa.model.vo.Purchase;

public interface PurchaseDAO {
	void insertPurchase(Purchase purchase);
	List<Purchase> selectPurchaseListWithMultipleCondition(Map<String, String> id);
	Purchase selectPurchase(String purchaseId);
	
	void updatePurchase(Purchase purchase);
	void deletePurchase(String purchaseId);
	List<Purchase> searchPurchaseList(Purchase purchase);
}
