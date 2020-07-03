package com.chaitanya.mis.convertor;

import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.mis.model.ExpenseMISDTO;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class MISConvertor {
	
	public static ExpenseMISDTO setExpenseHeaderJPAtoDTO(ExpenseHeaderJPA expenseHeaderJPA) {
		ExpenseMISDTO expenseMISDTO=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			expenseMISDTO=new ExpenseMISDTO(); 
			expenseMISDTO.setExpenseHeaderId(expenseHeaderJPA.getExpenseHeaderId());
			expenseMISDTO.setExpenseType(expenseHeaderJPA.getExpenseType());
			expenseMISDTO.setStartDate(Convertor.calendartoString(expenseHeaderJPA.getStartDate(),Convertor.dateFormat));
			expenseMISDTO.setEndDate(Convertor.calendartoString(expenseHeaderJPA.getEndDate(),Convertor.dateFormat));
			expenseMISDTO.setPurpose(expenseHeaderJPA.getPurpose());
			
			if(Validation.validateForEmptyString(expenseHeaderJPA.getVoucherNumber())){
				expenseMISDTO.setVoucherNumber(expenseHeaderJPA.getVoucherNumber());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getExpenseDetailJPA())){
				expenseMISDTO.setTotalAmount(expenseHeaderJPA.getExpenseDetailJPA().stream().mapToDouble(o -> o.getAmount()).sum());
			}
			
			if(Validation.validateForNullObject(expenseHeaderJPA.getCreatedBy())){
				expenseMISDTO.setCreatedBy(expenseHeaderJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getModifiedBy())){
				expenseMISDTO.setModifiedBy(expenseHeaderJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getCreatedDate())){
				expenseMISDTO.setCreatedDate(Convertor.calendartoString(expenseHeaderJPA.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getModifiedDate())){
				expenseMISDTO.setModifiedDate(Convertor.calendartoString(expenseHeaderJPA.getModifiedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getEmployeeJPA())) {
				expenseMISDTO.setEmployeeId(expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
			}
		}
		return expenseMISDTO;
	}
	
}
