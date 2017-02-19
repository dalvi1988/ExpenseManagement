package com.chaitanya.company.service;

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
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.company.convertor.CompanyConvertor;
import com.chaitanya.company.dao.ICompanyDAO;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.utility.Validation;

@Service("companyService")
public class CompanyService implements ICompanyService{
	@Autowired
	private ICompanyDAO companyDAO;
	
	private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	
	private boolean validateCompanyBrachMasterDTO(BaseDTO baseDTO) {
		return baseDTO == null  || !(baseDTO instanceof CompanyDTO);
	}
	
	@Override
	public List<BranchDTO> findBranchOnCompany(BaseDTO baseDTO) {
		if(validateCompanyBrachMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of CompanyDTO type.");
		}
		CompanyDTO companyDTO=(CompanyDTO) baseDTO;
		CompanyJPA company=new CompanyJPA();
		company.setCompanyId(companyDTO.getCompanyId());
		List<BranchJPA> branchList=companyDAO.findBrachOnCompany(company);
		List<BranchDTO> branchKeyValueDTOList=null;
		if(Validation.validateCollectionForNullSize(branchList)){
			branchKeyValueDTOList=new ArrayList<BranchDTO>();
			for(BranchJPA branch:branchList){
				BranchDTO branchDTO=new BranchDTO();
				branchDTO.setBranchId(branch.getBranchId());
				branchDTO.setBranchName(branch.getBranchName());
				
				branchKeyValueDTOList.add(branchDTO);
			}
		}
		
		return branchKeyValueDTOList;
	}
	
	

	@Override
	public BaseDTO addCompany(BaseDTO baseDTO) {
		logger.debug("CompanyService: addCompany-Start");
		if(validateCompanyBrachMasterDTO(baseDTO)){
			throw new IllegalArgumentException("Object expected of CompanyMasterDTO type.");
		}
		try{
			CompanyJPA companyJPA=CompanyConvertor.setCompanyDTOtoJPA((CompanyDTO)baseDTO);
			if (Validation.validateForNullObject(companyJPA)) {
				companyJPA=companyDAO.add(companyJPA);
				if(Validation.validateForNullObject(companyJPA)){
					baseDTO=CompanyConvertor.setCompanyJPAtoDTO(companyJPA);
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
			logger.error("Company Service: Exception",e);
		}
		catch(Exception e){
			baseDTO.setServiceStatus(ServiceStatus.SYSTEM_FAILURE);
			logger.error("Company Service: Exception",e);
		}
		logger.debug("CompanyService: addCompany-End");
		return baseDTO;
	}

	@Override
	public List<CompanyDTO> findAll() {
		List<CompanyDTO> companyDTOList = null;
		List<CompanyJPA> companyJPAList = companyDAO.findAll();
		if (Validation.validateCollectionForNullSize(companyJPAList)) {
			companyDTOList = new ArrayList<CompanyDTO>();
			for (Iterator<CompanyJPA> iterator = companyJPAList.iterator(); iterator
					.hasNext();) {
				CompanyJPA companyJPA = iterator.next();
				CompanyDTO companyDTO = CompanyConvertor
						.setCompanyJPAtoDTO(companyJPA);
				companyDTOList.add(companyDTO);
			}
		}
		return companyDTOList;
	}

	
}
