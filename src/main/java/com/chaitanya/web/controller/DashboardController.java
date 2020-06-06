package com.chaitanya.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.advance.service.IAdvanceService;
import com.chaitanya.dashboard.model.DashboardDTO;
import com.chaitanya.dashboard.service.IDashboardService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expense.service.IExpenseService;
import com.chaitanya.login.model.LoginUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DashboardController {

	@Autowired 
	private IDashboardService dashboardService;
	
	@Autowired
	private IExpenseService expenseService;
	
	@Autowired
	private IAdvanceService advanceService;
	
	private Logger logger= LoggerFactory.getLogger(DashboardController.class);
	
	@RequestMapping(value="/employeeDashboard",method=RequestMethod.GET)
	public ModelAndView employeeDashboard() {
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DashboardDTO dashboardDTO= new DashboardDTO();
			EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
			dashboardDTO.setEmployeeDTO(employeeDTO);
			List<DashboardDTO> dashboardDTOList = dashboardService.totalAmountGroupByMonth(dashboardDTO);
			ExpenseHeaderDTO expenseHeaderDTO= new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(employeeDTO);
			Long draftExpenseCount=  expenseService.getDraftExpenseCount(expenseHeaderDTO);
			Long pendingExpenseCount=  expenseService.getPendingExpenseCount(expenseHeaderDTO);
			Long rejectedExpenseCount=  expenseService.getRejectedExpenseCount(expenseHeaderDTO);
			Long paidExpenseCount=  expenseService.getPaidExpenseCount(expenseHeaderDTO);
			
			AdvanceDTO advanceDTO= new AdvanceDTO();
			advanceDTO.setEmployeeDTO(employeeDTO);
			Long draftAdvanceCount = advanceService.getDraftAdvanceCount(advanceDTO);
			Long pendingAdvanceCount = advanceService.getPendingAdvanceCount(advanceDTO);
			Long paidAdvanceCount = advanceService.getPaidAdvancesCount(advanceDTO);
			Long rejectedAdvanceCount = advanceService.getRejectedAdvanceCount(advanceDTO);
			
			
			model.addObject("dashboardDTOList", mapper.writeValueAsString(dashboardDTOList));
			model.addObject("draftExpenseCount", draftExpenseCount);
			model.addObject("pendingExpenseCount", pendingExpenseCount);
			model.addObject("rejectedExpenseCount", rejectedExpenseCount);
			model.addObject("paidExpenseCount", paidExpenseCount);
			model.addObject("draftAdvanceCount", draftAdvanceCount);
			model.addObject("pendingAdvanceCount", pendingAdvanceCount);
			model.addObject("paidAdvanceCount", paidAdvanceCount);
			model.addObject("rejectedAdvanceCount", rejectedAdvanceCount);
			
			model.setViewName("dashboard/employeeDashboardJSP");
		}
		catch(Exception e){
			logger.error("DashboardController: employeeDashboard",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	
}
