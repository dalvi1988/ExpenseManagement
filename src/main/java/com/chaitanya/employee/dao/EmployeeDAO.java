package com.chaitanya.employee.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.utility.Validation;

@Repository
@Transactional
public class EmployeeDAO implements IEmployeeDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public EmployeeJPA add(EmployeeJPA employee){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(employee);
		return employee;
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeJPA> findAllUnderCompany(CompanyDTO companyDTO) {
		Session session=sessionFactory.getCurrentSession();
		List<EmployeeJPA> employeeList=null;
		if(Validation.validateForNullObject(companyDTO)){
			employeeList=(List<EmployeeJPA>)session.createCriteria(EmployeeJPA.class)
										.createAlias("branchJPA.companyJPA", "company",JoinType.LEFT_OUTER_JOIN)
										.add(Restrictions.eq("company.companyId", companyDTO.getCompanyId()))
										.list();
		}
		return employeeList;
	}

	
}
