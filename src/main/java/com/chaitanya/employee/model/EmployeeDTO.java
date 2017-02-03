package com.chaitanya.employee.model;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public class EmployeeDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long employeeId;
	private String  firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private Character gender;
	private BranchDTO branchDTO;
	private String branchName;
	private Long branchId;
	private Long reportingMgr;

	
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
	public BranchDTO getBranchDTO() {
		return branchDTO;
	}
	public void setBranchDTO(BranchDTO branchDTO) {
		this.branchDTO = branchDTO;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getReportingMgr() {
		return reportingMgr;
	}
	public void setReportingMgr(Long reportingMgr) {
		this.reportingMgr = reportingMgr;
	}

}
