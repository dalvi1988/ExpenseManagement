package com.chaitanya.employee.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;

public class EmployeeDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long employeeId;
	private String  firstName;
	private String middleName;
	private String lastName;
	private String fullName;
	private String emailId;
	private Character gender;
	private BranchDTO branchDTO;
	private DepartmentDTO departmentDTO;
	private EmployeeDTO reportingMgrDTO;
	private String branchName;
	private Long branchId;
	private Long reportingMgr;
	private Long departmentId;
	
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
	public String getFullName(){
		return firstName+" "+lastName;
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
		this.branchId=branchDTO.getBranchId();
		this.branchName=branchDTO.getBranchName();
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public EmployeeDTO getReportingMgrDTO() {
		return reportingMgrDTO;
	}
	public void setReportingMgrDTO(EmployeeDTO reportingMgrDTO) {
		this.reportingMgrDTO = reportingMgrDTO;
		this.reportingMgr=reportingMgrDTO.getEmployeeId();
	}
	
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
		BranchDTO branchDTO=new BranchDTO();
		branchDTO.setBranchId(branchId);
		this.setBranchDTO(branchDTO);
	}
	
	public Long getReportingMgr() {
		return reportingMgr;
	}
	public void setReportingMgr(Long reportingMgr) {
		this.reportingMgr = reportingMgr;
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeId(reportingMgr);
		this.setReportingMgrDTO(employeeDTO);
	}
	
	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}
	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
		this.departmentId=departmentDTO.getDepartmentId();
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
		DepartmentDTO departmentDTO= new DepartmentDTO();
		departmentDTO.setDepartmentId(departmentId);
		this.setDepartmentDTO(departmentDTO);
	}
}
