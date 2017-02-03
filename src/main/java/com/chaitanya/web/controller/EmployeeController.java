package com.chaitanya.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.Command;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.company.service.ICompanyService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeController {

	@Autowired
	@Qualifier("employeeService")
	IEmployeeService employeeService;
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value="employee",method=RequestMethod.GET)
	public ModelAndView getAllEmployee() throws JsonProcessingException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<EmployeeDTO> employeeDTOList=null;
		List<BranchDTO> branchDTOList=null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
			employeeDTOList=new ArrayList<EmployeeDTO>();
			EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
			if(Validation.validateForNullObject(employeeDTO.getBranchDTO().getCompanyDTO())){
				employeeDTOList=employeeService.findEmpployeeOnCompany(employeeDTO);
				branchDTOList=companyService.findBranchOnCompany(employeeDTO.getBranchDTO().getCompanyDTO());
			}
			
			model.addObject("employeeList", mapper.writeValueAsString(employeeDTOList));
			model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
			
		}
		model.setViewName("master/employeeJSP");
		return model;
	}
	
	@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	public @ResponseBody EmployeeDTO addDepartment(@RequestBody EmployeeDTO receivedEmployeeDTO){
		EmployeeDTO toBeSentEmployeeDTO=null;
		if(Validation.validateForNullObject(receivedEmployeeDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedEmployeeDTO.getEmployeeId())){
				receivedEmployeeDTO.setCommand(Command.ADD);
				receivedEmployeeDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEmployeeDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			else{
				receivedEmployeeDTO.setCommand(Command.UPDATE);
				receivedEmployeeDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEmployeeDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			BaseDTO baseDTO=employeeService.addEmployee(receivedEmployeeDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentEmployeeDTO=(EmployeeDTO)baseDTO;
				if(receivedEmployeeDTO.getCommand().equals(Command.ADD)){
					toBeSentEmployeeDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentEmployeeDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentEmployeeDTO=receivedEmployeeDTO;
				toBeSentEmployeeDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
		}
		else{
			toBeSentEmployeeDTO=receivedEmployeeDTO;
			toBeSentEmployeeDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentEmployeeDTO;
	}
}
