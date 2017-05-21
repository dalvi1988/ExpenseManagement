package com.chaitanya.branch.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.BranchJPA;

public interface IBranchDAO {
	public List<BranchJPA> findAllBranchUnderCompany(CompanyDTO conpanyDTO);

	public BranchJPA add(BranchJPA department);

}
