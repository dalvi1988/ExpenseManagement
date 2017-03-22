package com.chaitanya.expense.convertor;

import java.text.ParseException;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseConvertor {
	
	public static BranchDTO setBranchJPAtoDTO(BranchJPA branchJPA){
		BranchDTO branchDTO=null;
		if(Validation.validateForNullObject(branchJPA)){
			branchDTO=new BranchDTO(); 
			branchDTO.setBranchId(branchJPA.getBranchId());
			branchDTO.setBranchName(branchJPA.getBranchName());
			branchDTO.setBranchCode(branchJPA.getBranchCode());
			if(Validation.validateForNullObject(branchJPA.getCreatedBy())){
				branchDTO.setCreatedBy(branchJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(branchJPA.getModifiedBy())){
				branchDTO.setModifiedBy(branchJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(branchJPA.getCreatedDate())){
				branchDTO.setCreatedDate(Convertor.calendartoString(branchJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(branchJPA.getModifiedDate())){
				branchDTO.setModifiedDate(Convertor.calendartoString(branchJPA.getModifiedDate()));
			}
			branchDTO.setStatus(Convertor.convetStatusToBool(branchJPA.getStatus()));
		}
		return branchDTO;
	}
	
	
	public static BranchJPA setBranchDTOToJPA(BranchDTO branchDTO) throws ParseException
	{
		BranchJPA branchJPA=null;
		if(Validation.validateForNullObject(branchDTO)){
			branchJPA=new BranchJPA();
			if(Validation.validateForZero(branchDTO.getBranchId())){
				branchJPA.setBranchId(branchDTO.getBranchId());
			}
			if(Validation.validateForZero(branchDTO.getModifiedBy())){
				branchJPA.setModifiedBy(branchDTO.getModifiedBy());
			}
			if(Validation.validateForZero(branchDTO.getCreatedBy())){
				branchJPA.setCreatedBy(branchDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(branchDTO.getCreatedDate())){
				branchJPA.setCreatedDate(Convertor.stringToCalendar(branchDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(branchDTO.getModifiedDate())){
				branchJPA.setModifiedDate(Convertor.stringToCalendar(branchDTO.getModifiedDate()));
			}
			CompanyJPA companyJPA=new CompanyJPA();
			companyJPA.setCompanyId(branchDTO.getCompanyDTO().getCompanyId());
			branchJPA.setCompanyJPA(companyJPA);
			
			branchJPA.setBranchCode(branchDTO.getBranchCode());
			branchJPA.setBranchName(branchDTO.getBranchName());
			branchJPA.setStatus(Convertor.convertStatusToChar(branchDTO.getStatus()));
		}
		return branchJPA;
	}
}
