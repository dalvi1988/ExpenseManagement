package com.chaitanya.web.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.Command;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.payment.model.PaymentDTO;
import com.chaitanya.payment.service.IPaymentService;
import com.chaitanya.utility.ApplicationConstant;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

@Controller
public class PaymentController {

	@Autowired 
	private IPaymentService paymentService;
	
	private Logger logger= LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public @ResponseBody PaymentDTO makePayment(@RequestBody PaymentDTO receivedPaymentDTO){
		PaymentDTO toBeSentPaymentDTO=null;
		try{
			if(Validation.validateForNullObject(receivedPaymentDTO)){
				LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				receivedPaymentDTO.setDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
				
				receivedPaymentDTO.setPaidByEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
				
				BaseDTO baseDTO=paymentService.makePayment(receivedPaymentDTO);
				if(Validation.validateForSuccessStatus(baseDTO)){
					toBeSentPaymentDTO=(PaymentDTO)baseDTO;
					if(receivedPaymentDTO.getCommand().equals(Command.ADD)){
						toBeSentPaymentDTO.setMessage(new StringBuilder(ApplicationConstant.SAVE_RECORD));
					}
					else
						toBeSentPaymentDTO.setMessage(new StringBuilder(ApplicationConstant.UPDATE_RECORD));
				}
				else if(Validation.validateForBusinessFailureStatus(baseDTO)){
					toBeSentPaymentDTO=receivedPaymentDTO;
					toBeSentPaymentDTO.setMessage(new StringBuilder(ApplicationConstant.BUSSINESS_FAILURE));
				}
			}
		}
		catch(Exception e){
			logger.error("PaymentController: makePayment",e);
			toBeSentPaymentDTO.setMessage(new StringBuilder(ApplicationConstant.SYSTEM_FAILURE));
		}
		return toBeSentPaymentDTO;
	}
	
}
