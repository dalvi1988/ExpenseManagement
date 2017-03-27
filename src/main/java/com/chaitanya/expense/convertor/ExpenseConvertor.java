package com.chaitanya.expense.convertor;

import java.text.ParseException;

import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseConvertor {
	
	public static ExpenseHeaderDTO setExpenseHeaderJPAtoDTO(ExpenseHeaderJPA expenseHeaderJPA){
		ExpenseHeaderDTO expenseHeaderDTO=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			expenseHeaderDTO=new ExpenseHeaderDTO(); 
			expenseHeaderDTO.setExpenseHeaderId(expenseHeaderJPA.getExpenseHeaderId());
			expenseHeaderDTO.setStartDate(Convertor.calendartoString(expenseHeaderJPA.getStartDate()));
			expenseHeaderDTO.setEndDate(Convertor.calendartoString(expenseHeaderJPA.getEndDate()));
			expenseHeaderDTO.setTitle(expenseHeaderJPA.getTitle());
			expenseHeaderDTO.setPurpose(expenseHeaderJPA.getPurpose());
			
		}
		return expenseHeaderDTO;
	}
	
	public static ExpenseDetailDTO setExpenseDetailJPAtoDTO(ExpenseDetailJPA expenseDetailJPA){
		ExpenseDetailDTO expenseDetailDTO=null;
		
		if(Validation.validateForNullObject(expenseDetailJPA)){
			expenseDetailDTO=new ExpenseDetailDTO(); 
			expenseDetailDTO.setExpenseDetailId(expenseDetailJPA.getExpenseDetailId());
			expenseDetailDTO.setDate(Convertor.calendartoString(expenseDetailJPA.getDate()));
			expenseDetailDTO.setFromLocation(expenseDetailJPA.getFromLocation());
			expenseDetailDTO.setToLocation(expenseDetailJPA.getToLocation());
			expenseDetailDTO.setAmount(expenseDetailJPA.getAmount());
		}
		return expenseDetailDTO;
	}
	
	
	public static ExpenseHeaderJPA setExpenseHeaderDTOToJPA(ExpenseHeaderDTO expenseHeaderDTO) throws ParseException
	{
		ExpenseHeaderJPA expenseHeaderJPA=null;
		if(Validation.validateForNullObject(expenseHeaderDTO)){
			expenseHeaderJPA=new ExpenseHeaderJPA();
			expenseHeaderJPA.setExpenseHeaderId(expenseHeaderDTO.getExpenseHeaderId());
			expenseHeaderJPA.setStartDate(Convertor.stringToCalendar(expenseHeaderDTO.getStartDate()));
			expenseHeaderJPA.setEndDate(Convertor.stringToCalendar(expenseHeaderDTO.getEndDate()));
			expenseHeaderJPA.setPurpose(expenseHeaderDTO.getPurpose());
			expenseHeaderJPA.setTitle(expenseHeaderDTO.getTitle());
		}
		return expenseHeaderJPA;
	}
	
	public static ExpenseDetailJPA setExpenseDetailDTOToJPA(ExpenseDetailDTO expenseDetailDTO) throws ParseException
	{
		ExpenseDetailJPA expenseDetailJPA=null;
		if(Validation.validateForNullObject(expenseDetailDTO)){
			expenseDetailJPA=new ExpenseDetailJPA();
			expenseDetailJPA.setExpenseDetailId(expenseDetailDTO.getExpenseDetailId());
			expenseDetailJPA.setDate(Convertor.stringToCalendar(expenseDetailDTO.getDate()));
			expenseDetailJPA.setFromLocation(expenseDetailDTO.getFromLocation());
			expenseDetailJPA.setToLocation(expenseDetailDTO.getToLocation());
			expenseDetailJPA.setAmount(expenseDetailDTO.getAmount());
			//expenseDetailJPA.setFileName(expenseDetailDTO.getFil());
		}
		return expenseDetailJPA;
	}
}
