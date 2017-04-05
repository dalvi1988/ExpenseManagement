package com.chaitanya.expenseCategory.convertor;

import java.text.ParseException;

import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseCategoryConvertor {
	
	public static ExpenseCategoryDTO setExpenseCategoryJPAtoDTO(ExpenseCategoryJPA expenseCategoryJPA){
		ExpenseCategoryDTO expenseCategoryDTO=null;
		
		if(Validation.validateForNullObject(expenseCategoryJPA)){
			
			expenseCategoryDTO=new ExpenseCategoryDTO(); 
			expenseCategoryDTO.setExpCategoryId(expenseCategoryJPA.getExpCategoryId());
			expenseCategoryDTO.setExpenseName(expenseCategoryJPA.getExpenseName());
			expenseCategoryDTO.setGlCode(expenseCategoryJPA.getGlCode());
			expenseCategoryDTO.setLocationRequired(Convertor.convetStatusToBool(expenseCategoryJPA.getLocationRequired()));
			expenseCategoryDTO.setUnitRequired(Convertor.convetStatusToBool(expenseCategoryJPA.getUnitRequired()));
			
			if(Validation.validateForZero(expenseCategoryJPA.getAmount())){
				expenseCategoryDTO.setAmount(expenseCategoryJPA.getAmount());
			}
			
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
			
			expenseCategoryJPA.setExpenseName(expenseCategoryDTO.getExpenseName());
			expenseCategoryJPA.setGlCode(expenseCategoryDTO.getGlCode());
			expenseCategoryJPA.setLocationRequired(Convertor.convertStatusToChar(expenseCategoryDTO.getLocationRequired()));
			expenseCategoryJPA.setUnitRequired(Convertor.convertStatusToChar(expenseCategoryDTO.getUnitRequired()));
			expenseCategoryJPA.setStatus(Convertor.convertStatusToChar(expenseCategoryDTO.getStatus()));
			
			if(Validation.validateForZero(expenseCategoryDTO.getAmount())){
				expenseCategoryJPA.setAmount(expenseCategoryDTO.getAmount());
			}
			
			if(Validation.validateForZero(expenseCategoryDTO.getExpCategoryId())){
				expenseCategoryJPA.setExpCategoryId(expenseCategoryDTO.getExpCategoryId());
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
			
		}
		return expenseCategoryJPA;
	}
}
