package com.chaitanya.utility.model;

public class VoucherStatusDTO {
	
	private Integer voucherStatusId;
	
	private String voucherStatus;
	
	private String textToDisplay;
	
	public String getTextToDisplay() {
		return textToDisplay;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

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

}
