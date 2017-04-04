package com.chaitanya.employee.service;

import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;


public interface IEmployeeService {
	List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO);
	
	List<EmployeeDTO> findEmployeeOnUnderDeptBranch(BaseDTO baseDTO);

	BaseDTO addEmployee(BaseDTO baseDTO);
}
