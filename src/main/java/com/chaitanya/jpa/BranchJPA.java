package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="branch_details")
public class BranchJPA {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branch_id")
	private Long branchId;
	
	@Column(name="branch_code",unique=true,nullable=false)
	private String branchCode;
	
	@Column(name="branch_name",unique=true,nullable=false)
	private String branchName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyJPA companyJPA;
	
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
	public CompanyJPA getCompanyJPA() {
		return companyJPA;
	}
	public void setCompanyJPA(CompanyJPA companyJPA) {
		this.companyJPA = companyJPA;
	}

}
