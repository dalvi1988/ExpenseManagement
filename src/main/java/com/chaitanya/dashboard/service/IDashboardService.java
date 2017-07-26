package com.chaitanya.dashboard.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.dashboard.model.DashboardDTO;

public interface IDashboardService {

	List<DashboardDTO> totalAmountGroupByMonth(BaseDTO baseDTO) throws ParseException;
}
