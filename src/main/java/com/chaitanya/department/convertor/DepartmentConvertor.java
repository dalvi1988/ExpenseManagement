package com.chaitanya.department.convertor;

import java.text.ParseException;

import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class DepartmentConvertor {
	
	public static DepartmentDTO setDepartmentJPAToDTO(DepartmentJPA department){
		DepartmentDTO departmentDTO=null;
		if(Validation.validateForNullObject(department)){
			departmentDTO=new DepartmentDTO(); 
			departmentDTO.setDepartmentId(department.getDepartmentId());
			departmentDTO.setDepartmentName(department.getDepartmentName());
			departmentDTO.setDepartmentCode(department.getDepartmentCode());
			if(Validation.validateForNullObject(department.getCreatedBy())){
				departmentDTO.setCreatedBy(department.getCreatedBy());
			}
			if(Validation.validateForNullObject(department.getModifiedBy())){
				departmentDTO.setModifiedBy(department.getModifiedBy());
			}
			if(Validation.validateForNullObject(department.getCreatedDate())){
				departmentDTO.setCreatedDate(Convertor.calendartoString(department.getCreatedDate()));
			}
			if(Validation.validateForNullObject(department.getModifiedDate())){
				departmentDTO.setModifiedDate(Convertor.calendartoString(department.getModifiedDate()));
			}
			departmentDTO.setStatus(Convertor.convetStatusToBool(department.getStatus()));
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
			department.setDepartmentCode(departmentDTO.getDepartmentCode());
			department.setDepartmentName(departmentDTO.getDepartmentName());
			department.setStatus(Convertor.convertStatusToChar(departmentDTO.getStatus()));
		}
		return department;
	}
}
