package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Disposable;
import com.scsa.model.vo.Product;

public interface DisposableDAO {
	boolean insertDisposable(Disposable disposable);
//	List<Disposable> selectDisposableList();
//	List<Disposable> selectDisposableListWithCompId();
	Disposable selectDisposable(String dispId);
	List<Disposable> selectDisposableList();
	boolean deleteDisposable(String dispId);
}
