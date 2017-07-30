package com.chaitanya.payment.service;

import java.text.ParseException;

import com.chaitanya.base.BaseDTO;

public interface IPaymentService {

	BaseDTO makePayment(BaseDTO baseDTO) throws ParseException;


}
