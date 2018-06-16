package com.scsa.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.scsa.model.vo.RecycleBox;

public class RecycleBoxDAOImpl implements RecycleBoxDAO {
	SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<RecycleBox> selectRecycleBoxList() {
		return sqlSession.selectList("recycleBox.selectRecycleBox");
	}

	@Override
	public List<RecycleBox> selectRecycleBoxListWithCurrentLocation(RecycleBox recycleBox) {
		return sqlSession.selectList("recycleBox.selectRecycleBoxWithCurrLocation");
	}

	@Override
	public boolean insertRecycleBox(RecycleBox recycleBox) {
		System.out.println(recycleBox);
		return sqlSession.insert("recycleBox.insertRecycleBox", recycleBox)!=0;
	}

	/*@Override
	public boolean deleteRecycleBox(String recycleBoxId) {
		boolean result = false;
		if(sqlSession.delete("recycleBox.deleteRecycleBox", recycleBoxId)>0) {
			result = true;
		}
		return result;
	}*/

	@Override
	public boolean deleteRecycleBox(String recycleBoxId) {
		boolean result = false;
		if(sqlSession.delete("recycleBox.deleteRecycleBox", recycleBoxId) > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean updateRecycleBox(RecycleBox recycleBox) {
		boolean result = false;
		if(sqlSession.update("recycleBox.updateRecycleBox", recycleBox) > 0) {
			result = true;
		}
		return result;
	}
}
