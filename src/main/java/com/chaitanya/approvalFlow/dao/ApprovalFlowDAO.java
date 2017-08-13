package com.chaitanya.approvalFlow.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;

@Repository
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
			.add(Restrictions.isNotNull("departmentJPA.departmentId"))
			.add(Restrictions.eq("isBranchFlow",'N'))
				.list();
		return departmentHeadList;
	}
	
	public List<ApprovalFlowJPA> findFinanceFlowUnderBranch(BranchDTO branchDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ApprovalFlowJPA> departmentHeadList=(List<ApprovalFlowJPA>)session.createCriteria(ApprovalFlowJPA.class)
			.add(Restrictions.eq("branchJPA.branchId",branchDTO.getBranchId() ))
			.add(Restrictions.eq("isBranchFlow",'N'))
			.add(Restrictions.isNull("departmentJPA.departmentId"))
				.list();
		return departmentHeadList;
	}
	
	public List<ApprovalFlowJPA> findBranchFlowUnderBranch(BranchDTO branchDTO) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ApprovalFlowJPA> departmentHeadList=(List<ApprovalFlowJPA>)session.createCriteria(ApprovalFlowJPA.class)
			.add(Restrictions.eq("branchJPA.branchId",branchDTO.getBranchId() ))
			.add(Restrictions.eq("isBranchFlow",'Y'))
			.add(Restrictions.isNull("departmentJPA.departmentId"))
				.list();
		return departmentHeadList;
	}


	@Override
	public Integer deactivateFunctionalFlow(ApprovalFlowDTO approvalFlowDTO) {
		Session session=sessionFactory.getCurrentSession();
		
		int updatedEntities = session.createQuery("update ApprovalFlowJPA set status='N' where flowId= :flowId")
				.setLong( "flowId", approvalFlowDTO.getFlowId() )
		        .executeUpdate();
		
		return updatedEntities;
	}

	@Override
	public ApprovalFlowDTO validateFunctionalFlow(ApprovalFlowDTO approvalFlowDTO) {

		Session session=sessionFactory.getCurrentSession();
		try{
			Long count =(Long)session.createCriteria(ApprovalFlowJPA.class)
						.add(Restrictions.eq("branchJPA.branchId",approvalFlowDTO.getBranchDTO().getBranchId() ))
						.add(Restrictions.eq("isBranchFlow",'N'))
						.add(Restrictions.eq("departmentJPA.departmentId",approvalFlowDTO.getDepartmentDTO().getDepartmentId()))
						.add(Restrictions.eq("status",'Y'))
						.setProjection(Projections.rowCount())
						.uniqueResult();
			
			if(count == 0){
				approvalFlowDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				approvalFlowDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}catch(Exception e){
			approvalFlowDTO.setServiceStatus(ServiceStatus.FAILURE);
		}
	
		return approvalFlowDTO;
	}

	@Override
	public ApprovalFlowDTO validateFinanceFlow(ApprovalFlowDTO approvalFlowDTO) {

		Session session=sessionFactory.getCurrentSession();
		try{
			Long count =(Long)session.createCriteria(ApprovalFlowJPA.class)
						.add(Restrictions.eq("branchJPA.branchId",approvalFlowDTO.getBranchDTO().getBranchId() ))
						.add(Restrictions.eq("isBranchFlow",'N'))
						.add(Restrictions.isNull("departmentJPA.departmentId"))
						.add(Restrictions.eq("status",'Y'))
						.setProjection(Projections.rowCount())
						.uniqueResult();
			
			if(count == 0){
				approvalFlowDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				approvalFlowDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}catch(Exception e){
			approvalFlowDTO.setServiceStatus(ServiceStatus.FAILURE);
		}
	
		return approvalFlowDTO;
	}

	@Override
	public ApprovalFlowDTO validateBranchFlow(ApprovalFlowDTO approvalFlowDTO) {
		Session session=sessionFactory.getCurrentSession();
		try{
			Long count =(Long)session.createCriteria(ApprovalFlowJPA.class)
						.add(Restrictions.eq("branchJPA.branchId",approvalFlowDTO.getBranchDTO().getBranchId() ))
						.add(Restrictions.eq("isBranchFlow",'Y'))
						.add(Restrictions.isNull("departmentJPA.departmentId"))
						.add(Restrictions.eq("status",'Y'))
						.setProjection(Projections.rowCount())
						.uniqueResult();
			
			if(count == 0){
				approvalFlowDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				approvalFlowDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}catch(Exception e){
			approvalFlowDTO.setServiceStatus(ServiceStatus.FAILURE);
		}
	
		return approvalFlowDTO;
		
	}

}
