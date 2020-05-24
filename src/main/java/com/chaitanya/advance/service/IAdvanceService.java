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

	List<AdvanceDTO> getPendingAdvancesAtPaymentDesk(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getRejectedAdvanceList(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getApprovedAdvanceByEmp(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getPaymentDeskAdvances(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getPaidAdvances(BaseDTO baseDTO) throws ParseException;

	List<AdvanceDTO> getProcessedByMeAdvances(BaseDTO baseDTO) throws ParseException;

	Long getDraftAdvanceCount(BaseDTO baseDTO) throws ParseException;

	Long getPendingAdvanceCount(BaseDTO baseDTO) throws ParseException;

	Long getPaidAdvancesCount(BaseDTO baseDTO) throws ParseException;

	Long getRejectedAdvanceCount(BaseDTO baseDTO) throws ParseException;


}
