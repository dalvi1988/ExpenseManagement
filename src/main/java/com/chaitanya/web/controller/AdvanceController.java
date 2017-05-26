package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@Controller
public class AdvanceController {

	@Autowired 
	private IEventService eventService;
	
	@Autowired 
	private IAdvanceService advanceService;
	
	private Logger logger= LoggerFactory.getLogger(AdvanceController.class);
	
	@RequestMapping(value="/advance",method=RequestMethod.GET)
	public ModelAndView advance() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		//ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			EventDTO eventDTO= new EventDTO();
			eventDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
			List<EventDTO> eventDTOList = eventService.findAllUnderCompany(eventDTO);
			model.addObject("eventList", eventDTOList);
			AdvanceDTO advanceDTO=new AdvanceDTO();
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
		System.out.println(receivedAdvanceDTO);
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
				if(receivedAdvanceDTO.getVoucherStatusId() == 2){
					toBeSentEventDTO.setMessage(new StringBuilder("Your advance number: "+ toBeSentEventDTO.getAdvanceNumber()+"  has beed send for approval."));
				}
				else{
					toBeSentEventDTO.setMessage(new StringBuilder("Your voucher has been saved in draft."));
				}
			}
			else{
				toBeSentEventDTO=receivedAdvanceDTO;
				toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			}
		}
		catch(Exception e){
			logger.error("EventController: addEvent",e);
			toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return toBeSentEventDTO;
	}
	
}
