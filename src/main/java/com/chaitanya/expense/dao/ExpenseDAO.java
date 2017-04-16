package com.chaitanya.expense.dao;


import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.DepartmentHeadJPA;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.ProcessInstanceJPA;
import com.chaitanya.jpa.VoucherStatusJPA;
import com.chaitanya.utility.FTPUtility;
import com.chaitanya.utility.Validation;

@Repository
@Transactional(rollbackFor=IOException.class)
public class ExpenseDAO implements IExpenseDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ExpenseHeaderJPA saveUpdateExpense(ExpenseHeaderJPA expenseHeaderJPA) throws IOException{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(expenseHeaderJPA);
		
		//Create process instance if voucher not saved as draft.
		if(expenseHeaderJPA.getVoucherStatusJPA().getVoucherStatusId() != 1)
			updateProcessInstance(expenseHeaderJPA,session);
		
		//Update attachment
		for(ExpenseDetailJPA expenseDetailJPA : expenseHeaderJPA.getExpenseDetailJPA()){
			if(Validation.validateForNullObject(expenseDetailJPA.getReceipt()))
				FTPUtility.uploadFile(expenseDetailJPA.getReceipt());
		}
		return expenseHeaderJPA;
	}

	@SuppressWarnings("unchecked")
	private void updateProcessInstance(ExpenseHeaderJPA expenseHeaderJPA,Session session) {
		// Get Employee details by id
		
		EmployeeJPA employeeJPA= (EmployeeJPA) session.get(EmployeeJPA.class,expenseHeaderJPA.getEmployeeJPA().getEmployeeId());
		
		Criterion deptCriterion= Restrictions.or(
								 Restrictions.eq("departmentJPA.departmentId",employeeJPA.getDepartmentJPA().getDepartmentId()),
								 Restrictions.isNull("departmentJPA.departmentId")
								 );
		
		List<ApprovalFlowJPA> approvalFlowList= session.createCriteria(ApprovalFlowJPA.class)
												.add(Restrictions.eq("branchJPA.branchId", employeeJPA.getBranchJPA().getBranchId()))
												.add(deptCriterion)
												.add(Restrictions.eq("status", 'Y'))
												.list();
		
		ApprovalFlowJPA functionalFlowJPA=null;
		ApprovalFlowJPA branchFlowJPA=null;
		
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
		    getApprovalOfLevel(expenseHeaderJPA,functionalFlowJPA, employeeJPA, session);
		}
		else if(branchFlowFlag == true){
			System.out.println("Executin branch flow.");
			getApprovalOfLevel(expenseHeaderJPA, branchFlowJPA, employeeJPA, session);
		}
		
		
	}
	
	private void getApprovalOfLevel(ExpenseHeaderJPA expenseHeaderJPA, ApprovalFlowJPA aprrovalFlowJPA, EmployeeJPA employeeJPA, Session session){
		Long approvalId = null;
		
		for(int i=1; i<=3; i++){
			Long level = null;
			if(i == 1)
				level=aprrovalFlowJPA.getLevel1();
			else if(i == 2)
				level=aprrovalFlowJPA.getLevel2();
			else if(i == 3)
				level=aprrovalFlowJPA.getLevel3();
			
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
				approvalId=aprrovalFlowJPA.getLevel1();
			}
			
			if(employeeJPA.getEmployeeId()== approvalId){
				ProcessHistoryJPA processHistoryJPA = new ProcessHistoryJPA();
				processHistoryJPA.setComment("Skipping level "+i+ " because voucher creator and approval are same");
				processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				expenseHeaderJPA.getProcessHistoryJPA().add(processHistoryJPA);
				continue;
			}
			else{
				ProcessInstanceJPA processInstanceJPA = new ProcessInstanceJPA();
				
				EmployeeJPA pendingAt = new EmployeeJPA();
				pendingAt.setEmployeeId(approvalId);
				processInstanceJPA.setPendingAt(pendingAt);
				
				VoucherStatusJPA voucherStatusJPA = new VoucherStatusJPA();
				voucherStatusJPA.setVoucherStatusId(Integer.parseInt(i+"1"));
				processInstanceJPA.setVoucherStatusJPA(voucherStatusJPA);
				
				processInstanceJPA.setExpenseHeaderJPA(expenseHeaderJPA);
				expenseHeaderJPA.setProcessInstanceJPA(processInstanceJPA);
				break;
			}
		}
	}

	@Override
	public List<ExpenseHeaderJPA> getDraftExpenseList(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderList= session.createCriteria(ExpenseHeaderJPA.class)
				.add(Restrictions.eq("employeeJPA.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.add(Restrictions.eq("voucherStatusJPA.voucherStatusId",new Integer(1)))
				.list();
		return expsensHeaderList;
	}

	@Override
	public ExpenseHeaderJPA getExpense(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		ExpenseHeaderJPA expsensHeaderJPA= (ExpenseHeaderJPA) session.createCriteria(ExpenseHeaderJPA.class)
												.add(Restrictions.eq("expenseHeaderId",expenseHeaderDTO.getExpenseHeaderId() ))
												.uniqueResult();
		return expsensHeaderJPA;
	}
	
	@Override
	public List<ExpenseHeaderJPA> getExpenseToBeApprove(ExpenseHeaderDTO expenseHeaderDTO) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ExpenseHeaderJPA> expsensHeaderList= session.createCriteria(ExpenseHeaderJPA.class)
				.add(Restrictions.eq("processInstanceJPA.pendingAt.employeeId",expenseHeaderDTO.getEmployeeDTO().getEmployeeId()))
				.list();

		return expsensHeaderList;
	}

}
