package com.chaitanya.expense.dao;

import java.io.IOException;
import java.util.List;

import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ExpenseHeaderJPA;

public interface IExpenseDAO {

	public ExpenseHeaderJPA saveUpdateExpense(ExpenseHeaderJPA department) throws IOException;

	public List<ExpenseHeaderJPA> getDraftExpenseList(ExpenseHeaderDTO expenseHeaderDTO);

	public ExpenseHeaderJPA getExpense(ExpenseHeaderDTO expenseHeaderDTO);

	public List<ExpenseHeaderJPA> getExpenseToBeApprove(
			ExpenseHeaderDTO expenseHeaderDTO);

}
