package com.chaitanya.expenseCategory.convertor;

import java.text.ParseException;

import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseCategoryConvertor {
	
	public static ExpenseCategoryDTO setExpenseCategoryJPAtoDTO(ExpenseCategoryJPA expenseCategoryJPA){
		ExpenseCategoryDTO expenseCategoryDTO=null;
		if(Validation.validateForNullObject(expenseCategoryJPA)){
			expenseCategoryDTO=new ExpenseCategoryDTO(); 
			expenseCategoryDTO.setBranchId(expenseCategoryJPA.getBranchId());
			expenseCategoryDTO.setBranchName(expenseCategoryJPA.getBranchName());
			expenseCategoryDTO.setBranchCode(expenseCategoryJPA.getBranchCode());
			if(Validation.validateForNullObject(expenseCategoryJPA.getCreatedBy())){
				expenseCategoryDTO.setCreatedBy(expenseCategoryJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(expenseCategoryJPA.getModifiedBy())){
				expenseCategoryDTO.setModifiedBy(expenseCategoryJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(expenseCategoryJPA.getCreatedDate())){
				expenseCategoryDTO.setCreatedDate(Convertor.calendartoString(expenseCategoryJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(expenseCategoryJPA.getModifiedDate())){
				expenseCategoryDTO.setModifiedDate(Convertor.calendartoString(expenseCategoryJPA.getModifiedDate()));
			}
			expenseCategoryDTO.setStatus(Convertor.convetStatusToBool(expenseCategoryJPA.getStatus()));
		}
		return expenseCategoryDTO;
	}
	
	
	public static ExpenseCategoryJPA setExpenseCategoryDTOToJPA(ExpenseCategoryDTO expenseCategoryDTO) throws ParseException
	{
		ExpenseCategoryJPA expenseCategoryJPA=null;
		if(Validation.validateForNullObject(expenseCategoryDTO)){
			expenseCategoryJPA=new ExpenseCategoryJPA();
			if(Validation.validateForZero(expenseCategoryDTO.getBranchId())){
				expenseCategoryJPA.setBranchId(expenseCategoryDTO.getBranchId());
			}
			if(Validation.validateForZero(expenseCategoryDTO.getModifiedBy())){
				expenseCategoryJPA.setModifiedBy(expenseCategoryDTO.getModifiedBy());
			}
			if(Validation.validateForZero(expenseCategoryDTO.getCreatedBy())){
				expenseCategoryJPA.setCreatedBy(expenseCategoryDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(expenseCategoryDTO.getCreatedDate())){
				expenseCategoryJPA.setCreatedDate(Convertor.stringToCalendar(expenseCategoryDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(expenseCategoryDTO.getModifiedDate())){
				expenseCategoryJPA.setModifiedDate(Convertor.stringToCalendar(expenseCategoryDTO.getModifiedDate()));
			}
			CompanyJPA companyJPA=new CompanyJPA();
			companyJPA.setCompanyId(expenseCategoryDTO.getCompanyDTO().getCompanyId());
			expenseCategoryJPA.setCompanyJPA(companyJPA);
			
			expenseCategoryJPA.setBranchCode(expenseCategoryDTO.getBranchCode());
			expenseCategoryJPA.setBranchName(expenseCategoryDTO.getBranchName());
			expenseCategoryJPA.setStatus(Convertor.convertStatusToChar(expenseCategoryDTO.getStatus()));
		}
		return expenseCategoryJPA;
	}
}
