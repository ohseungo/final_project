package com.scsa.model.service;

import java.util.List;
import java.util.Map;

import com.scsa.model.dao.PurchaseDAO;
import com.scsa.model.vo.Purchase;

public class PurchaseServiceImpl implements PurchaseService {
	PurchaseDAO purchaseDao;
	
	
	public void setPurchaseDao(PurchaseDAO purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	@Override
	public void addPurchase(Purchase purchase) {
	}

	@Override
	public List<Purchase> searchPurchaseListWithMultipleCondition(Map<String, String> id) {
		
		return purchaseDao.selectPurchaseListWithMultipleCondition(id);
	}

	@Override
	public Purchase searchPurchase(String purchaseId) {
		return null;
	}

	@Override
	public void changePurchase(Purchase purchase) {
	}

	@Override
	public void removePurchase(String purchaseId) {
	}

	@Override
	public List<Purchase> searchPurchaseList(Purchase purchase) {
		return purchaseDao.searchPurchaseList(purchase);
	}
	
}
