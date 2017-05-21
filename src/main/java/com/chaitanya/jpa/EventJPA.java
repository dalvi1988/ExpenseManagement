package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="event_details")
public class EventJPA {
	
	@Id @GeneratedValue
	@Column(name="event_id")
	private Long eventId;
	
	@Column(name="event_code",unique=true,nullable=false)
	private String eventCode;
	
	@Column(name="event_name",unique=true,nullable=false)
	private String eventName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "branch_id")
	private BranchJPA branchJPA;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="modified_by")
    private Long modifiedBy;
	
	@Column(name="created_date")
	private Calendar createdDate;
		
	@Column(name="modified_date")
	private Calendar modifiedDate;
	
	@Column(name="status",nullable=false)
	private Character status;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long branchId) {
		this.eventId = branchId;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String branchCode) {
		this.eventCode = branchCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String branchName) {
		this.eventName = branchName;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}

	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public BranchJPA getBranchJPA() {
		return branchJPA;
	}
	public void setBranchJPA(BranchJPA branchJPA) {
		this.branchJPA = branchJPA;
	}

}
