package com.chaitanya.branch.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.branch.convertor.BranchConvertor;
import com.chaitanya.branch.dao.IBranchDAO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.utility.Validation;

@Service("branchService")
public class BranchService implements IBranchService{
	@Autowired
	private IBranchDAO branchDAO;
	
	private Logger logger= LoggerFactory.getLogger(BranchService.class);
	
	@Override
	public List<BranchDTO> findBranchOnCompany(BaseDTO baseDTO) {
		logger.debug("BranchService: findBranchOnCompany-Start");
		if(validateCompanyBrachMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of BranchDTO type.");
		}
		BranchJPA company=new BranchJPA();
		List<BranchJPA> branchList=branchDAO.findBrachOnCompany(company);
		List<BranchDTO> branchDTOList=null;
		if(Validation.validateCollectionForNullSize(branchList)){
			branchDTOList=new ArrayList<BranchDTO>();
			for(BranchJPA branchJPA:branchList){
				BranchDTO branchDTO=BranchConvertor.setBranchJPAtoDTO(branchJPA);
				branchDTOList.add(branchDTO);
			}
		}
		logger.debug("BranchService: findBranchOnCompany-End");
		return branchDTOList;
	}
	
	

	@Override
	public BaseDTO addBranch(BaseDTO baseDTO) {
		logger.debug("BranchService: addBranch-Start");
		if(validateCompanyBrachMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of CompanyMasterDTO type.");
		}
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
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("Branch Service: Exception",e);
		}
		logger.debug("BranchService: addBranch-End");
		return baseDTO;
	}

	@Override
	public List<BranchDTO> findAll() {
		List<BranchDTO> companyDTOList = null;
		List<BranchJPA> companyJPAList = branchDAO.findAll();
		if (Validation.validateCollectionForNullSize(companyJPAList)) {
			companyDTOList = new ArrayList<BranchDTO>();
			for (Iterator<BranchJPA> iterator = companyJPAList.iterator(); iterator
					.hasNext();) {
				BranchJPA branchJPA = iterator.next();
				BranchDTO brachDTO = BranchConvertor
						.setBranchJPAtoDTO(branchJPA);
				companyDTOList.add(brachDTO);
			}
		}
		return companyDTOList;
	}
	
	private boolean validateCompanyBrachMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof BranchDTO);
	}
}
