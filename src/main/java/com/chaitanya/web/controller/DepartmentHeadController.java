package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.departmentHead.service.IDepartmentHeadService;
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
	
	@RequestMapping(value="/departmentHeadMaster",method=RequestMethod.GET)
	public ModelAndView getDepartmentHeadJSP() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<DepartmentDTO> departmentDTOList= departmentService.findAll();
		model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
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
		
}
