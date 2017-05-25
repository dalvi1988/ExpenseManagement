package com.chaitanya.event.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public class EventDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String eventCode;
	private String eventName;
	private BranchDTO branchDTO;
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer branchId) {
		this.eventId = branchId;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public BranchDTO getBranchDTO() {
		return branchDTO;
	}
	public void setBranchDTO(BranchDTO branchDTO) {
		this.branchDTO = branchDTO;
	}
	
}
