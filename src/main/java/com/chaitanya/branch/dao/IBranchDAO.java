package com.chaitanya.branch.dao;

import java.util.List;

import com.chaitanya.jpa.BranchJPA;

public interface IBranchDAO {
	public List<BranchJPA> findBrachOnCompany(BranchJPA company);

	public BranchJPA add(BranchJPA department);

	public List<BranchJPA> findAll();
}
