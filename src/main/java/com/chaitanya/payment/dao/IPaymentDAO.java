package com.chaitanya.payment.dao;

import com.chaitanya.jpa.PaymentJPA;

public interface IPaymentDAO {
	public PaymentJPA makePayment(PaymentJPA paymentJPA);

	public int updateProcessInstance(PaymentJPA paymentJPA);

	public void updateProcessHistory(PaymentJPA paymentJPA);

	public void updateAdvanceProcessHistory(PaymentJPA paymentJPA);

}
