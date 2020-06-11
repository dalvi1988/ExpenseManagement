package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="payment_details")
public class PaymentJPA {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_detail_id")
	private Long paymentDetailId;
	
	@Column(name="module_name")
	private String moduleName;
	
	@Column(name="voucher_id")
	private Long voucherId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paid_by")
	private EmployeeJPA paidByEmployeeJPA;
	
	@Column(name="date")
	private Calendar date;
	
	@Column(name="amount")
	private Double amount;

	public Long getPaymentDetailId() {
		return paymentDetailId;
	}

	public void setPaymentDetailId(Long paymentDetailId) {
		this.paymentDetailId = paymentDetailId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public EmployeeJPA getPaidByEmployeeJPA() {
		return paidByEmployeeJPA;
	}

	public void setPaidByEmployeeJPA(EmployeeJPA paidByEmployeeJPA) {
		this.paidByEmployeeJPA = paidByEmployeeJPA;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}
		
}
