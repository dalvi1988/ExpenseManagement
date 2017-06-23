package com.chaitanya.company.model;

import com.chaitanya.base.BaseDTO;

public class CompanyDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private String companyCode;
	private String companyName;
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
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
