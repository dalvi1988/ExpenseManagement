package com.chaitanya.advance.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.utility.model.VoucherStatusDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class AdvanceDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Long advanceDetailId;
	private String advanceNumber;
	private String purpose;
	private Double amount;
	private Boolean isEvent;
	private Integer eventId;
	private String rejectionComment;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="eventName")
	@JsonIdentityReference(alwaysAsId=true)
	private EventDTO eventDTO;
	
	private Integer voucherStatusId;
	@JsonIgnore
	private VoucherStatusDTO voucherStatusDTO;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO employeeDTO;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO pendingAtEmployeeDTO;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO approvedByEmployeeDTO;

	public Long getAdvanceDetailId() {
		return advanceDetailId;
	}

	public Boolean getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(Boolean isEvent) {
		this.isEvent = isEvent;
	}

	public void setAdvanceDetailId(Long advanceDetailId) {
		this.advanceDetailId = advanceDetailId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
		EventDTO eventDTO=new EventDTO();
		eventDTO.setEventId(eventId);
		this.setEventDTO(eventDTO);
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}
	@JsonIgnore
	public void setEventDTO(EventDTO eventDTO) {
		this.eventDTO = eventDTO;
		this.eventId=eventDTO.getEventId();
	}

	public Integer getVoucherStatusId() {
		return voucherStatusId;
	}

	public void setVoucherStatusId(Integer voucherStatusId) {
		this.voucherStatusId = voucherStatusId;
		VoucherStatusDTO voucherStatusDTO= new VoucherStatusDTO();
		voucherStatusDTO.setVoucherStatusId(voucherStatusId);
		this.setVoucherStatusDTO(voucherStatusDTO);
	}

	public VoucherStatusDTO getVoucherStatusDTO() {
		return voucherStatusDTO;
	}

	@JsonIgnore
	public void setVoucherStatusDTO(VoucherStatusDTO voucherStatusDTO) {
		this.voucherStatusDTO = voucherStatusDTO;
		this.voucherStatusId=voucherStatusDTO.getVoucherStatusId();
	}

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}
	@JsonIgnore
	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}

	public String getAdvanceNumber() {
		return advanceNumber;
	}

	public void setAdvanceNumber(String advanceNumber) {
		this.advanceNumber = advanceNumber;
	}

	public EmployeeDTO getPendingAtEmployeeDTO() {
		return pendingAtEmployeeDTO;
	}
	@JsonIgnore
	public void setPendingAtEmployeeDTO(EmployeeDTO pendingAtEmployeeDTO) {
		this.pendingAtEmployeeDTO = pendingAtEmployeeDTO;
	}

	public EmployeeDTO getProcessedByEmployeeDTO() {
		return approvedByEmployeeDTO;
	}
	@JsonIgnore
	public void setProcessedByEmployeeDTO(EmployeeDTO processedByEmployeeDTO) {
		this.approvedByEmployeeDTO = processedByEmployeeDTO;
	}

	public String getRejectionComment() {
		return rejectionComment;
	}

	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}
	
}
