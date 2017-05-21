package com.chaitanya.event.dao;

import java.util.List;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.EventJPA;

public interface IEventDAO {

	public EventJPA add(EventJPA department);

	public List<EventJPA> findAllUnderCompany(CompanyDTO companyDTO);
}
