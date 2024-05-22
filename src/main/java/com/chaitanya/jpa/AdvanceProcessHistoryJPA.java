package com.chaitanya.jpa;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "advance_process_history")
public class AdvanceProcessHistoryJPA {

	@Id
	@GeneratedValue
	@Column(name = "process_history_id")
	private Long processHistoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "advance_detail_id")
	private AdvanceJPA advanceJPA;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_status_id")
	private VoucherStatusJPA voucherStatusJPA;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processed_by")
	private EmployeeJPA processedBy;

	@Column(name = "processed_date")
	private Calendar processDate;

	@Column(name = "comments")
	private String comment;

	public Long getProcessHistoryId() {
		return processHistoryId;
	}

	public void setProcessHistoryId(Long processHistoryId) {
		this.processHistoryId = processHistoryId;
	}

	public VoucherStatusJPA getVoucherStatusJPA() {
		return voucherStatusJPA;
	}

	public void setVoucherStatusJPA(VoucherStatusJPA voucherStatusJPA) {
		this.voucherStatusJPA = voucherStatusJPA;
	}

	public EmployeeJPA getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(EmployeeJPA processedBy) {
		this.processedBy = processedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public AdvanceJPA getAdvanceJPA() {
		return advanceJPA;
	}

	public void setAdvanceJPA(AdvanceJPA advanceJPA) {
		this.advanceJPA = advanceJPA;
	}

	public Calendar getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Calendar processDate) {
		this.processDate = processDate;
	}

}
