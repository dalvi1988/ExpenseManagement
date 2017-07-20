package com.chaitanya.advance.convertor;

import java.text.ParseException;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.jpa.AdvanceJPA;
import com.chaitanya.jpa.AdvanceProcessHistoryJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.EventJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class AdvanceConvertor {
	
	public static AdvanceDTO setAdvanceJPAtoDTO(AdvanceJPA advanceJPA) throws ParseException{
		AdvanceDTO advanceDTO=null;
		if(Validation.validateForNullObject(advanceJPA)){
			advanceDTO=new AdvanceDTO(); 
			advanceDTO.setAdvanceDetailId(advanceJPA.getAdvanceDetailId());
			advanceDTO.setPurpose(advanceJPA.getPurpose());
			advanceDTO.setAmount(advanceJPA.getAmount());
			advanceDTO.setIsEvent(Convertor.convetStatusToBool(advanceJPA.getIsEvent()));
			
			if(Validation.validateForNullObject(advanceJPA.getEventJPA())){
				EventDTO eventDTO=new EventDTO();
				eventDTO.setEventId(advanceJPA.getEventJPA().getEventId());
				advanceDTO.setEventDTO(eventDTO);
			}
			
			if(Validation.validateForEmptyString(advanceJPA.getAdvanceNumber())){
				advanceDTO.setAdvanceNumber(advanceJPA.getAdvanceNumber());
			}
			if(Validation.validateForNullObject(advanceJPA.getCreatedBy())){
				advanceDTO.setCreatedBy(advanceJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(advanceJPA.getModifiedBy())){
				advanceDTO.setModifiedBy(advanceJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(advanceJPA.getCreatedDate())){
				advanceDTO.setCreatedDate(Convertor.calendartoString(advanceJPA.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(advanceJPA.getModifiedDate())){
				advanceDTO.setModifiedDate(Convertor.calendartoString(advanceJPA.getModifiedDate(),Convertor.dateFormatWithTime));
			}
		}
		return advanceDTO;
	}
	
	
	public static AdvanceJPA setAdvanceDTOToJPA(AdvanceDTO advanceDTO) throws ParseException
	{
		AdvanceJPA advanceJPA=null;
		if(Validation.validateForNullObject(advanceDTO)){
			advanceJPA=new AdvanceJPA();
			
			EmployeeJPA employeeJPA=new EmployeeJPA();
			employeeJPA.setEmployeeId(advanceDTO.getEmployeeDTO().getEmployeeId());
			advanceJPA.setEmployeeJPA(employeeJPA);
			
			advanceJPA.setAmount(advanceDTO.getAmount());
			advanceJPA.setPurpose(advanceDTO.getPurpose());
			advanceJPA.setIsEvent(Convertor.convertStatusToChar(advanceDTO.getIsEvent()));
			
			VoucherStatusJPA voucherStatusJPA=new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(advanceDTO.getVoucherStatusDTO().getVoucherStatusId());
			advanceJPA.setVoucherStatusJPA(voucherStatusJPA);
			
			if(advanceDTO.getIsEvent()== true){
				EventJPA eventJPA= new EventJPA();
				eventJPA.setEventId(advanceDTO.getEventDTO().getEventId());
				advanceJPA.setEventJPA(eventJPA);
			}
			
			if(Validation.validateForZero(advanceDTO.getAdvanceDetailId())){
				advanceJPA.setAdvanceDetailId(advanceDTO.getAdvanceDetailId());
			}
			
			if(Validation.validateForZero(advanceDTO.getModifiedBy())){
				advanceJPA.setModifiedBy(advanceDTO.getModifiedBy());
			}
			if(Validation.validateForZero(advanceDTO.getCreatedBy())){
				advanceJPA.setCreatedBy(advanceDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(advanceDTO.getCreatedDate())){
				advanceJPA.setCreatedDate(Convertor.stringToCalendar(advanceDTO.getCreatedDate(),Convertor.dateFormatWithTime));
			}
			if(Validation.validateForNullObject(advanceDTO.getModifiedDate())){
				advanceJPA.setModifiedDate(Convertor.stringToCalendar(advanceDTO.getModifiedDate(),Convertor.dateFormatWithTime));
			}
			
		}
		return advanceJPA;
	}
	
	public static AdvanceProcessHistoryJPA setAdvanceJPAtoProcessHistoryJPA(AdvanceJPA advanceJPA){
		AdvanceProcessHistoryJPA processHistoryJPA=null;
		
		if(Validation.validateForNullObject(advanceJPA)){
			processHistoryJPA=new AdvanceProcessHistoryJPA(); 
			processHistoryJPA.setVoucherStatusJPA(advanceJPA.getVoucherStatusJPA());
			processHistoryJPA.setProcessedBy(advanceJPA.getEmployeeJPA());
			processHistoryJPA.setProcessDate(advanceJPA.getCreatedDate());
			
		}
		return processHistoryJPA;
	}
}
