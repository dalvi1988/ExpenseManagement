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
@Table(name="functional_flow")
public class ApprovalFlowJPA {
	
	@Id @GeneratedValue
	@Column(name="flow_id")
	private Long flowId;
	
	@OneToOne
	@JoinColumn(name="branch_id", nullable=false)
	private BranchJPA branchJPA;
	
	@OneToOne
	@JoinColumn(name="department_id")
	private DepartmentJPA departmentJPA;
	
	@Column(name="no_of_level", nullable=false)
	private Integer noOfLevel;
	
	@Column(name="level1", nullable=false)
	private Long level1;
	
	@Column(name="level2", nullable=true)
	private Long level2;
	
	@Column(name="level3", nullable=true)
	private Long level3;
	
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

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
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

	public Integer getNoOfLevel() {
		return noOfLevel;
	}

	public void setNoOfLevel(Integer noOfLevel) {
		this.noOfLevel = noOfLevel;
	}

	public Long getLevel1() {
		return level1;
	}

	public void setLevel1(Long level1) {
		this.level1 = level1;
	}

	public Long getLevel2() {
		return level2;
	}

	public void setLevel2(Long level2) {
		this.level2 = level2;
	}

	public Long getLevel3() {
		return level3;
	}

	public void setLevel3(Long level3) {
		this.level3 = level3;
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
