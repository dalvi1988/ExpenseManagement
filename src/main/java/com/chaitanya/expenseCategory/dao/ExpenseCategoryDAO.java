package com.chaitanya.expenseCategory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.ExpenseCategoryJPA;

@Repository
@Transactional
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
	public List<ExpenseCategoryJPA> findAll() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseCategoryJPA> expenseCategoryList=(List<ExpenseCategoryJPA>)session.createCriteria(ExpenseCategoryJPA.class)
				.list();
		return expenseCategoryList;
	}

}
