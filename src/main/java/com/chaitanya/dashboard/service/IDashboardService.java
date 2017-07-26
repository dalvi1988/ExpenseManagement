package com.chaitanya.dashboard.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;

public interface IDashboardService {

	BaseDTO totalAmountGroupByMonth(BaseDTO baseDTO) throws ParseException;
}
