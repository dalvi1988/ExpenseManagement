package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.Command;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.event.service.IEventService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AdvanceController {

	@Autowired 
	private IEventService eventService;
	
	private Logger logger= LoggerFactory.getLogger(AdvanceController.class);
	
	@RequestMapping(value="/advance",method=RequestMethod.GET)
	public ModelAndView advance() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			EventDTO eventDTO= new EventDTO();
			eventDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
			List<EventDTO> eventDTOList = eventService.findAllUnderCompany(eventDTO);
			model.addObject("eventList", mapper.writeValueAsString(eventDTOList));
			model.setViewName("advance/advanceJSP");
		}
		catch(Exception e){
			logger.error("AdvanceController: advance",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	
	@RequestMapping(value="/saveAdvance", method=RequestMethod.POST)
	public @ResponseBody EventDTO saveAdvance(@RequestBody EventDTO receivedEventDTO){
		EventDTO toBeSentEventDTO=null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedEventDTO.getEventId())){
				receivedEventDTO.setCommand(Command.ADD);
				receivedEventDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEventDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedEventDTO.setCommand(Command.UPDATE);
				receivedEventDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEventDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			receivedEventDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
			BaseDTO baseDTO=eventService.addEvent(receivedEventDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentEventDTO=(EventDTO)baseDTO;
				if(receivedEventDTO.getCommand().equals(Command.ADD)){
					toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentEventDTO=receivedEventDTO;
				toBeSentEventDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
			else if(Validation.validateForBusinessFailureStatus(baseDTO)){
				toBeSentEventDTO=receivedEventDTO;
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
