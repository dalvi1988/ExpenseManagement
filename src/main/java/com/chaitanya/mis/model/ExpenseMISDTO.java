package com.chaitanya.mis.model;

import com.chaitanya.expense.model.ExpenseHeaderDTO;

public class ExpenseMISDTO extends ExpenseHeaderDTO {

	private static final long serialVersionUID = 1L;
	
	private String branchName;
	private String departmentName;
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	

}
