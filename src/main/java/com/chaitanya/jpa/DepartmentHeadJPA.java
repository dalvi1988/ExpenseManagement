package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="department_head")
public class DepartmentHeadJPA {
	
	@Id @GeneratedValue
	@Column(name="department_head_id")
	private Long deptHeadId;
	
	@OneToOne
	@JoinColumn(name="branch_id",unique=true,nullable=false)
	private BranchJPA branchJPA;
	
	@OneToOne
	@JoinColumn(name="department_id",unique=true,nullable=false)
	private DepartmentJPA departmentJPA;
	
	@OneToOne
	@JoinColumn(name="employee_id",unique=true,nullable=false)
	private EmployeeJPA employeeJPA;
	
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

	public Long getDeptHeadId() {
		return deptHeadId;
	}

	public void setDeptHeadId(Long deptHeadId) {
		this.deptHeadId = deptHeadId;
	}

	public BranchJPA getBranchJPA() {
		return branchJPA;
	}

	public void setBranchJPA(BranchJPA branchJPA) {
		this.branchJPA = branchJPA;
	}

	public DepartmentJPA getDepartmentJPA() {
		return departmentJPA;
	}

	public void setDepartmentJPA(DepartmentJPA departmentJPA) {
		this.departmentJPA = departmentJPA;
	}

	public EmployeeJPA getEmployeeJPA() {
		return employeeJPA;
	}

	public void setEmployeeJPA(EmployeeJPA employeeJPA) {
		this.employeeJPA = employeeJPA;
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

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
	
	
}
