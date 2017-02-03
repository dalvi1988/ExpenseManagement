package com.chaitanya.login.convertor;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.login.model.LoginDTO;
import com.chaitanya.utility.Validation;

public class LoginConvertor {
	
	public static LoginDTO setLoginToLoginDTO(LoginJPA login){
		LoginDTO loginDTO=null;
		if(Validation.validateForNullObject(login)){
			loginDTO=new LoginDTO();
			if(Validation.validateForNullObject(login.getEmployeeDetails())){
				EmployeeDTO employeeDTO=new EmployeeDTO();
				if(Validation.validateForZero(login.getEmployeeDetails().getEmployeeId()))
					employeeDTO.setEmployeeId(login.getEmployeeDetails().getEmployeeId());
				
				if(Validation.validateForEmptyString(login.getEmployeeDetails().getFirstName()))
					employeeDTO.setFirstName(login.getEmployeeDetails().getFirstName());
				
				if(Validation.validateForEmptyString(login.getEmployeeDetails().getLastName()))
					employeeDTO.setLastName(login.getEmployeeDetails().getLastName());
				
				if(Validation.validateForNullObject(login.getEmployeeDetails().getBranchJPA())){
					BranchDTO branchDTO=new BranchDTO();
					branchDTO.setBranchId(login.getEmployeeDetails().getBranchJPA().getBranchId());
					if(Validation.validateForNullObject(login.getEmployeeDetails().getBranchJPA().getCompanyJPA()))
					{
						CompanyDTO companyDTO=new CompanyDTO();
						companyDTO.setCompanyId(login.getEmployeeDetails().getBranchJPA().getCompanyJPA().getCompanyId());
						branchDTO.setCompanyDTO(companyDTO);
					}
					employeeDTO.setBranchDTO(branchDTO);
				}
				loginDTO.setEmployeeDTO(employeeDTO);
			}
		}
		return loginDTO;	
	}

}
