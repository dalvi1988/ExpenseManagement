package com.chaitanya.branch.model;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.company.model.CompanyDTO;

public class BranchDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Long branchId;
	private String branchCode;
	private String branchName;
	private CompanyDTO companyDTO;
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}
	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}
}
