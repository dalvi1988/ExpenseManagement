package com.chaitanya.login.model;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;

public class LoginDTO extends BaseDTO{
	
	private static final long serialVersionUID = 1L;
	private EmployeeDTO employeeDTO;
	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}
	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
}
