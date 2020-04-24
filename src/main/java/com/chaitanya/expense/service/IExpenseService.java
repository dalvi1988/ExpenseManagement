package com.chaitanya.expense.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;

public interface IExpenseService {

	BaseDTO saveUpdateExpense(BaseDTO baseDTO) throws ParseException, IOException, Exception;

	List<ExpenseHeaderDTO> getDraftExpenseList(BaseDTO baseDTO) throws ParseException;

	BaseDTO getExpense(BaseDTO baseDTO) throws ParseException;

	List<ExpenseHeaderDTO> getExpenseToBeApprove(BaseDTO baseDTO) throws ParseException;

	List<ExpenseDetailDTO> getExpenseDetailsByHeaderId(BaseDTO baseDTO);

	BaseDTO approveRejectExpenses(BaseDTO baseDTO) throws IOException, ParseException;

	List<ExpenseHeaderDTO> getPendingExpenseList(BaseDTO baseDTO) throws ParseException;

	List<ExpenseHeaderDTO> getRejectedExpenseList(BaseDTO baseDTO) throws ParseException;

	List<ExpenseHeaderDTO> getPendingExpensesAtPaymentDesk(BaseDTO baseDTO)throws ParseException;

	List<ExpenseHeaderDTO> getPaidExpenseList(BaseDTO baseDTO) throws ParseException;

	List<ExpenseHeaderDTO> getPaymentDeskExpense(BaseDTO baseDTO) throws ParseException;

	List<ExpenseHeaderDTO> getProcessedByMeExpense(BaseDTO baseDTO) throws ParseException;

	List<ApprovalFlowDTO> viewVoucherApprovalFlow(BaseDTO baseDTO) throws ParseException;

}
