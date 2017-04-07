package com.chaitanya.expenseCategory.model;

import com.chaitanya.base.BaseDTO;

public class ExpenseCategoryDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private Long expenseCategoryId;
	
	private String expenseName;
		
	private String glCode;
	
	private Boolean locationRequired;
	
    private Boolean unitRequired;
	
	private Double amount;
	
	private Boolean status;

	public Long getExpenseCategoryId() {
		return expenseCategoryId;
	}

	public void setExpenseCategoryId(Long expenseCategoryId) {
		this.expenseCategoryId = expenseCategoryId;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public Boolean getLocationRequired() {
		return locationRequired;
	}

	public void setLocationRequired(Boolean locationRequired) {
		this.locationRequired = locationRequired;
	}

	public Boolean getUnitRequired() {
		return unitRequired;
	}

	public void setUnitRequired(Boolean unitRequired) {
		this.unitRequired = unitRequired;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
