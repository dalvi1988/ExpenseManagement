package com.chaitanya.department.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.DepartmentJPA;


public interface IDepartmentDAO {

	public DepartmentJPA add(DepartmentJPA department);
	
	public List<DepartmentJPA> findAllDepartmentUnderCompany(CompanyDTO companyDTO);

}
