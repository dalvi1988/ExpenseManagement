package com.chaitanya.advance.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.base.BaseDTO;

public interface IAdvanceService {

	BaseDTO saveAdvance(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getDraftAdvanceList(BaseDTO baseDTO);


}
