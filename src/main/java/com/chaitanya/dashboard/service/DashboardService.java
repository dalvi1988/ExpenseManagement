package com.chaitanya.dashboard.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
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
	public BaseDTO totalAmountGroupByMonth(BaseDTO baseDTO) throws ParseException {
		logger.debug("DashboardService: totalAmountGroupByMonth-Start");
		validateDashboardDTO(baseDTO);
		try{
			DashboardDTO dashboardDTO=(DashboardDTO)baseDTO;
			if (Validation.validateForNullObject(dashboardDTO)) {
				List list=dashboardDAO.totalAmountGroupByMonth(dashboardDTO);
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(DataIntegrityViolationException e){
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
			logger.error("DashboardService: Exception",e);
		}
		logger.debug("DashboardService: totalAmountGroupByMonth-End");
		return baseDTO;
	}
	
	private void validateDashboardDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof DashboardDTO)){
			throw new IllegalArgumentException("Object expected of DashboardDTO type.");
		}
	}
}
