package com.chaitanya.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expense.service.IExpenseService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CompanyMIS {
	
	@Autowired
	IExpenseService expenseService;
	
	private Logger logger= LoggerFactory.getLogger(CompanyMIS.class);

	@RequestMapping(value="/expenseMIS",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getAllExpenseByCompany() {
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
			ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				 expenseHeaderDTOList = expenseService.getAllExpensesByCompany(expenseHeaderDTO);
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.setViewName("mis/companyExpenseMIS");
		}
		catch(Exception e){
			logger.error("CompanyMisController: getAllExpenseByCompany",e);
			model.setViewName("others/505");
		}
		return model;
	}

}
