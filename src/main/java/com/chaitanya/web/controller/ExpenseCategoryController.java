package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.Command;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.expenseCategory.service.IExpenseCategoryService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ExpenseCategoryController {

	@Autowired 
	@Qualifier("expenseCategoryService")
	private IExpenseCategoryService expenseCategoryService;
	
	
	@RequestMapping(value="/expenseCategory",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getAllExpenseCategory() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		
		List<ExpenseCategoryDTO> expenseCategoryDTOList=expenseCategoryService.findAll();
		
		model.addObject("expenseCategoryList", mapper.writeValueAsString(expenseCategoryDTOList));
		model.setViewName("master/expenseCategoryJSP");
		return model;
	}
	@RequestMapping(value="/addExpenseCategory", method=RequestMethod.POST)
	public @ResponseBody ExpenseCategoryDTO addExpenseCategory(@RequestBody ExpenseCategoryDTO receivedBranchDTO){
		ExpenseCategoryDTO toBeSentBranchDTO=null;
		if(Validation.validateForNullObject(receivedBranchDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedBranchDTO.getExpenseCategoryId())){
				receivedBranchDTO.setCommand(Command.ADD);
				receivedBranchDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedBranchDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedBranchDTO.setCommand(Command.UPDATE);
				receivedBranchDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedBranchDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			BaseDTO baseDTO=expenseCategoryService.addExpenseCategory(receivedBranchDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentBranchDTO=(ExpenseCategoryDTO)baseDTO;
				if(receivedBranchDTO.getCommand().equals(Command.ADD)){
					toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentBranchDTO=receivedBranchDTO;
				toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
			else if(Validation.validateForBusinessFailureStatus(baseDTO)){
				toBeSentBranchDTO=receivedBranchDTO;
				toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			}
		}
		else{
			toBeSentBranchDTO=receivedBranchDTO;
			toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentBranchDTO;
	}
	
}
