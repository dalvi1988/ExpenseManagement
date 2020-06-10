package com.chaitanya.department.convertor;

import java.text.ParseException;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class DepartmentConvertor {
	
	public static DepartmentDTO setDepartmentJPAToDTO(DepartmentJPA departmentJPA){
		DepartmentDTO departmentDTO=null;
		if(Validation.validateForNullObject(departmentJPA)){
			departmentDTO=new DepartmentDTO(); 
			departmentDTO.setDepartmentId(departmentJPA.getDepartmentId());
			departmentDTO.setDepartmentName(departmentJPA.getDeptName());
			departmentDTO.setDepartmentCode(departmentJPA.getDeptCode());
			if(Validation.validateForNullObject(departmentJPA.getBranchJPA())){
				BranchDTO branchDTO= new BranchDTO();
				branchDTO.setBranchId(departmentJPA.getBranchJPA().getBranchId());
				//branchDTO.setBranchName(employeeJPA.getBranchJPA().getBranchName());
				departmentDTO.setBranchDTO(branchDTO);
			}
			
			if(Validation.validateForNullObject(departmentJPA.getCreatedBy())){
				departmentDTO.setCreatedBy(departmentJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(departmentJPA.getModifiedBy())){
				departmentDTO.setModifiedBy(departmentJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(departmentJPA.getCreatedDate())){
				departmentDTO.setCreatedDate(Convertor.calendartoString(departmentJPA.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(departmentJPA.getModifiedDate())){
				departmentDTO.setModifiedDate(Convertor.calendartoString(departmentJPA.getModifiedDate(),Convertor.dateFormatWithTime));
			}
			
			departmentDTO.setStatus(Convertor.convetStatusToBool(departmentJPA.getStatus()));
		}
		return departmentDTO;
	}
	
	
	public static DepartmentJPA setDepartmentDTOToJPA(DepartmentDTO departmentDTO) throws ParseException
	{
		DepartmentJPA departmentJPA=null;
		if(Validation.validateForNullObject(departmentDTO)){
			departmentJPA=new DepartmentJPA();
			if(Validation.validateForNullObject(departmentDTO.getDepartmentId())){
				departmentJPA.setDepartmentId(departmentDTO.getDepartmentId());
			}

			departmentJPA.setDeptCode(departmentDTO.getDepartmentCode());
			departmentJPA.setDeptName(departmentDTO.getDepartmentName());
			departmentJPA.setStatus(Convertor.convertStatusToChar(departmentDTO.getStatus()));
			
			if(Validation.validateForNullObject(departmentDTO.getBranchDTO())){
				BranchJPA branchDetails=new BranchJPA();
				branchDetails.setBranchId(departmentDTO.getBranchDTO().getBranchId());
				departmentJPA.setBranchJPA(branchDetails);
			}
			if(Validation.validateForZero(departmentDTO.getModifiedBy())){
				departmentJPA.setModifiedBy(departmentDTO.getModifiedBy());
			}
			if(Validation.validateForZero(departmentDTO.getCreatedBy())){
				departmentJPA.setCreatedBy(departmentDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(departmentDTO.getCreatedDate())){
				departmentJPA.setCreatedDate(Convertor.stringToCalendar(departmentDTO.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(departmentDTO.getModifiedDate())){
				departmentJPA.setModifiedDate(Convertor.stringToCalendar(departmentDTO.getModifiedDate(),Convertor.dateFormatWithTime));
			}
		}
		return departmentJPA;
	}
}
