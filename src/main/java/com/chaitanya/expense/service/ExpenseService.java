package com.chaitanya.expense.service;

import java.io.File;
import java.io.IOException;
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
import com.chaitanya.event.convertor.EventConvertor;
import com.chaitanya.expense.convertor.ExpenseConvertor;
import com.chaitanya.expense.dao.IExpenseDAO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expenseCategory.convertor.ExpenseCategoryConvertor;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.ProcessInstanceJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.FTPUtility;
import com.chaitanya.utility.Validation;

@Service("expenseService")
@Transactional(rollbackFor=Exception.class)
public class ExpenseService implements IExpenseService{
	
	@Autowired
	private IExpenseDAO expenseDAO;
	
	private Logger logger= LoggerFactory.getLogger(ExpenseService.class);
	

	private void validateExpenseDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof ExpenseHeaderDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
			
	}

	
	@Override
	public BaseDTO saveUpdateExpense(BaseDTO baseDTO) throws ParseException, IOException {
		logger.debug("ExpenseService: addExpense-Start");
		validateExpenseDTO(baseDTO);
		
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
			
			//Create process instance if voucher not saved as draft.
			if(expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId() != 1){
				if(! Validation.validateForEmptyString(expenseHeaderJPA.getVoucherNumber())){
					String voucherNumber = expenseDAO.generateVoucherNumber(expenseHeaderDTO);
					expenseHeaderJPA.setVoucherNumber(voucherNumber);
				}
				else{
					expenseHeaderJPA.setVoucherNumber(expenseHeaderJPA.getVoucherNumber()+"*");
				}
				expenseDAO.updateProcessInstance(expenseHeaderJPA,expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId(),null);
			}
			
			//Add, Update,delete attachment
			addUpdateDeleteAttachment(expenseHeaderDTO,expenseHeaderJPA.getExpenseHeaderId());
			baseDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("ExpenseService: addExpense-End");
		return baseDTO;
	}

	private void addUpdateDeleteAttachment(ExpenseHeaderDTO expenseHeaderDTO,Long expenseHeaderId) throws IllegalStateException, IOException {
		
		for(ExpenseDetailDTO expenseDetailDTO: expenseHeaderDTO.getAddedExpenseDetailsDTOList()){
			if(Validation.validateForNullObject(expenseDetailDTO.getReceipt()) && ! expenseDetailDTO.getReceipt().isEmpty()){
				File convFile = new File( expenseDetailDTO.getReceipt().getOriginalFilename());
				expenseDetailDTO.getReceipt().transferTo(convFile);
				FTPUtility.uploadFile(convFile,""+expenseHeaderId);
			}
		}
		
		for(ExpenseDetailDTO expenseDetailDTO: expenseHeaderDTO.getUpdatedExpenseDetailsDTOList()){
			if(Validation.validateForNullObject(expenseDetailDTO.getReceipt()) && ! expenseDetailDTO.getReceipt().isEmpty()){
				File convFile = new File( expenseDetailDTO.getReceipt().getOriginalFilename());
				expenseDetailDTO.getReceipt().transferTo(convFile);
				FTPUtility.uploadFile(convFile,""+expenseHeaderId);
			}
		}
	}


	@Override
	public List<ExpenseHeaderDTO> getDraftExpenseList(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getDraftExpense-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
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
		logger.debug("ExpenseService: getDraftExpense-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getPendingExpenseList(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPendingExpenseList-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getPendingExpenseList(expenseHeaderDTO);
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt())){
							expHeaderDTO.setPendingAtEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy())){
							expHeaderDTO.setProcessedByEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy()));
						}
					}
					expenseHeaderDTOList.add(expHeaderDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getPendingExpenseList-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getRejectedExpenseList(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getRejectedExpenseList-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getRejectedExpenseList(expenseHeaderDTO);
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt())){
							expHeaderDTO.setPendingAtEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy())){
							expHeaderDTO.setProcessedByEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy()));
						}
					}
					expenseHeaderDTOList.add(expHeaderDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getRejectedExpenseList-End");
		return  expenseHeaderDTOList;
	}
	@Override
	public List<ExpenseHeaderDTO> getExpenseToBeApprove(BaseDTO baseDTO) {

		logger.debug("ExpenseService: getExpenseToBeApprove-Start");
		validateExpenseDTO(baseDTO);
		
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
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy())){
							expHeaderDTO.setProcessedByEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getEventJPA())){
							expHeaderDTO.setEventDTO(EventConvertor.setEventJPAtoDTO(expenseHeaderJPA.getEventJPA()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getAdvanceJPA())){
							expHeaderDTO.setAdvanceDTO(AdvanceConvertor.setAdvanceJPAtoDTO(expenseHeaderJPA.getAdvanceJPA()));
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
		validateExpenseDTO(baseDTO);
		
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
	public BaseDTO getExpense(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getExpense-Start");
		validateExpenseDTO(baseDTO);
		
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
		logger.debug("ExpenseService: getExpense-End");
		return  baseDTO;
	}
	
	@Override
	public BaseDTO approveRejectExpenses(BaseDTO baseDTO) throws IOException, ParseException {
		logger.debug("ExpenseService: approveRejectExpenses-Start");
		validateExpenseDTO(baseDTO);
		
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			ExpenseHeaderJPA expenseHeaderJPA =expenseDAO.approveRejectExpenses(expenseHeaderDTO);
			Integer statusID= expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA().getVoucherStatusId();
			
			VoucherStatusJPA voucherStatusJPA= new VoucherStatusJPA();
			
			if(Validation.validateForNullObject(expenseHeaderJPA)){
				if(expenseHeaderDTO.getVoucherStatusId() == 3){// Approved
					// Set Voucher Status in ExpenseHEader
					voucherStatusJPA.setVoucherStatusId(statusID+1);
					expenseHeaderJPA.setVoucherStatusJPA(voucherStatusJPA);
					expenseDAO.updateProcessInstance(expenseHeaderJPA,expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA().getVoucherStatusId(),expenseHeaderDTO.getProcessedByEmployeeDTO());
					
				}
				else if(expenseHeaderDTO.getVoucherStatusId() == 4){//Rejected
					voucherStatusJPA.setVoucherStatusId(3);
					expenseHeaderJPA.setVoucherStatusJPA(voucherStatusJPA);
					
					ProcessInstanceJPA processInstanceJPA = expenseHeaderJPA.getProcessInstanceJPA();
					if(! Validation.validateForNullObject(processInstanceJPA)){
						processInstanceJPA= new ProcessInstanceJPA();
					}
					
					EmployeeJPA pendingAt = new EmployeeJPA();
					pendingAt.setEmployeeId(expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
					processInstanceJPA.setPendingAt(pendingAt);
					
					EmployeeJPA approveBy = new EmployeeJPA();
					approveBy.setEmployeeId(expenseHeaderDTO.getProcessedByEmployeeDTO().getEmployeeId());
					processInstanceJPA.setProcessedBy(approveBy);
					
					VoucherStatusJPA voucherStatus = new VoucherStatusJPA();
					voucherStatus.setVoucherStatusId(statusID+2);
					processInstanceJPA.setVoucherStatusJPA(voucherStatus);
					
					processInstanceJPA.setComment(expenseHeaderDTO.getRejectionComment());
					
					processInstanceJPA.setExpenseHeaderJPA(expenseHeaderJPA);
					expenseHeaderJPA.setProcessInstanceJPA(processInstanceJPA);
				}
				
				// Set Process History
				ProcessHistoryJPA processHistoryJPA = new ProcessHistoryJPA();
				processHistoryJPA.setVoucherStatusJPA(voucherStatusJPA);
				processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				
				EmployeeJPA approveBy = new EmployeeJPA();
				approveBy.setEmployeeId(expenseHeaderDTO.getProcessedByEmployeeDTO().getEmployeeId());
				processHistoryJPA.setProcessedBy(approveBy);
				
				processHistoryJPA.setComment(expenseHeaderDTO.getRejectionComment());
				List<ProcessHistoryJPA> processHistoryJPAList= new ArrayList<ProcessHistoryJPA>();
				processHistoryJPAList.add(processHistoryJPA);
				expenseHeaderJPA.setProcessHistoryJPA(processHistoryJPAList);
				
				
				baseDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("ExpenseService: approveRejectExpenses-End");
		return  baseDTO;
	}
	
}
