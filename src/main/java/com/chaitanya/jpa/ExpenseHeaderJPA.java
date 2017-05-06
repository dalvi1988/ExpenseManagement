package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="expense_header")
public class ExpenseHeaderJPA {
	
	@Id @GeneratedValue
	@Column(name="expense_header_id")
	private Long expenseHeaderId;
	
	@Column(name="voucher_number")
	private String voucherNumber;
	
	@OneToMany(mappedBy="expenseHeaderJPA",fetch=FetchType.EAGER) 
	@Cascade({CascadeType.ALL})
	@Fetch (FetchMode.SELECT)
	private List<ExpenseDetailJPA> expenseDetailJPA; 
	
	@OneToMany(mappedBy="expenseHeaderJPA",fetch=FetchType.EAGER) 
	@Cascade({CascadeType.ALL})
	@Fetch (FetchMode.SELECT)
	private List<ProcessHistoryJPA> processHistoryJPA;
	
	@OneToOne(mappedBy="expenseHeaderJPA") 
	@Cascade({CascadeType.ALL})
	@Fetch (FetchMode.SELECT)
	private ProcessInstanceJPA processInstanceJPA;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private EmployeeJPA employeeJPA;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_status")
	private VoucherStatusJPA voucherStatusJPA;
	
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

	public EmployeeJPA getEmployeeJPA() {
		return employeeJPA;
	}

	public void setEmployeeJPA(EmployeeJPA employeeJPA) {
		this.employeeJPA = employeeJPA;
	}

	public VoucherStatusJPA getVoucherStatusJPA() {
		return voucherStatusJPA;
	}

	public List<ProcessHistoryJPA> getProcessHistoryJPA() {
		return processHistoryJPA;
	}

	public void setProcessHistoryJPA(List<ProcessHistoryJPA> processHistoryJPA) {
		this.processHistoryJPA = processHistoryJPA;
	}

	public void setVoucherStatusJPA(VoucherStatusJPA voucherStatusJPA) {
		this.voucherStatusJPA = voucherStatusJPA;
	}

	public ProcessInstanceJPA getProcessInstanceJPA() {
		return processInstanceJPA;
	}

	public void setProcessInstanceJPA(ProcessInstanceJPA processInstanceJPA) {
		this.processInstanceJPA = processInstanceJPA;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

}
