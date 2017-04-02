package com.chaitanya.expense.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;

public interface IExpenseService {

	BaseDTO addExpense(BaseDTO baseDTO);

	List<ExpenseHeaderDTO> getDraftExpenseList(BaseDTO baseDTO);

	BaseDTO getExpense(BaseDTO baseDTO);

}
