package com.chaitanya.approvalFlow.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.Base.BaseDTO.ServiceStatus;
import com.chaitanya.approvalFlow.convertor.ApprovalFlowConvertor;
import com.chaitanya.approvalFlow.dao.IApprovalFlowDAO;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.utility.Validation;

@Service("ApprovalFlowService")
public class ApprovalFlowService implements IApprovalFlowService {

	@Autowired
	private IApprovalFlowDAO approvalFlowDAO;
	
	private Logger logger= LoggerFactory.getLogger(ApprovalFlowService.class);

	private boolean validateApprovalFlowDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof ApprovalFlowDTO);
	}
	
	@Override
	public List<ApprovalFlowDTO> findFunctionalFlowUnderBranch(BaseDTO baseDTO) {
		logger.debug("ApprovalFlowService: findFunctionalFlowUnderBranch-Start");
		
		if(validateApprovalFlowDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of ApprovalFlowDTO type.");
		}
		
		List<ApprovalFlowDTO> approvalFlowDTOList= null;
		try{
			if (Validation.validateForNullObject(baseDTO)) {
				ApprovalFlowDTO approvalFlowDTO=(ApprovalFlowDTO) baseDTO;;
				List<ApprovalFlowJPA> approvalFlowJPAList =approvalFlowDAO.findFunctionalFlowUnderBranch(approvalFlowDTO.getBranchDTO());
				if(Validation.validateForNullObject(approvalFlowJPAList)){
					approvalFlowDTOList= new ArrayList<ApprovalFlowDTO>();
					for(ApprovalFlowJPA approvalFlowJPA: approvalFlowJPAList){
						ApprovalFlowDTO deptHeadDTO=ApprovalFlowConvertor.setApprovalFlowJPAToDTO(approvalFlowJPA);
						approvalFlowDTOList.add(deptHeadDTO);
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
		logger.debug("ApprovalFlowService: findFunctionalFlowUnderBranch-End");
		return  approvalFlowDTOList;
	}

	/*@Override
	public BaseDTO addDepartmentHead(BaseDTO baseDTO) {
		logger.debug("ApprovalFlowService: addDepartmentHead-Start");
		if(validateDepartmentHeadMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of DepartmentHeadDTO type.");
		}
		try{
			DepartmentHeadJPA approvalFlowJPA=DepartmentHeadConvertor.setDepartmentHeadDTOToJPA((DepartmentHeadDTO)baseDTO);
			if (Validation.validateForNullObject(approvalFlowJPA)) {
				approvalFlowJPA=approvalFlowDAO.add(approvalFlowJPA);
				if(Validation.validateForNullObject(approvalFlowJPA)){
					baseDTO=DepartmentHeadConvertor.setDepartmentHeadJPAToDTO(approvalFlowJPA);
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
			logger.error("ApprovalFlowService: Exception",e);
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("ApprovalFlowService: Exception",e);
		}
		logger.debug("ApprovalFlowService: addDepartmentHead-End");
		return baseDTO;
	}*/
	
	
}
