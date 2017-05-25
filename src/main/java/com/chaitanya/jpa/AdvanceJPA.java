package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="advance_details")
public class AdvanceJPA  {
	
	@Id @GeneratedValue
	@Column(name="advance_detail_id")
	private Long advanceDetailId;
	
	@Column(name="purpose",unique=true,nullable=false)
	private String purpose;
	
	@Column(name="amount",unique=true,nullable=false)
	private Double amount;
	
	@Column(name="is_event",unique=true,nullable=false)
	private Character isEvent;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_status")
	private VoucherStatusJPA voucherStatusJPA;
	
	@Column(name="date")
	private Calendar date;
	
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
}
