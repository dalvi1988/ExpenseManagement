package com.chaitanya.company.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.company.convertor.CompanyConvertor;
import com.chaitanya.company.dao.ICompanyDAO;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.utility.Validation;

@Service("companyService")
@Transactional(rollbackFor=Exception.class)
public class CompanyService implements ICompanyService{
	@Autowired
	private ICompanyDAO companyDAO;
	
	private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	
	private void validateCompanyBrachMasterDTO(BaseDTO baseDTO) {
		if(baseDTO == null  || !(baseDTO instanceof CompanyDTO)){
			throw new IllegalArgumentException("Object expected of CompanyMasterDTO type.");
		}
	}
	
	@Override
	public BaseDTO addCompany(BaseDTO baseDTO) throws ParseException {
		logger.debug("CompanyService: addCompany-Start");
		validateCompanyBrachMasterDTO(baseDTO);
		
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
