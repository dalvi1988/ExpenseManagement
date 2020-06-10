package com.chaitanya.department.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.company.model.CompanyDTO;
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
	public List<DepartmentJPA> findAllDepartmentUnderCompany(CompanyDTO companyDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<DepartmentJPA> departmentList=(List<DepartmentJPA>)session.createCriteria(DepartmentJPA.class)
									.createAlias("branchJPA.companyJPA", "company",JoinType.LEFT_OUTER_JOIN)
									.add(Restrictions.eq("company.companyId", companyDTO.getCompanyId()))
									.list();
		return departmentList;
	}
	
}
