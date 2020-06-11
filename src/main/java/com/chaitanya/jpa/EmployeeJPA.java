package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="employee_details")
public class EmployeeJPA {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Long employeeId;
	
	@Column(name="first_name",nullable=false)
	private String firstName;
	
	@Column(name="middle_name",nullable=true)
	private String middleName;
	
	@Column(name="last_name",nullable=false)
	private String lastName;
	
	@Column(name="email_id",nullable=false)
	private String emailId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reporting_mgr")
	private EmployeeJPA reportingMgr;
	
	@OneToMany(mappedBy="reportingMgr",fetch=FetchType.LAZY)
	private Set<EmployeeJPA> subordinates = new HashSet<EmployeeJPA>();

		
	@OneToOne
	@JoinColumn(name="department_id")
	private DepartmentJPA departmentJPA;
	
	@OneToOne
	@JoinColumn(name="branch_id")
	private BranchJPA branchJPA;
	
	@Column(name="gender")
	private Character gender;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="modified_by")
    private Long modifiedBy;
	
	@Column(name="created_date")
	private Calendar createdDate;
		
	@Column(name="modified_date")
	private Calendar modifiedDate;
	
	@Column(name="status")
	private Character status;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}



	public BranchJPA getBranchJPA() {
		return branchJPA;
	}

	public void setBranchJPA(BranchJPA branchJPA) {
		this.branchJPA = branchJPA;
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

	public EmployeeJPA getReportingMgr() {
		return reportingMgr;
	}

	public void setReportingMgr(EmployeeJPA manager) {
		this.reportingMgr = manager;
	}

	public Set<EmployeeJPA> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<EmployeeJPA> subordinates) {
		this.subordinates = subordinates;
	}
	
	public DepartmentJPA getDepartmentJPA() {
		return departmentJPA;
	}

	public void setDepartmentJPA(DepartmentJPA departmentJPA) {
		this.departmentJPA = departmentJPA;
	}

}
