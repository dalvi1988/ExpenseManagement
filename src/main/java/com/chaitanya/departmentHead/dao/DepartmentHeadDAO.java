package com.chaitanya.departmentHead.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.DepartmentHeadJPA;

@Repository
@Transactional
public class DepartmentHeadDAO implements IDepartmentHeadDAO{

	
	@Autowired
	SessionFactory sessionFactory;
	
	public DepartmentHeadJPA add(DepartmentHeadJPA department){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(department);
		return department;
	}

	public List<DepartmentHeadJPA> findDepartmentHeadUnderCompany(CompanyDTO companyDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<DepartmentHeadJPA> departmentHeadList=(List<DepartmentHeadJPA>)session.createCriteria(DepartmentHeadJPA.class)
				.list();
		return departmentHeadList;
	}

}
