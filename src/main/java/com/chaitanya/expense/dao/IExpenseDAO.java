package com.chaitanya.expense.dao;

import java.io.IOException;

import com.chaitanya.jpa.ExpenseHeaderJPA;

public interface IExpenseDAO {

	public ExpenseHeaderJPA add(ExpenseHeaderJPA department) throws IOException;

}
