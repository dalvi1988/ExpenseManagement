package com.chaitanya.company.model;

import com.chaitanya.base.BaseDTO;

public class CompanyDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private Long companyId;
	private String companyCode;
	private String companyName;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
