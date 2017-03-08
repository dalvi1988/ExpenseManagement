package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.Command;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.approvalFlow.service.IApprovalFlowService;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.branch.service.IBranchService;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Utility;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ApprovalFlowController {

	@Autowired 
	private IBranchService branchService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	IEmployeeService employeeService;
	
	@Autowired
	IApprovalFlowService approvalService;
	
	@RequestMapping(value="/approvalFlow",method=RequestMethod.GET)
	public ModelAndView getFunctionalFlow() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<EmployeeDTO> employeeDTOList=null;
		List<DepartmentDTO> departmentDTOList=null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
			employeeDTOList=new ArrayList<EmployeeDTO>();
			EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
			if(Validation.validateForNullObject(employeeDTO.getBranchDTO().getCompanyDTO())){
				employeeDTOList=employeeService.findEmployeeOnCompany(employeeDTO);
				Utility.addLevelsToEmployeeDTO(employeeDTOList);
				departmentDTOList=departmentService.findAll();
			}
			
			model.addObject("employeeList", mapper.writeValueAsString(employeeDTOList));
			model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
		}
		model.setViewName("approvalflow/approvalFlowJSP");
		return model;
	}
	
	@RequestMapping(value="/fuctionalFlow",method={RequestMethod.POST})
	public @ResponseBody String getFunctionalFlow(@RequestBody ApprovalFlowDTO receivedApprovalFlowDTO) throws JsonProcessingException{
		List<ApprovalFlowDTO> approvalFlowList = null;
		ObjectMapper mapper=new ObjectMapper();
		
		if(Validation.validateForNullObject(receivedApprovalFlowDTO.getBranchDTO())){
			approvalFlowList = approvalService.findFunctionalFlowUnderBranch(receivedApprovalFlowDTO);
		}

		return "{\"data\":"+mapper.writeValueAsString(approvalFlowList)+"}";
	}

	public @ResponseBody BranchDTO addDepartment(@RequestBody BranchDTO receivedBranchDTO){
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
