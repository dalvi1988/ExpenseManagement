package com.chaitanya.expense.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.ExpenseHeaderJPA;

@Repository
@Transactional
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseHeaderJPA add(ExpenseHeaderJPA expenseHeaderJPA){
		try{
		Session session=sessionFactory.getCurrentSession();
		session.save(expenseHeaderJPA);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return expenseHeaderJPA;
	}

}
