package com.chaitanya.approvalFlow.dao;

import java.util.List;

import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;



public interface IApprovalFlowDAO {

	public ApprovalFlowJPA add(ApprovalFlowJPA department);

	public List<ApprovalFlowJPA> findFunctionalFlowUnderBranch(BranchDTO branchDTO);

	public Integer deactivateFunctionalFlow(ApprovalFlowDTO approvalFlowDTO);
	
}
