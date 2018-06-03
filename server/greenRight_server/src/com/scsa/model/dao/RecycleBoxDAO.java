package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.RecycleBox;

public interface RecycleBoxDAO {
	List<RecycleBox> selectRecycleBoxList();
	List<RecycleBox> selectRecycleBoxListWithCurrentLocation(float latitude, float longitude);
	boolean insertRecycleBox(RecycleBox recycleBox);

}
