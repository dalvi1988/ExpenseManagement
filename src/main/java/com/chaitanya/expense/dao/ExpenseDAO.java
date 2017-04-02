package com.chaitanya.expense.dao;


import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.utility.FTPUtility;
import com.chaitanya.utility.Validation;

@Repository
@Transactional(rollbackFor=IOException.class)
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseHeaderJPA add(ExpenseHeaderJPA expenseHeaderJPA) throws IOException{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(expenseHeaderJPA);
		for(ExpenseDetailJPA expenseDetailJPA : expenseHeaderJPA.getExpenseDetailJPA()){
			if(Validation.validateForNullObject(expenseDetailJPA.getReceipt()))
				FTPUtility.uploadFile(expenseDetailJPA.getReceipt());
		}
		return expenseHeaderJPA;
	}

	@Override
	public List<ExpenseHeaderJPA> getDraftExpenseList(
			ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderList= session.createCriteria(ExpenseHeaderJPA.class)
													.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId() ))
													.list();
		return expsensHeaderList;
	}

	@Override
	public ExpenseHeaderJPA getExpense(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		ExpenseHeaderJPA expsensHeaderJPA= (ExpenseHeaderJPA) session.createCriteria(ExpenseHeaderJPA.class)
													.add(Restrictions.eq("expenseHeaderId",expenseHeaderDTO.getExpenseHeaderId() ))
													.uniqueResult();
		return expsensHeaderJPA;
	}

}
