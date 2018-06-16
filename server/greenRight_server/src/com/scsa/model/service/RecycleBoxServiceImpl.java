package com.scsa.model.service;

import java.util.List;
import java.util.Map;

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
	public List<RecycleBox> findRecycleBoxListWithCurrentLocation(RecycleBox recycleBox) {
		return recycleBoxDao.selectRecycleBoxListWithCurrentLocation(recycleBox);
	}

	@Override
	public boolean addRecycleBox(RecycleBox recycleBox) {
		System.out.println(recycleBox);
		return recycleBoxDao.insertRecycleBox(recycleBox);
	}

	@Override
	public boolean deleteRecycleBox(String recycleBoxId) {
		return recycleBoxDao.deleteRecycleBox(recycleBoxId);
	}

	@Override
	public boolean updateRecycleBox(RecycleBox recycleBox) {
		return recycleBoxDao.updateRecycleBox(recycleBox);
	}

/*	@Override
	public boolean deleteRecycleBox(String recycleBoxId) {
		return recycleBoxDao.deleteRecycleBox(recycleBoxId);
	}*/

	
	
}
