package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.ajax.AjaxResponse;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.departmentHead.service.IDepartmentHeadService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class DepartmentHeadController {

	@Autowired 
	private IDepartmentHeadService deptHeadSerrvice;
	
	@RequestMapping(value="/departmentHeadMaster",method=RequestMethod.GET)
	public ModelAndView getDepartmentHeadJSP() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();

		model.setViewName("master/departmentHeadJSP");
		return model;
	}
	
	@RequestMapping(value="/departmentHead",method={RequestMethod.POST,RequestMethod.GET},produces="application/json; charset=UTF-8")
	public @ResponseBody List<DepartmentHeadDTO> getDepartmentHead(DepartmentHeadDTO receivedDepartmentHeadDTO){
		List<DepartmentHeadDTO> departmentHeadDTOList = null;
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Validation.validateForNullObject(receivedDepartmentHeadDTO.getBranchDTO())){

			 departmentHeadDTOList = deptHeadSerrvice.findDepartmentHeadUnderBranch(receivedDepartmentHeadDTO);
		}

		return departmentHeadDTOList;
	}
	
}
