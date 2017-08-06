package com.chaitanya.payment.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.jpa.PaymentJPA;
import com.chaitanya.jpa.ProcessHistoryJPA;
import com.chaitanya.jpa.VoucherStatusJPA;

@Repository
public class PaymentDAO implements IPaymentDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PaymentJPA makePayment(PaymentJPA paymentJPA) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(paymentJPA);
		return paymentJPA;
	}

	@Override
	public int updateProcessInstance(PaymentJPA paymentJPA) {
		String module=paymentJPA.getModuleName().equals("Expense")?"ProcessInstanceJPA":"AdvanceProcessInstanceJPA";
		String hql = "update "+module+" set pendingAt=null, processedBy=:processedBy, voucherStatusJPA=5 where expenseHeaderJPA.expenseHeaderId = :expenseHeaderId";
	    Query query =sessionFactory.getCurrentSession().createQuery(hql);
	    query.setLong("processedBy",paymentJPA.getPaidByEmployeeJPA().getEmployeeId());
	    query.setLong("expenseHeaderId",paymentJPA.getVoucherId());
	    return query.executeUpdate();
		
	}

	@Override
	public void updateProcessHistory(PaymentJPA paymentJPA) {
		ProcessHistoryJPA processHistoryJPA =new ProcessHistoryJPA();
		
		ExpenseHeaderJPA expenseHeaderJPA=new ExpenseHeaderJPA();
		expenseHeaderJPA.setExpenseHeaderId(paymentJPA.getVoucherId());
		processHistoryJPA.setExpenseHeaderJPA(expenseHeaderJPA);
		
		VoucherStatusJPA voucherStatusJPA =new VoucherStatusJPA();
		voucherStatusJPA.setVoucherStatusId(5);
		processHistoryJPA.setVoucherStatusJPA(voucherStatusJPA);
		
	    processHistoryJPA.setProcessedBy(paymentJPA.getPaidByEmployeeJPA());
	   
		processHistoryJPA.setProcessDate(paymentJPA.getDate());
		
		Session session=sessionFactory.getCurrentSession();
		session.save(processHistoryJPA);
		
	}


}
