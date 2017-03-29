package com.chaitanya.expense.dao;


import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.utility.FTPUtility;

@Repository
@Transactional(rollbackFor=IOException.class)
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseHeaderJPA add(ExpenseHeaderJPA expenseHeaderJPA) throws IOException{
		Session session = sessionFactory.getCurrentSession();
		session.save(expenseHeaderJPA);
		for(ExpenseDetailJPA expenseDetailJPA : expenseHeaderJPA.getExpenseDetailJPA()){
			FTPUtility.uploadFile(expenseDetailJPA.getReceipt());
		}
		return expenseHeaderJPA;
	}

}
