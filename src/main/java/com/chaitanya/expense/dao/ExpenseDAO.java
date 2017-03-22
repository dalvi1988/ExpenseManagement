package com.chaitanya.expense.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.BranchJPA;

@Repository
@Transactional
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked" )
	public List<BranchJPA> findBrachOnCompany(BranchJPA branchJPA) {
		Session session=sessionFactory.getCurrentSession();
		List<BranchJPA> branchList=null;
		branchList=(List<BranchJPA>)session.createCriteria(BranchJPA.class)
				.add(Restrictions.eq("companyDetails.companyId",branchJPA.getBranchId()))
								.list();
		return branchList;
	}

	@Override
	public BranchJPA add(BranchJPA branchJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(branchJPA);
		return branchJPA;
	}

	@Override
	public List<BranchJPA> findAll() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BranchJPA> branchList=(List<BranchJPA>)session.createCriteria(BranchJPA.class)
				.list();
		return branchList;
	}

}
