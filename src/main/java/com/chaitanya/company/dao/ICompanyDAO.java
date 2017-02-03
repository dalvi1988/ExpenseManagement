package com.chaitanya.company.dao;

import java.util.List;

import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.login.model.LoginDTO;

public interface ICompanyDAO {
	public List<BranchJPA> findBrachOnCompany(CompanyJPA company);

	public CompanyJPA add(CompanyJPA department);

	public List<CompanyJPA> findAll();
	
}
