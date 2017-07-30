package com.chaitanya.payment.service;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;
import com.chaitanya.jpa.PaymentJPA;
import com.chaitanya.payment.convertor.PaymentConvertor;
import com.chaitanya.payment.dao.IPaymentDAO;
import com.chaitanya.payment.model.PaymentDTO;
import com.chaitanya.utility.Validation;

@Service("paymentService")
@Transactional(rollbackFor=Exception.class)
public class PaymentService implements IPaymentService{
	@Autowired
	private IPaymentDAO paymentDAO;
	
	private Logger logger= LoggerFactory.getLogger(PaymentService.class);


	@Override
	public BaseDTO makePayment(BaseDTO baseDTO) throws ParseException {
		logger.debug("PaymentService: makePayment-Start");
		validatePaymentDTO(baseDTO);
		try{
			PaymentJPA paymentJPA=PaymentConvertor.setPaymentDTOToJPA((PaymentDTO)baseDTO);
			if (Validation.validateForNullObject(paymentJPA)) {
				paymentJPA=paymentDAO.makePayment(paymentJPA);
				if(Validation.validateForNullObject(paymentJPA.getPaymentDetailId())){
					if(paymentJPA.getModuleName().equals("Expense")){
						int updateCount=paymentDAO.updateProcessInstance(paymentJPA);
						if(updateCount ==1){
							
						}
						else{
							baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
						}
					}
				}
				baseDTO.setServiceStatus(ServiceStatus.SUCCESS);
			}
			else{
				baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			}
		}
		catch(DataIntegrityViolationException e){
			baseDTO.setServiceStatus(ServiceStatus.BUSINESS_VALIDATION_FAILURE);
			baseDTO.setMessage(new StringBuilder(e.getMessage()));
			logger.error("PaymentService: Exception",e);
		}
		logger.debug("PaymentService: makePayment-End");
		return baseDTO;
	}
	
	private void validatePaymentDTO(BaseDTO baseDTO) {
		if( baseDTO == null  || !(baseDTO instanceof PaymentDTO)){
			throw new IllegalArgumentException("Object expected of PaymentDTO type.");
		}
	}
}
