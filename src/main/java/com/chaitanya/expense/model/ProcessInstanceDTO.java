package com.chaitanya.expense.model;

import com.chaitanya.utility.model.VoucherStatusDTO;

public class ProcessInstanceDTO {

	private Long ProcessInstanceId;
	
	private VoucherStatusDTO voucherStatusDTO; 
	
	private Long pendingAt;

	public Long getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public VoucherStatusDTO getVoucherStatusDTO() {
		return voucherStatusDTO;
	}

	public void setVoucherStatusDTO(VoucherStatusDTO voucherStatusDTO) {
		this.voucherStatusDTO = voucherStatusDTO;
	}

	public Long getPendingAt() {
		return pendingAt;
	}

	public void setPendingAt(Long pendingAt) {
		this.pendingAt = pendingAt;
	}
	
}
