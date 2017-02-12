package com.chaitanya.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.ServiceStatus;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.dao.IEmployeeDAO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.utility.Validation;

@Service("employeeService")
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	IEmployeeDAO employeeDAO;
	private Logger logger= LoggerFactory.getLogger(EmployeeService.class);
	
	private boolean validateEmployeeMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof EmployeeDTO);
	}
	
	public List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO) {
		logger.debug("EmployeeService: findEmpployeeOnCompany-Start");
		if(validateEmployeeMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of EmployeeMasterDTO type.");
		}
		
		List<EmployeeDTO> employeeDTOList = null;
		try{
			EmployeeDTO employeeDTO=(EmployeeDTO)baseDTO;
			if(Validation.validateForNullObject(employeeDTO)){
				//EmployeeJPA employee=EmployeeConvertor.setEmployeeDTOToEmployee(employeeDTO);
				CompanyDTO companyDTO=employeeDTO.getBranchDTO().getCompanyDTO();
				List<EmployeeJPA> employeeList = employeeDAO.findAllUnderCompany(companyDTO);
				if (Validation.validateCollectionForNullSize(employeeList)) {
					employeeDTOList = new ArrayList<EmployeeDTO>();
					for (EmployeeJPA emp:employeeList) {
						EmployeeDTO departmentDTO = EmployeeConvertor.setEmployeeToEmployeeDTO(emp);
						employeeDTOList.add(departmentDTO);
					}
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
				else{
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS_WITH_NO_DATA);
				}
			}
			
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("EmployeeService: Exception",e);
		}
		return employeeDTOList;
	}

	@Override
	public BaseDTO addEmployee(BaseDTO baseDTO) {
		logger.debug("EmployeeService: addEmployee-Start");
		if(validateEmployeeMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of EmployeeMasterDTO type.");
		}
		try{
			EmployeeJPA employeeJPA=EmployeeConvertor.setEmployeeDTOToEmployee((EmployeeDTO)baseDTO);
			if (Validation.validateForNullObject(employeeJPA)) {
				employeeJPA=employeeDAO.add(employeeJPA);
				if(Validation.validateForNullObject(employeeJPA)){
					baseDTO=EmployeeConvertor.setEmployeeToEmployeeDTO(employeeJPA);
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
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
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("EmployeeService: Exception",e);
		}
		logger.debug("EmployeeService: addEmployee-End");
		return baseDTO;
	}
	


	
}
