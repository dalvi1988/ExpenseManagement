package com.chaitanya.expense.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExpenseHeaderDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	private Long expenseHeaderId;
	
	@NotNull
	@Size(min=8)
	private String startDate;
	
	@NotNull
	@Size(min=8)
	private String endDate;
	
	@NotNull
	@Size(min=5)
	private String title;
	
	private String purpose;
	
	@JsonIgnore
	private EmployeeDTO employeeDTO;
	
	private List<ExpenseDetailDTO> addedExpenseDetailsDTOList;
	
	private List<ExpenseDetailDTO> updatedExpenseDetailsDTOList;

	private List<ExpenseDetailDTO> deletedExpenseDetailsDTOList;

	public Long getExpenseHeaderId() {
		return expenseHeaderId;
	}

	public void setExpenseHeaderId(Long expenseHeaderId) {
		this.expenseHeaderId = expenseHeaderId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public List<ExpenseDetailDTO> getAddedExpenseDetailsDTOList() {
		return addedExpenseDetailsDTOList;
	}

	public void setAddedExpenseDetailsDTOList(List<ExpenseDetailDTO> addedExpenseDetailsDTOList) {
		this.addedExpenseDetailsDTOList = addedExpenseDetailsDTOList;
	}

	public List<ExpenseDetailDTO> getUpdatedExpenseDetailsDTOList() {
		return updatedExpenseDetailsDTOList;
	}

	public void setUpdatedExpenseDetailsDTOList(List<ExpenseDetailDTO> updatedExpenseDetailsDTOList) {
		this.updatedExpenseDetailsDTOList = updatedExpenseDetailsDTOList;
	}

	public List<ExpenseDetailDTO> getDeletedExpenseDetailsDTOList() {
		return deletedExpenseDetailsDTOList;
	}

	public void setDeletedExpenseDetailsDTOList(List<ExpenseDetailDTO> deletedExpenseDetailsDTOList) {
		this.deletedExpenseDetailsDTOList = deletedExpenseDetailsDTOList;
	}

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
	
}
