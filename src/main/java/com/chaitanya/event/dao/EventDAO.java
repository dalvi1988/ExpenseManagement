package com.chaitanya.event.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.EventJPA;

@Repository
public class EventDAO implements IEventDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public EventJPA add(EventJPA branchJPA) {
//		Session session=sessionFactory.getCurrentSession();
//		session.saveOrUpdate(branchJPA);
//		return branchJPA;
		return null;
	}

	@Override
	public List<EventJPA> findAllUnderCompany(CompanyDTO companyDTO) {
//		Session session=sessionFactory.getCurrentSession();
//		@SuppressWarnings("unchecked")
//		List<EventJPA> eventList=(List<EventJPA>)session.createCriteria(EventJPA.class)
//				.createAlias("branchJPA","branchJPA", JoinType.INNER_JOIN)
//				.add(Restrictions.eq("branchJPA.companyJPA.companyId",companyDTO.getCompanyId()))
//				.list();
//		return eventList;
		return null;
	}

}
