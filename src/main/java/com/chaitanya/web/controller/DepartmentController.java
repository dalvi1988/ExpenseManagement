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
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.branch.service.IBranchService;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DepartmentController {

	@Autowired 
	@Qualifier("departmentService")
	private IDepartmentService departmentService;
	
	@Autowired
	IBranchService branchService;
	
	@RequestMapping(value="/department",method=RequestMethod.GET)
	public ModelAndView getDepartment() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
		DepartmentDTO departmentDTO= new DepartmentDTO();
		departmentDTO.setBranchDTO(employeeDTO.getBranchDTO());
		List<DepartmentDTO> departmentDTOList=departmentService.findAllDepartmentUnderCompany(departmentDTO);
		List<BranchDTO> branchDTOList=branchService.findAllBranchUnderCompany(employeeDTO.getBranchDTO());
		model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
		model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
		model.setViewName("master/departmentJSP");
		return model;
	}
	
	@RequestMapping(value="/addDepartment", method=RequestMethod.POST)
	public @ResponseBody DepartmentDTO addDepartment(@RequestBody DepartmentDTO receivedDepartmentDTO){
		DepartmentDTO toBeSentDepartmentDTO=null;
		if(Validation.validateForNullObject(receivedDepartmentDTO)){
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedDepartmentDTO.getDepartmentId())){
				receivedDepartmentDTO.setCommand(Command.ADD);
				receivedDepartmentDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedDepartmentDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedDepartmentDTO.setCommand(Command.UPDATE);
				receivedDepartmentDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedDepartmentDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			BaseDTO baseDTO=departmentService.addDepartment(receivedDepartmentDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSentDepartmentDTO=(DepartmentDTO)baseDTO;
				if(receivedDepartmentDTO.getCommand().equals(Command.ADD)){
					toBeSentDepartmentDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
				}
				else
					toBeSentDepartmentDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
			}
			else if(Validation.validateForSystemFailureStatus(baseDTO)){
				toBeSentDepartmentDTO=receivedDepartmentDTO;
				toBeSentDepartmentDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
			}
			else if(Validation.validateForBusinessFailureStatus(baseDTO)){
				toBeSentDepartmentDTO=receivedDepartmentDTO;
				toBeSentDepartmentDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
			}
		}
		else{
			toBeSentDepartmentDTO=receivedDepartmentDTO;
			toBeSentDepartmentDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentDepartmentDTO;
	}
	
}
