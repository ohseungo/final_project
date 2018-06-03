package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.RecycleBox;

public interface RecycleBoxService {
	List<RecycleBox> findRecycleBoxList();
	List<RecycleBox> findRecycleBoxListWithCurrentLocation(float latitude, float longitude);
	boolean addRecycleBox(RecycleBox recycleBox);
}
