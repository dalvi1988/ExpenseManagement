package com.chaitanya.department.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.DepartmentJPA;

@Repository
@Transactional
public class DepartmentDAO implements IDepartmentDAO{

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public DepartmentJPA add(DepartmentJPA department){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(department);
		return department;
	}

	@Override
	public List<DepartmentJPA> findAll() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<DepartmentJPA> departmentList=(List<DepartmentJPA>)session.createCriteria(DepartmentJPA.class)
				.list();
		return departmentList;
	}
	
}
