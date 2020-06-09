package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.chaitanya.company.model.CompanyDTO;
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
	
	private Logger logger= LoggerFactory.getLogger(ExpenseCategoryController.class);
	
	@RequestMapping(value="/expenseCategory",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getAllExpenseCategory() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ExpenseCategoryDTO categoryDTO = new ExpenseCategoryDTO();
			CompanyDTO companyDTO = user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO();
			categoryDTO.setCompanyDTO(companyDTO);
			
			List<ExpenseCategoryDTO> expenseCategoryDTOList = expenseCategoryService.findExpenseCategoryByCompany(categoryDTO);
			
			model.addObject("expenseCategoryList", mapper.writeValueAsString(expenseCategoryDTOList));
			model.setViewName("master/expenseCategoryJSP");
		}
		catch(Exception e){
			logger.error("ExpenseCategoryController: getAllExpenseCategory",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/addExpenseCategory", method=RequestMethod.POST)
	public @ResponseBody ExpenseCategoryDTO addExpenseCategory(@RequestBody ExpenseCategoryDTO receivedCategoryDTO){
		ExpenseCategoryDTO toBeSentCategoryDTO=null;
		try{
			if(Validation.validateForNullObject(receivedCategoryDTO)){
				LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(!Validation.validateForNullObject(receivedCategoryDTO.getExpenseCategoryId())){
					receivedCategoryDTO.setCommand(Command.ADD);
					receivedCategoryDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedCategoryDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
				}
				else{
					receivedCategoryDTO.setCommand(Command.UPDATE);
					receivedCategoryDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedCategoryDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
				}
				CompanyDTO companyDTO = user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO();
				receivedCategoryDTO.setCompanyDTO(companyDTO);
				
				BaseDTO baseDTO=expenseCategoryService.addExpenseCategory(receivedCategoryDTO);
				if(Validation.validateForSuccessStatus(baseDTO)){
					toBeSentCategoryDTO=(ExpenseCategoryDTO)baseDTO;
					if(receivedCategoryDTO.getCommand().equals(Command.ADD)){
						toBeSentCategoryDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
					}
					else
						toBeSentCategoryDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
				}
				else if(Validation.validateForBusinessFailureStatus(baseDTO)){
					toBeSentCategoryDTO=receivedCategoryDTO;
					toBeSentCategoryDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
				}
			}
			else{
				toBeSentCategoryDTO=receivedCategoryDTO;
				toBeSentCategoryDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
		}
		catch(Exception e){
			toBeSentCategoryDTO=receivedCategoryDTO;
			toBeSentCategoryDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return toBeSentCategoryDTO;
	}
	
}
