package com.chaitanya.approvalFlow.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;

@Repository
@Transactional
public class ApprovalFlowDAO implements IApprovalFlowDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	public ApprovalFlowJPA add(ApprovalFlowJPA approvalFlowJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(approvalFlowJPA);
		return approvalFlowJPA;
	}

	public List<ApprovalFlowJPA> findFunctionalFlowUnderBranch(BranchDTO branchDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ApprovalFlowJPA> departmentHeadList=(List<ApprovalFlowJPA>)session.createCriteria(ApprovalFlowJPA.class)
			.add(Restrictions.eq("branchJPA.branchId",branchDTO.getBranchId() ))
			//.add(Restrictions.eq("departmentJPA.departmentId",null))
				.list();
		return departmentHeadList;
	}

}
