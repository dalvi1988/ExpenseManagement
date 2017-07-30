package com.chaitanya.payment.dao;

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


}
