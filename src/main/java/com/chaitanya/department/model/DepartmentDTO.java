package com.chaitanya.department.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.utility.Validation;


public class DepartmentDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	private Long departmentId;

	@NotNull
	@Size(min=3,max=30,message="Department lenght should be between 3 to 30 character")
	private String departmentName;

	@NotNull
	@Size(min=3,max=30,message="Department lenght should be between 3 to 30 character")
	private String departmentCode;
	
	private BranchDTO branchDTO;

	private Long branchId;
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String depatmentCode) {
		this.departmentCode = depatmentCode;
	}

	public BranchDTO getBranchDTO() {
		return branchDTO;
	}

	public void setBranchDTO(BranchDTO branchDTO) {
		this.branchDTO = branchDTO;
		this.branchId=branchDTO.getBranchId();
	}
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
		if(Validation.validateForNullObject(branchId)) {
			BranchDTO branchDTO= new BranchDTO();
			branchDTO.setBranchId(branchId);
			this.branchDTO=branchDTO;
		}
	}


	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
