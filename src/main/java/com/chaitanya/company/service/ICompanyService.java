package com.chaitanya.company.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.company.model.CompanyDTO;

public interface ICompanyService {

	BaseDTO addCompany(BaseDTO baseDTO);

	List<CompanyDTO> findAll();

	List<BranchDTO> findBranchOnCompany(BaseDTO baseDTO);


}
