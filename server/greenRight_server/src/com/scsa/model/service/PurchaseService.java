package com.scsa.model.service;

import java.util.List;
import java.util.Map;

import com.scsa.model.vo.Purchase;

public interface PurchaseService {
	boolean addPurchase(Purchase purchase);
	List<Purchase> searchPurchaseListWithMultipleCondition(Map<String, String> id);
	Purchase searchPurchase(String purchaseId);
	void changePurchase(Purchase purchase);
	void removePurchase(String purchaseId);
	List<Purchase> searchPurchaseList(Purchase purchase);
}
