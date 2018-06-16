package com.scsa.model.service;

import java.util.List;
import java.util.Map;

import com.scsa.model.vo.RecycleBox;

public interface RecycleBoxService {
	List<RecycleBox> findRecycleBoxList();
	List<RecycleBox> findRecycleBoxListWithCurrentLocation(RecycleBox recycleBox);
	boolean addRecycleBox(RecycleBox recycleBox);
	boolean deleteRecycleBox(String recycleBoxId);
	boolean updateRecycleBox(RecycleBox recycleBox);
}
