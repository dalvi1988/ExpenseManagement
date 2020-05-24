package com.chaitanya.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.advance.service.IAdvanceService;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.event.service.IEventService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdvanceController {

	@Autowired 
	private IEventService eventService;
	
	@Autowired 
	private IAdvanceService advanceService;
	
	private Logger logger= LoggerFactory.getLogger(AdvanceController.class);
	
	@RequestMapping(value="/advance",method=RequestMethod.GET)
	public ModelAndView advance(@RequestParam(value="advanceDetailId",required=false) Long advanceDetailId) throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		//ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			AdvanceDTO advanceDTO=new AdvanceDTO();
			if(Validation.validateForZero(advanceDetailId)){
				advanceDTO.setAdvanceDetailId(advanceDetailId);
				BaseDTO baseDTO= advanceService.getAdvance(advanceDTO);
				
				advanceDTO = (AdvanceDTO) baseDTO;
			}
			EventDTO eventDTO= new EventDTO();
			eventDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
			List<EventDTO> eventDTOList = eventService.findAllUnderCompany(eventDTO);
			model.addObject("eventList", eventDTOList);
			model.addObject("advance", advanceDTO);
			model.setViewName("advance/advanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: advance",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	
	@RequestMapping(value="/saveAdvance", method=RequestMethod.POST)
	public @ResponseBody AdvanceDTO saveAdvance(AdvanceDTO receivedAdvanceDTO){
		AdvanceDTO toBeSentEventDTO=null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			receivedAdvanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			if(!Validation.validateForNullObject(receivedAdvanceDTO.getAdvanceDetailId())){
				receivedAdvanceDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedAdvanceDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedAdvanceDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedAdvanceDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			BaseDTO baseDTO=advanceService.saveAdvance(receivedAdvanceDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentEventDTO=(AdvanceDTO)baseDTO;
				if(receivedAdvanceDTO.getVoucherStatusId() != 1){
					toBeSentEventDTO.setMessage(new StringBuilder("Your advance number: "+ toBeSentEventDTO.getAdvanceNumber()+"  has beed send for approval."));
				}
				else{
					toBeSentEventDTO.setMessage(new StringBuilder("Your advance has been saved in draft."));
				}
			}
			else{
				toBeSentEventDTO=receivedAdvanceDTO;
				toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			}
		}
		catch(Exception e){
			logger.error("AdvanceController: saveAdvance",e);
			toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return toBeSentEventDTO;
	}
	
	@RequestMapping(value="/viewDraftAdvance",method=RequestMethod.GET)
	public ModelAndView viewDraftExpense() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 advanceDTOList = advanceService.getDraftAdvanceList(advanceDTO);
			}
			
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/draftAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: viewDraftExpense",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/pendingAdvance",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPendingAdvance() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 advanceDTOList = advanceService.getPendingAdvanceList(advanceDTO);
			}
			
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/pendingAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getPendingAdvance",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/toBeApproveAdvance",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getAdvanceApprovalPage() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper= new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			advanceDTOList=advanceService.getAdvanceToBeApprove(advanceDTO);
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/approvalAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getAdvanceApprovalPage",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/processedByMeAdvances",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getProcessedByMeAdvances() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper= new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			advanceDTOList=advanceService.getProcessedByMeAdvances(advanceDTO);
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/processedByMeAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getAdvanceApprovalPage",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/approveRejectAdvance",method=RequestMethod.POST)
	public @ResponseBody BaseDTO approveRejectExpenses(@RequestBody AdvanceDTO advanceDTO) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		BaseDTO baseDTO= null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 advanceDTO.setProcessedByEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			 
		     baseDTO=advanceService.approveRejectAdvance(advanceDTO);
			 if(Validation.validateForSuccessStatus(baseDTO)){
				 AdvanceDTO advDTO=(AdvanceDTO)baseDTO;
				 if(advanceDTO.getVoucherStatusId() == 3){
					 baseDTO.setMessage(new StringBuilder("Advance Number "+ advDTO.getAdvanceNumber()+" has been approved\n."));
				 }
				 else if(advanceDTO.getVoucherStatusId() == 4){
					 baseDTO.setMessage(new StringBuilder("Advance Number "+ advDTO.getAdvanceNumber()+" has been rejected\n."));
				 }
				 
			 }else {
				 baseDTO=new BaseDTO();
				 baseDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			 }
		}
		catch(Exception e){
			logger.error("AdvanceController: approveRejectExpenses",e);
			baseDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return baseDTO;
	}
	@RequestMapping(value="/paymentDeskAdvance",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPaymentDeskAdvanceList() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper= new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			advanceDTOList=advanceService.getPaymentDeskAdvances(advanceDTO);
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/paymentAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getPaymentDeskAdvanceList",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/pendingAdvanceAtPaymentDesk",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPendingAdvancesAtPaymentDesk() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 advanceDTOList = advanceService.getPendingAdvancesAtPaymentDesk(advanceDTO);
			}
			
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/pendingPaymentAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getPendingAdvanceAtPaymentDesk",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/rejectedAdvance",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getRejectedAdvance() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 advanceDTOList = advanceService.getRejectedAdvanceList(advanceDTO);
			}
			
			model.addObject("advanceList", mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/rejectedAdvanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: getRejectedAdvance",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/paidAdvances",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getPaidAdvances() throws JsonGenerationException, JsonMappingException, IOException{
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdvanceDTO> advanceDTOList=null;
			AdvanceDTO advanceDTO=new AdvanceDTO();
			advanceDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 advanceDTOList = advanceService.getPaidAdvances(advanceDTO);
			}
			
			model.addObject("advanceList",mapper.writeValueAsString(advanceDTOList));
			model.setViewName("advance/paidAdvances");
		}
		catch(Exception e){
			logger.error("AdvanceController: getPaidAdvances",e);
			model.setViewName("others/505");
		}
		return model;
	}
}
