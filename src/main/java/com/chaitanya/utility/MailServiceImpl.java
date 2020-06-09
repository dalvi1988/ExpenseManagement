package com.chaitanya.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chaitanya.employee.model.EmployeeDTO;
 
 
@Service("mailService")
public class MailServiceImpl {
 
    @Autowired
    JavaMailSender mailSender;
 
    public void sendAutoGeneratePassword(EmployeeDTO employeeDTO, String password) {
 
    	SimpleMailMessage  message = new SimpleMailMessage();
 
    	message.setTo(employeeDTO.getEmailId());
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
 
    /*private MimeMessagePreparator getMessagePreparator(final ProductOrder order) {
 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom("customerserivces@yourshop.com");
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(order.getCustomerInfo().getEmail()));
                mimeMessage.setText("Dear " + order.getCustomerInfo().getName()
                        + ", thank you for placing order. Your order id is " + order.getOrderId() + ".");
                mimeMessage.setSubject("Your order on Demoapp");
            }
        };
        return preparator;
    }*/
 
}
