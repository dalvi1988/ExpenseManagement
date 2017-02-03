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

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.Command;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.company.service.ICompanyService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CompanyController {

	@Autowired 
	@Qualifier("companyService")
	private ICompanyService companyService;
	
	@RequestMapping(value="/company",method=RequestMethod.GET)
	public ModelAndView getDepartment() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<CompanyDTO> companyDTOList=companyService.findAll();
		model.addObject("companyList", mapper.writeValueAsString(companyDTOList));
		model.setViewName("master/companyJSP");
		return model;
	}
	
	@RequestMapping(value="/addCompany", method=RequestMethod.POST)
	public @ResponseBody CompanyDTO addDepartment(@RequestBody CompanyDTO receivedCompanyDTO){
		CompanyDTO toBeSentCompanyDTO=null;
		if(Validation.validateForNullObject(receivedCompanyDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedCompanyDTO.getCompanyId())){
				receivedCompanyDTO.setCommand(Command.ADD);
				receivedCompanyDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedCompanyDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			else{
				receivedCompanyDTO.setCommand(Command.UPDATE);
				receivedCompanyDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedCompanyDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			BaseDTO baseDTO=companyService.addCompany(receivedCompanyDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentCompanyDTO=(CompanyDTO)baseDTO;
				if(receivedCompanyDTO.getCommand().equals(Command.ADD)){
					toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentCompanyDTO=receivedCompanyDTO;
				toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
		}
		else{
			toBeSentCompanyDTO=receivedCompanyDTO;
			toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentCompanyDTO;
	}
	
}
