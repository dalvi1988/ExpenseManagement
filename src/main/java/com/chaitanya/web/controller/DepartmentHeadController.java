package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.departmentHead.service.IDepartmentHeadService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DepartmentHeadController {

	@Autowired 
	private IDepartmentHeadService deptHeadSerrvice;
	
	
	@RequestMapping(value="/departmentHead",method=RequestMethod.GET)
	public ModelAndView getDepartmentHead() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		List<DepartmentHeadDTO> departmentHeadDTOList = null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
			DepartmentHeadDTO departmentHeadDTO= new DepartmentHeadDTO();
			departmentHeadDTO.setLoginDTO(user.getLoginDTO());
					
			 departmentHeadDTOList = deptHeadSerrvice.findDepartmentHeadUnderCompany(departmentHeadDTO);
		}
		model.addObject("departmentHeadList", mapper.writeValueAsString(departmentHeadDTOList));
		model.setViewName("master/departmentHeadJSP");
		return model;
	}
	
	
}
