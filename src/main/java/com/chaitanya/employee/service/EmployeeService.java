package com.chaitanya.employee.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.dao.IEmployeeDAO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.login.dao.LoginDAO;
import com.chaitanya.utility.MailServiceImpl;
import com.chaitanya.utility.Utility;
import com.chaitanya.utility.Validation;

@Service("employeeService")
@Transactional(rollbackFor={Exception.class})
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	IEmployeeDAO employeeDAO;
	
	@Autowired
	LoginDAO loginDAO;
	
	@Autowired
	MailServiceImpl mailService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private Logger logger= LoggerFactory.getLogger(EmployeeService.class);
	
	private boolean validateEmployeeMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof EmployeeDTO);
	}
	
	public List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO) throws ParseException {
		logger.debug("EmployeeService: findEmployeeOnCompany-Start");
		if(validateEmployeeMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of EmployeeMasterDTO type.");
		}
		
		List<EmployeeDTO> employeeDTOList = null;
		EmployeeDTO employeeDTO=(EmployeeDTO)baseDTO;
		if(Validation.validateForNullObject(employeeDTO)){
			//EmployeeJPA employee=EmployeeConvertor.setEmployeeDTOToEmployee(employeeDTO);
			CompanyDTO companyDTO=employeeDTO.getBranchDTO().getCompanyDTO();
			List<EmployeeJPA> employeeList = employeeDAO.findAllUnderCompany(companyDTO);
			if (Validation.validateCollectionForNullSize(employeeList)) {
				employeeDTOList = new ArrayList<EmployeeDTO>();
				for (EmployeeJPA emp:employeeList) {
					EmployeeDTO departmentDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(emp);
					employeeDTOList.add(departmentDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS_WITH_NO_DATA);
			}
		}
		
		logger.debug("EmployeeService: findEmployeeOnCompany-End");
		return employeeDTOList;
	}
	
	public List<EmployeeDTO> findEmployeeOnUnderDeptBranch(BaseDTO baseDTO) throws ParseException {
		logger.debug("EmployeeService: findEmployeeOnUnderDeptBranch-Start");
		if(validateEmployeeMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of EmployeeMasterDTO type.");
		}
		
		List<EmployeeDTO> employeeDTOList = null;
		EmployeeDTO employeeDTO=(EmployeeDTO)baseDTO;
		if(Validation.validateForNullObject(employeeDTO)){
			
			List<EmployeeJPA> employeeList = employeeDAO.findEmployeeOnUnderDeptBranch(employeeDTO);
			if (Validation.validateCollectionForNullSize(employeeList)) {
				employeeDTOList = new ArrayList<EmployeeDTO>();
				for (EmployeeJPA emp:employeeList) {
					EmployeeDTO departmentDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(emp);
					employeeDTOList.add(departmentDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS_WITH_NO_DATA);
			}
		}
			
		logger.debug("EmployeeService: findEmployeeOnUnderDeptBranch-End");
		return employeeDTOList;
	}

	@Override
	public BaseDTO addEmployee(BaseDTO baseDTO) throws Exception {
		logger.debug("EmployeeService: addEmployee-Start");
		if(validateEmployeeMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of EmployeeMasterDTO type.");
		}
		try{
		    EmployeeDTO employeeDTO= (EmployeeDTO)baseDTO;
			EmployeeJPA employeeJPA=EmployeeConvertor.setEmployeeDTOToEmployee(employeeDTO);
			if (Validation.validateForNullObject(employeeJPA)) {
				employeeJPA = employeeDAO.add(employeeJPA);
				if(! Validation.validateForZero(employeeDTO.getEmployeeId())){
					LoginJPA loginJPA=new LoginJPA();
					String original= Utility.SessionIdentifierGenerator.nextSessionId();
					loginJPA.setPassword(passwordEncoder.encode(original));
					loginJPA.setEmployeeJPA(employeeJPA);
					loginJPA.setUserName(employeeDTO.getEmailId());
					loginDAO.saveLoginDetail(loginJPA);
					mailService.sendAutoGeneratePassword(employeeDTO,original);
				}
				else {
					LoginJPA loginJPA= loginDAO.getLoginDetailByEmployeeId(employeeJPA);
					if(!loginJPA.getUserName().equals(employeeJPA.getEmailId())){
						String original= Utility.SessionIdentifierGenerator.nextSessionId();
						loginJPA.setPassword(passwordEncoder.encode(original));
						loginJPA.setUserName(employeeJPA.getEmailId());
						loginDAO.saveLoginDetail(loginJPA);
						mailService.sendAutoGeneratePassword(employeeDTO,original);
					}
				}
				baseDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(employeeJPA);
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(DataIntegrityViolationException e){
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
			logger.error("EmployeeService: Exception",e);
		}
		logger.debug("EmployeeService: addEmployee-End");
		return baseDTO;
	}
}
