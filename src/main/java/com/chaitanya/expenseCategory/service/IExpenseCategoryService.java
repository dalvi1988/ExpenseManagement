package com.chaitanya.expenseCategory.service;

import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;

public interface IExpenseCategoryService {

	BaseDTO addExpenseCategory(BaseDTO baseDTO);

	List<ExpenseCategoryDTO> findAll();

}
