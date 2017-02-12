package com.chaitanya.employee.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;


public interface IEmployeeService {
	List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO);

	BaseDTO addEmployee(BaseDTO baseDTO);
}
