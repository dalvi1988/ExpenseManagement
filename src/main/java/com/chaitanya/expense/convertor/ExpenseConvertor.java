package com.chaitanya.expense.convertor;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ExpenseConvertor {
	
	public static ExpenseHeaderDTO setExpenseHeaderJPAtoDTO(ExpenseHeaderJPA expenseHeaderJPA) throws ParseException{
		ExpenseHeaderDTO expenseHeaderDTO=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			expenseHeaderDTO=new ExpenseHeaderDTO(); 
			expenseHeaderDTO.setExpenseHeaderId(expenseHeaderJPA.getExpenseHeaderId());
			expenseHeaderDTO.setStartDate(Convertor.calendartoString(expenseHeaderJPA.getStartDate(),Convertor.dateFormat));
			expenseHeaderDTO.setEndDate(Convertor.calendartoString(expenseHeaderJPA.getEndDate(),Convertor.dateFormat));
			expenseHeaderDTO.setTitle(expenseHeaderJPA.getTitle());
			expenseHeaderDTO.setPurpose(expenseHeaderJPA.getPurpose());
			if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
				if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt())){
					EmployeeDTO pendingAtEmployeeDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt());
					expenseHeaderDTO.setPendingAtEmployeeDTO(pendingAtEmployeeDTO);
				}
				
				if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getApprovedBy())){
					EmployeeDTO approvedByEmployeeDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getApprovedBy());
					expenseHeaderDTO.setApprovedByEmployeeDTO(approvedByEmployeeDTO);
				}
			}
			if(Validation.validateForEmptyString(expenseHeaderJPA.getVoucherNumber())){
				expenseHeaderDTO.setVoucherNumber(expenseHeaderJPA.getVoucherNumber());
			}
		}
		return expenseHeaderDTO;
	}
	
	public static ProcessHistoryJPA setExpenseHeaderJPAtoProcessHistoryJPA(ExpenseHeaderJPA expenseHeaderJPA){
		ProcessHistoryJPA processHistoryJPA=null;
		
		if(Validation.validateForNullObject(expenseHeaderJPA)){
			processHistoryJPA=new ProcessHistoryJPA(); 
			//expenseHeaderDTO.setExpenseHeaderId(expenseHeaderJPA.getExpenseHeaderId());
			processHistoryJPA.setVoucherStatusJPA(expenseHeaderJPA.getVoucherStatusJPA());
			//processInstanceJPA.set
			processHistoryJPA.setProcessedBy(expenseHeaderJPA.getEmployeeJPA());
			//processHistoryJPA.setProcessDate(expenseHeaderJPA.getPurpose());
			
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
			
			VoucherStatusJPA voucherStatusJPA=new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(expenseHeaderDTO.getVoucherStatusDTO().getVoucherStatusId());
			expenseHeaderJPA.setVoucherStatusJPA(voucherStatusJPA);
			
			expenseHeaderJPA.setStartDate(Convertor.stringToCalendar(expenseHeaderDTO.getStartDate(),Convertor.dateFormat));
			expenseHeaderJPA.setEndDate(Convertor.stringToCalendar(expenseHeaderDTO.getEndDate(),Convertor.dateFormat));
			expenseHeaderJPA.setPurpose(expenseHeaderDTO.getPurpose());
			expenseHeaderJPA.setTitle(expenseHeaderDTO.getTitle());
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
