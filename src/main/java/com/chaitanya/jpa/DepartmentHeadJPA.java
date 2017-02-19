package com.chaitanya.jpa;

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
	
	
}
