package com.chaitanya.expense.model;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.utility.model.VoucherStatusDTO;

public class ProcessHistoryDTO {

	private Long processHistoryId;

	private VoucherStatusDTO voucherStatusDTO;
	
	private EmployeeDTO processedBy;
	
	private String processDate;
	
	private String comment;

	public Long getProcessHistoryId() {
		return processHistoryId;
	}

	public void setProcessHistoryId(Long processHistoryId) {
		this.processHistoryId = processHistoryId;
	}

	public VoucherStatusDTO getVoucherStatusDTO() {
		return voucherStatusDTO;
	}

	public void setVoucherStatusDTO(VoucherStatusDTO voucherStatusDTO) {
		this.voucherStatusDTO = voucherStatusDTO;
	}

	public EmployeeDTO getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(EmployeeDTO processedBy) {
		this.processedBy = processedBy;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
