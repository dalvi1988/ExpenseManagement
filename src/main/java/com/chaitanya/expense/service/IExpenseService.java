package com.chaitanya.expense.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public interface IExpenseService {

	BaseDTO addBranch(BaseDTO baseDTO);

	List<BranchDTO> findAll();

	List<BranchDTO> findBranchOnCompany(BaseDTO baseDTO);

}
