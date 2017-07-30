package com.chaitanya.payment.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;

public class PaymentDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private Long paymentDetailId;
	private String moduleName;
	private String voucherId;
	private EmployeeDTO paidByEmployeeDTO;
	private String date;
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
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public EmployeeDTO getPaidByEmployeeDTO() {
		return paidByEmployeeDTO;
	}
	public void setPaidByEmployeeDTO(EmployeeDTO paidByEmployeeDTO) {
		this.paidByEmployeeDTO = paidByEmployeeDTO;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
