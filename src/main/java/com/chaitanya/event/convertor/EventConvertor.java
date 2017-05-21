package com.chaitanya.event.convertor;

import java.text.ParseException;

import com.chaitanya.event.model.EventDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.EventJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class EventConvertor {
	
	public static EventDTO setEventJPAtoDTO(EventJPA eventJPA){
		EventDTO eventDTO=null;
		if(Validation.validateForNullObject(eventJPA)){
			eventDTO=new EventDTO(); 
			eventDTO.setEventId(eventJPA.getEventId());
			eventDTO.setEventName(eventJPA.getEventName());
			eventDTO.setEventCode(eventJPA.getEventCode());
			if(Validation.validateForNullObject(eventJPA.getCreatedBy())){
				eventDTO.setCreatedBy(eventJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(eventJPA.getModifiedBy())){
				eventDTO.setModifiedBy(eventJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(eventJPA.getCreatedDate())){
				eventDTO.setCreatedDate(Convertor.calendartoString(eventJPA.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(eventJPA.getModifiedDate())){
				eventDTO.setModifiedDate(Convertor.calendartoString(eventJPA.getModifiedDate(),Convertor.dateFormatWithTime));
			}
			eventDTO.setStatus(Convertor.convetStatusToBool(eventJPA.getStatus()));
		}
		return eventDTO;
	}
	
	
	public static EventJPA setEventDTOToJPA(EventDTO eventDTO) throws ParseException
	{
		EventJPA eventJPA=null;
		if(Validation.validateForNullObject(eventDTO)){
			eventJPA=new EventJPA();
			if(Validation.validateForZero(eventDTO.getEventId())){
				eventJPA.setEventId(eventDTO.getEventId());
			}
			if(Validation.validateForZero(eventDTO.getModifiedBy())){
				eventJPA.setModifiedBy(eventDTO.getModifiedBy());
			}
			if(Validation.validateForZero(eventDTO.getCreatedBy())){
				eventJPA.setCreatedBy(eventDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(eventDTO.getCreatedDate())){
				eventJPA.setCreatedDate(Convertor.stringToCalendar(eventDTO.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(eventDTO.getModifiedDate())){
				eventJPA.setModifiedDate(Convertor.stringToCalendar(eventDTO.getModifiedDate(),Convertor.dateFormatWithTime));
			}
			
			BranchJPA branchJPA= new BranchJPA();
			branchJPA.setBranchId(eventDTO.getBranchDTO().getBranchId());
			eventJPA.setBranchJPA(branchJPA);
			
			eventJPA.setEventCode(eventDTO.getEventCode());
			eventJPA.setEventName(eventDTO.getEventName());
			eventJPA.setStatus(Convertor.convertStatusToChar(eventDTO.getStatus()));
		}
		return eventJPA;
	}
}
