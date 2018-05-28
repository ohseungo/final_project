package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.Store;

public interface StoreService {
	List<Store> findStoreList();
	List<Store> findStoreListWithCurrentLocation(float latitude, float longitude);
}
