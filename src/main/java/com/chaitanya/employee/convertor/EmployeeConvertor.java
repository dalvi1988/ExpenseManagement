package com.chaitanya.employee.convertor;

import java.text.ParseException;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class EmployeeConvertor {
	
	public static EmployeeDTO setEmployeeToEmployeeDTO(EmployeeJPA employeeJPA) throws ParseException{
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
				employeeDTO.setBranchDTO(branchDTO);
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
		EmployeeJPA employee=null;
		if(Validation.validateForNullObject(employeeDTO)){
			employee=new EmployeeJPA();
			
			if(Validation.validateForNullObject(employeeDTO.getEmployeeId())){
				employee.setEmployeeId(employeeDTO.getEmployeeId());
			}
			employee.setFirstName(employeeDTO.getFirstName());
			employee.setMiddleName(employeeDTO.getMiddleName());
			employee.setLastName(employeeDTO.getLastName());
			employee.setEmailId(employeeDTO.getEmailId());
			employee.setGender(employeeDTO.getGender());
			
			if(Validation.validateForNullObject(employeeDTO.getReportingMgrDTO())){
				EmployeeJPA reportingMgr= new EmployeeJPA();
				reportingMgr.setEmployeeId(employeeDTO.getReportingMgr());
				employee.setReportingMgr(reportingMgr);
			}
			if(Validation.validateForNullObject(employeeDTO.getBranchDTO())){
				BranchJPA branchDetails=new BranchJPA();
				branchDetails.setBranchId(employeeDTO.getBranchDTO().getBranchId());
				employee.setBranchJPA(branchDetails);
			}
			if(Validation.validateForNullObject(employeeDTO.getDepartmentDTO())){
				DepartmentJPA departmentJPA=new DepartmentJPA();
				departmentJPA.setDepartmentId(employeeDTO.getDepartmentDTO().getDepartmentId());
				employee.setDepartmentJPA(departmentJPA);
			}
			
			if(Validation.validateForZero(employeeDTO.getModifiedBy())){
				employee.setModifiedBy(employeeDTO.getModifiedBy());
			}
			if(Validation.validateForZero(employeeDTO.getCreatedBy())){
				employee.setModifiedBy(employeeDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(employeeDTO.getCreatedDate())){
				employee.setCreatedDate(Convertor.stringToCalendar(employeeDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(employeeDTO.getModifiedDate())){
				employee.setModifiedDate(Convertor.stringToCalendar(employeeDTO.getModifiedDate()));
			}
			employee.setStatus(Convertor.convertStatusToChar(employeeDTO.getStatus()));
		}
		return employee;
	}
}
