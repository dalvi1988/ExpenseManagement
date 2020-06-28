package com.chaitanya.mis.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.advance.convertor.AdvanceConvertor;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.expense.dao.IExpenseDAO;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.mis.convertor.MISConvertor;
import com.chaitanya.mis.model.ExpenseMISDTO;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

@Service
@Transactional(rollbackFor=Exception.class)
public class MISService implements IMISService{
	
	private Logger logger= LoggerFactory.getLogger(MISService.class);
	
	@Autowired
	private IExpenseDAO expenseDAO;
	/**
	 * For MIS
	 */
	@Override
	public List<ExpenseMISDTO> getAllExpensesByCompany(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getAllExpensesByCompany-Start");
		//validateExpenseDTO(baseDTO);
		
		List<ExpenseMISDTO> expenseMISDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseMISDTO misDTO=(ExpenseMISDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getAllExpensesByCompany(misDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO());
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseMISDTOList= new ArrayList<ExpenseMISDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseMISDTO expenseMISDTO=MISConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getAdvanceJPA())){
						expenseMISDTO.setAdvanceDTO(AdvanceConvertor.setAdvanceJPAtoDTO(expenseHeaderJPA.getAdvanceJPA()));
					}
					if(Validation.validateForNullObject(expenseHeaderJPA.getEmployeeJPA())){
						expenseMISDTO.setEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getEmployeeJPA()));
					}
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA())){
							expenseMISDTO.setVoucherStatusDTO(Convertor.setVoucherStatusJPAToDTO(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA()));
						}
					}
					expenseMISDTO.setBranchName(expenseHeaderJPA.getEmployeeJPA().getBranchJPA().getBranchName());
					expenseMISDTO.setDepartmentName(expenseHeaderJPA.getEmployeeJPA().getDepartmentJPA().getDeptName());
					
					expenseMISDTOList.add(expenseMISDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getAllExpensesByCompany-End");
		return  expenseMISDTOList;
	}


}
