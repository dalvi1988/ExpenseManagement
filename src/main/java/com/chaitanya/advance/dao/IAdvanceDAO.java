package com.chaitanya.advance.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.EventJPA;

public interface IAdvanceDAO {

	public EventJPA add(EventJPA department);

	public List<EventJPA> findAllUnderCompany(CompanyDTO companyDTO);
}
