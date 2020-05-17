package com.chaitanya.expense.dao;


import java.io.IOException;
import java.util.List;

import javax.persistence.ParameterMode;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.ProcessInstanceJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.Validation;
import com.chaitanya.utility.VoucherStatusEnum;

@Repository
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseHeaderJPA saveUpdateExpense(ExpenseHeaderJPA expenseHeaderJPA) throws IOException{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(expenseHeaderJPA);
		
		return expenseHeaderJPA;
	}

//	@SuppressWarnings("unchecked")
//	public void updateProcessInstance(ExpenseHeaderJPA expenseHeaderJPA, int currentVoucherStatus,EmployeeDTO approvalEmployeeDTO) {
//		Session session = sessionFactory.getCurrentSession();
//		
//		// Get Employee details by id who has created voucher
//		EmployeeJPA employeeJPA= (EmployeeJPA) session.get(EmployeeJPA.class,expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
//		
//		Criterion deptCriterion= Restrictions.or(
//								 Restrictions.eq("departmentJPA.departmentId",employeeJPA.getDepartmentJPA().getDepartmentId()),
//								 Restrictions.isNull("departmentJPA.departmentId")
//								 );
//		
//		List<ApprovalFlowJPA> approvalFlowList = (List<ApprovalFlowJPA>) session.createCriteria(ApprovalFlowJPA.class)
//												.add(Restrictions.eq("branchJPA.branchId", employeeJPA.getBranchJPA().getBranchId()))
//												.add(deptCriterion)
//												.add(Restrictions.eq("status", 'Y'))
//												.list();
//		
//		
//		ApprovalFlowJPA functionalFlowJPA=null;
//		ApprovalFlowJPA branchFlowJPA=null;
//		ApprovalFlowJPA financeFlowJPA=null;
//		
//		boolean functionFlowFlag=false,branchFlowFlag=false,financeFlowFlag=false;
//		
//		if(Validation.validateForNullObject(approvalFlowList)){
//			for(ApprovalFlowJPA approvalFlowJPA : approvalFlowList){
//				if(functionFlowFlag==false && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
//					functionFlowFlag = true;
//					functionalFlowJPA = approvalFlowJPA;
//				}
//				else if(branchFlowFlag==false && approvalFlowJPA.getIsBranchFlow()=='Y' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
//					branchFlowFlag = true;
//					branchFlowJPA = approvalFlowJPA;
//				}
//				else if(financeFlowFlag==false && approvalFlowJPA.getIsBranchFlow()=='N' && Validation.validateForNullObject(approvalFlowJPA.getBranchJPA()) && !Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
//					financeFlowFlag = true;
//					financeFlowJPA = approvalFlowJPA;
//				}
//			}
//		}
//		
//		if( financeFlowFlag== false){
//			System.out.println("No finance Worklflow");
//		}
//		else if(functionFlowFlag == false && branchFlowFlag == false){
//			System.out.println("No Functional & Branch Flow Worklflow");
//		}
//		else if(functionFlowFlag == true){
//			System.out.println("Executing function flow.");
//	    	getApprovalOfLevel(currentVoucherStatus,expenseHeaderJPA,functionalFlowJPA,financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
//		}
//		else if(branchFlowFlag == true){
//			System.out.println("Executing branch flow.");
//	    	getApprovalOfLevel(currentVoucherStatus,expenseHeaderJPA,branchFlowJPA, financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
//		}
//		
//	}
	
	public void updateProcessInstanceByApprovalFlow(int currentVoucerStatus, ExpenseHeaderJPA expenseHeaderJPA, ApprovalFlowJPA functionalApprovalFlow, ApprovalFlowJPA financeApprovalFlow, EmployeeDTO approvalEmployeeDTO){
		Session session = sessionFactory.getCurrentSession();
		EmployeeJPA employeeJPA= expenseHeaderJPA.getEmployeeJPA();
		Long approvalId = null;
		int nextStatusId=0;
		Long level = null;
		String levelInfo = null;
		
		if(currentVoucerStatus == VoucherStatusEnum.SEND_FOR_APPROVAL.getValue()){//2
			nextStatusId = VoucherStatusEnum.FUNCTIONAL_PENDING_AT_1ST_LEVEL.getValue();
			level = functionalApprovalFlow.getLevel1();
			levelInfo = "Functional Level1";
		}
		else if(currentVoucerStatus == VoucherStatusEnum.FUNCTIONAL_PENDING_AT_1ST_LEVEL.getValue()){//11
			nextStatusId = VoucherStatusEnum.FUNCTIONAL_PENDING_AT_2ND_LEVEL.getValue();
			level = functionalApprovalFlow.getLevel2();
			levelInfo = "Functional Level2";
		}
		else if(currentVoucerStatus == VoucherStatusEnum.FUNCTIONAL_PENDING_AT_2ND_LEVEL.getValue()){//21
			nextStatusId = VoucherStatusEnum.FUNCTIONAL_PENDING_AT_3RD_LEVEL.getValue();
			level = functionalApprovalFlow.getLevel3();
			levelInfo = "Functional Level3";
		}
		else if(currentVoucerStatus == VoucherStatusEnum.FUNCTIONAL_PENDING_AT_3RD_LEVEL.getValue()){//31 
			nextStatusId= VoucherStatusEnum.FINANCE_PENDING_AT_1ST_LEVEL.getValue();
			level = financeApprovalFlow.getLevel1();
			levelInfo = "Finance Level1";
		}
		else if(currentVoucerStatus ==  VoucherStatusEnum.FINANCE_PENDING_AT_1ST_LEVEL.getValue()){//111
			nextStatusId = VoucherStatusEnum.FINANCE_PENDING_AT_2ND_LEVEL.getValue();
			level = financeApprovalFlow.getLevel2();
			levelInfo = "Finance Level2";
		}
		else if(currentVoucerStatus == VoucherStatusEnum.FINANCE_PENDING_AT_2ND_LEVEL.getValue()){//121
			nextStatusId = VoucherStatusEnum.FINANCE_PENDING_AT_3RD_LEVEL.getValue();
			level = financeApprovalFlow.getLevel3();
			levelInfo = "Finance Level3";
		}
		else if(currentVoucerStatus == VoucherStatusEnum.FINANCE_PENDING_AT_3RD_LEVEL.getValue()){//131
			nextStatusId=VoucherStatusEnum.COMPLETELY_APPROVED.getValue();
			
			ProcessInstanceJPA processInstanceJPA = expenseHeaderJPA.getProcessInstanceJPA();
			if(! Validation.validateForNullObject(processInstanceJPA)){
				processInstanceJPA= new ProcessInstanceJPA();
			}
			
			/*EmployeeJPA pendingAt = new EmployeeJPA();
			pendingAt.setEmployeeId(expenseHeaderJPA.getEmployeeJPA().getEmployeeId());*/
			processInstanceJPA.setPendingAt(null);
			
			EmployeeJPA approveBy = new EmployeeJPA();
			approveBy.setEmployeeId(approvalEmployeeDTO.getEmployeeId());
			processInstanceJPA.setProcessedBy(approveBy);
		
			
			VoucherStatusJPA voucherStatusJPA = new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(nextStatusId);
			processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
			
			processInstanceJPA.setExpenseHeaderJPA(expenseHeaderJPA);
			
			processInstanceJPA.setComment("");
			
			expenseHeaderJPA.setProcessInstanceJPA(processInstanceJPA);
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
				ProcessHistoryJPA processHistoryJPA = new ProcessHistoryJPA();
				processHistoryJPA.setComment("Skipping "+levelInfo+" because voucher creator and approval are same");
				processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				processHistoryJPA.setProcessDate(expenseHeaderJPA.getModifiedDate());
				expenseHeaderJPA.getProcessHistoryJPA().add(processHistoryJPA);
				updateProcessInstanceByApprovalFlow(nextStatusId,expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow,approvalEmployeeDTO);
			}
			else{
				ProcessInstanceJPA processInstanceJPA = expenseHeaderJPA.getProcessInstanceJPA();
				if(! Validation.validateForNullObject(processInstanceJPA)){
					processInstanceJPA= new ProcessInstanceJPA();
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
				voucherStatusJPA.setVoucherStatusId(nextStatusId);
				processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
				
				processInstanceJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				
				processInstanceJPA.setComment("");
				
				expenseHeaderJPA.setProcessInstanceJPA(processInstanceJPA);
			}
		}
		else if(currentVoucerStatus != 131){
			System.out.println(levelInfo +" not available.");
			updateProcessInstanceByApprovalFlow(nextStatusId,expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow,approvalEmployeeDTO);
		}
	}
	
	@Override
	public String generateVoucherNumber(ExpenseHeaderDTO expenseHeaderDTO) {
	
		Session session = sessionFactory.getCurrentSession();
		ProcedureCall query =  session.createStoredProcedureCall("voucher_number");
				query.registerParameter(
			        "module", String.class, ParameterMode.IN).bindValue("EMPLOYEE_EXPENSE");
				query.registerParameter(
				        "companyId", Integer.class, ParameterMode.IN).bindValue(expenseHeaderDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId());
				query.registerParameter(
			        "voucherNumber", String.class, ParameterMode.OUT);

		ProcedureOutputs procedureResult=query.getOutputs();
		String voucherNumber= (String) procedureResult.getOutputParameterValue("voucherNumber");
		voucherNumber="Voucher/"+expenseHeaderDTO.getStartDate()+"-"+expenseHeaderDTO.getEndDate()+"/"+voucherNumber;
		return voucherNumber;
	}

	@Override
	public List<ExpenseHeaderJPA> getDraftExpenseList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("voucherStatusJPA","voucherStatusJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.list();
		return expsensHeaderList;
	}
	
	@Override
	public Long getDraftExpenseCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Long expsensHeaderCount= (Long) session.createCriteria(ExpenseHeaderJPA.class)
				.setProjection(Projections.rowCount())
				.createAlias("voucherStatusJPA","voucherStatusJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.uniqueResult();
		return expsensHeaderCount;
	}
	
	@Override
	public ExpenseHeaderJPA getExpense(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		ExpenseHeaderJPA expsensHeaderJPA= (ExpenseHeaderJPA) session.createCriteria(ExpenseHeaderJPA.class)
												.setFetchMode("advanceJPA",FetchMode.JOIN)
												.add(Restrictions.eq("expenseHeaderId",expenseHeaderDTO.getExpenseHeaderId() ))
												.uniqueResult();
		return expsensHeaderJPA;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getPendingExpenseList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={11,21,31,41,51,61,71,81,91,101,111,121,131,141,151};
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return expsensHeaderJPAList;
	}
	
	@Override
	public Long getPendingExpenseCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={11,21,31,41,51,61,71,81,91,101,111,121,131,141,151};
		Long pendingExpenseCount= (Long) session.createCriteria(ExpenseHeaderJPA.class)
				.setProjection(Projections.rowCount())
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.uniqueResult();
		return pendingExpenseCount;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getPendingExpensesAtPaymentDesk(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA", "employeeJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA.branchJPA", "branchJPA",JoinType.INNER_JOIN)
				.createAlias("branchJPA.companyJPA", "companyJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", 4))
				.list();
		return expsensHeaderJPAList;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getPaymentDeskExpense(ExpenseHeaderDTO expenseHeaderDTO) {
		// TODO	Get voucher whose status is 4 of current company
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA", "employeeJPA",JoinType.INNER_JOIN)
				.createAlias("employeeJPA.branchJPA", "branchJPA",JoinType.INNER_JOIN)
				.createAlias("branchJPA.companyJPA", "companyJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("companyJPA.companyId",expenseHeaderDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", 4))
				.list();
		return expsensHeaderJPAList;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getExpenseToBeApprove(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		
		DetachedCriteria subquery = DetachedCriteria.forClass(ProcessInstanceJPA.class)
									.add(Restrictions.eq("pendingAt.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
									.add(Restrictions.not(Restrictions.in("voucherStatusJPA.voucherStatusId", voucherId)))
									.setProjection(Projections.property("expenseHeaderJPA.expenseHeaderId"));
		
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
												.setFetchMode("eventJPA",FetchMode.JOIN)
												.setFetchMode("employeeJPA",FetchMode.JOIN)
												.setFetchMode("advanceJPA",FetchMode.JOIN)
												.add(Subqueries.propertyIn("expenseHeaderId", subquery))
												.list();
														

		return expsensHeaderJPAList;
	}
	
	@Override
	public List<ProcessHistoryJPA> getProcessedByMeExpense(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={1,2};
				
		@SuppressWarnings("unchecked")
		List<ProcessHistoryJPA> processHistoryJPAList= session.createCriteria(ProcessHistoryJPA.class)

												.add(Restrictions.not(Restrictions.in("voucherStatusJPA.voucherStatusId", voucherId)))
												.add(Restrictions.eq("processedBy.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
												.list();
														

		return processHistoryJPAList;
	}
	
	@Override
	public ExpenseHeaderJPA getExpenseHeaderById(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		ExpenseHeaderJPA expenseHeaderJPA = (ExpenseHeaderJPA) session.get(ExpenseHeaderJPA.class, expenseHeaderDTO.getExpenseHeaderId());
														
		return expenseHeaderJPA;
	}
	

	@Override
	public List<ExpenseHeaderJPA> getRejectedExpenseList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return expsensHeaderJPAList;
	}
	
	@Override
	public Long getRejectedExpenseCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		Long rejectedExpenseCount= (Long) session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.uniqueResult();
		return rejectedExpenseCount;
	}
	
	@Override
	public ExpenseHeaderJPA persistExpense(ExpenseHeaderJPA expenseHeaderJPA)
			throws IOException {
		Session session = sessionFactory.getCurrentSession();
		session.merge(expenseHeaderJPA);
		
		return expenseHeaderJPA;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetailJPA> getExpenseDetailsByHeaderId(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		List<ExpenseDetailJPA> expsensDetailJPAList= session.createCriteria(ExpenseDetailJPA.class)
												.add(Restrictions.eq("expenseHeaderJPA.expenseHeaderId", expenseHeaderDTO.getExpenseHeaderId()))
												.setFetchMode("expenseCategoryJPA",FetchMode.JOIN)
												.list();
														
		return expsensDetailJPAList;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getPaidExpenseList(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", 5))
				.list();
		return expsensHeaderJPAList;

	}
	
	@Override
	public Long getPaidExpenseCount(EmployeeDTO employeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		Long paidExpenseCount= (Long) session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("employeeJPA.employeeId",employeeDTO.getEmployeeId()))
				.add(Restrictions.eq("processInstanceJPA.voucherStatusJPA.voucherStatusId", 5))
				.uniqueResult();
		return paidExpenseCount;
	}

	@Override
	public void deleteExpenseDetail(ExpenseDetailJPA expenseDetailJPA) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(expenseDetailJPA);
	}
}
