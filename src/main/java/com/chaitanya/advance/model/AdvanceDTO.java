package com.chaitanya.advance.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.utility.model.VoucherStatusDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdvanceDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Long advanceDetailId;
	private String purpose;
	private Double amount;
	private Boolean isEvent;
	private Integer eventId;
	private EventDTO eventDTO;
	
	private Integer voucherStatusId;
	@JsonIgnore
	private VoucherStatusDTO voucherStatusDTO;
	

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

	public void setEventDTO(EventDTO eventDTO) {
		this.eventDTO = eventDTO;
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
	
}
