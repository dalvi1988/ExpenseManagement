package com.chaitanya.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Utility;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeController {

	@Autowired
	IEmployeeService employeeService;
	@Autowired
	IBranchService branchService;
	@Autowired
	IDepartmentService departmentService;
	
	private Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	
	@RequestMapping(value="employee",method=RequestMethod.GET)
	public ModelAndView getAllEmployee() throws JsonProcessingException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			List<EmployeeDTO> employeeDTOList=null;
			
			List<BranchDTO> branchDTOList=null;
			List<DepartmentDTO> departmentDTOList=null;
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				employeeDTOList=new ArrayList<EmployeeDTO>();
				
				EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
				if(Validation.validateForNullObject(employeeDTO.getBranchDTO().getCompanyDTO())){
					employeeDTOList=employeeService.findEmployeeOnCompany(employeeDTO);
					branchDTOList=branchService.findAllBranchUnderCompany(employeeDTO.getBranchDTO());
					DepartmentDTO departmentDTO= new DepartmentDTO();
					departmentDTO.setBranchDTO(employeeDTO.getBranchDTO());
					departmentDTOList=departmentService.findAllDepartmentUnderCompany(departmentDTO);
				}
				
				model.addObject("employeeList", mapper.writeValueAsString(employeeDTOList));
				model.addObject("reportingMgrList", mapper.writeValueAsString(employeeDTOList));
				model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
				model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
			}
			model.setViewName("master/employeeJSP");
		}
		catch(Exception e){
			logger.error("EmployeeController: getAllEmployee",e);
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value="employeeList",method=RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getAllEmployeeList() throws JsonProcessingException, ParseException{
			List<EmployeeDTO> employeeDTOList=null;
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				employeeDTOList=new ArrayList<EmployeeDTO>();
			
				EmployeeDTO employeeDTO=user.getLoginDTO().getEmployeeDTO();
				if(Validation.validateForNullObject(employeeDTO.getBranchDTO().getCompanyDTO())){
					employeeDTOList=employeeService.findEmployeeOnCompany(employeeDTO);
				}
			}
		return employeeDTOList;
	}
	
	@RequestMapping(value="/empUnderDeptBranchWithLevel",method=RequestMethod.POST)
	public @ResponseBody String getDepartmentHead(@RequestBody EmployeeDTO receivedEmployeeDTO) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		List<EmployeeDTO> empUnderDeptBranchDTOList=null;
		empUnderDeptBranchDTOList=new ArrayList<EmployeeDTO>();
		try{
			if(Validation.validateForNullObject(receivedEmployeeDTO)){
				empUnderDeptBranchDTOList=employeeService.findEmployeeOnUnderDeptBranch(receivedEmployeeDTO);
				Utility.addLevelsToEmployeeDTO(empUnderDeptBranchDTOList);
			}
		}
		catch(Exception e){
			
		}

		return "{\"data\":" +mapper.writeValueAsString(empUnderDeptBranchDTOList) + "}";
	}
	
	@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	public @ResponseBody EmployeeDTO addEmployee(@RequestBody EmployeeDTO receivedEmployeeDTO){
		EmployeeDTO toBeSentEmployeeDTO=null;
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Validation.validateForNullObject(receivedEmployeeDTO.getEmployeeId())){
				receivedEmployeeDTO.setCommand(Command.ADD);
				receivedEmployeeDTO.setCreatedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEmployeeDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			}
			else{
				receivedEmployeeDTO.setCommand(Command.UPDATE);
				receivedEmployeeDTO.setModifiedBy(user.getLoginDTO().getEmployeeDTO().getEmployeeId());
				receivedEmployeeDTO.setModifiedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
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
		}
		catch(Exception e){
			logger.error("EmployeeController: addEmployee",e);
			toBeSentEmployeeDTO=receivedEmployeeDTO;
			toBeSentEmployeeDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		
		return toBeSentEmployeeDTO;
	}
	
}
