package com.chaitanya.dashboard.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;

public class DashboardDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	private String label;
	
	private Double amount;
	
	private String data;
	
	private EmployeeDTO employeeDTO;

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
