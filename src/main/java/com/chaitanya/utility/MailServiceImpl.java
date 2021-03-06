package com.chaitanya.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
 
 
@Service("mailService")
public class MailServiceImpl {
 
    @Autowired
    JavaMailSender mailSender;
    
    @Autowired
    VelocityEngine velocityEngine;
    @Autowired
    Environment environment;
 
    public void sendAutoGeneratePassword(EmployeeDTO employeeDTO, String password) {
 
    	SimpleMailMessage  message = new SimpleMailMessage();
 
    	message.setTo(employeeDTO.getEmailId());
    	message.setBcc("expensewala@gmail.com");
    	message.setSubject("ExpenseWala Sign-in Credential"); 
    	message.setText("Dear "+employeeDTO.getFullName()+", "
    			+ "\n\nThank you for registering at ExpenseWala( http://www.expensewala.com ). Your login credential are as follow:"
    			+ "\n"
    			+ "\nUser Name: "+employeeDTO.getEmailId()
    			+ "\nPassword: "+password
    			+ "\n\nRegards"
    			+ "\nExpensWala Team"
    			); 
        mailSender.send(message);
    }
 
     public void sendApprovalMail(final ExpenseHeaderDTO expenseHeaderDTO,final List<ExpenseDetailDTO> expenseDetailDTOList) throws MessagingException, FileNotFoundException {
 
    	MimeMessage message =mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	  
        helper.setSubject("Expense voucher for approval; (Voucher No:  "+expenseHeaderDTO.getVoucherNumber()+")");
        helper.setBcc("expensewala@gmail.com");
        helper.setTo(expenseHeaderDTO.getPendingAtEmployeeDTO().getEmailId());
        Iterator<ExpenseDetailDTO> iterator= expenseDetailDTOList.iterator();
        String drive= environment.getProperty("fileDrive");
		String filePath=drive+"/"+expenseHeaderDTO.getEmployeeDTO().getBranchDTO().getCompanyDTO().getCompanyId()+"/"+expenseHeaderDTO.getExpenseHeaderId();
        while(iterator.hasNext()) {
        	ExpenseDetailDTO expenseDetailDTO= iterator.next();
        	String fileName=expenseDetailDTO.getFileName();
        	if(Validation.validateForNullObject(fileName)) {
        		FileSystemResource file = new FileSystemResource(filePath+"/"+fileName);
        		helper.addAttachment(fileName, file);
        	}
        }
  
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("expenseHeaderDTO", expenseHeaderDTO);
        model.put("expenseDetailList", expenseDetailDTOList);
        
        String text = geVelocityTemplateContent(model);//Use Velocity
        System.out.println("Template content : "+text);
        helper.setText(text,true);
                // use the true flag to indicate you need a multipart message
        mailSender.send(message);
    }
     
     public void sendRejectedMail(ExpenseHeaderDTO expenseHeaderDTO) {
    	 
     	SimpleMailMessage  message = new SimpleMailMessage();
  
     	message.setTo(expenseHeaderDTO.getEmployeeDTO().getEmailId());
     	message.setBcc("expensewala@gmail.com");
     	message.setSubject("Your Expense voucher "+expenseHeaderDTO.getVoucherNumber() +" has been rejected."); 
     	message.setText("Dear "+expenseHeaderDTO.getEmployeeDTO().getFullName()+", "
     			+ "\n\nYour expense voucher "+expenseHeaderDTO.getVoucherNumber() +"has been rejected by "+expenseHeaderDTO.getProcessedByEmployeeDTO().getFullName()
     			+ "\nRejected comment: "+expenseHeaderDTO.getRejectionComment()
     			+ "\nPlease log into system to resubmit voucher. Your voucher is available at 'Rejected Expenses' under 'Expense Voucher Status' menu."
     			+ "\n\nRegards"
     			+ "\nExpensWala Team"
     			); 
         mailSender.send(message);
     }
 
     public String geVelocityTemplateContent(Map<String, Object> model){
         StringBuffer content = new StringBuffer();
         try{
             content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailTemplate/approvalMailTemplate.vm","UTF-8", model));
             return content.toString();
         }catch(Exception e){
             System.out.println("Exception occured while processing velocity template:"+e.getMessage());
         }
           return "";
     }
  
}
