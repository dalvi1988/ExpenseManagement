package com.chaitanya.departmentHead.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.departmentHead.convertor.DepartmentHeadConvertor;
import com.chaitanya.departmentHead.dao.IDepartmentHeadDAO;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.utility.Validation;

@Service("departmentHeadService")
public class DepartmentHeadService implements IDepartmentHeadService {

	@Autowired
	private IDepartmentHeadDAO departmentHeadDAO;
	
	private Logger logger= LoggerFactory.getLogger(DepartmentHeadService.class);

	private void validateDepartmentHeadMasterDTO(BaseDTO baseDTO) {
		if(baseDTO == null  || !(baseDTO instanceof DepartmentHeadDTO))
		{
			throw new IllegalArgumentException("Object expected of DepartmentHeadDTO type.");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DepartmentHeadDTO> findDepartmentHeadUnderBranch(BaseDTO baseDTO) {
		logger.debug("DepartmentHeadService: findDepartmentHeadUnderBranch-Start");
		validateDepartmentHeadMasterDTO(baseDTO);
		List<DepartmentHeadDTO> departmentHeadDTOList= null;
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				DepartmentHeadDTO departmentHeadDTO=(DepartmentHeadDTO) baseDTO;;
				List<DepartmentHeadJPA> departmentHeadJPAList =departmentHeadDAO.findDepartmentHeadUnderBranch(departmentHeadDTO.getBranchDTO());
				if(Validation.validateForNullObject(departmentHeadJPAList)){
					departmentHeadDTOList= new ArrayList<DepartmentHeadDTO>();
					for(DepartmentHeadJPA departmentHeadJPA: departmentHeadJPAList){
						DepartmentHeadDTO deptHeadDTO=DepartmentHeadConvertor.setDepartmentHeadJPAToDTO(departmentHeadJPA);
						departmentHeadDTOList.add(deptHeadDTO);
					}
					baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
				}
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("Department Service: Exception",e);
		}
		logger.debug("DepartmentHeadService: findDepartmentHeadUnderBranch-End");
		return  departmentHeadDTOList;
	}

	@Override
	public BaseDTO addDepartmentHead(BaseDTO baseDTO) throws ParseException {
		logger.debug("DepartmentHeadService: addDepartmentHead-Start");
		validateDepartmentHeadMasterDTO(baseDTO);
		DepartmentHeadDTO departmentHeadDTO=(DepartmentHeadDTO) baseDTO;
		DepartmentHeadJPA isDepartmentHeadExists= departmentHeadDAO.checkDepartmentHeadExist(departmentHeadDTO);
		if(Validation.validateForNullObject(isDepartmentHeadExists)) {
			if(!departmentHeadDTO.getDeptHeadId().equals(isDepartmentHeadExists.getDeptHeadId())) {
				if(departmentHeadDTO.getDepartmentDTO().getDepartmentId().equals(isDepartmentHeadExists.getDepartmentJPA().getDepartmentId())){
					baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
					throw new DataIntegrityViolationException("Deparment Head already exists for selected department.");
				}
			}
		}
		if (Validation.validateForNullObject(departmentHeadDTO)) {
			DepartmentHeadJPA departmentHeadJPA=DepartmentHeadConvertor.setDepartmentHeadDTOToJPA((DepartmentHeadDTO)baseDTO);
			departmentHeadJPA=departmentHeadDAO.add(departmentHeadJPA);
			if(Validation.validateForNullObject(departmentHeadJPA)){
				baseDTO=DepartmentHeadConvertor.setDepartmentHeadJPAToDTO(departmentHeadJPA);
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("DepartmentHeadService: addDepartmentHead-End");
		return baseDTO;
	}
	
	
}
