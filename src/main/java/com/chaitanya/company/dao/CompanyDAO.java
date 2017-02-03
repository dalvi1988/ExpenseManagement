package com.chaitanya.company.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.CompanyJPA;

@Repository
@Transactional
public class CompanyDAO implements ICompanyDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked" )
	public List<BranchJPA> findBrachOnCompany(CompanyJPA company) {
		Session session=sessionFactory.getCurrentSession();
		List<BranchJPA> branchList=null;
		branchList=(List<BranchJPA>)session.createCriteria(BranchJPA.class)
				.add(Restrictions.eq("companyJPA.companyId",company.getCompanyId()))
								.list();
		return branchList;
	}

	@Override
	public CompanyJPA add(CompanyJPA companyJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(companyJPA);
		return companyJPA;
	}

	@Override
	public List<CompanyJPA> findAll() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<CompanyJPA> companyList=(List<CompanyJPA>)session.createCriteria(CompanyJPA.class)
				.list();
		return companyList;
	}

}
