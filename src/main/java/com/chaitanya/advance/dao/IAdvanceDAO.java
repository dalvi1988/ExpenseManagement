package com.chaitanya.advance.dao;

import java.util.List;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.AdvanceJPA;
import com.chaitanya.jpa.AdvanceProcessHistoryJPA;

public interface IAdvanceDAO {
	AdvanceJPA saveUpdateAdvance(AdvanceJPA branchJPA);

	void updateProcessInstance(AdvanceJPA advanceJPA, int voucherStatusId,
			EmployeeDTO employeeDTO);

	String generateAdvanceNumber(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getDraftAdvanceList(AdvanceDTO advanceDTO);

	AdvanceJPA getAdvance(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getPendingAdvanceList(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getAdvanceToBeApprove(AdvanceDTO advanceDTO);

	AdvanceJPA getAdvanceById(AdvanceDTO advanceDTO);
	
	List<AdvanceJPA> getPendingAdvancesAtPaymentDesk(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getRejectedAdvanceList(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getApprovedAdvanceByEmp(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getPaymentDeskAdvances(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getPaidAdvances(AdvanceDTO advanceDTO);

	List<AdvanceProcessHistoryJPA> getProcessedByMeAdvances(AdvanceDTO advanceDTO);

}
