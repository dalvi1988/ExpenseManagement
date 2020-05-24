package com.chaitanya.dashboard.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.dashboard.dao.IDashboardDAO;
import com.chaitanya.dashboard.model.DashboardDTO;
import com.chaitanya.utility.Validation;

@Service("dashboardService")
@Transactional(rollbackFor=Exception.class)
public class DashboardService implements IDashboardService{
	@Autowired
	private IDashboardDAO dashboardDAO;
	
	private Logger logger= LoggerFactory.getLogger(DashboardService.class);

	@Override
	public List<DashboardDTO> totalAmountGroupByMonth(BaseDTO baseDTO) throws ParseException, IOException {
		logger.debug("DashboardService: totalAmountGroupByMonth-Start");
		validateDashboardDTO(baseDTO);
		List<DashboardDTO> list=null;
		DashboardDTO dashboardDTO=(DashboardDTO)baseDTO;
		if (Validation.validateForNullObject(dashboardDTO)) {
			list=dashboardDAO.totalAmountGroupByMonth(dashboardDTO);
		}
		logger.debug("DashboardService: totalAmountGroupByMonth-End");
		return list;
	}
	
	private void validateDashboardDTO(BaseDTO baseDTO) throws IOException{
		if( baseDTO == null  || !(baseDTO instanceof DashboardDTO)){
			throw new IOException("Object expected of DashboardDTO type.");
		}
	}
}
