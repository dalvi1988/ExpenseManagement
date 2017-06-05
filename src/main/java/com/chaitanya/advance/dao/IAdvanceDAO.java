package com.chaitanya.advance.dao;

import java.util.List;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.AdvanceJPA;

public interface IAdvanceDAO {
	AdvanceJPA saveUpdateAdvance(AdvanceJPA branchJPA);

	void updateProcessInstance(AdvanceJPA advanceJPA, int voucherStatusId,
			EmployeeDTO employeeDTO);

	String generateAdvanceNumber(AdvanceJPA advanceJPA);

	List<AdvanceJPA> getDraftAdvanceList(AdvanceDTO advanceDTO);

	AdvanceJPA getAdvance(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getPendingAdvanceList(AdvanceDTO advanceDTO);

	List<AdvanceJPA> getAdvanceToBeApprove(AdvanceDTO advanceDTO);

	AdvanceJPA approveRejectAdvance(AdvanceDTO advanceDTO);
}
