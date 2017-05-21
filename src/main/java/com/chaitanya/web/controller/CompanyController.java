package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private ICompanyService companyService;
	
	private Logger logger= LoggerFactory.getLogger(CompanyController.class);
	
	@RequestMapping(value="/company",method=RequestMethod.GET)
	public ModelAndView getCompanyDetails() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			List<CompanyDTO> companyDTOList=companyService.findAll();
			model.addObject("companyList", mapper.writeValueAsString(companyDTOList));
			model.setViewName("master/companyJSP");
		}
		catch(Exception e){
			logger.error("CompanyController: getCompanyDetails",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/addCompany", method=RequestMethod.POST)
	public @ResponseBody CompanyDTO addCompany(@RequestBody CompanyDTO receivedCompanyDTO){
		CompanyDTO toBeSentCompanyDTO=null;
		try{
			if(Validation.validateForNullObject(receivedCompanyDTO)){
				LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(!Validation.validateForNullObject(receivedCompanyDTO.getCompanyId())){
					receivedCompanyDTO.setCommand(Command.ADD);
					receivedCompanyDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedCompanyDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
				}
				else{
					receivedCompanyDTO.setCommand(Command.UPDATE);
					receivedCompanyDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedCompanyDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
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
				else if(Validation.validateForBusinessFailureStatus(baseDTO)){
					toBeSentCompanyDTO=receivedCompanyDTO;
					toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
				}
			}
		}
		catch(Exception e){
			logger.error("CompanyController: addCompanyb",e);
			toBeSentCompanyDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentCompanyDTO;
	}
	
}
