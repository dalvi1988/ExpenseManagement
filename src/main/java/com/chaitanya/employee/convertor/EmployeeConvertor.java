package com.chaitanya.employee.convertor;

import java.text.ParseException;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class EmployeeConvertor {
	
	public static EmployeeDTO setEmployeeJPAToEmployeeDTO(EmployeeJPA employeeJPA) throws ParseException{
		EmployeeDTO employeeDTO=null;
		if(Validation.validateForNullObject(employeeJPA)){
			employeeDTO=new EmployeeDTO(); 
			if(Validation.validateForZero(employeeJPA.getEmployeeId())){
				employeeDTO.setEmployeeId(employeeJPA.getEmployeeId());
			}
			if(Validation.validateForEmptyString(employeeJPA.getFirstName())){
				employeeDTO.setFirstName(employeeJPA.getFirstName());
			}
			if(Validation.validateForEmptyString(employeeJPA.getMiddleName())){
				employeeDTO.setMiddleName(employeeJPA.getMiddleName());			
			}
			if(Validation.validateForEmptyString(employeeJPA.getLastName())){
				employeeDTO.setLastName(employeeJPA.getLastName());
			}
			if(Validation.validateForEmptyString(employeeJPA.getEmailId())){
				employeeDTO.setEmailId(employeeJPA.getEmailId());
			}
			employeeDTO.setGender(employeeJPA.getGender());
			
			if(Validation.validateForNullObject(employeeJPA.getBranchJPA())){
				BranchDTO branchDTO= new BranchDTO();
				branchDTO.setBranchId(employeeJPA.getBranchJPA().getBranchId());
				//branchDTO.setBranchName(employeeJPA.getBranchJPA().getBranchName());
				employeeDTO.setBranchDTO(branchDTO);
			}
			
			if(Validation.validateForNullObject(employeeJPA.getDepartmentJPA())){
				DepartmentDTO departmentDTO= new DepartmentDTO();
				departmentDTO.setDepartmentId(employeeJPA.getDepartmentJPA().getDepartmentId());
				employeeDTO.setDepartmentDTO(departmentDTO);
			}
			
			if(Validation.validateForNullObject(employeeJPA.getReportingMgr())){
				EmployeeDTO reportingMgrDTO= new EmployeeDTO();
				reportingMgrDTO.setEmployeeId(employeeJPA.getReportingMgr().getEmployeeId());
				employeeDTO.setReportingMgrDTO(reportingMgrDTO);
			}
				
			if(Validation.validateForNullObject(employeeJPA.getCreatedBy())){
				employeeDTO.setCreatedBy(employeeJPA.getEmployeeId());
			}
			if(Validation.validateForNullObject(employeeJPA.getModifiedBy())){
				employeeDTO.setModifiedBy(employeeJPA.getEmployeeId());
			}
			if(Validation.validateForNullObject(employeeJPA.getCreatedDate())){
				employeeDTO.setCreatedDate(Convertor.calendartoString(employeeJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(employeeJPA.getModifiedDate())){
				employeeDTO.setModifiedDate(Convertor.calendartoString(employeeJPA.getModifiedDate()));
			}
			employeeDTO.setStatus(Convertor.convetStatusToBool(employeeJPA.getStatus()));
		}
		return employeeDTO;
	}
	
	
	public static EmployeeJPA setEmployeeDTOToEmployee(EmployeeDTO employeeDTO) throws ParseException
	{
		EmployeeJPA employeeJPA=null;
		if(Validation.validateForNullObject(employeeDTO)){
			employeeJPA=new EmployeeJPA();
			
			if(Validation.validateForNullObject(employeeDTO.getEmployeeId())){
				employeeJPA.setEmployeeId(employeeDTO.getEmployeeId());
			}
			employeeJPA.setFirstName(employeeDTO.getFirstName());
			employeeJPA.setMiddleName(employeeDTO.getMiddleName());
			employeeJPA.setLastName(employeeDTO.getLastName());
			employeeJPA.setEmailId(employeeDTO.getEmailId());
			employeeJPA.setGender(employeeDTO.getGender());
			
			if(Validation.validateForNullObject(employeeDTO.getReportingMgrDTO())){
				EmployeeJPA reportingMgr= new EmployeeJPA();
				reportingMgr.setEmployeeId(employeeDTO.getReportingMgr());
				employeeJPA.setReportingMgr(reportingMgr);
			}
			if(Validation.validateForNullObject(employeeDTO.getBranchDTO())){
				BranchJPA branchDetails=new BranchJPA();
				branchDetails.setBranchId(employeeDTO.getBranchDTO().getBranchId());
				employeeJPA.setBranchJPA(branchDetails);
			}
			if(Validation.validateForNullObject(employeeDTO.getDepartmentDTO())){
				DepartmentJPA departmentJPA=new DepartmentJPA();
				departmentJPA.setDepartmentId(employeeDTO.getDepartmentDTO().getDepartmentId());
				employeeJPA.setDepartmentJPA(departmentJPA);
			}
			
			if(Validation.validateForZero(employeeDTO.getModifiedBy())){
				employeeJPA.setModifiedBy(employeeDTO.getModifiedBy());
			}
			if(Validation.validateForZero(employeeDTO.getCreatedBy())){
				employeeJPA.setModifiedBy(employeeDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(employeeDTO.getCreatedDate())){
				employeeJPA.setCreatedDate(Convertor.stringToCalendar(employeeDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(employeeDTO.getModifiedDate())){
				employeeJPA.setModifiedDate(Convertor.stringToCalendar(employeeDTO.getModifiedDate()));
			}
			employeeJPA.setStatus(Convertor.convertStatusToChar(employeeDTO.getStatus()));
		}
		return employeeJPA;
	}
}
