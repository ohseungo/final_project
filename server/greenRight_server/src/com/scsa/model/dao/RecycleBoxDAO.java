package com.scsa.model.dao;

import java.util.List;
import java.util.Map;

import com.scsa.model.vo.RecycleBox;

public interface RecycleBoxDAO {
	List<RecycleBox> selectRecycleBoxList();
	List<RecycleBox> selectRecycleBoxListWithCurrentLocation(RecycleBox recycleBox);
	boolean insertRecycleBox(RecycleBox recycleBox);
	boolean deleteRecycleBox(String recycleBoxId);
	boolean updateRecycleBox(RecycleBox recycleBox);
}
