package com.chaitanya.advance.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.ParameterMode;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.AdvanceJPA;
import com.chaitanya.jpa.AdvanceProcessHistoryJPA;
import com.chaitanya.jpa.AdvanceProcessInstanceJPA;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

@Repository
public class AdvanceDAO implements IAdvanceDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public AdvanceJPA saveUpdateAdvance(AdvanceJPA advanceJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(advanceJPA);
		return advanceJPA;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateProcessInstance(AdvanceJPA advanceJPA, int currentVoucherStatus,EmployeeDTO approvalEmployeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		// Get Employee details by id
		
		EmployeeJPA employeeJPA= (EmployeeJPA) session.get(EmployeeJPA.class,advanceJPA.getEmployeeJPA().getEmployeeId());
		
		Criterion deptCriterion= Restrictions.or(
								 Restrictions.eq("departmentJPA.departmentId",employeeJPA.getDepartmentJPA().getDepartmentId()),
								 Restrictions.isNull("departmentJPA.departmentId")
								 );
		
		List<ApprovalFlowJPA> approvalFlowList = (List<ApprovalFlowJPA>) session.createCriteria(ApprovalFlowJPA.class)
												.add(Restrictions.eq("branchJPA.branchId", employeeJPA.getBranchJPA().getBranchId()))
												.add(deptCriterion)
												.add(Restrictions.eq("status", 'Y'))
												.list();
		
		ApprovalFlowJPA functionalFlowJPA=null;
		ApprovalFlowJPA branchFlowJPA=null;
		ApprovalFlowJPA financeFlowJPA=null;
		
		boolean functionFlowFlag=false,branchFlowFlag=false,financeFlowFlag=false;
		
		if(Validation.validateForNullObject(approvalFlowList)){
			for(ApprovalFlowJPA approvalFlowJPA:approvalFlowList){
				if(functionFlowFlag==false && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					functionFlowFlag = true;
					functionalFlowJPA = approvalFlowJPA;
				}
				else if(branchFlowFlag==false && approvalFlowJPA.getIsBranchFlow()=='Y' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					branchFlowFlag = true;
					branchFlowJPA = approvalFlowJPA;
				}
				else if(financeFlowFlag==false && approvalFlowJPA.getIsBranchFlow()=='N' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
					financeFlowFlag = true;
					financeFlowJPA = approvalFlowJPA;
				}
			}
		}
		
		if( financeFlowFlag== false){
			System.out.println("No finance Worklflow");
		}
		else if(functionFlowFlag == false && branchFlowFlag == false){
			System.out.println("No Functional & Branch Flow Worklflow");
		}
		else if(functionFlowFlag == true){
			System.out.println("Executing function flow.");
	    	getApprovalOfLevel(currentVoucherStatus,advanceJPA,functionalFlowJPA,financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
		}
		else if(branchFlowFlag == true){
			System.out.println("Executing branch flow.");
	    	getApprovalOfLevel(currentVoucherStatus,advanceJPA,branchFlowJPA, financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
		}
		
		
	}
	
	private void getApprovalOfLevel(int currentVoucerStatus, AdvanceJPA advanceJPA, ApprovalFlowJPA functionalApprovalFlow, ApprovalFlowJPA financeApprovalFlow, EmployeeJPA employeeJPA,EmployeeDTO approvalEmployeeDTO, Session session){
		Long approvalId = null;
		int statusId=0;
		Long level = null;
		String levelInfo = null;
		
		if(currentVoucerStatus == 2){
			statusId=11;
			level = functionalApprovalFlow.getLevel1();
			levelInfo = "Functional Level1";

		}
		else if(currentVoucerStatus == 11){
			statusId=21;
			level = functionalApprovalFlow.getLevel2();
			levelInfo = "Functional Level2";
		}
		else if(currentVoucerStatus == 21){
			statusId=31;
			level = functionalApprovalFlow.getLevel3();
			levelInfo = "Functional Level3";
		}
		else if(currentVoucerStatus == 31){
			statusId=111;
			level = financeApprovalFlow.getLevel1();
			levelInfo = "Finance Level1";
		}
		else if(currentVoucerStatus == 111){
			statusId=121;
			level = financeApprovalFlow.getLevel2();
			levelInfo = "Finance Level2";
		}
		else if(currentVoucerStatus == 121){
			statusId=131;
			level = financeApprovalFlow.getLevel3();
			levelInfo = "Finance Level3";
		}
		else if(currentVoucerStatus == 131){
			statusId=4;
			
			AdvanceProcessInstanceJPA processInstanceJPA = advanceJPA.getProcessInstanceJPA();
			if(! Validation.validateForNullObject(processInstanceJPA)){
				processInstanceJPA= new AdvanceProcessInstanceJPA();
			}
			
			/*EmployeeJPA pendingAt = new EmployeeJPA();
			pendingAt.setEmployeeId(advanceJPA.getEmployeeJPA().getEmployeeId());*/
			processInstanceJPA.setPendingAt(null);
			
			EmployeeJPA approveBy = new EmployeeJPA();
			approveBy.setEmployeeId(approvalEmployeeDTO.getEmployeeId());
			processInstanceJPA.setProcessedBy(approveBy);
		
			
			VoucherStatusJPA voucherStatusJPA = new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(statusId);
			processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
			
			processInstanceJPA.setAdvanceJPA(advanceJPA);
			
			processInstanceJPA.setComment("");
			
			advanceJPA.setProcessInstanceJPA(processInstanceJPA);
			
		}
			
		if(Validation.validateForNullObject(level)){
			if(level == -10){
				 approvalId= employeeJPA.getReportingMgr().getEmployeeId();
			}
			else if(level == -11){
				approvalId=employeeJPA.getReportingMgr().getReportingMgr().getEmployeeId();
			}
			else if(level == -1){
				approvalId = (Long) session.createCriteria(DepartmentHeadJPA.class)
							.setProjection(Projections.property("employeeJPA.employeeId"))
							.add(Restrictions.eq("departmentJPA.departmentId",employeeJPA.getDepartmentJPA().getDepartmentId()))
							.add(Restrictions.eq("branchJPA.branchId",employeeJPA.getBranchJPA().getBranchId()))
							.uniqueResult();
			}
			else{
				approvalId=level;
			}
		
			if(employeeJPA.getEmployeeId()== approvalId){
				AdvanceProcessHistoryJPA processHistoryJPA = new AdvanceProcessHistoryJPA();
				processHistoryJPA.setComment("Skipping "+levelInfo+" because voucher creator and approval are same");
				processHistoryJPA.setAdvanceJPA(advanceJPA);
				processHistoryJPA.setProcessDate(advanceJPA.getModifiedDate());
				advanceJPA.getProcessHistoryJPA().add(processHistoryJPA);
				getApprovalOfLevel(statusId,advanceJPA,functionalApprovalFlow,financeApprovalFlow, employeeJPA,approvalEmployeeDTO, session);
			}
			else{
				AdvanceProcessInstanceJPA processInstanceJPA = advanceJPA.getProcessInstanceJPA();
				if(! Validation.validateForNullObject(processInstanceJPA)){
					processInstanceJPA= new AdvanceProcessInstanceJPA();
				}
				
				EmployeeJPA pendingAt = new EmployeeJPA();
				pendingAt.setEmployeeId(approvalId);
				processInstanceJPA.setPendingAt(pendingAt);
				
				if(Validation.validateForNullObject(approvalEmployeeDTO)){
					EmployeeJPA approveBy = new EmployeeJPA();
					approveBy.setEmployeeId(approvalEmployeeDTO.getEmployeeId());
					processInstanceJPA.setProcessedBy(approveBy);
				}
				
				VoucherStatusJPA voucherStatusJPA = new VoucherStatusJPA();
				voucherStatusJPA.setVoucherStatusId(statusId);
				processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
				
				processInstanceJPA.setAdvanceJPA(advanceJPA);
				
				processInstanceJPA.setComment("");
				
				advanceJPA.setProcessInstanceJPA(processInstanceJPA);
			}
		}
		else if(currentVoucerStatus != 131){
			System.out.println(levelInfo +" not available.");
			getApprovalOfLevel(statusId,advanceJPA,functionalApprovalFlow,financeApprovalFlow, employeeJPA,approvalEmployeeDTO, session);
		}
	}

	@Override
	public String generateAdvanceNumber(AdvanceDTO advanceDTO) {
	
		Session session = sessionFactory.getCurrentSession();
		ProcedureCall query =  session.createStoredProcedureCall("voucher_number");
				query.registerParameter(
			        "module", String.class, ParameterMode.IN).bindValue("ADVANCE_EXPENSE");
				query.registerParameter(
				        "companyId", Integer.class, ParameterMode.IN).bindValue(advanceDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId());
				query.registerParameter(
			        "voucherNumber", String.class, ParameterMode.OUT);

		ProcedureOutputs procedureResult=query.getOutputs();
		String voucherNumber= (String) procedureResult.getOutputParameterValue("voucherNumber");
		voucherNumber="Advance/"+Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormat)+"/"+voucherNumber;
		return voucherNumber;
	}

	
	
	@Override
	public List<AdvanceJPA> getDraftAdvanceList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.list();
		return advanceJPAList;
	}
	
	@Override
	public Long getDraftAdvanceCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Long draftAdvanceCount = (Long)session.createCriteria(AdvanceJPA.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.uniqueResult();
		return draftAdvanceCount;
	}

	@Override
	public AdvanceJPA getAdvance(AdvanceDTO advanceDTO) {
		Session session = sessionFactory.getCurrentSession();
		AdvanceJPA advanceJPA= (AdvanceJPA) session.createCriteria(AdvanceJPA.class)
												.add(Restrictions.eq("advanceDetailId",advanceDTO.getAdvanceDetailId() ))
												.uniqueResult();
		return advanceJPA;
	}

	@Override
	public List<AdvanceJPA> getPendingAdvanceList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={4,11,21,31,41,51,61,71,81,91,101,111,121,131,141,151};
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return advanceJPAList;
	}
	
	@Override
	public Long getPendingAdvanceCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={4,11,21,31,41,51,61,71,81,91,101,111,121,131,141,151};
		Long pendingAdvanceCount= (Long) session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.uniqueResult();
		return pendingAdvanceCount;
	}

	@Override
	public List<AdvanceJPA> getAdvanceToBeApprove(AdvanceDTO advanceDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};//Rejected List
		
		DetachedCriteria subquery = DetachedCriteria.forClass(AdvanceProcessInstanceJPA.class)
									.add(Restrictions.eq("pendingAt.employeeId",advanceDTO.getEmployeeDTO().getEmployeeId()))
									.add(Restrictions.not(Restrictions.in("voucherStatusJPA.voucherStatusId", voucherId)))
									.setProjection(Projections.property("advanceJPA.advanceDetailId"));
		
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
												.setFetchMode("employeeJPA",FetchMode.JOIN)
												.add(Subqueries.propertyIn("advanceDetailId", subquery))
												.list();
														

		return advanceJPAList;
	
	}
	
	@Override
	public List<AdvanceProcessHistoryJPA> getProcessedByMeAdvances(AdvanceDTO advanceDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={1,2};//Draft and Send For Approval
				
		@SuppressWarnings("unchecked")
		List<AdvanceProcessHistoryJPA> advanceJPAList= session.createCriteria(AdvanceProcessHistoryJPA.class)
												.add(Restrictions.not(Restrictions.in("voucherStatusJPA.voucherStatusId", voucherId)))
												.add(Restrictions.eq("processedBy.employeeId",advanceDTO.getEmployeeDTO().getEmployeeId()))
												.list();
														

		return advanceJPAList;
	
	}
	
	@Override
	public AdvanceJPA getAdvanceById(AdvanceDTO advanceDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		AdvanceJPA advanceJPA = (AdvanceJPA) session.get(AdvanceJPA.class, advanceDTO.getAdvanceDetailId());
														
		return advanceJPA;
	}
	
	@Override
	public List<AdvanceJPA> getRejectedAdvanceList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return advanceJPAList;
	}
	
	@Override
	public Long getRejectedAdvanceCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		Long rejectedAdvanceCount= (Long) session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.uniqueResult();
		return rejectedAdvanceCount;
	}

	@Override
	public List<AdvanceJPA> getApprovedAdvanceByEmp(AdvanceDTO advanceDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",advanceDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", new Integer(5)))
				.list();
		return advanceJPAList;
	}
	
	/**
	 * Fetch list of advances of login employee
	 */
	@Override
	public List<AdvanceJPA> getPendingAdvancesAtPaymentDesk(EmployeeDTO employeeDTO) {Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", new Integer(4)))
				.list();
		return advanceJPAList;
	}
	
	@Override
	public List<AdvanceJPA> getPaidAdvances(AdvanceDTO advanceDTO) {Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",advanceDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", new Integer(5)))
				.list();
		return advanceJPAList;
	}
	
	@Override
	public Long getPaidAdvancesCount(EmployeeDTO employeeDTO) {Session session = sessionFactory.getCurrentSession();
		Long paidAdvanceCount= (Long) session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", new Integer(5)))
				.uniqueResult();
		return paidAdvanceCount;
	}
	
	
	/**
	 * Fetch List of advances pending for payment of all employee under company
	 */
	@Override
	public List<AdvanceJPA> getPaymentDeskAdvances(AdvanceDTO advanceDTO) {Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<AdvanceJPA> advanceJPAList= session.createCriteria(AdvanceJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA", "employeeJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA.branchJPA", "branchJPA",JoinType.INNER_JOIN)
				.createAlias("branchJPA.companyJPA", "companyJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("companyJPA.companyId",advanceDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", new Integer(4)))
				.list();
		return advanceJPAList;
	}

}
