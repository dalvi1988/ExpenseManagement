package com.chaitanya.branch.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.BranchJPA;

@Repository
public class BranchDAO implements IBranchDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked" )
	public List<BranchJPA> findAllBranchUnderCompany(CompanyDTO companyDTO) {
		Session session=sessionFactory.getCurrentSession();
		List<BranchJPA> branchList=null;
		branchList=(List<BranchJPA>)session.createCriteria(BranchJPA.class)
				.add(Restrictions.eq("companyJPA.companyId",companyDTO.getCompanyId()))
								.list();
		return branchList;
	}

	@Override
	public BranchJPA add(BranchJPA branchJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(branchJPA);
		return branchJPA;
	}


}
