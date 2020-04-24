package com.chaitanya.expense.dao;

import java.io.IOException;
import java.util.List;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;

public interface IExpenseDAO {

	public ExpenseHeaderJPA saveUpdateExpense(ExpenseHeaderJPA expenseHeader) throws IOException;
	
	public ExpenseHeaderJPA persistExpense(ExpenseHeaderJPA expenseHeader) throws IOException;

	public List<ExpenseHeaderJPA> getDraftExpenseList(ExpenseHeaderDTO expenseHeaderDTO);

	public ExpenseHeaderJPA getExpense(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getExpenseToBeApprove(
			ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseDetailJPA> getExpenseDetailsByHeaderId(ExpenseHeaderDTO expenseHeaderDTO);

	public ExpenseHeaderJPA getExpenseHeaderById(ExpenseHeaderDTO expenseHeaderDTO);

	public void updateProcessInstanceByApprovalFlow(int currentVoucerStatus, ExpenseHeaderJPA expenseHeaderJPA, ApprovalFlowJPA functionalApprovalFlow, ApprovalFlowJPA financeApprovalFlow, EmployeeDTO approvalEmployeeDTO);

	public String generateVoucherNumber(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getPendingExpenseList(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getRejectedExpenseList(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getPendingExpensesAtPaymentDesk(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getPaidExpenseList(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getPaymentDeskExpense(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ProcessHistoryJPA> getProcessedByMeExpense(ExpenseHeaderDTO expenseHeaderDTO);

	void deleteExpenseDetail(ExpenseDetailJPA expenseDetailJPA);


}
