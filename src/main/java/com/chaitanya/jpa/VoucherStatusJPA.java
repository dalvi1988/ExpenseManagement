package com.chaitanya.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "voucher_status")
public class VoucherStatusJPA {

	@Id
	@Column(name = "voucher_status_id")
	private Integer voucherStatusId;

	@Column(name = "voucher_status", unique = true, nullable = false)
	private String voucherStatus;

	@Column(name = "text_to_display", nullable = false)
	private String textToDisplay;

	public Integer getVoucherStatusId() {
		return voucherStatusId;
	}

	public void setVoucherStatusId(Integer voucherStatusId) {
		this.voucherStatusId = voucherStatusId;
	}

	public String getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public String getTextToDisplay() {
		return textToDisplay;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

}
