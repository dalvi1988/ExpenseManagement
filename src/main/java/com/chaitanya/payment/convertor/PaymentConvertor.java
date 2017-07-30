package com.chaitanya.payment.convertor;

import java.text.ParseException;

import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.PaymentJPA;
import com.chaitanya.payment.model.PaymentDTO;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class PaymentConvertor {
	
	public static PaymentDTO setPaymentJPAtoDTO(PaymentJPA paymentJPA){
		PaymentDTO paymentDTO=null;
		if(Validation.validateForNullObject(paymentJPA)){
			
		}
		return paymentDTO;
	}
	
	
	public static PaymentJPA setPaymentDTOToJPA(PaymentDTO paymentDTO) throws ParseException
	{
		PaymentJPA paymentJPA=null;
		if(Validation.validateForNullObject(paymentDTO)){
			paymentJPA= new PaymentJPA();
			paymentJPA.setPaymentDetailId(paymentDTO.getPaymentDetailId());
			paymentJPA.setModuleName(paymentDTO.getModuleName());
			paymentJPA.setVoucherId(paymentDTO.getVoucherId());
			paymentJPA.setAmount(paymentDTO.getAmount());
			paymentJPA.setDate(Convertor.stringToCalendar(paymentDTO.getDate(),Convertor.dateFormatWithTime));
			
			EmployeeJPA paidByEmployeeJPA= new EmployeeJPA();
			paidByEmployeeJPA.setEmployeeId(paymentDTO.getPaidByEmployeeDTO().getEmployeeId());
			paymentJPA.setPaidByEmployeeJPA(paidByEmployeeJPA);
		}
		return paymentJPA;
	}
}
