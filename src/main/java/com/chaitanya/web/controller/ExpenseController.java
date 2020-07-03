package com.chaitanya.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.advance.service.IAdvanceService;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.customException.ApprovalFlowException;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.event.service.IEventService;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expense.service.IExpenseService;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.expenseCategory.service.IExpenseCategoryService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.MailServiceImpl;
import com.chaitanya.utility.Utility;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ExpenseController {
	
	@Autowired
	private IExpenseService expenseService;
	
	@Autowired 
	private IExpenseCategoryService expenseCategoryService;
	
	@Autowired 
	private IEventService eventService;
	
	@Autowired 
	private IAdvanceService advanceService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	
	private Logger logger= LoggerFactory.getLogger(ExpenseController.class);
	
	@RequestMapping(value="/expense",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView createExpense(@RequestParam(value="expenseHeaderId",required=false) Long expenseHeaderId) throws Exception{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper= new ObjectMapper();
		ExpenseHeaderDTO expenseHeaderDTO =new ExpenseHeaderDTO();
		List<ExpenseCategoryDTO> expenseCategoryDTOList= null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForZero(expenseHeaderId)){
			expenseHeaderDTO.setExpenseHeaderId(expenseHeaderId);
			BaseDTO baseDTO= expenseService.getExpense(expenseHeaderDTO);
			
			if(Validation.validateForSuccessStatus(baseDTO)){
				expenseHeaderDTO = (ExpenseHeaderDTO) baseDTO;
			}else{
				throw new Exception("");
			}
		}
		else{
			expenseHeaderDTO.setExpenseType("EmployeeExpense");
		}
		EventDTO eventDTO= new EventDTO();
		eventDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
		List<EventDTO> eventDTOList = eventService.findAllUnderCompany(eventDTO);
		model.addObject("eventList", eventDTOList);
		
		AdvanceDTO advanceDTO= new AdvanceDTO();
		advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
		List<AdvanceDTO> advanceDTOList= advanceService.getApprovedAdvanceByEmp(advanceDTO);
		model.addObject("advanceList", mapper.writeValueAsString(advanceDTOList));
		
		ExpenseCategoryDTO expenseCategoryDTO=new ExpenseCategoryDTO();
		expenseCategoryDTO.setCompanyDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO());
		expenseCategoryDTOList = expenseCategoryService.getAllActiveExpenseCategoryByCompany(expenseCategoryDTO);
		
		model.addObject("expenseDetailList", mapper.writeValueAsString(expenseHeaderDTO.getAddedExpenseDetailsDTOList()));
		model.addObject("expenseCategoryList", mapper.writeValueAsString(expenseCategoryDTOList));
		model.addObject("ExpenseHeaderDTO", expenseHeaderDTO);
		model.setViewName("expense/expenseJSP");
		return model;
	}
	
	@RequestMapping(value="/toBeApproveExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getExppenseAprrovalPage() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		model.setViewName("expense/approvalExpenseJSP");
		return model;
	}
	
	@RequestMapping(value="/processedByMeExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getApprovedByMeExpensePage() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		model.setViewName("expense/processedByMeExpenseJSP");
		return model;
	}
	
	@RequestMapping(value="/toBeApproveExpenseList",method=RequestMethod.POST)
	public @ResponseBody String getExpenseHeaderListForApproval() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			expenseHeaderDTOList=expenseService.getExpenseToBeApprove(expenseHeaderDTO);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return "{\"data\":"+mapper.writeValueAsString(expenseHeaderDTOList)+"}";
	}
	
	@RequestMapping(value="/processedByMeExpenseList",method=RequestMethod.POST)
	public @ResponseBody String getProcessedByMeExpenses() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			expenseHeaderDTOList=expenseService.getProcessedByMeExpense(expenseHeaderDTO);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return "{\"data\":"+mapper.writeValueAsString(expenseHeaderDTOList)+"}";
	}
	
	
	@RequestMapping(value="/expenseDetail",method=RequestMethod.POST)
	public @ResponseBody String getExpenseDetailListByHeaderId(@RequestBody ExpenseHeaderDTO receivedExpenseHeaderDTO) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		
		List<ExpenseDetailDTO> expenseDetailsDTOList=null;

		expenseDetailsDTOList=expenseService.getExpenseDetailsByHeaderId(receivedExpenseHeaderDTO);

		return "{\"data\":"+mapper.writeValueAsString(expenseDetailsDTOList)+"}";
	}
	
	@RequestMapping(value="/approveRejectExpense",method=RequestMethod.POST)
	public @ResponseBody BaseDTO approveRejectExpenses(@RequestBody ExpenseHeaderDTO expenseHeaderDTO) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		BaseDTO baseDTO= new BaseDTO();
		
		try{
			 LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
			 expenseHeaderDTO.setProcessedByEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			 
		     baseDTO=expenseService.approveRejectExpenses(expenseHeaderDTO);
			 if(Validation.validateForSuccessStatus(baseDTO)){
				 ExpenseHeaderDTO expHeaderDTO=(ExpenseHeaderDTO)baseDTO;
				 if(expenseHeaderDTO.getVoucherStatusId() == 3){
					    // For approval mail
					 try {
						final ExpenseHeaderDTO expenseHeaderDTOforMail = expHeaderDTO;
						if(Validation.validateForNullObject(expHeaderDTO.getPendingAtEmployeeDTO())) {
							EmployeeDTO pendingAtEmployeeDTO=(EmployeeDTO) employeeService.getEmployeeById(expHeaderDTO.getPendingAtEmployeeDTO());
							expenseHeaderDTOforMail.setPendingAtEmployeeDTO(pendingAtEmployeeDTO);
							expenseHeaderDTOforMail.setEmployeeDTO((EmployeeDTO) employeeService.getEmployeeById(expenseHeaderDTOforMail.getEmployeeDTO()));
							List<ExpenseDetailDTO> expenseDetailDTOList= expenseService.getExpenseDetailsByHeaderId(expenseHeaderDTOforMail);
							new Thread(new Runnable() {
								public void run() {
									try {
										// Send mail to approval
										mailService.sendApprovalMail(expenseHeaderDTOforMail,expenseDetailDTOList);
										} catch (Exception e) {
											logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
										}
									}
								}
							).start();
						}
					 }
					catch(Exception e) {
						logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
					}
					 baseDTO.setMessage(new StringBuilder("Voucher Number "+ expHeaderDTO.getVoucherNumber()+" has been approved\n."));
				 }
				 else if(expenseHeaderDTO.getVoucherStatusId() == 4){
					 baseDTO.setMessage(new StringBuilder("Voucher Number "+ expHeaderDTO.getVoucherNumber()+" has been rejected\n."));
						    // For Rejection mail
						 try {
							final ExpenseHeaderDTO expenseHeaderDTOforMail = expHeaderDTO;
							EmployeeDTO processedByEmployeeDTO=(EmployeeDTO) employeeService.getEmployeeById(expHeaderDTO.getProcessedByEmployeeDTO());
							expenseHeaderDTOforMail.setProcessedByEmployeeDTO(processedByEmployeeDTO);
							expenseHeaderDTOforMail.setRejectionComment(expenseHeaderDTO.getRejectionComment());
							expenseHeaderDTOforMail.setEmployeeDTO((EmployeeDTO) employeeService.getEmployeeById(expenseHeaderDTOforMail.getEmployeeDTO()));
							new Thread(new Runnable() {
								public void run() {
									try {
										// Send mail to approval
										mailService.sendRejectedMail(expenseHeaderDTOforMail);
										} catch (Exception e) {
											logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
										}
									}
								}
							).start();
						 }
						catch(Exception e) {
							logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
						}
				 }
			 }
			 else{
				 baseDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE) );
			 }
		}
		catch(IllegalStateException e) {
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
		}
		catch(ApprovalFlowException e) {
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
		}
		catch(Exception e){
			baseDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}

		return baseDTO;
	}
	
	@RequestMapping(value="/approveFromEmail",method=RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public @ResponseBody String approveRejectExpensesFromMail(ExpenseHeaderDTO expenseHeaderDTO) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		BaseDTO baseDTO= new BaseDTO();
		
		try{
			 EmployeeDTO processedByEmployeeDTO= new EmployeeDTO();
			 processedByEmployeeDTO.setEmployeeId(expenseHeaderDTO.getEmployeeId());
				
			 expenseHeaderDTO.setProcessedByEmployeeDTO(processedByEmployeeDTO);
			 expenseHeaderDTO.setStatus(Boolean.TRUE);// This status to used in service to check token or sessionId for mail confirmation
			 
		     baseDTO=expenseService.approveRejectExpenses(expenseHeaderDTO);
			 if(Validation.validateForSuccessStatus(baseDTO)){
				 ExpenseHeaderDTO expHeaderDTO=(ExpenseHeaderDTO)baseDTO;
				 if(expenseHeaderDTO.getVoucherStatusId() == 3){
						// For approval mail
					 try {
						final ExpenseHeaderDTO expenseHeaderDTOforMail = expHeaderDTO;
						if(Validation.validateForNullObject(expHeaderDTO.getPendingAtEmployeeDTO())) {
							EmployeeDTO pendingAtEmployeeDTO=(EmployeeDTO) employeeService.getEmployeeById(expHeaderDTO.getPendingAtEmployeeDTO());
							expenseHeaderDTOforMail.setPendingAtEmployeeDTO(pendingAtEmployeeDTO);
							expenseHeaderDTOforMail.setRejectionComment(expenseHeaderDTO.getRejectionComment());
							new Thread(new Runnable() {
								public void run() {
									try {
										expenseHeaderDTOforMail.setEmployeeDTO((EmployeeDTO) employeeService.getEmployeeById(expenseHeaderDTOforMail.getEmployeeDTO()));
										List<ExpenseDetailDTO> expenseDetailDTOList= expenseService.getExpenseDetailsByHeaderId(expenseHeaderDTOforMail);
									// Send mail to approval
									
										mailService.sendApprovalMail(expenseHeaderDTOforMail,expenseDetailDTOList);
									}
									catch(Exception e) {
										logger.debug("ExpenseService: approveFromEmail-sendingmail: "+ e);
									}
					
								}
							}).start();
						}
					 }
					 catch(Exception e) {
						 logger.debug("ExpenseService: approveFromEmail-sendingmail: "+ e);
					 }
					 return "<h1>Voucher Number "+ expHeaderDTO.getVoucherNumber()+" has been approved.</h1>";
					 
				 }
				 else if(expenseHeaderDTO.getVoucherStatusId() == 4){
					   // For Rejection mail
					 try {
						final ExpenseHeaderDTO expenseHeaderDTOforMail = expHeaderDTO;
						EmployeeDTO rejectedByEmployeeDTO=(EmployeeDTO) employeeService.getEmployeeById(expHeaderDTO.getProcessedByEmployeeDTO());
						expenseHeaderDTOforMail.setProcessedByEmployeeDTO(rejectedByEmployeeDTO);
						new Thread(new Runnable() {
							public void run() {

								try {
									expenseHeaderDTOforMail.setEmployeeDTO((EmployeeDTO) employeeService.getEmployeeById(expenseHeaderDTOforMail.getEmployeeDTO()));
									// Send mail to approval
									
									mailService.sendRejectedMail(expenseHeaderDTOforMail);
									} catch (Exception e) {
										logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
									}
								}
							}
							).start();
						 }
						catch(Exception e) {
							logger.debug("ExpenseService: approveRejectExpense-SendingMail: "+ e);
						}

					 return "<h1>Voucher Number "+ expHeaderDTO.getVoucherNumber()+" has been rejected.</h1>";
				 }
			 }
			 else{
				 return ApplicationConstant.BUSSINESS_FAILURE;
			 }
		}
		catch(IllegalStateException e) {
			return "<h1>"+e.getMessage()+"<h1>";
		}
		catch(ApprovalFlowException e) {
			return "<h1>"+e.getMessage()+"<h1>";
		}
		catch(Exception e){
			return "<h1>"+ApplicationConstant.SYSTEM_FAILURE+"<h1>";
		}

		return "<h1>"+ApplicationConstant.SYSTEM_FAILURE+"<h1>";
	}
	

	@RequestMapping(value="/saveExpense",method=RequestMethod.POST)
	public @ResponseBody ExpenseHeaderDTO saveExpense(@Valid @ModelAttribute("ExpenseHeaderDTO") ExpenseHeaderDTO receivedExpenseHeaderDTO,BindingResult result, @RequestParam("addedFiles") List<MultipartFile> addedFiles,@RequestParam("updatedFiles") List<MultipartFile> updatedFiles, String data) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		ExpenseHeaderDTO toBeSendExpenseHeaderDTO = null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			receivedExpenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(!Validation.validateForNullObject(receivedExpenseHeaderDTO.getExpenseHeaderId())){
				receivedExpenseHeaderDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedExpenseHeaderDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedExpenseHeaderDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedExpenseHeaderDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			
			// added List
			String addList=mapper.readTree(data).get("addList").toString();
			List<ExpenseDetailDTO> addedExpenseDetailDTOList =
				    mapper.readValue(addList, new TypeReference<List<ExpenseDetailDTO>>(){});
			for(int i=0; i< addedExpenseDetailDTOList.size(); i++){
				ExpenseDetailDTO expenseDetailDTO=addedExpenseDetailDTOList.get(i);
				MultipartFile receipt=addedFiles.get(i);
				expenseDetailDTO.setReceipt(receipt);
			}
			
			//updated list
			String updateList=mapper.readTree(data).get("updateList").toString();
			List<ExpenseDetailDTO> updatedExpenseDetailDTOList =
				    mapper.readValue(updateList, new TypeReference<List<ExpenseDetailDTO>>(){});
			for(int i=0; i< updatedExpenseDetailDTOList.size(); i++){
				ExpenseDetailDTO expenseDetailDTO=updatedExpenseDetailDTOList.get(i);
				MultipartFile receipt=updatedFiles.get(i);
				expenseDetailDTO.setReceipt(receipt);
			}
			
			String deletedList=mapper.readTree(data).get("deleteList").toString();
			List<ExpenseDetailDTO> deletedExpenseDetailDTOList =
				    mapper.readValue(deletedList, new TypeReference<List<ExpenseDetailDTO>>(){});
			
			receivedExpenseHeaderDTO.setAddedExpenseDetailsDTOList(addedExpenseDetailDTOList);
			receivedExpenseHeaderDTO.setUpdatedExpenseDetailsDTOList(updatedExpenseDetailDTOList);
			receivedExpenseHeaderDTO.setDeletedExpenseDetailsDTOList(deletedExpenseDetailDTOList);
			
			BaseDTO baseDTO= expenseService.saveUpdateExpense(receivedExpenseHeaderDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSendExpenseHeaderDTO=(ExpenseHeaderDTO)baseDTO;
				if(receivedExpenseHeaderDTO.getVoucherStatusId() == 2){
					toBeSendExpenseHeaderDTO.setMessage(new StringBuilder("Your voucher number: "+toBeSendExpenseHeaderDTO.getVoucherNumber()+" has been send for approval."));
					
					// For approval mail
					final ExpenseHeaderDTO expenseHeaderDTOforMail = toBeSendExpenseHeaderDTO;
					if(Validation.validateForNullObject(expenseHeaderDTOforMail.getPendingAtEmployeeDTO())) {
						EmployeeDTO pendingAtEmployeeDTO=(EmployeeDTO) employeeService.getEmployeeById(toBeSendExpenseHeaderDTO.getPendingAtEmployeeDTO());
						expenseHeaderDTOforMail.setPendingAtEmployeeDTO(pendingAtEmployeeDTO);
						expenseHeaderDTOforMail.setEmployeeDTO(receivedExpenseHeaderDTO.getEmployeeDTO());
						List<ExpenseDetailDTO> expenseDetailDTOList= expenseService.getExpenseDetailsByHeaderId(expenseHeaderDTOforMail);
						new Thread(new Runnable() {
							public void run() {
								// Send mail to approval
								try {
									mailService.sendApprovalMail(expenseHeaderDTOforMail,expenseDetailDTOList);
								}
								catch(Exception e) {
									logger.debug("ExpenseService: saveUpdateExpense-sendingmail: "+ e);
								}
				
							}
						}).start();
					}
				}
				else{
					toBeSendExpenseHeaderDTO.setMessage(new StringBuilder("Your voucher has been saved in draft."));
				}
			}
			else{
				toBeSendExpenseHeaderDTO=receivedExpenseHeaderDTO;
			}
		}
		catch(ApprovalFlowException e) {
			toBeSendExpenseHeaderDTO = receivedExpenseHeaderDTO;
			toBeSendExpenseHeaderDTO.setMessage(new StringBuilder(e.getMessage()));
		}
		catch(Exception e){
			logger.error("ExpenseController: saveExpense",e);
			toBeSendExpenseHeaderDTO = receivedExpenseHeaderDTO;
			toBeSendExpenseHeaderDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			toBeSendExpenseHeaderDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}

		return toBeSendExpenseHeaderDTO;
	}
	
	@RequestMapping(value="/viewDraftExpense",method=RequestMethod.GET)
	public ModelAndView viewDraftExpense() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getDraftExpenseList(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/draftExpensesJSP");
		}
		catch(Exception e){
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/pendingExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPendingExpense() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getPendingExpenseList(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/pendingExpensesJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: getPendingExpense",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/paymentDeskExpenses",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPaymentDeskExpense() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getPaymentDeskExpense(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/paymentDeskExpenseJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: paymentDeskExpenseList",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/pendingExpensesAtPaymentDesk",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPendingExpensesAtPaymentDesk() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getPendingExpensesAtPaymentDesk(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/pendingPaymentExpenseJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: pendingExpensesAtPaymentDesk",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/rejectedExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getRejectedExpense() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getRejectedExpenseList(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/rejectedExpensesJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: getRejectedExpense",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/paidExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPaidExpense() {
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getPaidExpenseList(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("expense/paidExpensesJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: getPaidExpense",e);
			model.setViewName("others/505");
		}
		return model;
	}

	
	@RequestMapping(value="/getFile", method = RequestMethod.GET)
	public void getFile(String fileName, String voucherId,
	    HttpServletResponse response) {
	    try {
	    	response.setContentType("application/octet-stream");
	    	// set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	        		fileName);
	        response.setHeader(headerKey, headerValue);
	        LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String drive= environment.getProperty("fileDrive");
			String filePath=drive+"/"+user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId()+"/"+voucherId;
	      // get your file as InputStream
	      //InputStream	inputStream =FTPUtility.retriveFile("Attachment/"+voucherId+"/"+fileName);
	        InputStream inputStream = new FileInputStream(filePath+"/"+fileName);
	      // copy it to response's OutputStream
	      IOUtils.copy(inputStream, response.getOutputStream());
	      response.flushBuffer();
	    } catch (IOException ex) {
	      logger.error("Error writing file to output stream. Filename was '{}'", ex);
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}
	
	// View Approval Flow for Voucher
	@RequestMapping(value = "/viewVoucherApprovalFlow", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView viewVoucherApprovalFlow(@RequestParam(value="employeeId",required=false) Long employeeId,
			@RequestParam(value="expenseHeaderId",required=false) Long expenseHeaderId) {

		ModelAndView model = new ModelAndView();
		try{
		// check if user is login
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				model.addObject("username", userDetail.getUsername());
				ExpenseHeaderDTO expenseHeaderDTO= new ExpenseHeaderDTO();
				EmployeeDTO employeeDTO= new EmployeeDTO();
				employeeDTO.setEmployeeId(employeeId);
				expenseHeaderDTO.setExpenseHeaderId(expenseHeaderId);
				expenseHeaderDTO.setEmployeeDTO(employeeDTO);
				List<ApprovalFlowDTO> approvalFlowDTOList = expenseService.viewVoucherApprovalFlow(expenseHeaderDTO);
				
				model.addObject("approvalFlowList",approvalFlowDTOList);
			}
	
			model.setViewName("expense/viewVoucherApprovalFlowJSP");
		}catch(Exception e){
			logger.error("ExpenseController: viewVoucherApprovalFlow",e);
			model.setViewName("others/505");
		}
		
		return model;
	}
	
	@RequestMapping(value="/accountingEntries",method=RequestMethod.GET)
	public @ResponseBody ModelAndView fetchAccountingEntries() {
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.fetchAccountingEntries(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("accounting/accountingEntryJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: fetchAccountingEntries",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/fetchedAccountingEntries",method=RequestMethod.GET)
	public @ResponseBody ModelAndView fetchedAccountingEntries() {
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.fetchedAccountingEntries(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("accounting/fetchedAccountingEntryJSP");
		}
		catch(Exception e){
			logger.error("ExpenseController: fetchedAccountingEntries",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/exportAccountingEntry",method= {RequestMethod.POST,RequestMethod.GET})
	public HttpEntity<byte[]> exportAccountingEntries(@RequestParam ArrayList<Long> expenseHeaderIds) throws FileNotFoundException {
		
		try{
			ExpenseHeaderDTO expenseHeaderDTO= new ExpenseHeaderDTO();
			StringBuilder concanatedId= Utility.convertArrayListToCommaString(expenseHeaderIds);
			expenseHeaderDTO.setMessage(concanatedId);//Used BaseDTO message variable instead of creating new
			byte[] fileData= expenseService.exportAccountingEntries(expenseHeaderDTO);
	
			HttpHeaders header= new HttpHeaders();
			header.setContentType(new MediaType("application","vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=AccountingEntry_"+Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime)+".xlsx");
			header.set("Access-Control-Allow-Origin", "Content-Disposition");
			
			header.setContentLength(fileData.length);
			return new HttpEntity<byte[]>(fileData,header);
		}
		catch(Exception e){
			logger.error("ExpenseController: exportAccountingEntry",e);
		}
		return null;
	}
	
	@RequestMapping(value="/downloadLog", method = RequestMethod.GET)
	public void downloadLog(
	    HttpServletResponse response) {
	    try {
	    	response.setContentType("text/plain");
	    	// set headers for the response
	        String headerKey = "Content-Disposition";
	        String fileName="error.log";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	        		fileName);
	        response.setHeader(headerKey, headerValue);
	        String drive= environment.getProperty("fileDrive");
	      // get your file as InputStream
	      //InputStream	inputStream =FTPUtility.retriveFile("Attachment/"+voucherId+"/"+fileName);
	        InputStream inputStream = new FileInputStream(drive+"/error.log");
	      // copy it to response's OutputStream
	      IOUtils.copy(inputStream, response.getOutputStream());
	      response.flushBuffer();
	    } catch (Exception ex) {
	      logger.error("Error writing file to output stream. Filename was '{}'", ex);
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}
}
