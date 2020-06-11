package com.chaitanya.event.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public class EventDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Long eventId;
	private String eventCode;
	private String eventName;
	private BranchDTO branchDTO;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
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
