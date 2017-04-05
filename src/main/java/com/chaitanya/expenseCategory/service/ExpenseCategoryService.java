package com.chaitanya.expenseCategory.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.expenseCategory.convertor.ExpenseCategoryConvertor;
import com.chaitanya.expenseCategory.dao.IExpenseCategoryDAO;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.utility.Validation;

@Service("expenseCategoryService")
public class ExpenseCategoryService implements IExpenseCategoryService{
	@Autowired
	private IExpenseCategoryDAO expenseCategoryDAO;
	
	private Logger logger= LoggerFactory.getLogger(ExpenseCategoryService.class);
	

	@Override
	public BaseDTO addExpenseCategory(BaseDTO baseDTO) {
		logger.debug("ExpenseCategoryService: addExpenseCategory-Start");
		if(validateExpenseCategoryMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of CompanyMasterDTO type.");
		}
		try{
			ExpenseCategoryJPA expenseCategoryJPA=ExpenseCategoryConvertor.setExpenseCategoryDTOToJPA((ExpenseCategoryDTO)baseDTO);
			if (Validation.validateForNullObject(expenseCategoryJPA)) {
				expenseCategoryJPA=expenseCategoryDAO.add(expenseCategoryJPA);
				if(Validation.validateForNullObject(expenseCategoryJPA)){
					baseDTO=ExpenseCategoryConvertor.setExpenseCategoryJPAtoDTO(expenseCategoryJPA);
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
			logger.error("ExpenseCategoryService: Exception",e);
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ExpenseCategoryService: Exception",e);
		}
		logger.debug("ExpenseCategoryService: addExpenseCategory-End");
		return baseDTO;
	}

	@Override
	public List<ExpenseCategoryDTO> findAll() {
		logger.debug("ExpenseCategoryService: findAll-Start");
		List<ExpenseCategoryDTO> expenseCategoryDTOList = null;
		List<ExpenseCategoryJPA> expenseCategoryJPAList = expenseCategoryDAO.findAll();
		
		if (Validation.validateCollectionForNullSize(expenseCategoryJPAList)) {
			expenseCategoryDTOList = new ArrayList<ExpenseCategoryDTO>();
			for (ExpenseCategoryJPA iterator : expenseCategoryJPAList) {
				ExpenseCategoryDTO expenseCategoryDTO = ExpenseCategoryConvertor.setExpenseCategoryJPAtoDTO(iterator);
				expenseCategoryDTOList.add(expenseCategoryDTO);
			}
		}
		logger.debug("ExpenseCategoryService: findAll-End");
		return expenseCategoryDTOList;
	}
	
	private boolean validateExpenseCategoryMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof ExpenseCategoryDTO);
	}
}
