package com.chaitanya.expenseCategory.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.expenseCategory.convertor.ExpenseCategoryConvertor;
import com.chaitanya.expenseCategory.dao.IExpenseCategoryDAO;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;
import com.chaitanya.utility.Validation;

@Service("expenseCategoryService")
@Transactional(rollbackFor=Exception.class)
public class ExpenseCategoryService implements IExpenseCategoryService{
	@Autowired
	private IExpenseCategoryDAO expenseCategoryDAO;
	
	private Logger logger= LoggerFactory.getLogger(ExpenseCategoryService.class);
	
	private void validateExpenseCategoryDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof ExpenseCategoryDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseCategoryDTO type.");
		}
	}

	@Override
	public BaseDTO addExpenseCategory(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseCategoryService: addExpenseCategory-Start");
		validateExpenseCategoryDTO(baseDTO);
		
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
		catch(ConstraintViolationException | DataIntegrityViolationException e){
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
			logger.error("ExpenseCategoryService: Exception",e);
		}
		logger.debug("ExpenseCategoryService: addExpenseCategory-End");
		return baseDTO;
	}

	@Override
	public List<ExpenseCategoryDTO> findExpenseCategoryByCompany(BaseDTO baseDTO) {
		logger.debug("ExpenseCategoryService: findExpenseCategoryByCompany-Start");

		validateExpenseCategoryDTO(baseDTO);
		
		List<ExpenseCategoryDTO> expenseCategoryDTOList = null;
		List<ExpenseCategoryJPA> expenseCategoryJPAList = expenseCategoryDAO.findExpenseCategoryByCompany((ExpenseCategoryDTO) baseDTO);
		
		if (Validation.validateCollectionForNullSize(expenseCategoryJPAList)) {
			expenseCategoryDTOList = new ArrayList<ExpenseCategoryDTO>();
			for (ExpenseCategoryJPA expenseCategoryJPA : expenseCategoryJPAList) {
				ExpenseCategoryDTO expenseCategoryDTO = ExpenseCategoryConvertor.setExpenseCategoryJPAtoDTO(expenseCategoryJPA);
				expenseCategoryDTOList.add(expenseCategoryDTO);
			}
		}
		logger.debug("ExpenseCategoryService: findExpenseCategoryByCompany-End");
		return expenseCategoryDTOList;
	}
	
}
