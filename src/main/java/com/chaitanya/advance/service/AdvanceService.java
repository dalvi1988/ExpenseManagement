package com.chaitanya.advance.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.event.convertor.EventConvertor;
import com.chaitanya.event.dao.IEventDAO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.jpa.EventJPA;
import com.chaitanya.utility.Validation;

@Service("advanceService")
@Transactional(rollbackFor=Exception.class)
public class AdvanceService implements IAdvanceService{
	@Autowired
	private IEventDAO eventDAO;
	
	private Logger logger= LoggerFactory.getLogger(AdvanceService.class);

	@Override
	public BaseDTO addEvent(BaseDTO baseDTO) throws ParseException {
		logger.debug("EventService: addEvent-Start");
		validateEventMasterDTO(baseDTO);
		try{
			EventJPA eventJPA=EventConvertor.setEventDTOToJPA((EventDTO)baseDTO);
			if (Validation.validateForNullObject(eventJPA)) {
				eventJPA=eventDAO.add(eventJPA);
				if(Validation.validateForNullObject(eventJPA)){
					baseDTO=EventConvertor.setEventJPAtoDTO(eventJPA);
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
			logger.error("EventService: addEvent ",e);
		}
		logger.debug("EventService: addEvent-End");
		return baseDTO;
	}

	@Override
	public List<EventDTO> findAllUnderCompany(BaseDTO baseDTO) {
		logger.debug("EventService: findAllUnderCompany-End");
		validateEventMasterDTO(baseDTO);
		EventDTO eventDTO=(EventDTO) baseDTO;
		List<EventDTO> eventDTOList = null;
		List<EventJPA> eventJPAList = eventDAO.findAllUnderCompany( eventDTO.getBranchDTO().getCompanyDTO());
		if (Validation.validateCollectionForNullSize(eventJPAList)) {
			eventDTOList = new ArrayList<EventDTO>();
			for (EventJPA eventJPA: eventJPAList) {
				EventDTO evtDTO = EventConvertor.setEventJPAtoDTO(eventJPA);
				eventDTOList.add(evtDTO);
			}
		}
		logger.debug("EventService: findAllUnderCompany-End");
		return eventDTOList;
	}
	
	private void validateEventMasterDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof EventDTO)){
			throw new IllegalArgumentException("Object expected of EventMasterDTO type.");
		}
	}
}
