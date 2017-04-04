package com.chaitanya.departmentHead.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.employee.model.EmployeeDTO;


public class DepartmentHeadDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	private Long deptHeadId;
	
	private Long departmentId;
	private DepartmentDTO departmentDTO;
	
	
	private Long branchId;
	private BranchDTO branchDTO;
	
	
	private Long employeeId;
	private EmployeeDTO employeeDTO;
	

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
		this.departmentId=this.departmentDTO.getDepartmentId();
	}

	public BranchDTO getBranchDTO() {
		return branchDTO;
	}

	public void setBranchDTO(BranchDTO branchhDTO) {
		this.branchDTO = branchhDTO;
		this.branchId =branchhDTO.getBranchId();
	}

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
		this.employeeId= employeeDTO.getEmployeeId();
	}

	public Long getDeptHeadId() {
		return deptHeadId;
	}

	public void setDeptHeadId(Long deptHeadId) {
		this.deptHeadId = deptHeadId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
		DepartmentDTO deptDTO= new DepartmentDTO();
		deptDTO.setDepartmentId(departmentId);
		this.departmentDTO=deptDTO;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
		BranchDTO branchDTO= new BranchDTO();
		branchDTO.setBranchId(branchId);
		this.branchDTO=branchDTO;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
		EmployeeDTO empDTO=new EmployeeDTO();
		empDTO.setEmployeeId(employeeId);
		this.employeeDTO=empDTO;
	}



}
