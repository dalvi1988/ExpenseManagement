package com.chaitanya.expense.dao;

import java.util.List;

import com.chaitanya.jpa.BranchJPA;

public interface IExpenseDAO {
	public List<BranchJPA> findBrachOnCompany(BranchJPA company);

	public BranchJPA add(BranchJPA department);

	public List<BranchJPA> findAll();
}
