package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="expense_header")
public class ExpenseHeaderJPA {
	
	@Id @GeneratedValue
	@Column(name="expense_header_id")
	private Long expenseHeaderId;
	
	@OneToMany(orphanRemoval=true,mappedBy="expenseHeaderJPA") 
	@Cascade({CascadeType.ALL})
	private List<ExpenseDetailJPA> expenseDetailJPA; 
	
	@Column(name="employee_id")
	private Long employeeId;
	
	@Column(name="start_date")
	private Calendar startDate;
		
	@Column(name="end_date")
	private Calendar endDate;
	
	@Column(name="title")
	private String title;
	
	@Column(name="purpose")
	private String purpose;

	public Long getExpenseHeaderId() {
		return expenseHeaderId;
	}

	public void setExpenseHeaderId(Long expenseHeaderId) {
		this.expenseHeaderId = expenseHeaderId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
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

	public List<ExpenseDetailJPA> getExpenseDetailJPA() {
		return expenseDetailJPA;
	}

	public void setExpenseDetailJPA(List<ExpenseDetailJPA> expenseDetailJPA) {
		this.expenseDetailJPA = expenseDetailJPA;
	}

}
