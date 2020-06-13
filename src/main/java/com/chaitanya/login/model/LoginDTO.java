package com.chaitanya.login.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;

public class LoginDTO extends BaseDTO{
	
	private static final long serialVersionUID = 1L;
	
	private String password;
	private EmployeeDTO employeeDTO;
	
	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}
	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
