package com.scsa.model.dao;

import com.scsa.model.vo.Company;

public interface CompanyDAO {
	Company selectCompanyWithCompId(String compId);
	
}
