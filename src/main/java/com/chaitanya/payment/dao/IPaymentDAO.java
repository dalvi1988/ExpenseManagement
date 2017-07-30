package com.chaitanya.payment.dao;

import com.chaitanya.jpa.PaymentJPA;

public interface IPaymentDAO {
	public PaymentJPA makePayment(PaymentJPA paymentJPA);

}
