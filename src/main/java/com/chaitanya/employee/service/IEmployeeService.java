package com.chaitanya.employee.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;


public interface IEmployeeService {
	List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO) throws ParseException;
	
	List<EmployeeDTO> findEmployeeOnUnderDeptBranch(BaseDTO baseDTO) throws ParseException;

	BaseDTO addEmployee(BaseDTO baseDTO) throws ParseException, Exception;

}
