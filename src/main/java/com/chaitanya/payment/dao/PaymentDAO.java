package com.chaitanya.payment.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.PaymentJPA;

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
		String hql = "update ProcessInstanceJPA set pendingAt=null, processedBy=:processedBy, voucherStatusJPA=5 where expenseHeaderJPA.expenseHeaderId = :expenseHeaderId";
	    Query query =sessionFactory.getCurrentSession().createQuery(hql);
	    query.setLong("processedBy",paymentJPA.getPaidByEmployeeJPA().getEmployeeId());
	    query.setString("expenseHeaderId",paymentJPA.getVoucherId());
	    return query.executeUpdate();
		
	}


}
