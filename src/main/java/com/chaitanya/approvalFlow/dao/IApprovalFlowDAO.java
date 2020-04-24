package com.chaitanya.approvalFlow.dao;

import java.util.List;

import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.EmployeeJPA;



public interface IApprovalFlowDAO {

	public ApprovalFlowJPA add(ApprovalFlowJPA department);

	public List<ApprovalFlowJPA> findFunctionalFlowUnderBranch(BranchDTO branchDTO);

	public Integer deactivateFunctionalFlow(ApprovalFlowDTO approvalFlowDTO);

	public List<ApprovalFlowJPA> findFinanceFlowUnderBranch(BranchDTO branchDTO);

	public List<ApprovalFlowJPA> findBranchFlowUnderBranch(BranchDTO branchDTO);

	public ApprovalFlowDTO validateFunctionalFlow(ApprovalFlowDTO approvalFlowDTO);

	public ApprovalFlowDTO validateFinanceFlow(ApprovalFlowDTO approvalFlowDTO);

	public ApprovalFlowDTO validateBranchFlow(ApprovalFlowDTO approvalFlowDTO);

	public List<ApprovalFlowJPA> getEmployeeApprovalFlow(EmployeeJPA employeeJPA);
	
}
