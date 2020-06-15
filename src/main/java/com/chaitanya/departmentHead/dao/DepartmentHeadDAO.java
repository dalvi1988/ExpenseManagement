package com.chaitanya.departmentHead.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.EmployeeJPA;

@Repository
@Transactional
public class DepartmentHeadDAO implements IDepartmentHeadDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public DepartmentHeadJPA add(DepartmentHeadJPA departmentHeadJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(departmentHeadJPA);
		return departmentHeadJPA;
	}

	@Override
	public List<DepartmentHeadJPA> findDepartmentHeadUnderBranch(BranchDTO branchDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<DepartmentHeadJPA> departmentHeadList=(List<DepartmentHeadJPA>)session.createCriteria(DepartmentHeadJPA.class)
			.add(Restrictions.eq("branchJPA.branchId",branchDTO.getBranchId() ))
				.list();
		return departmentHeadList;
	}
	
	@Override
	public DepartmentHeadJPA findByDepartmentHeadIdBranchId(EmployeeJPA employeeJPA){
		Session session=sessionFactory.getCurrentSession();
		DepartmentHeadJPA departmentHeadJPA = (DepartmentHeadJPA) session.createCriteria(DepartmentHeadJPA.class)
		.add(Restrictions.eq("departmentJPA.departmentId",employeeJPA.getDepartmentJPA().getDepartmentId()))
		.add(Restrictions.eq("branchJPA.branchId",employeeJPA.getBranchJPA().getBranchId()))
		.uniqueResult();
		return departmentHeadJPA;
	}

	@Override
	public DepartmentHeadJPA checkDepartmentHeadExist(DepartmentHeadDTO departmentHeadDTO) {
		Session session=sessionFactory.getCurrentSession();
		DepartmentHeadJPA departmentHeadJPA = (DepartmentHeadJPA) session.createCriteria(DepartmentHeadJPA.class)
		.add(Restrictions.eq("departmentJPA.departmentId",departmentHeadDTO.getDepartmentId()))
		.add(Restrictions.eq("branchJPA.branchId",departmentHeadDTO.getBranchDTO().getBranchId()))
		.uniqueResult();
		return departmentHeadJPA;
	}

}
