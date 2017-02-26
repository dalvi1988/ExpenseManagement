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
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.branch.service.IBranchService;
import com.chaitanya.company.service.ICompanyService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BranchController {

	@Autowired 
	@Qualifier("branchService")
	private IBranchService branchService;
	
	@Autowired
	@Qualifier("companyService")
	private ICompanyService companyService;
	
	@RequestMapping(value="/branch",method=RequestMethod.GET)
	public ModelAndView getBranch() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<BranchDTO> branchDTOList = branchService.findAll();
		model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
		model.setViewName("master/branchJSP");
		return model;
	}
	
	@RequestMapping(value="/branchList",method=RequestMethod.POST)
	public @ResponseBody List<BranchDTO> getAllBranchUnderCompany() throws JsonGenerationException, JsonMappingException, IOException{
		List<BranchDTO> branchDTOList=null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
			EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
			if(Validation.validateForNullObject(employeeDTO.getBranchDTO().getCompanyDTO())){
				branchDTOList=companyService.findBranchOnCompany(employeeDTO.getBranchDTO().getCompanyDTO());
			}
		}
		return branchDTOList;
	}
	@RequestMapping(value="/addBranch", method=RequestMethod.POST)
	public @ResponseBody BranchDTO addBranch(@RequestBody BranchDTO receivedBranchDTO){
		BranchDTO toBeSentBranchDTO=null;
		if(Validation.validateForNullObject(receivedBranchDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedBranchDTO.getBranchId())){
				receivedBranchDTO.setCommand(Command.ADD);
				receivedBranchDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedBranchDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			else{
				receivedBranchDTO.setCommand(Command.UPDATE);
				receivedBranchDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedBranchDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			receivedBranchDTO.setCompanyDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO());
			BaseDTO baseDTO=branchService.addBranch(receivedBranchDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentBranchDTO=(BranchDTO)baseDTO;
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
