package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Store;

public interface StoreDAO {
	
	List<Store> selectStoreList();
	List<Store> selectStoreListWithCurrentLocation(float latitude, float longitude);
	
}
