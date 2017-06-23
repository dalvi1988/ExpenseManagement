package com.chaitanya.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="process_instance")
public class ProcessInstanceJPA {

	@Id @GeneratedValue
	@Column(name="process_instance_id")
	private Long processInstanceId;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="expense_header_id")
	private ExpenseHeaderJPA expenseHeaderJPA;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="voucher_status_id")
	private VoucherStatusJPA voucherStatusJPA;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pending_at")
	private EmployeeJPA pendingAt;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="processed_by")
	private EmployeeJPA processedBy;
	
	@Column(name="comments")
	private String comment;

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public ExpenseHeaderJPA getExpenseHeaderJPA() {
		return expenseHeaderJPA;
	}

	public void setExpenseHeaderJPA(ExpenseHeaderJPA expenseHeaderJPA) {
		this.expenseHeaderJPA = expenseHeaderJPA;
	}

	public VoucherStatusJPA getVoucherStatusJPA() {
		return voucherStatusJPA;
	}

	public void setVoucherStatusJPA(VoucherStatusJPA voucherStatusJPA) {
		this.voucherStatusJPA = voucherStatusJPA;
	}

	public EmployeeJPA getPendingAt() {
		return pendingAt;
	}

	public void setPendingAt(EmployeeJPA pendingAt) {
		this.pendingAt = pendingAt;
	}

	public EmployeeJPA getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(EmployeeJPA processedBy) {
		this.processedBy = processedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
