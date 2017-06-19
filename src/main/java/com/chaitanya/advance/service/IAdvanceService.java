package com.chaitanya.advance.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.base.BaseDTO;

public interface IAdvanceService {

	BaseDTO saveAdvance(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getDraftAdvanceList(BaseDTO baseDTO) throws ParseException;

	BaseDTO getAdvance(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getPendingAdvanceList(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getAdvanceToBeApprove(BaseDTO baseDTO) throws ParseException;
	
	BaseDTO approveRejectAdvance(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getAdvanceForPayment(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getRejectedAdvanceList(BaseDTO baseDTO) throws ParseException;


}
