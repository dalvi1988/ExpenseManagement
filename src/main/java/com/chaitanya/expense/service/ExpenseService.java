package com.chaitanya.expense.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.convertor.ExpenseConvertor;
import com.chaitanya.expense.dao.IExpenseDAO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expenseCategory.convertor.ExpenseCategoryConvertor;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.utility.Validation;

@Service("expenseService")
public class ExpenseService implements IExpenseService{
	
	@Autowired
	private IExpenseDAO expenseDAO;
	
	private Logger logger= LoggerFactory.getLogger(ExpenseService.class);
	

	private boolean validateExpenseMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof ExpenseHeaderDTO);
	}

	
	@Override
	public BaseDTO saveUpdateExpense(BaseDTO baseDTO) {
		logger.debug("ExpenseService: addExpense-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		try{
			ExpenseHeaderDTO expenseHeaderDTO = (ExpenseHeaderDTO)baseDTO;
			ExpenseHeaderJPA expenseHeaderJPA = ExpenseConvertor.setExpenseHeaderDTOToJPA(expenseHeaderDTO);
			List<ExpenseDetailJPA> expenseDetailJPAList=new ArrayList<>();
			
			for(ExpenseDetailDTO expenseDetailDTO: expenseHeaderDTO.getAddedExpenseDetailsDTOList()){
				ExpenseDetailJPA expenseDetailJPA = ExpenseConvertor.setExpenseDetailDTOToJPA(expenseDetailDTO);
				expenseDetailJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				expenseDetailJPAList.add(expenseDetailJPA);
			}
			
			for(ExpenseDetailDTO expenseDetailDTO: expenseHeaderDTO.getUpdatedExpenseDetailsDTOList()){
				ExpenseDetailJPA expenseDetailJPA = ExpenseConvertor.setExpenseDetailDTOToJPA(expenseDetailDTO);
				expenseDetailJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				expenseDetailJPAList.add(expenseDetailJPA);
			}
			expenseHeaderJPA.setExpenseDetailJPA(expenseDetailJPAList);
			
			ProcessHistoryJPA processHistoryJPA = ExpenseConvertor.setExpenseHeaderJPAtoProcessHistoryJPA(expenseHeaderJPA);
			processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
			List<ProcessHistoryJPA> processHistoryJPAList= new ArrayList<ProcessHistoryJPA>();
			processHistoryJPAList.add(processHistoryJPA);
			expenseHeaderJPA.setProcessHistoryJPA(processHistoryJPAList);
			
			if (Validation.validateForNullObject(expenseHeaderJPA)) {
				expenseHeaderJPA = expenseDAO.saveUpdateExpense(expenseHeaderJPA);
				if(Validation.validateForNullObject(expenseHeaderJPA)){
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(DataIntegrityViolationException e){
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
			logger.error("ExpenseService: Exception",e);
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: addExpense-End");
		return baseDTO;
	}

	@Override
	public List<ExpenseHeaderDTO> getDraftExpenseList(BaseDTO baseDTO) {
		logger.debug("ExpenseService: getDraftExpense-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
				List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getDraftExpenseList(expenseHeaderDTO);
				if(Validation.validateForNullObject(expenseHeaderJPAList)){
					expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
					for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
						ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
						expenseHeaderDTOList.add(expHeaderDTO);
					}
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: getDraftExpense-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getExpenseToBeApprove(BaseDTO baseDTO) {

		logger.debug("ExpenseService: getExpenseToBeApprove-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
				List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getExpenseToBeApprove(expenseHeaderDTO);
				if(Validation.validateForNullObject(expenseHeaderJPAList)){
					expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
					for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
						ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
						if(Validation.validateForNullObject(expenseHeaderJPA.getEmployeeJPA())){
							expHeaderDTO.setEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getEmployeeJPA()));
						}
						expenseHeaderDTOList.add(expHeaderDTO);
					}
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: getExpenseToBeApprove-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseDetailDTO> getExpenseDetailsByHeaderId(BaseDTO baseDTO) {

		logger.debug("ExpenseService: getExpenseDetailsByHeaderId-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		List<ExpenseDetailDTO> expenseDetailDTOList= null;
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
				List<ExpenseDetailJPA> expenseDetailsJPAList =expenseDAO.getExpenseDetailsByHeaderId(expenseHeaderDTO);
				if(Validation.validateForNullObject(expenseDetailsJPAList)){
					expenseDetailDTOList= new ArrayList<ExpenseDetailDTO>();
					for(ExpenseDetailJPA expenseDetailsJPA: expenseDetailsJPAList){
						ExpenseDetailDTO expDetailDTO=ExpenseConvertor.setExpenseDetailJPAtoDTO(expenseDetailsJPA);
						if(Validation.validateForNullObject(expenseDetailsJPA.getExpenseCategoryJPA()))
						{
							expDetailDTO.setExpenseCategoryDTO(ExpenseCategoryConvertor.setExpenseCategoryJPAtoDTO(expenseDetailsJPA.getExpenseCategoryJPA()));
						}
						expenseDetailDTOList.add(expDetailDTO);
					}
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: getExpenseDetailsByHeaderId-End");
		return  expenseDetailDTOList;
	}

	@Override
	public BaseDTO getExpense(BaseDTO baseDTO) {
		logger.debug("ExpenseService: getExpense-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
				ExpenseHeaderJPA expenseHeaderJPA =expenseDAO.getExpense(expenseHeaderDTO);
				if(Validation.validateForNullObject(expenseHeaderJPA)){
					expenseHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					List<ExpenseDetailDTO> expenseDetailDTOList= new ArrayList<ExpenseDetailDTO>();
					for(ExpenseDetailJPA expenseDetailJPA: expenseHeaderJPA.getExpenseDetailJPA()){
						ExpenseDetailDTO expenseDetailDTO=ExpenseConvertor.setExpenseDetailJPAtoDTO(expenseDetailJPA);
						expenseDetailDTOList.add(expenseDetailDTO);
					}
					expenseHeaderDTO.setAddedExpenseDetailsDTOList(expenseDetailDTOList);
					baseDTO=expenseHeaderDTO;
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: getExpense-End");
		return  baseDTO;
	}
	
	@Override
	public BaseDTO approveRejectExpenses(BaseDTO baseDTO) {
		logger.debug("ExpenseService: approveRejectExpenses-Start");
		if(validateExpenseMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
				ExpenseHeaderJPA expenseHeaderJPA =expenseDAO.approveRejectExpenses(expenseHeaderDTO);
				if(Validation.validateForNullObject(expenseHeaderJPA)){
					
					baseDTO=expenseHeaderDTO;
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseService: Exception",e);
		}
		logger.debug("ExpenseService: approveRejectExpenses-End");
		return  baseDTO;
	}
	
}
