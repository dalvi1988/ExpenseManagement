package com.chaitanya.login.convertor;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.login.model.LoginDTO;
import com.chaitanya.utility.Validation;

public class LoginConvertor {
	
	public static LoginDTO setLoginJPAToDTO(LoginJPA login){
		LoginDTO loginDTO=null;
		if(Validation.validateForNullObject(login)){
			loginDTO=new LoginDTO();
			if(Validation.validateForNullObject(login.getEmployeeJPA())){
				EmployeeDTO employeeDTO=new EmployeeDTO();
				if(Validation.validateForZero(login.getEmployeeJPA().getEmployeeId()))
					employeeDTO.setEmployeeId(login.getEmployeeJPA().getEmployeeId());
				
				if(Validation.validateForEmptyString(login.getEmployeeJPA().getFirstName()))
					employeeDTO.setFirstName(login.getEmployeeJPA().getFirstName());
				
				if(Validation.validateForEmptyString(login.getEmployeeJPA().getLastName()))
					employeeDTO.setLastName(login.getEmployeeJPA().getLastName());
				
				if(Validation.validateForEmptyString(login.getEmployeeJPA().getEmployeeCode()))
					employeeDTO.setEmployeeCode(login.getEmployeeJPA().getEmployeeCode());
				
				if(Validation.validateForNullObject(login.getEmployeeJPA().getBranchJPA())){
					BranchDTO branchDTO=new BranchDTO();
					branchDTO.setBranchId(login.getEmployeeJPA().getBranchJPA().getBranchId());
					branchDTO.setBranchName(login.getEmployeeJPA().getBranchJPA().getBranchName());
					if(Validation.validateForNullObject(login.getEmployeeJPA().getBranchJPA().getCompanyJPA()))
					{
						CompanyDTO companyDTO=new CompanyDTO();
						companyDTO.setCompanyId(login.getEmployeeJPA().getBranchJPA().getCompanyJPA().getCompanyId());
						companyDTO.setCompanyName(login.getEmployeeJPA().getBranchJPA().getCompanyJPA().getCompanyName());
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
