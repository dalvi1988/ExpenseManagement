package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="expense_details")
public class ExpenseDetailJPA {
	
	@Id @GeneratedValue
	@Column(name="expense_detail_id")
	private Long expenseDetailId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="expense_header_id")
	private ExpenseHeaderJPA expenseHeaderJPA;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="expense_category_id")
	private ExpenseCategoryJPA expenseCategoryJPA;
	
	@Column(name="date",nullable=false)
	private Calendar date;
	
	@Column(name="from_location")
	private String fromLocation;
	
	@Column(name="to_location")
	private String toLocation;
	
	@Column(name="description",nullable=false)
	private String description;

	@Column(name="unit")
	private Integer unit;

	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="file_name")
	private String fileName;
	
	/*@Transient
	private File receipt;
*/
	public Long getExpenseDetailId() {
		return expenseDetailId;
	}

	public void setExpenseDetailId(Long expenseDetailId) {
		this.expenseDetailId = expenseDetailId;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExpenseHeaderJPA getExpenseHeaderJPA() {
		return expenseHeaderJPA;
	}

	public void setExpenseHeaderJPA(ExpenseHeaderJPA expenseHeaderJPA) {
		this.expenseHeaderJPA = expenseHeaderJPA;
	}

	/*public File getReceipt() {
		return receipt;
	}

	public void setReceipt(File receipt) {
		this.receipt = receipt;
	}*/

	public ExpenseCategoryJPA getExpenseCategoryJPA() {
		return expenseCategoryJPA;
	}

	public void setExpenseCategoryJPA(ExpenseCategoryJPA expenseCategoryJPA) {
		this.expenseCategoryJPA = expenseCategoryJPA;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

}
