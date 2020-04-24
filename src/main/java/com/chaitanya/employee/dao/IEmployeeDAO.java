package com.chaitanya.employee.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.EmployeeJPA;

public interface IEmployeeDAO {

	EmployeeJPA add(EmployeeJPA employee);

	List<EmployeeJPA> findAllUnderCompany(CompanyDTO companyDTO);

	List<EmployeeJPA> findEmployeeOnUnderDeptBranch(EmployeeDTO employeeDTO);

	EmployeeJPA findEmployeeByEmailId(EmployeeDTO employeeDTO);

	EmployeeJPA getEmployeeByEmployeeID(EmployeeJPA employeeJPA);

	
}
