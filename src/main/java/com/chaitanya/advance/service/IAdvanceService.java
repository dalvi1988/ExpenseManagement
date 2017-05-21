package com.chaitanya.advance.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.event.model.EventDTO;

public interface IAdvanceService {

	BaseDTO addEvent(BaseDTO baseDTO) throws ParseException;

	List<EventDTO> findAllUnderCompany(BaseDTO baseDTO);

}
