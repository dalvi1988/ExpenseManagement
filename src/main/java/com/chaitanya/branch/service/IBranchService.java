package com.chaitanya.branch.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public interface IBranchService {

	BaseDTO addBranch(BaseDTO baseDTO) throws ParseException;

	List<BranchDTO> findAllBranchUnderCompany(BaseDTO baseDTO);

}
