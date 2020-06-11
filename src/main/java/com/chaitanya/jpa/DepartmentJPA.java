package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="department_details")
public class DepartmentJPA {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dept_id")
	private Long departmentId;
	
	@Column(name="dept_name",unique=true,nullable=false)
	private String deptName;
	
	@Column(name="dept_code",unique=true,nullable=false)
	private String deptCode;
	
	@OneToOne
	@JoinColumn(name="branch_id")
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
    Character status;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String departmentName) {
		this.deptName = departmentName;
	}

	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String departmentCode) {
		this.deptCode = departmentCode;
	}

	public BranchJPA getBranchJPA() {
		return branchJPA;
	}
	public void setBranchJPA(BranchJPA branchJPA) {
		this.branchJPA = branchJPA;
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
	
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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
}
