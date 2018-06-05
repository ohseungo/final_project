package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.DisposableDAO;
import com.scsa.model.vo.Disposable;

public class DisposableServiceImpl implements DisposableService {

	DisposableDAO disposableDao;
	
	public void setDisposableDao(DisposableDAO disposableDao) {
		this.disposableDao = disposableDao;
	}

	@Override
	public boolean addDisposable(Disposable disposable) {
		return disposableDao.insertDisposable(disposable);
	}

	@Override
	public Disposable selectDisposable(String dispId) {
		return disposableDao.selectDisposable(dispId);
	}

	@Override
	public List<Disposable> selectDisposableList() {
		return disposableDao.selectDisposableList();
	}

	@Override
	public boolean deleteDisposable(String dispId) {
		return disposableDao.deleteDisposable(dispId);
	}

}
