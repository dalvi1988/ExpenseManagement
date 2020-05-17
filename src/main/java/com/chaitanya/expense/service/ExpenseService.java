package com.chaitanya.expense.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.advance.convertor.AdvanceConvertor;
import com.chaitanya.approvalFlow.convertor.ApprovalFlowConvertor;
import com.chaitanya.approvalFlow.dao.IApprovalFlowDAO;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.departmentHead.dao.IDepartmentHeadDAO;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.dao.IEmployeeDAO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.event.convertor.EventConvertor;
import com.chaitanya.expense.convertor.ExpenseConvertor;
import com.chaitanya.expense.dao.IExpenseDAO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expenseCategory.convertor.ExpenseCategoryConvertor;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.ProcessInstanceJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.FTPUtility;
import com.chaitanya.utility.Validation;
import com.chaitanya.utility.model.VoucherStatusDTO;

@Service("expenseService")
@Transactional(rollbackFor=Exception.class)
public class ExpenseService implements IExpenseService{
	
	@Autowired
	private IExpenseDAO expenseDAO;
	
	@Autowired
	private IApprovalFlowDAO approvalFlowDAO;
	
	@Autowired 
	private IEmployeeDAO employeeDAO;
	
	@Autowired
	private IDepartmentHeadDAO departmentHeadDAO;
	
	private Logger logger= LoggerFactory.getLogger(ExpenseService.class);
	

	private void validateExpenseDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof ExpenseHeaderDTO)){
			throw new IllegalArgumentException("Object expected of ExpenseHeaderDTO type.");
		}
			
	}

	
	@Override
	public BaseDTO saveUpdateExpense(BaseDTO baseDTO) throws Exception {
		logger.debug("ExpenseService: saveUpdateExpense-Start");
		validateExpenseDTO(baseDTO);
		
		ExpenseHeaderDTO expenseHeaderDTO = (ExpenseHeaderDTO)baseDTO;
		ExpenseHeaderJPA expenseHeaderJPA = ExpenseConvertor.setExpenseHeaderDTOToJPA(expenseHeaderDTO);
		//expenseHeaderJPA=expenseDAO.getExpense(expenseHeaderDTO);
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
			expenseDAO.saveUpdateExpense(expenseHeaderJPA);
			
			for(ExpenseDetailDTO expenseDetailDTO: expenseHeaderDTO.getDeletedExpenseDetailsDTOList()){
				ExpenseDetailJPA expenseDetailJPA= new ExpenseDetailJPA();
				expenseDetailJPA.setExpenseDetailId(expenseDetailDTO.getExpenseDetailId());
				expenseDAO.deleteExpenseDetail(expenseDetailJPA);
			}

			//Create process instance if voucher not saved as draft.
			if(expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId() != 1){
				if(! Validation.validateForEmptyString(expenseHeaderJPA.getVoucherNumber())){
					String voucherNumber = expenseDAO.generateVoucherNumber(expenseHeaderDTO);
					expenseHeaderJPA.setVoucherNumber(voucherNumber);
				}
				else{
					expenseHeaderJPA.setVoucherNumber(expenseHeaderJPA.getVoucherNumber()+"*");
				}
				EmployeeJPA employeeJPA= employeeDAO.getEmployeeByEmployeeID(expenseHeaderJPA.getEmployeeJPA());
				expenseHeaderJPA.setEmployeeJPA(employeeJPA);
				List<ApprovalFlowJPA> approvalFlowList = approvalFlowDAO.getEmployeeApprovalFlow(expenseHeaderJPA.getEmployeeJPA());
				ApprovalFlowJPA functionalApprovalFlow= getFunctionOrBranchApprovalFlow(approvalFlowList);// Check functional or branch approval flow
				ApprovalFlowJPA financeApprovalFlow = getFinanceApprovalFlow(approvalFlowList);
				if(!Validation.validateForNullObject(functionalApprovalFlow) || !Validation.validateForNullObject(financeApprovalFlow)){
					System.out.println("No Approval Flow");// Handle Exception
				}
				else {
					expenseDAO.updateProcessInstanceByApprovalFlow(expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId(),expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow,expenseHeaderDTO.getProcessedByEmployeeDTO());
					//expenseDAO.updateProcessInstance(expenseHeaderJPA,expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId(),null);
				}
			}
			
			//Add, Update,delete attachment
			addUpdateDeleteAttachment(expenseHeaderDTO,expenseHeaderJPA.getExpenseHeaderId());
			baseDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("ExpenseService: saveUpdateExpense-End");
		return baseDTO;
	}

	private void addUpdateDeleteAttachment(ExpenseHeaderDTO expenseHeaderDTO,Long expenseHeaderId) throws Exception {
		
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
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getDraftExpenseList(expenseHeaderDTO.getEmployeeDTO());
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
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
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getPendingExpenseList(expenseHeaderDTO.getEmployeeDTO());
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getEmployeeJPA())){
						expHeaderDTO.setEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getEmployeeJPA()));
					}
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt())){
							expHeaderDTO.setPendingAtEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getPendingAt()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy())){
							expHeaderDTO.setProcessedByEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getProcessInstanceJPA().getProcessedBy()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA())){
							expHeaderDTO.setVoucherStatusDTO(Convertor.setVoucherStatusJPAToDTO(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA()));
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
	public Long getPendingExpenseCount(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPendingExpenseCount-Start");
		validateExpenseDTO(baseDTO);
		Long pendingExpenseCount= -1L;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			pendingExpenseCount =expenseDAO.getPendingExpenseCount(expenseHeaderDTO.getEmployeeDTO());
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getPendingExpenseCount-End");
		return  pendingExpenseCount;
	}
	
	
	@Override
	public List<ExpenseHeaderDTO> getPendingExpensesAtPaymentDesk(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPendingExpensesAtPaymentDesk-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getPendingExpensesAtPaymentDesk(expenseHeaderDTO);
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getAdvanceJPA())){
							expHeaderDTO.setAdvanceDTO(AdvanceConvertor.setAdvanceJPAtoDTO(expenseHeaderJPA.getAdvanceJPA()));
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
		
		logger.debug("ExpenseService: getPendingExpensesAtPaymentDesk-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getPaymentDeskExpense(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPendingExpensesAtPaymentDesk-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getPaymentDeskExpense(expenseHeaderDTO);
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
					if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
						if(Validation.validateForNullObject(expenseHeaderJPA.getAdvanceJPA())){
							expHeaderDTO.setAdvanceDTO(AdvanceConvertor.setAdvanceJPAtoDTO(expenseHeaderJPA.getAdvanceJPA()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getEmployeeJPA())){
							expHeaderDTO.setEmployeeDTO(EmployeeConvertor.setEmployeeJPAToEmployeeDTO(expenseHeaderJPA.getEmployeeJPA()));
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
		
		logger.debug("ExpenseService: getPendingExpensesAtPaymentDesk-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getRejectedExpenseList(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getRejectedExpenseList-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getRejectedExpenseList(expenseHeaderDTO.getEmployeeDTO());
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
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA())){
							expHeaderDTO.setVoucherStatusDTO(Convertor.setVoucherStatusJPAToDTO(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA()));
						}
						if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA().getComment()))
							expHeaderDTO.setRejectionComment(expenseHeaderJPA.getProcessInstanceJPA().getComment());
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
	public Long getRejectedExpenseCount(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getRejectedExpenseCount-Start");
		validateExpenseDTO(baseDTO);
		Long rejectedExpenseCount= -1L;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			rejectedExpenseCount =expenseDAO.getRejectedExpenseCount(expenseHeaderDTO.getEmployeeDTO());
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getRejectedExpenseCount-End");
		return  rejectedExpenseCount;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getExpenseToBeApprove(BaseDTO baseDTO) throws ParseException {

		logger.debug("ExpenseService: getExpenseToBeApprove-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
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

		logger.debug("ExpenseService: getExpenseToBeApprove-End");
		return  expenseHeaderDTOList;
	}
	
	@Override
	public List<ExpenseHeaderDTO> getProcessedByMeExpense(BaseDTO baseDTO) throws ParseException {

		logger.debug("ExpenseService: getProcessedByMeExpense-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ProcessHistoryJPA> processHistoryJPAList =expenseDAO.getProcessedByMeExpense(expenseHeaderDTO);
			if(Validation.validateForNullObject(processHistoryJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ProcessHistoryJPA processHistoryJPA: processHistoryJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(processHistoryJPA.getExpenseHeaderJPA());
					ExpenseHeaderJPA expenseHeaderJPA=processHistoryJPA.getExpenseHeaderJPA();
					
					if(Validation.validateForNullObject(processHistoryJPA.getVoucherStatusJPA())){
						VoucherStatusDTO voucherStatusDTO = Convertor.setVoucherStatusJPAToDTO(processHistoryJPA.getVoucherStatusJPA());
						expHeaderDTO.setVoucherStatusDTO(voucherStatusDTO);
					}
					
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
					if(Validation.validateForNullObject(processHistoryJPA.getProcessDate()))
						expHeaderDTO.setProcessedDate(Convertor.calendartoString(processHistoryJPA.getProcessDate(),Convertor.dateFormatWithTime));
					
					if(Validation.validateForNullObject(processHistoryJPA.getComment()))
						expHeaderDTO.setRejectionComment(processHistoryJPA.getComment());
						
					expenseHeaderDTOList.add(expHeaderDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getProcessedByMeExpense-End");
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
				
				if(Validation.validateForNullObject(expenseHeaderJPA.getEventJPA())){
					expenseHeaderDTO.setEventDTO(EventConvertor.setEventJPAtoDTO(expenseHeaderJPA.getEventJPA()));
				}
				if(Validation.validateForNullObject(expenseHeaderJPA.getProcessInstanceJPA())){
					expenseHeaderDTO.setProcessInstanceId(expenseHeaderJPA.getProcessInstanceJPA().getProcessInstanceId());
				}
				if(Validation.validateForNullObject(expenseHeaderJPA.getAdvanceJPA())){
					expenseHeaderDTO.setAdvanceDTO(AdvanceConvertor.setAdvanceJPAtoDTO(expenseHeaderJPA.getAdvanceJPA()));
				}
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
			ExpenseHeaderJPA expenseHeaderJPA =expenseDAO.getExpenseHeaderById(expenseHeaderDTO);
			Integer statusID= expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA().getVoucherStatusId();
			
			VoucherStatusJPA voucherStatusJPA= new VoucherStatusJPA();
			
			if(Validation.validateForNullObject(expenseHeaderJPA)){
				if(expenseHeaderDTO.getVoucherStatusId() == 3){// Approved
					List<ApprovalFlowJPA> approvalFlowList = approvalFlowDAO.getEmployeeApprovalFlow(expenseHeaderJPA.getEmployeeJPA());
					ApprovalFlowJPA functionalApprovalFlow= getFunctionOrBranchApprovalFlow(approvalFlowList);// Check functional or branch approval flow
					ApprovalFlowJPA financeApprovalFlow = getFinanceApprovalFlow(approvalFlowList);
					if(!Validation.validateForNullObject(functionalApprovalFlow) || !Validation.validateForNullObject(financeApprovalFlow)){
						System.out.println("No Approval Flow");// Handle Exception
					}
					else {
						// update Voucher Status in ExpenseHEader
						voucherStatusJPA.setVoucherStatusId(statusID+1);
						expenseHeaderJPA.setVoucherStatusJPA(voucherStatusJPA);
						//expenseDAO.updateProcessInstance(expenseHeaderJPA,expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA().getVoucherStatusId(),expenseHeaderDTO.getProcessedByEmployeeDTO());
						expenseDAO.updateProcessInstanceByApprovalFlow(expenseHeaderJPA.getProcessInstanceJPA().getVoucherStatusJPA().getVoucherStatusId(),expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow,expenseHeaderDTO.getProcessedByEmployeeDTO());
					}
					
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
				VoucherStatusJPA voucherStatus = new VoucherStatusJPA();
				voucherStatus.setVoucherStatusId(voucherStatusJPA.getVoucherStatusId());
				processHistoryJPA.setVoucherStatusJPA(voucherStatus);
				processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				
				EmployeeJPA approveBy = new EmployeeJPA();
				approveBy.setEmployeeId(expenseHeaderDTO.getProcessedByEmployeeDTO().getEmployeeId());
				processHistoryJPA.setProcessedBy(approveBy);
				
				processHistoryJPA.setProcessDate(Calendar.getInstance());
				
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
	
	
	private ApprovalFlowJPA getFinanceApprovalFlow(List<ApprovalFlowJPA> approvalFlowList) {
		ApprovalFlowJPA financeApprovalFlowJPA =null;
		if(Validation.validateForNullObject(approvalFlowList)){
			for(ApprovalFlowJPA approvalFlowJPA : approvalFlowList){
				if(approvalFlowJPA.getIsBranchFlow()=='N' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					financeApprovalFlowJPA = approvalFlowJPA;
				}
			}
		}
		return financeApprovalFlowJPA;
	}


	private ApprovalFlowJPA getFunctionOrBranchApprovalFlow(List<ApprovalFlowJPA> approvalFlowList) {
		ApprovalFlowJPA approvalFlow =null;
		if(Validation.validateForNullObject(approvalFlowList)){
			for(ApprovalFlowJPA approvalFlowJPA : approvalFlowList){
				if(Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					approvalFlow = approvalFlowJPA;
					break;
				}
				else if(approvalFlowJPA.getIsBranchFlow()=='Y' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					approvalFlow = approvalFlowJPA;
					break;
				}
			}
		}

		return approvalFlow;
	}


	@Override
	public List<ExpenseHeaderDTO> getPaidExpenseList(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPaidExpenseList-Start");
		validateExpenseDTO(baseDTO);
		
		List<ExpenseHeaderDTO> expenseHeaderDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			List<ExpenseHeaderJPA> expenseHeaderJPAList =expenseDAO.getPaidExpenseList(expenseHeaderDTO.getEmployeeDTO());
			if(Validation.validateForNullObject(expenseHeaderJPAList)){
				expenseHeaderDTOList= new ArrayList<ExpenseHeaderDTO>();
				for(ExpenseHeaderJPA expenseHeaderJPA: expenseHeaderJPAList){
					ExpenseHeaderDTO expHeaderDTO=ExpenseConvertor.setExpenseHeaderJPAtoDTO(expenseHeaderJPA);
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
		
		logger.debug("ExpenseService: getPaidExpenseList-End");
		return  expenseHeaderDTOList;
	}

	@Override
	public Long getPaidExpenseCount(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getPaidExpenseCount-Start");
		validateExpenseDTO(baseDTO);
		
		Long paindExpenseCount= -1L;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			paindExpenseCount =expenseDAO.getPaidExpenseCount(expenseHeaderDTO.getEmployeeDTO());
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		
		logger.debug("ExpenseService: getPaidExpenseCount-End");
		return  paindExpenseCount;
	}


	@Override
	public List<ApprovalFlowDTO> viewVoucherApprovalFlow(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: viewVoucherApprovalFlow-Start");
		validateExpenseDTO(baseDTO);
		
		ExpenseHeaderDTO expenseHeaderDTO =(ExpenseHeaderDTO) baseDTO;
		ExpenseHeaderJPA expenseHeaderJPA= expenseDAO.getExpense(expenseHeaderDTO);
		EmployeeJPA employeeJPA= expenseHeaderJPA.getEmployeeJPA();
		List<ApprovalFlowJPA> approvalFlowList = approvalFlowDAO.getEmployeeApprovalFlow(employeeJPA);
		
		ApprovalFlowJPA functionalApprovalFlow= getFunctionOrBranchApprovalFlow(approvalFlowList);// Check functional or branch approval flow
		ApprovalFlowJPA financeApprovalFlow = getFinanceApprovalFlow(approvalFlowList);
		
		List<ApprovalFlowDTO> mergeApprovalFlow= new ArrayList<ApprovalFlowDTO>();
		ApprovalFlowDTO functionalAppprovalFlowDTO= updateEmployeeDetailsInApprovalFlow(functionalApprovalFlow,employeeJPA);
		ApprovalFlowDTO financeApprovalFlowDTO= updateEmployeeDetailsInApprovalFlow(financeApprovalFlow,employeeJPA);
		
		mergeApprovalFlow.add(functionalAppprovalFlowDTO);
		mergeApprovalFlow.add(financeApprovalFlowDTO);
		
		logger.debug("ExpenseService: viewVoucherApprovalFlow-End");
		return mergeApprovalFlow;
		
	}


	private ApprovalFlowDTO updateEmployeeDetailsInApprovalFlow(ApprovalFlowJPA approvalFlowJPA,EmployeeJPA employeeJPA) throws ParseException {
		ApprovalFlowDTO approvalFlowDTO= new ApprovalFlowDTO();
		approvalFlowDTO = ApprovalFlowConvertor.setApprovalFlowJPAToDTO(approvalFlowJPA);
		if(Validation.validateForNullObject(approvalFlowJPA.getLevel1())){
			EmployeeDTO level1EmployeeDTO= fetchEmployeeByAprpovalLevel(approvalFlowJPA.getLevel1(), employeeJPA);
			approvalFlowDTO.setLevel1EmployeeDTO(level1EmployeeDTO);
		}
		if(Validation.validateForNullObject(approvalFlowJPA.getLevel2())){
			EmployeeDTO level2EmployeeDTO= fetchEmployeeByAprpovalLevel(approvalFlowJPA.getLevel2(), employeeJPA);
			approvalFlowDTO.setLevel2EmployeeDTO(level2EmployeeDTO);
		}
		if(Validation.validateForNullObject(approvalFlowJPA.getLevel3())){
			EmployeeDTO level3EmployeeDTO= fetchEmployeeByAprpovalLevel(approvalFlowJPA.getLevel3(), employeeJPA);
			approvalFlowDTO.setLevel3EmployeeDTO(level3EmployeeDTO);
		}
		
		
		return approvalFlowDTO;
	}
	
	private EmployeeDTO fetchEmployeeByAprpovalLevel(Long level,EmployeeJPA employeeJPA) throws ParseException{
		EmployeeJPA approvalEmployeeJPA= null;
		if(level == -10){
			approvalEmployeeJPA= employeeDAO.getEmployeeByEmployeeID(employeeJPA.getReportingMgr());
		}
		else if(level == -11){
			approvalEmployeeJPA= employeeDAO.getEmployeeByEmployeeID(employeeJPA.getReportingMgr().getReportingMgr());
		}
		else if(level == -1){
			DepartmentHeadJPA departmentHeadJPA = departmentHeadDAO.findByDepartmentHeadIdBranchId(employeeJPA);
			approvalEmployeeJPA= departmentHeadJPA.getEmployeeJPA();
		}
		else{
			EmployeeJPA emplyee= new EmployeeJPA();
			emplyee.setEmployeeId(level);
			approvalEmployeeJPA= employeeDAO.getEmployeeByEmployeeID(emplyee);
		}
		return EmployeeConvertor.setEmployeeJPAToEmployeeDTO(approvalEmployeeJPA);
	}

	@Override
	public Long getDraftExpenseCount(BaseDTO baseDTO) throws ParseException {
		logger.debug("ExpenseService: getDraftExpenseCount-Start");
		validateExpenseDTO(baseDTO);
		Long expenseCount =-1L;
		if (Validation.validateForNullObject(baseDTO)) {
			ExpenseHeaderDTO expenseHeaderDTO=(ExpenseHeaderDTO) baseDTO;;
			expenseCount =expenseDAO.getDraftExpenseCount(expenseHeaderDTO.getEmployeeDTO());
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("ExpenseService: getDraftExpenseCount-End");
		return  expenseCount;
	}
	
}
