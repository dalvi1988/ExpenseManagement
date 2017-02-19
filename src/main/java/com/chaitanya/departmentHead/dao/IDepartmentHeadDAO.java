package com.chaitanya.departmentHead.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.DepartmentHeadJPA;


public interface IDepartmentHeadDAO {

	public DepartmentHeadJPA add(DepartmentHeadJPA department);

	public List<DepartmentHeadJPA> findDepartmentHeadUnderCompany(
			CompanyDTO companyDTO);
	
}
