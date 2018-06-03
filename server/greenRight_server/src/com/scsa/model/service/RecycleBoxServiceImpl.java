package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.RecycleBoxDAO;
import com.scsa.model.vo.RecycleBox;

public class RecycleBoxServiceImpl implements RecycleBoxService {
	RecycleBoxDAO recycleBoxDao;
	
	
	public void setRecycleBoxDao(RecycleBoxDAO recycleBoxDao) {
		this.recycleBoxDao = recycleBoxDao;
	}

	@Override
	public List<RecycleBox> findRecycleBoxList() {
		return recycleBoxDao.selectRecycleBoxList();
	}

	@Override
	public List<RecycleBox> findRecycleBoxListWithCurrentLocation(float latitude, float longitude) {
		return null;
	}

	@Override
	public boolean addRecycleBox(RecycleBox recycleBox) {
		return recycleBoxDao.insertRecycleBox(recycleBox);
	}

}
