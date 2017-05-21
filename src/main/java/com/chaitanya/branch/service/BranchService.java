package com.chaitanya.branch.service;

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
import com.chaitanya.branch.convertor.BranchConvertor;
import com.chaitanya.branch.dao.IBranchDAO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.utility.Validation;

@Service("branchService")
@Transactional(rollbackFor=Exception.class)
public class BranchService implements IBranchService{
	@Autowired
	private IBranchDAO branchDAO;
	
	private Logger logger= LoggerFactory.getLogger(BranchService.class);
	
	@Override
	public List<BranchDTO> findAllBranchUnderCompany(BaseDTO baseDTO) {
		logger.debug("BranchService: findAllBranchUnderCompany-Start");
		validateCompanyBrachMasterDTO(baseDTO);
		
		BranchDTO branchDTO=(BranchDTO)baseDTO;
		List<BranchJPA> branchList=branchDAO.findAllBranchUnderCompany(branchDTO.getCompanyDTO());
		List<BranchDTO> branchDTOList=null;
		if(Validation.validateCollectionForNullSize(branchList)){
			branchDTOList=new ArrayList<BranchDTO>();
			for(BranchJPA branchJPA:branchList){
				BranchDTO brcDTO=BranchConvertor.setBranchJPAtoDTO(branchJPA);
				branchDTOList.add(brcDTO);
			}
		}
		logger.debug("BranchService: findAllBranchUnderCompany-End");
		return branchDTOList;
	}
	
	

	@Override
	public BaseDTO addBranch(BaseDTO baseDTO) throws ParseException {
		logger.debug("BranchService: addBranch-Start");
		validateCompanyBrachMasterDTO(baseDTO);
		try{
			BranchJPA branchJPA=BranchConvertor.setBranchDTOToJPA((BranchDTO)baseDTO);
			if (Validation.validateForNullObject(branchJPA)) {
				branchJPA=branchDAO.add(branchJPA);
				if(Validation.validateForNullObject(branchJPA)){
					baseDTO=BranchConvertor.setBranchJPAtoDTO(branchJPA);
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
			logger.error("Branch Service: Exception",e);
		}
		logger.debug("BranchService: addBranch-End");
		return baseDTO;
	}
	
	private void validateCompanyBrachMasterDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof BranchDTO)){
			throw new IllegalArgumentException("Object expected of BranchDTO type.");
		}
	}
}
