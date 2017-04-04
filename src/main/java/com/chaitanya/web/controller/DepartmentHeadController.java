package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.departmentHead.service.IDepartmentHeadService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DepartmentHeadController {

	@Autowired 
	private IDepartmentHeadService deptHeadService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	IEmployeeService employeeService;
	
	@RequestMapping(value="/departmentHeadMaster",method=RequestMethod.GET)
	public ModelAndView getDepartmentHeadJSP() throws JsonGenerationException, JsonMappingException, IOException{
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
				departmentDTOList=departmentService.findAll();
			}
			
			model.addObject("employeeList", mapper.writeValueAsString(employeeDTOList));
			model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
			
		}
		model.setViewName("master/departmentHeadJSP");
		return model;
	}
	
	@RequestMapping(value="/departmentHead",method={RequestMethod.POST,RequestMethod.GET},produces="application/json; charset=UTF-8")
	public @ResponseBody String getDepartmentHead(@RequestBody DepartmentHeadDTO receivedDepartmentHeadDTO) throws JsonProcessingException{
		List<DepartmentHeadDTO> departmentHeadDTOList = null;
		ObjectMapper mapper=new ObjectMapper();
		
		//LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(receivedDepartmentHeadDTO.getBranchDTO())){
			 departmentHeadDTOList = deptHeadService.findDepartmentHeadUnderBranch(receivedDepartmentHeadDTO);
		}

		return "{\"data\":"+mapper.writeValueAsString(departmentHeadDTOList)+"}";
	}
	
	@RequestMapping(value="/addDepartmentHead", method=RequestMethod.POST)
	public @ResponseBody DepartmentHeadDTO addDepartmentHead(@RequestBody DepartmentHeadDTO receivedDepartmentHeadDTO){
		DepartmentHeadDTO toBeSentBranchDTO=null;
		if(Validation.validateForNullObject(receivedDepartmentHeadDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedDepartmentHeadDTO.getDeptHeadId())){
				receivedDepartmentHeadDTO.setCommand(Command.ADD);
				receivedDepartmentHeadDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedDepartmentHeadDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			else{
				receivedDepartmentHeadDTO.setCommand(Command.UPDATE);
				receivedDepartmentHeadDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedDepartmentHeadDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance()));
			}
			BaseDTO baseDTO=deptHeadService.addDepartmentHead(receivedDepartmentHeadDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentBranchDTO=(DepartmentHeadDTO)baseDTO;
				if(receivedDepartmentHeadDTO.getCommand().equals(Command.ADD)){
					toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentBranchDTO=receivedDepartmentHeadDTO;
				toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
			else if(Validation.validateForBusinessFailureStatus(baseDTO)){
				toBeSentBranchDTO=receivedDepartmentHeadDTO;
				toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			}
		}
		else{
			toBeSentBranchDTO=receivedDepartmentHeadDTO;
			toBeSentBranchDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentBranchDTO;
	}
		
}
