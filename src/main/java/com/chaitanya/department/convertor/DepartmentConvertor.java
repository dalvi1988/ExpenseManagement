package com.chaitanya.department.convertor;

import java.text.ParseException;

import com.chaitanya.department.model.DepartmentDTO;
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
			
			if(Validation.validateForNullObject(departmentJPA.getCreatedBy())){
				departmentDTO.setCreatedBy(departmentJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(departmentJPA.getModifiedBy())){
				departmentDTO.setModifiedBy(departmentJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(departmentJPA.getCreatedDate())){
				departmentDTO.setCreatedDate(Convertor.calendartoString(departmentJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(departmentJPA.getModifiedDate())){
				departmentDTO.setModifiedDate(Convertor.calendartoString(departmentJPA.getModifiedDate()));
			}
			
			departmentDTO.setStatus(Convertor.convetStatusToBool(departmentJPA.getStatus()));
		}
		return departmentDTO;
	}
	
	
	public static DepartmentJPA setDepartmentDTOToJPA(DepartmentDTO departmentDTO) throws ParseException
	{
		DepartmentJPA department=null;
		if(Validation.validateForNullObject(departmentDTO)){
			department=new DepartmentJPA();
			if(Validation.validateForNullObject(departmentDTO.getDepartmentId())){
				department.setDepartmentId(departmentDTO.getDepartmentId());
			}
			if(Validation.validateForZero(departmentDTO.getModifiedBy())){
				department.setModifiedBy(departmentDTO.getModifiedBy());
			}
			if(Validation.validateForZero(departmentDTO.getCreatedBy())){
				department.setCreatedBy(departmentDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(departmentDTO.getCreatedDate())){
				department.setCreatedDate(Convertor.stringToCalendar(departmentDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(departmentDTO.getModifiedDate())){
				department.setModifiedDate(Convertor.stringToCalendar(departmentDTO.getModifiedDate()));
			}
			department.setDeptCode(departmentDTO.getDepartmentCode());
			department.setDeptName(departmentDTO.getDepartmentName());
			department.setStatus(Convertor.convertStatusToChar(departmentDTO.getStatus()));
		}
		return department;
	}
}
