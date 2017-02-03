package com.chaitanya.department.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chaitanya.Base.BaseDTO;


public class DepartmentDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	Long departmentId;

	@NotNull
	@Size(min=3,max=30,message="Department lenght should be between 3 to 30 character")
	String departmentName;

	@NotNull
	@Size(min=3,max=30,message="Department lenght should be between 3 to 30 character")
	String departmentCode;
		

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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
