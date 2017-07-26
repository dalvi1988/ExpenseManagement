package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.dashboard.model.DashboardDTO;
import com.chaitanya.dashboard.service.IDashboardService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.login.model.LoginUserDetails;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DashboardController {

	@Autowired 
	private IDashboardService dashboardService;
	
	private Logger logger= LoggerFactory.getLogger(DashboardController.class);
	
	@RequestMapping(value="/employeeDashboard",method=RequestMethod.GET)
	public ModelAndView employeeDashboard() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DashboardDTO dashboardDTO= new DashboardDTO();
			EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
			dashboardDTO.setEmployeeDTO(employeeDTO);
			List<DashboardDTO> dashboardDTOList = dashboardService.totalAmountGroupByMonth(dashboardDTO);
			model.addObject("dashboardDTOList", mapper.writeValueAsString(dashboardDTOList));
			model.setViewName("dashboard/employeeDashboardJSP");
		}
		catch(Exception e){
			logger.error("DashboardController: employeeDashboard",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	
}
