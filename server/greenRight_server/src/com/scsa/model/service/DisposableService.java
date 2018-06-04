package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.Disposable;

public interface DisposableService {
	boolean addDisposable(Disposable disposable);
//	List<Disposable> searchDisposableList();
//	List<Disposable> searchDisposableListWithCompId();
//	List<Disposable> selectDisposableList();
//	List<Disposable> selectDisposableListWithCompId();
	Disposable selectDisposable(String dispId);
	List<Disposable> selectDisposableList();
	boolean deleteDisposable(String dispId);
}
