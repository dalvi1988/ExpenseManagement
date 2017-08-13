package com.chaitanya.approvalFlow.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.approvalFlow.convertor.ApprovalFlowConvertor;
import com.chaitanya.approvalFlow.dao.IApprovalFlowDAO;
import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Validation;

@Service("ApprovalFlowService")
@Transactional(rollbackFor=Exception.class)
public class ApprovalFlowService implements IApprovalFlowService {

	@Autowired
	private IApprovalFlowDAO approvalFlowDAO;
	
	private Logger logger= LoggerFactory.getLogger(ApprovalFlowService.class);

	private void validateApprovalFlowDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof ApprovalFlowDTO)){
			throw new IllegalArgumentException("Object expected of ApprovalFlowDTO type.");
		}
	}
	
	@Override
	public List<ApprovalFlowDTO> findFunctionalFlowUnderBranch(BaseDTO baseDTO) {
		logger.debug("ApprovalFlowService: findFunctionalFlowUnderBranch-Start");
		
		validateApprovalFlowDTO(baseDTO);
		
		List<ApprovalFlowDTO> approvalFlowDTOList= null;
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
		logger.debug("ApprovalFlowService: findFunctionalFlowUnderBranch-End");
		return  approvalFlowDTOList;
	}

	@Override
	public List<ApprovalFlowDTO> findFinanceFlowUnderBranch(BaseDTO baseDTO) {
		logger.debug("ApprovalFlowService: findFinanceFlowUnderBranch-Start");
		
		validateApprovalFlowDTO(baseDTO);
		
		List<ApprovalFlowDTO> approvalFlowDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ApprovalFlowDTO approvalFlowDTO=(ApprovalFlowDTO) baseDTO;;
			List<ApprovalFlowJPA> approvalFlowJPAList =approvalFlowDAO.findFinanceFlowUnderBranch(approvalFlowDTO.getBranchDTO());
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
		logger.debug("ApprovalFlowService: findFinanceFlowUnderBranch-End");
		return  approvalFlowDTOList;
	}

	@Override
	public List<ApprovalFlowDTO> findBranchFlowUnderBranch(BaseDTO baseDTO) {
		logger.debug("ApprovalFlowService: findBranchFlowUnderBranch-Start");
		
		validateApprovalFlowDTO(baseDTO);
		
		List<ApprovalFlowDTO> approvalFlowDTOList= null;
		if (Validation.validateForNullObject(baseDTO)) {
			ApprovalFlowDTO approvalFlowDTO=(ApprovalFlowDTO) baseDTO;;
			List<ApprovalFlowJPA> approvalFlowJPAList =approvalFlowDAO.findBranchFlowUnderBranch(approvalFlowDTO.getBranchDTO());
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
		
		logger.debug("ApprovalFlowService: findBranchFlowUnderBranch-End");
		return  approvalFlowDTOList;
	}
	
	@Override
	public BaseDTO deactivateFunctionalFlow(BaseDTO baseDTO){
		logger.debug("ApprovalFlowService: deactivateFunctionalFlow-Start");
		
		validateApprovalFlowDTO(baseDTO);
		
		ApprovalFlowDTO approvalFlowDTO=(ApprovalFlowDTO) baseDTO;
		if (Validation.validateForNullObject(baseDTO)) {
			Integer result =approvalFlowDAO.deactivateFunctionalFlow(approvalFlowDTO);
			
			if(result == 1){
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		else{
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
		}
		logger.debug("ApprovalFlowService: deactivateFunctionalFlow-End");
		return  baseDTO;
	}

	@Override
	public BaseDTO addFunctionalFlow(BaseDTO baseDTO) throws ParseException {
		logger.debug("ApprovalFlowService: addFunctionalFlow-Start");
		
		validateApprovalFlowDTO(baseDTO);
		
		try{
			ApprovalFlowDTO approvalFlowDTO=(ApprovalFlowDTO)baseDTO;
			ApprovalFlowJPA approvalFlowJPA=ApprovalFlowConvertor.setApprovalFlowDTOToJPA(approvalFlowDTO);
			
			if(approvalFlowDTO.getFlowType().equalsIgnoreCase(ApplicationConstant.FINANCE_FLOW)){
				approvalFlowDTO= approvalFlowDAO.validateFinanceFlow(approvalFlowDTO);
			}
			else if(approvalFlowDTO.getFlowType().equalsIgnoreCase(ApplicationConstant.FUNCTIONAL_FLOW)){
				approvalFlowDTO= approvalFlowDAO.validateFunctionalFlow(approvalFlowDTO);
			}
			else if(approvalFlowDTO.getFlowType().equalsIgnoreCase(ApplicationConstant.BRANCH_FLOW)){
				approvalFlowDTO= approvalFlowDAO.validateBranchFlow(approvalFlowDTO);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			}
			
			if (Validation.validateForSuccessStatus(approvalFlowDTO) && Validation.validateForNullObject(approvalFlowJPA)) {
				
				approvalFlowJPA=approvalFlowDAO.add(approvalFlowJPA);
				if(Validation.validateForNullObject(approvalFlowJPA)){
					baseDTO=ApprovalFlowConvertor.setApprovalFlowJPAToDTO(approvalFlowJPA);
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
		logger.debug("ApprovalFlowService: addFunctionalFlow-End");
		return baseDTO;
	}
	
}
