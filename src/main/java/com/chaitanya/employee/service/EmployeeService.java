package com.chaitanya.employee.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.convertor.EmployeeConvertor;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.repositories.EmployeeRepository;
import com.chaitanya.repositories.LoginRepository;
import com.chaitanya.utility.MailServiceImpl;
import com.chaitanya.utility.Utility;
import com.chaitanya.utility.Validation;

@Service("employeeService")
@Transactional(rollbackFor = { Exception.class })
public class EmployeeService implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepositoty;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	MailServiceImpl mailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private void validateEmployeeMasterDTO(BaseDTO baseDTO) {
		if (baseDTO == null || !(baseDTO instanceof EmployeeDTO)) {
			throw new IllegalArgumentException("Object expected of EmployeeDTO type.");
		}
	}

	@Override
	public List<EmployeeDTO> findEmployeeOnCompany(BaseDTO baseDTO) throws ParseException {
		logger.debug("EmployeeService: findEmployeeOnCompany-Start");
		validateEmployeeMasterDTO(baseDTO);

		List<EmployeeDTO> employeeDTOList = null;
		EmployeeDTO employeeDTO = (EmployeeDTO) baseDTO;
		if (Validation.validateForNullObject(employeeDTO)) {
			// EmployeeJPA employee=EmployeeConvertor.setEmployeeDTOToEmployee(employeeDTO);
			CompanyDTO companyDTO = employeeDTO.getBranchDTO().getCompanyDTO();
			List<EmployeeJPA> employeeList = employeeRepositoty.findAllByCompanyId(companyDTO.getCompanyId());
			if (Validation.validateCollectionForNullSize(employeeList)) {
				employeeDTOList = new ArrayList<EmployeeDTO>();
				for (EmployeeJPA emp : employeeList) {
					EmployeeDTO departmentDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(emp);
					employeeDTOList.add(departmentDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			} else {
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS_WITH_NO_DATA);
			}
		}

		logger.debug("EmployeeService: findEmployeeOnCompany-End");
		return employeeDTOList;
	}

	@Override
	public List<EmployeeDTO> findEmployeeOnUnderDeptBranch(BaseDTO baseDTO) throws ParseException {
		logger.debug("EmployeeService: findEmployeeOnUnderDeptBranch-Start");
		validateEmployeeMasterDTO(baseDTO);
		List<EmployeeDTO> employeeDTOList = null;
		EmployeeDTO employeeDTO = (EmployeeDTO) baseDTO;
		if (Validation.validateForNullObject(employeeDTO)) {

			List<EmployeeJPA> employeeList = employeeRepositoty.findAllByDepartmentIdBranchId(
					employeeDTO.getDepartmentDTO().getDepartmentId(), employeeDTO.getBranchDTO().getBranchId());
			if (Validation.validateCollectionForNullSize(employeeList)) {
				employeeDTOList = new ArrayList<EmployeeDTO>();
				for (EmployeeJPA emp : employeeList) {
					EmployeeDTO departmentDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(emp);
					employeeDTOList.add(departmentDTO);
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			} else {
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS_WITH_NO_DATA);
			}
		}

		logger.debug("EmployeeService: findEmployeeOnUnderDeptBranch-End");
		return employeeDTOList;
	}

	@Override
	public BaseDTO addEmployee(BaseDTO baseDTO) throws Exception {
		logger.debug("EmployeeService: addEmployee-Start");
		validateEmployeeMasterDTO(baseDTO);
		EmployeeDTO employeeDTO = (EmployeeDTO) baseDTO;
		EmployeeJPA employeeJPA = EmployeeConvertor.setEmployeeDTOToEmployee(employeeDTO);
		if (Validation.validateForNullObject(employeeJPA)) {
			employeeJPA = employeeRepositoty.save(employeeJPA);
			try {
				if (!Validation.validateForZero(employeeDTO.getEmployeeId())) {
					LoginJPA loginJPA = new LoginJPA();
					String original = Utility.SessionIdentifierGenerator.nextSessionId();
					loginJPA.setPassword(passwordEncoder.encode(original));
					loginJPA.setEmployeeJPA(employeeJPA);
					loginJPA.setUserName(employeeDTO.getEmailId());
					loginRepository.save(loginJPA);
					mailService.sendAutoGeneratePassword(employeeDTO, original);
				} else {
					LoginJPA loginJPA = loginRepository.findByEmployeeId(employeeDTO.getEmployeeId());
					if (!loginJPA.getUserName().equals(employeeJPA.getEmailId())) {
						String original = Utility.SessionIdentifierGenerator.nextSessionId();
						loginJPA.setPassword(passwordEncoder.encode(original));
						loginJPA.setUserName(employeeJPA.getEmailId());
						loginRepository.save(loginJPA);
						mailService.sendAutoGeneratePassword(employeeDTO, original);
					}
				}
			} catch (Exception e) {
				logger.debug("EmployeeService: addEmployee-PasswordMail " + e);
			}
			baseDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(employeeJPA);
			baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
		} else {
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("EmployeeService: addEmployee-End");
		return baseDTO;
	}

	@Override
	public BaseDTO getEmployeeById(BaseDTO baseDTO) throws ParseException {
		logger.debug("EmployeeService: getEmployeeById-Start");
		validateEmployeeMasterDTO(baseDTO);
		EmployeeDTO emplDTO = (EmployeeDTO) baseDTO;
		EmployeeJPA emplJPA = new EmployeeJPA();
		emplJPA.setEmployeeId(emplDTO.getEmployeeId());

		EmployeeJPA empJPA = employeeRepositoty.findByEmployeeId(emplJPA.getEmployeeId());
		EmployeeDTO employeeDTO = EmployeeConvertor.setEmployeeJPAToEmployeeDTO(empJPA);

		logger.debug("EmployeeService: getEmployeeById-End");
		return employeeDTO;
	}

}
