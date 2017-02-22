package com.chaitanya.departmentHead.convertor;

import java.text.ParseException;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.utility.Validation;

public class DepartmentHeadConvertor {
	
	public static DepartmentHeadDTO setDepartmentHeadJPAToDTO(DepartmentHeadJPA departmentHeadJPA){
		DepartmentHeadDTO departmentHeadDTO=null;
		if(Validation.validateForNullObject(departmentHeadJPA)){
			departmentHeadDTO=new DepartmentHeadDTO(); 
			
			BranchDTO branchDTO= new BranchDTO();
			branchDTO.setBranchId(departmentHeadJPA.getBranchJPA().getBranchId());
			departmentHeadDTO.setBranchDTO(branchDTO);
			
			EmployeeDTO employeeDTO= new EmployeeDTO();
			employeeDTO.setEmployeeId(departmentHeadJPA.getEmployeeJPA().getEmployeeId());
			departmentHeadDTO.setEmployeeDTO(employeeDTO);
			
			DepartmentDTO departmentDTO= new DepartmentDTO();
			departmentDTO.setDepartmentId(departmentHeadJPA.getDepartmentJPA().getDepartmentId());
			departmentHeadDTO.setDepartmentDTO(departmentDTO);
			
		}
		return departmentHeadDTO;
	}
	
	
	public static DepartmentHeadJPA setDepartmentHeadDTOToJPA(DepartmentHeadDTO departmentHeadDTO) throws ParseException
	{
		DepartmentHeadJPA departmentHeadJPA=null;
		if(Validation.validateForNullObject(departmentHeadDTO)){
			departmentHeadJPA=new DepartmentHeadJPA();
			if(Validation.validateForNullObject(departmentHeadDTO.getDeptHeadId())){
				departmentHeadJPA.setDeptHeadId(departmentHeadDTO.getDeptHeadId());
			}
			DepartmentJPA departmentJPA= new DepartmentJPA();
			departmentJPA.setDepartmentId(departmentHeadDTO.getDepartmentDTO().getDepartmentId());
			departmentHeadJPA.setDepartmentJPA(departmentJPA);
			
			BranchJPA branchJPA=new BranchJPA();
			branchJPA.setBranchId(departmentHeadDTO.getBranchDTO().getBranchId());
			departmentHeadJPA.setBranchJPA(branchJPA);
			
			EmployeeJPA employeeJPA=new EmployeeJPA();
			employeeJPA.setEmployeeId(departmentHeadDTO.getEmployeeDTO().getEmployeeId());
			departmentHeadJPA.setEmployeeJPA(employeeJPA);
		}
		return departmentHeadJPA;
	}
}
