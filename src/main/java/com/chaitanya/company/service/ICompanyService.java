package com.chaitanya.company.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.company.model.CompanyDTO;

public interface ICompanyService {

	BaseDTO addCompany(BaseDTO baseDTO) throws ParseException;

	List<CompanyDTO> findAll();


}
