package com.chaitanya.company.dao;

import java.util.List;

import com.chaitanya.jpa.CompanyJPA;

public interface ICompanyDAO {

	public CompanyJPA add(CompanyJPA department);

	public List<CompanyJPA> findAll();
	
}
