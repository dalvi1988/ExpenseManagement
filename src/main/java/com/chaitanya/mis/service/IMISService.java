package com.chaitanya.mis.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.mis.model.ExpenseMISDTO;

public interface IMISService {

	List<ExpenseMISDTO> getAllExpensesByCompany(BaseDTO baseDTO) throws ParseException;

}
