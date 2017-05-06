package com.chaitanya.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.login.model.LoginDTO;
import com.chaitanya.login.service.ILoginService;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Validation;

@Controller
@Transactional
public class LoginController {
		
	@Autowired
	ILoginService loginService;
	
	@RequestMapping(value = "/login/forgotPassword", method = RequestMethod.POST)
	public ModelAndView forgotPassoword(@RequestParam(value = "emailId", required = true) String emailId) {
		ModelAndView model= new ModelAndView();
		LoginDTO loginDTO= new LoginDTO();
		try{
			if(emailId.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
				EmployeeDTO employeeDTO = new EmployeeDTO();
				employeeDTO.setEmailId(emailId);
				loginDTO.setEmployeeDTO(employeeDTO);
				BaseDTO baseDTO = loginService.forgotPassword(loginDTO);
				if(Validation.validateForSystemFailureStatus(baseDTO)){
					model.addObject("error", ApplicationConstant.SYSTEM_FAILURE);
				}
				else{
					model.addObject("message", ApplicationConstant.PASSWORD_SENT);
				}
			}
			else{
				model.addObject("error", "Invalid email id: " +emailId);
			}
			model.setViewName("pages/login");
		}
		catch(Exception e){
			model.setViewName("others/505");
		}
		return model;
	}
	
	@RequestMapping(value = "/login/resetPassword", method = RequestMethod.POST)
	public String resetPassoword(@RequestParam(value = "oldPassword", required = true) String oldPassword,@RequestParam(value = "newPassword", required = true) String newPassword,
			@RequestParam(value = "confirmPassword", required = true) String confirmPassword) {

		
		return "";
	}

	
	@RequestMapping(value ={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("message", "You've been logged out successfully.");
		}
		
		model.setViewName("pages/login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

}