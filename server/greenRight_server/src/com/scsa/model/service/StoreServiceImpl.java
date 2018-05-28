package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.StoreDAO;
import com.scsa.model.vo.Store;

public class StoreServiceImpl implements StoreService {
	StoreDAO storeDao;
	
	
	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	@Override
	public List<Store> findStoreList() {
		return storeDao.selectStoreList();
	}

	@Override
	public List<Store> findStoreListWithCurrentLocation(float latitude, float longitude) {
		return null;
	}

}
