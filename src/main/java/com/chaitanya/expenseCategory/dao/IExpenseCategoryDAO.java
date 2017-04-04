package com.chaitanya.expenseCategory.dao;

import java.util.List;

import com.chaitanya.jpa.ExpenseCategoryJPA;

public interface IExpenseCategoryDAO {

	public ExpenseCategoryJPA add(ExpenseCategoryJPA department);

	public List<ExpenseCategoryJPA> findAll();
}
