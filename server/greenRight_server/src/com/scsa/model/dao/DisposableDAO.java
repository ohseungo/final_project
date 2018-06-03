package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Disposable;

public interface DisposableDAO {
	boolean insertDisposable(Disposable disposable);
	List<Disposable> selectDisposableList();
	List<Disposable> selectDisposableListWithCompId();
}
