package com.chaitanya.approvalFlow.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;

public interface IApprovalFlowService {

	List<ApprovalFlowDTO> findFunctionalFlowUnderBranch(BaseDTO baseDTO);
/*
	BaseDTO addDepartmentHead(BaseDTO baseDTO);
*/
}
