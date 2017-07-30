package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.Command;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.branch.service.IBranchService;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BranchController {

	@Autowired 
	private IBranchService branchService;
	
	private Logger logger= LoggerFactory.getLogger(BranchController.class);
	
	@RequestMapping(value="/branch",method=RequestMethod.GET)
	public ModelAndView getBranch() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			BranchDTO branchDTO= new BranchDTO();
			CompanyDTO companyDTO=user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO();
			branchDTO.setCompanyDTO(companyDTO);
			List<BranchDTO> branchDTOList = branchService.findAllBranchUnderCompany(branchDTO);
			model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
			model.setViewName("master/branchJSP");
		}
		catch(Exception e){
			logger.error("BranchController: getBranch",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="/branchList",method=RequestMethod.POST)
	public @ResponseBody List<BranchDTO> getAllBranchUnderCompany() throws JsonGenerationException, JsonMappingException, IOException{
		List<BranchDTO> branchDTOList=null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(user)){
			BranchDTO branchDTO= new BranchDTO();
			CompanyDTO companyDTO=user.getLoginDTO().getEmployeeDTO().getBranchDTO().getCompanyDTO();
			branchDTO.setCompanyDTO(companyDTO);
			branchDTOList=branchService.findAllBranchUnderCompany(branchDTO);
		}
		return branchDTOList;
	}
	@RequestMapping(value="/addBranch", method=RequestMethod.POST)
	public @ResponseBody BranchDTO addBranch(@RequestBody BranchDTO receivedBranchDTO){
		BranchDTO toBeSentBranchDTO=null;
		try{
			if(Validation.validateForNullObject(receivedBranchDTO)){
				LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(!Validation.validateForNullObject(receivedBranchDTO.getBranchId())){
					receivedBranchDTO.setCommand(Command.ADD);
					receivedBranchDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedBranchDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
				}
				else{
					receivedBranchDTO.setCommand(Command.UPDATE);
					receivedBranchDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
					receivedBranchDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
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
				else if(Validation.validateForBusinessFailureStatus(baseDTO)){
					toBeSentBranchDTO=receivedBranchDTO;
					toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
				}
			}
		}
		catch(Exception e){
			logger.error("BranchController: addBranch",e);
			toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return toBeSentBranchDTO;
	}
	
}
