package com.scsa.model.service;

import com.scsa.model.vo.Company;

public interface CompanyService {
	Company findCompanyWithCompId(String compId);
	
}
