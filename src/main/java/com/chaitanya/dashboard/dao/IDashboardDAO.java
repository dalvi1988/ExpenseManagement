package com.chaitanya.dashboard.dao;

import java.util.List;

import com.chaitanya.dashboard.model.DashboardDTO;

public interface IDashboardDAO {


	List<DashboardDTO> totalAmountGroupByMonth(DashboardDTO dashboardDTO);

}
