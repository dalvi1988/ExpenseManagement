package com.chaitanya.jpa;

import java.io.File;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="expense_details")
public class ExpenseDetailJPA {
	
	@Id @GeneratedValue
	@Column(name="expense_detail_id")
	private Long expenseDetailId;
	
	@ManyToOne
	@JoinColumn(name="expense_header_id")
	private ExpenseHeaderJPA expenseHeaderJPA;
		
	@Column(name="expense_name_id")
	private Long expenseNameId;
	
	@Column(name="date")
	private Calendar date;
	
	@Column(name="from_location")
	private String fromLocation;
	
	@Column(name="to_location")
	private String toLocation;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="file_name")
	private String fileName;
	
	@Transient
	private File receipt;

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

	public File getReceipt() {
		return receipt;
	}

	public void setReceipt(File receipt) {
		this.receipt = receipt;
	}

}
