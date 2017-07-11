package com.chaitanya.expense.dao;


import java.io.IOException;
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

	@SuppressWarnings("unchecked")
	public void updateProcessInstance(ExpenseHeaderJPA expenseHeaderJPA, int currentVoucherStatus,EmployeeDTO approvalEmployeeDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		// Get Employee details by id
		
		EmployeeJPA employeeJPA= (EmployeeJPA) session.get(EmployeeJPA.class,expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
		
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
	    	getApprovalOfLevel(currentVoucherStatus,expenseHeaderJPA,functionalFlowJPA,financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
		}
		else if(branchFlowFlag == true){
			System.out.println("Executing branch flow.");
	    	getApprovalOfLevel(currentVoucherStatus,expenseHeaderJPA,branchFlowJPA, financeFlowJPA, employeeJPA,approvalEmployeeDTO, session);
		}
		
		
	}
	
	private void getApprovalOfLevel(int currentVoucerStatus, ExpenseHeaderJPA expenseHeaderJPA, ApprovalFlowJPA functionalApprovalFlow, ApprovalFlowJPA financeApprovalFlow, EmployeeJPA employeeJPA,EmployeeDTO approvalEmployeeDTO, Session session){
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
			
			ProcessInstanceJPA processInstanceJPA = expenseHeaderJPA.getProcessInstanceJPA();
			if(! Validation.validateForNullObject(processInstanceJPA)){
				processInstanceJPA= new ProcessInstanceJPA();
			}
			
			EmployeeJPA pendingAt = new EmployeeJPA();
			pendingAt.setEmployeeId(expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
			processInstanceJPA.setPendingAt(pendingAt);
			
			EmployeeJPA approveBy = new EmployeeJPA();
			approveBy.setEmployeeId(approvalEmployeeDTO.getEmployeeId());
			processInstanceJPA.setProcessedBy(approveBy);
		
			
			VoucherStatusJPA voucherStatusJPA = new VoucherStatusJPA();
			voucherStatusJPA.setVoucherStatusId(statusId);
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
				getApprovalOfLevel(statusId,expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow, employeeJPA,approvalEmployeeDTO, session);
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
				voucherStatusJPA.setVoucherStatusId(statusId);
				processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
				
				processInstanceJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				
				processInstanceJPA.setComment("");
				
				expenseHeaderJPA.setProcessInstanceJPA(processInstanceJPA);
			}
		}
		else if(currentVoucerStatus != 131){
			System.out.println(levelInfo +" not available.");
			getApprovalOfLevel(statusId,expenseHeaderJPA,functionalApprovalFlow,financeApprovalFlow, employeeJPA,approvalEmployeeDTO, session);
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
	public List<ExpenseHeaderJPA> getDraftExpenseList(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderList= session.createCriteria(ExpenseHeaderJPA.class)
				.setFetchMode("advanceJPA",FetchMode.JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.list();
		return expsensHeaderList;
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
	public List<ExpenseHeaderJPA> getPendingExpenseList(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={11,21,31,41,51,61,71,81,91,101,111,121,131,141,151};
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return expsensHeaderJPAList;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getPendingAtPaymentDeskList(ExpenseHeaderDTO expenseHeaderDTO) {
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
	public ExpenseHeaderJPA approveRejectExpenses(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		
		ExpenseHeaderJPA expenseHeaderJPA = (ExpenseHeaderJPA) session.get(ExpenseHeaderJPA.class, expenseHeaderDTO.getExpenseHeaderId());
														
		return expenseHeaderJPA;
	}
	

	@Override
	public List<ExpenseHeaderJPA> getRejectedExpenseList(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		Object voucherId[]={13,23,33,43,53,63,73,83,93,103,113,123,133,143,153};
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderJPAList= session.createCriteria(ExpenseHeaderJPA.class)
				.createAlias("processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
				.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.in("processInstanceJPA.voucherStatusJPA.voucherStatusId", voucherId))
				.list();
		return expsensHeaderJPAList;
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
}
