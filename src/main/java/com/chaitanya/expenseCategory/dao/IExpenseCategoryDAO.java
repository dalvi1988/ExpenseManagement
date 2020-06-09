package com.chaitanya.expenseCategory.dao;

import java.util.List;

import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;

public interface IExpenseCategoryDAO {

	public ExpenseCategoryJPA add(ExpenseCategoryJPA department);


	public List<ExpenseCategoryJPA> getAllActiveExpenseCategoryByCompany(ExpenseCategoryDTO expenseCategoryDTO);


	List<ExpenseCategoryJPA> getAllExpenseCategoryByCompany(ExpenseCategoryDTO expenseCategoryDTO);
}
