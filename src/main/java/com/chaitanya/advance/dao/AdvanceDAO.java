package com.chaitanya.advance.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.EventJPA;

@Repository
public class AdvanceDAO implements IAdvanceDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public EventJPA add(EventJPA branchJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(branchJPA);
		return branchJPA;
	}

	@Override
	public List<EventJPA> findAllUnderCompany(CompanyDTO companyDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<EventJPA> eventList=(List<EventJPA>)session.createCriteria(EventJPA.class)
				.createAlias("branchJPA","branchJPA", JoinType.INNER_JOIN)
				.add(Restrictions.eq("branchJPA.companyJPA.companyId",companyDTO.getCompanyId()))
				.list();
		return eventList;
	}

}
