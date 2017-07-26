package com.chaitanya.dashboard.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.dashboard.model.DashboardDTO;
import com.chaitanya.jpa.BranchJPA;

public interface IDashboardDAO {


	List totalAmountGroupByMonth(DashboardDTO dashboardDTO);

}
