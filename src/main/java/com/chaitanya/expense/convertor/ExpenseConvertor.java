package com.chaitanya.expense.convertor;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.AdvanceJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.EventJPA;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseConvertor {
	
	public static ExpenseHeaderDTO setExpenseHeaderJPAtoDTO(ExpenseHeaderJPA expenseHeaderJPA) throws ParseException{
		ExpenseHeaderDTO expenseHeaderDTO=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			expenseHeaderDTO=new ExpenseHeaderDTO(); 
			expenseHeaderDTO.setExpenseHeaderId(expenseHeaderJPA.getExpenseHeaderId());
			expenseHeaderDTO.setExpenseType(expenseHeaderJPA.getExpenseType());
			expenseHeaderDTO.setStartDate(Convertor.calendartoString(expenseHeaderJPA.getStartDate(),Convertor.dateFormat));
			expenseHeaderDTO.setEndDate(Convertor.calendartoString(expenseHeaderJPA.getEndDate(),Convertor.dateFormat));
			expenseHeaderDTO.setPurpose(expenseHeaderJPA.getPurpose());
			
			if(Validation.validateForEmptyString(expenseHeaderJPA.getVoucherNumber())){
				expenseHeaderDTO.setVoucherNumber(expenseHeaderJPA.getVoucherNumber());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getExpenseDetailJPA())){
				expenseHeaderDTO.setTotalAmount(expenseHeaderJPA.getExpenseDetailJPA().stream().mapToDouble(o -> o.getAmount()).sum());
			}
			
			if(Validation.validateForNullObject(expenseHeaderJPA.getCreatedBy())){
				expenseHeaderDTO.setCreatedBy(expenseHeaderJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getModifiedBy())){
				expenseHeaderDTO.setModifiedBy(expenseHeaderJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getCreatedDate())){
				expenseHeaderDTO.setCreatedDate(Convertor.calendartoString(expenseHeaderJPA.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(expenseHeaderJPA.getModifiedDate())){
				expenseHeaderDTO.setModifiedDate(Convertor.calendartoString(expenseHeaderJPA.getModifiedDate(),Convertor.dateFormatWithTime));
			}
		}
		return expenseHeaderDTO;
	}
	
	public static ProcessHistoryJPA setExpenseHeaderJPAtoProcessHistoryJPA(ExpenseHeaderJPA expenseHeaderJPA){
		ProcessHistoryJPA processHistoryJPA=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			processHistoryJPA=new ProcessHistoryJPA(); 
			processHistoryJPA.setVoucherStatusJPA(expenseHeaderJPA.getVoucherStatusJPA());
			processHistoryJPA.setProcessedBy(expenseHeaderJPA.getEmployeeJPA());
			processHistoryJPA.setProcessDate(expenseHeaderJPA.getCreatedDate());
			
		}
		return processHistoryJPA;
	}
	
	public static ExpenseHeaderJPA setExpenseHeaderDTOToJPA(ExpenseHeaderDTO expenseHeaderDTO) throws ParseException
	{
		ExpenseHeaderJPA expenseHeaderJPA=null;
		if(Validation.validateForNullObject(expenseHeaderDTO)){
			expenseHeaderJPA=new ExpenseHeaderJPA();
			expenseHeaderJPA.setExpenseHeaderId(expenseHeaderDTO.getExpenseHeaderId());
			
			EmployeeJPA employeeJPA=new EmployeeJPA();
			employeeJPA.setEmployeeId(expenseHeaderDTO.getEmployeeDTO().getEmployeeId());
			expenseHeaderJPA.setEmployeeJPA(employeeJPA);
			
			if(Validation.validateForNullObject(expenseHeaderDTO.getAdvanceDTO())){
				if(expenseHeaderDTO.getAdvanceDTO().getAdvanceDetailId() != -1){
					AdvanceJPA advanceJPA=new AdvanceJPA();
					advanceJPA.setAdvanceDetailId(expenseHeaderDTO.getAdvanceDTO().getAdvanceDetailId());
					expenseHeaderJPA.setAdvanceJPA(advanceJPA);
				}
			}
			
			if(Validation.validateForEmptyString(expenseHeaderDTO.getExpenseType())){
				if(expenseHeaderDTO.getExpenseType().equalsIgnoreCase(ApplicationConstant.EXPENSE_TYPE_EVENT)){
					EventJPA eventJPA=new EventJPA();
					eventJPA.setEventId(expenseHeaderDTO.getEventDTO().getEventId());
					expenseHeaderJPA.setEventJPA(eventJPA);
					expenseHeaderJPA.setExpenseType(ApplicationConstant.EXPENSE_TYPE_EVENT);
				}
				else{
					expenseHeaderJPA.setExpenseType(ApplicationConstant.EXPENSE_TYPE_EMP);
				}
			}
			
			VoucherStatusJPA voucherStatusJPA=new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(expenseHeaderDTO.getVoucherStatusDTO().getVoucherStatusId());
			expenseHeaderJPA.setVoucherStatusJPA(voucherStatusJPA);
			
			expenseHeaderJPA.setStartDate(Convertor.stringToCalendar(expenseHeaderDTO.getStartDate(),Convertor.dateFormat));
			expenseHeaderJPA.setEndDate(Convertor.stringToCalendar(expenseHeaderDTO.getEndDate(),Convertor.dateFormat));
			expenseHeaderJPA.setPurpose(expenseHeaderDTO.getPurpose());
			
			if(Validation.validateForZero(expenseHeaderDTO.getModifiedBy())){
				expenseHeaderJPA.setModifiedBy(expenseHeaderDTO.getModifiedBy());
			}
			if(Validation.validateForZero(expenseHeaderDTO.getCreatedBy())){
				expenseHeaderJPA.setCreatedBy(expenseHeaderDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(expenseHeaderDTO.getCreatedDate())){
				expenseHeaderJPA.setCreatedDate(Convertor.stringToCalendar(expenseHeaderDTO.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(expenseHeaderDTO.getModifiedDate())){
				expenseHeaderJPA.setModifiedDate(Convertor.stringToCalendar(expenseHeaderDTO.getModifiedDate(),Convertor.dateFormatWithTime));
			}
		}
		return expenseHeaderJPA;
	}
	
	public static ExpenseDetailJPA setExpenseDetailDTOToJPA(ExpenseDetailDTO expenseDetailDTO) throws ParseException, IOException
	{
		ExpenseDetailJPA expenseDetailJPA=null;
		if(Validation.validateForNullObject(expenseDetailDTO)){
			expenseDetailJPA=new ExpenseDetailJPA();
			expenseDetailJPA.setExpenseDetailId(expenseDetailDTO.getExpenseDetailId());
			
			ExpenseCategoryJPA expenseCategoryJPA= new ExpenseCategoryJPA();
			expenseCategoryJPA.setExpCategoryId(expenseDetailDTO.getExpenseCategoryId());
			expenseDetailJPA.setExpenseCategoryJPA(expenseCategoryJPA);
			
			expenseDetailJPA.setDate(Convertor.stringToCalendar(expenseDetailDTO.getDate(),Convertor.dateFormat));
			expenseDetailJPA.setFromLocation(expenseDetailDTO.getFromLocation());
			expenseDetailJPA.setToLocation(expenseDetailDTO.getToLocation());
			expenseDetailJPA.setDescription(expenseDetailDTO.getDescription());
			if(Validation.validateForZero(expenseDetailDTO.getUnit())){
				expenseDetailJPA.setUnit(expenseDetailDTO.getUnit());
			}
			expenseDetailJPA.setAmount(expenseDetailDTO.getAmount());
			if(!expenseDetailDTO.getReceipt().isEmpty()){
				MultipartFile receipt= expenseDetailDTO.getReceipt();
				expenseDetailJPA.setFileName(receipt.getOriginalFilename());
			}
		}
		return expenseDetailJPA;
	}
	
	public static ExpenseDetailDTO setExpenseDetailJPAtoDTO(ExpenseDetailJPA expenseDetailJPA){
		ExpenseDetailDTO expenseDetailDTO=null;
		
		if(Validation.validateForNullObject(expenseDetailJPA)){
			expenseDetailDTO=new ExpenseDetailDTO(); 
			expenseDetailDTO.setExpenseDetailId(expenseDetailJPA.getExpenseDetailId());
			
			if(Validation.validateForNullObject(expenseDetailJPA.getExpenseCategoryJPA())){
				ExpenseCategoryDTO expenseCategoryDTO=new ExpenseCategoryDTO();
				expenseCategoryDTO.setExpenseCategoryId(expenseDetailJPA.getExpenseCategoryJPA().getExpCategoryId());
				expenseDetailDTO.setExpenseCategoryDTO(expenseCategoryDTO);
			}
			expenseDetailDTO.setDate(Convertor.calendartoString(expenseDetailJPA.getDate(),Convertor.dateFormat));
			expenseDetailDTO.setFromLocation(expenseDetailJPA.getFromLocation());
			expenseDetailDTO.setDescription(expenseDetailJPA.getDescription());
			
			if(Validation.validateForZero(expenseDetailJPA.getUnit())){
				expenseDetailDTO.setUnit(expenseDetailJPA.getUnit());
			}
			expenseDetailDTO.setToLocation(expenseDetailJPA.getToLocation());
			expenseDetailDTO.setAmount(expenseDetailJPA.getAmount());
			expenseDetailDTO.setFileName(expenseDetailJPA.getFileName());
		}
		return expenseDetailDTO;
	}

}
