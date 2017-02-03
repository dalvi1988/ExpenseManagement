package com.chaitanya.department.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.ServiceStatus;
import com.chaitanya.department.convertor.DepartmentConvertor;
import com.chaitanya.department.dao.IDepartmentDAO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.utility.Validation;

@Service("departmentService")
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentDAO departmentDAO;
	private Logger logger= LoggerFactory.getLogger(DepartmentService.class);

	@Override
	public BaseDTO addDepartment(BaseDTO baseDTO) {
		logger.debug("DepartmentService: addDepartment-Start");
		if(validateDepartmentMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of DepartmentMasterDTO type.");
		}
		try{
			DepartmentJPA department=DepartmentConvertor.setDepartmentDTOToJPA((DepartmentDTO)baseDTO);
			if (Validation.validateForNullObject(department)) {
				department=departmentDAO.add(department);
				if(Validation.validateForNullObject(department)){
					baseDTO=DepartmentConvertor.setDepartmentJPAToDTO(department);
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
			logger.error("Department Service: Exception",e);
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("Department Service: Exception",e);
		}
		logger.debug("DepartmentService: addDepartment-End");
		return baseDTO;
	}

	@Override
	public List<DepartmentDTO> findAll() {
		List<DepartmentDTO> departmentDTOList = null;
		List<DepartmentJPA> departmentList = departmentDAO.findAll();
		if (Validation.validateCollectionForNullSize(departmentList)) {
			departmentDTOList = new ArrayList<DepartmentDTO>();
			for (Iterator<DepartmentJPA> iterator = departmentList.iterator(); iterator
					.hasNext();) {
				DepartmentJPA department = iterator.next();
				DepartmentDTO departmentDTO = DepartmentConvertor
						.setDepartmentJPAToDTO(department);
				departmentDTOList.add(departmentDTO);
			}
		}
		return departmentDTOList;
	}
	private boolean validateDepartmentMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof DepartmentDTO);
	}
}
