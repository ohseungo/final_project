package com.scsa.model.service;

import com.scsa.model.dao.CompanyDAO;
import com.scsa.model.vo.Company;

public class CompanyServiceImpl implements CompanyService {
	CompanyDAO companyDao;
	
	
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}


	@Override
	public Company findCompanyWithCompId(String compId) {
		return companyDao.selectCompanyWithCompId(compId);
	}

}
