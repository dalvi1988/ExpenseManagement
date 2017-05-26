package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="advance_details")
public class AdvanceJPA  {
	
	@Id @GeneratedValue
	@Column(name="advance_detail_id")
	private Long advanceDetailId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private EmployeeJPA employeeJPA;
	
	@Column(name="advance_number",unique=true,nullable=false)
	private String advanceNumber;
	
	@Column(name="purpose",unique=true,nullable=false)
	private String purpose;
	
	@Column(name="amount",unique=true,nullable=false)
	private Double amount;
	
	@Column(name="is_event",unique=true,nullable=false)
	private Character isEvent;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_status_id")
	private VoucherStatusJPA voucherStatusJPA;
	
	@OneToMany(mappedBy="advanceJPA",fetch=FetchType.EAGER) 
	@Cascade({CascadeType.ALL})
	@Fetch (FetchMode.SELECT)
	private List<AdvanceProcessHistoryJPA> processHistoryJPA;
	
	@OneToOne(mappedBy="advanceJPA") 
	@Cascade({CascadeType.ALL})
	@Fetch (FetchMode.SELECT)
	private AdvanceProcessInstanceJPA processInstanceJPA;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private EventJPA eventJPA;
	
	@Column(name="date")
	private Calendar date;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="modified_by")
    private Long modifiedBy;
	
	@Column(name="created_date")
	private Calendar createdDate;
		
	@Column(name="modified_date")
	private Calendar modifiedDate;
	
	public VoucherStatusJPA getVoucherStatusJPA() {
		return voucherStatusJPA;
	}

	public void setVoucherStatusJPA(VoucherStatusJPA voucherStatusJPA) {
		this.voucherStatusJPA = voucherStatusJPA;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getAdvanceDetailId() {
		return advanceDetailId;
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

	public Character getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(Character isEvent) {
		this.isEvent = isEvent;
	}
	

	public EventJPA getEventJPA() {
		return eventJPA;
	}

	public void setEventJPA(EventJPA eventJPA) {
		this.eventJPA = eventJPA;
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
	
	public Calendar getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<AdvanceProcessHistoryJPA> getProcessHistoryJPA() {
		return processHistoryJPA;
	}

	public void setProcessHistoryJPA(List<AdvanceProcessHistoryJPA> processHistoryJPA) {
		this.processHistoryJPA = processHistoryJPA;
	}

	public AdvanceProcessInstanceJPA getProcessInstanceJPA() {
		return processInstanceJPA;
	}

	public void setProcessInstanceJPA(AdvanceProcessInstanceJPA processInstanceJPA) {
		this.processInstanceJPA = processInstanceJPA;
	}

	public EmployeeJPA getEmployeeJPA() {
		return employeeJPA;
	}

	public void setEmployeeJPA(EmployeeJPA employeeJPA) {
		this.employeeJPA = employeeJPA;
	}

	public String getAdvanceNumber() {
		return advanceNumber;
	}

	public void setAdvanceNumber(String advanceNumber) {
		this.advanceNumber = advanceNumber;
	}
}
