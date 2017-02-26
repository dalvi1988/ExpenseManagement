package com.chaitanya.departmentHead.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.ServiceStatus;
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

	private boolean validateDepartmentHeadMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof DepartmentHeadDTO);
	}
	
	@SuppressWarnings("null")
	@Override
	public List<DepartmentHeadDTO> findDepartmentHeadUnderBranch(BaseDTO baseDTO) {
		logger.debug("DepartmentHeadService: findDepartmentHeadUnderBranch-Start");
		if(validateDepartmentHeadMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of DepartmentHeadDTO type.");
		}
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
	
	
}
