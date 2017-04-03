package com.chaitanya.expense.model;

import org.springframework.web.multipart.MultipartFile;

import com.chaitanya.Base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDetailDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Long expenseDetailId;
	private Long expenseNameId;
	private String date;
	private String fromLocation;
	private String toLocation;
	private String description;
	private Double amount;
	@JsonIgnore
	private MultipartFile receipt;

	
	public Long getExpenseDetailId() {
		return expenseDetailId;
	}
	public void setExpenseDetailId(Long expenseDetailId) {
		this.expenseDetailId = expenseDetailId;
	}
	public Long getExpenseNameId() {
		return expenseNameId;
	}
	public void setExpenseNameId(Long expenseNameId) {
		this.expenseNameId = expenseNameId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public MultipartFile getReceipt() {
		return receipt;
	}
	public void setReceipt(MultipartFile receipt) {
		this.receipt = receipt;
	}
	
}
