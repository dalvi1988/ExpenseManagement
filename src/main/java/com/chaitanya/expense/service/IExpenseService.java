package com.chaitanya.expense.service;

import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;

public interface IExpenseService {

	BaseDTO saveUpdateExpense(BaseDTO baseDTO);

	List<ExpenseHeaderDTO> getDraftExpenseList(BaseDTO baseDTO);

	BaseDTO getExpense(BaseDTO baseDTO);

	List<ExpenseHeaderDTO> getExpenseToBeApprove(BaseDTO baseDTO);

}
