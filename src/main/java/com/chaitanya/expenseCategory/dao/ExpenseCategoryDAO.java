package com.chaitanya.expenseCategory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.jpa.ExpenseCategoryJPA;

@Repository
public class ExpenseCategoryDAO implements IExpenseCategoryDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseCategoryJPA add(ExpenseCategoryJPA expenseCategoryJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(expenseCategoryJPA);
		return expenseCategoryJPA;
	}

	@Override
	public List<ExpenseCategoryJPA> getAllActiveExpenseCategoryByCompany(ExpenseCategoryDTO expenseCategoryDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseCategoryJPA> expenseCategoryList=(List<ExpenseCategoryJPA>)session.createCriteria(ExpenseCategoryJPA.class)
													.add(Restrictions.eq("companyJPA.companyId", expenseCategoryDTO.getCompanyDTO().getCompanyId()))
													.add(Restrictions.eq("status", 'Y'))
													.list();
		return expenseCategoryList;
	}

	@Override
	public List<ExpenseCategoryJPA> getAllExpenseCategoryByCompany(ExpenseCategoryDTO expenseCategoryDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseCategoryJPA> expenseCategoryList=(List<ExpenseCategoryJPA>)session.createCriteria(ExpenseCategoryJPA.class)
													.add(Restrictions.eq("companyJPA.companyId", expenseCategoryDTO.getCompanyDTO().getCompanyId()))
													.list();
		return expenseCategoryList;
	}
}
