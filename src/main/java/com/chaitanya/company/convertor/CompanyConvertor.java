package com.chaitanya.company.convertor;

import java.text.ParseException;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.CompanyJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class CompanyConvertor {
	
	public static CompanyDTO setCompanyJPAtoDTO(CompanyJPA companyJPA){
		CompanyDTO companyDTO=null;
		if(Validation.validateForNullObject(companyJPA)){
			companyDTO=new CompanyDTO(); 
			companyDTO.setCompanyId(companyJPA.getCompanyId());
			companyDTO.setCompanyName(companyJPA.getCompanyName());
			companyDTO.setCompanyCode(companyJPA.getCompanyCode());
			if(Validation.validateForNullObject(companyJPA.getCreatedBy())){
				companyDTO.setCreatedBy(companyJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(companyJPA.getModifiedBy())){
				companyDTO.setModifiedBy(companyJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(companyJPA.getCreatedDate())){
				companyDTO.setCreatedDate(Convertor.calendartoString(companyJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(companyJPA.getModifiedDate())){
				companyDTO.setModifiedDate(Convertor.calendartoString(companyJPA.getModifiedDate()));
			}
			companyDTO.setStatus(Convertor.convetStatusToBool(companyJPA.getStatus()));
		}
		return companyDTO;
	}
	
	
	public static CompanyJPA setCompanyDTOtoJPA(CompanyDTO companyDTO) throws ParseException
	{
		CompanyJPA companyJPA=null;
		if(Validation.validateForNullObject(companyDTO)){
			companyJPA=new CompanyJPA();
			if(Validation.validateForZero(companyDTO.getCompanyId())){
				companyJPA.setCompanyId(companyDTO.getCompanyId());
			}
			if(Validation.validateForZero(companyDTO.getModifiedBy())){
				companyJPA.setModifiedBy(companyDTO.getModifiedBy());
			}
			if(Validation.validateForZero(companyDTO.getCreatedBy())){
				companyJPA.setCreatedBy(companyDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(companyDTO.getCreatedDate())){
				companyJPA.setCreatedDate(Convertor.stringToCalendar(companyDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(companyDTO.getModifiedDate())){
				companyJPA.setModifiedDate(Convertor.stringToCalendar(companyDTO.getModifiedDate()));
			}
			companyJPA.setCompanyCode(companyDTO.getCompanyCode());
			companyJPA.setCompanyName(companyDTO.getCompanyCode());
			companyJPA.setStatus(Convertor.convertStatusToChar(companyDTO.getStatus()));
		}
		return companyJPA;
	}
}
