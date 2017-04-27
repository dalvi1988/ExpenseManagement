package com.chaitanya.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chaitanya.employee.model.EmployeeDTO;
 
 
@Service("mailService")
public class MailServiceImpl {
 
    @Autowired
    JavaMailSender mailSender;
 
    public void sendAutoGeneratePassword(EmployeeDTO employeeDTO) {
 
 
    	SimpleMailMessage  message = new SimpleMailMessage();
 
        try {
        	message.setCc("dalvi21288@gmail.com");
        	message.setSubject("Autogenerated password"); 
        	message.setText(Utility.SessionIdentifierGenerator.nextSessionId()); 
            mailSender.send(message);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
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