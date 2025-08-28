package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EmailSendingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAccountCreationEmail(String to, String name, String accountType) {
        String subject = "Your New " + accountType + " Account";
        String body = "Hi " + name + ",\n\n" +
                      "Your " + accountType + " account has been successfully created.\n\n" +
                      "Thank you for choosing our bank.\n\n" +
                      "Regards,\nWorldBank Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wbank3252@gmail.com"); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    
    public void sendUserRegistrationEmail(String to, String name) {
        String subject = "Registration Successfull";
        String body = "Hi " + name + ",\n\n" +
                      "Your Registration has been successfully completed.\n\n" +
                      "Thank you for choosing our bank.\n\n" +
                      "Regards,\nWorldBank Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wbank3252@gmail.com"); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Dear User,\n"
        		+ "\nYour One Time Password (OTP) for Registering is: " + otp
        		+ "\n\nBest Regards,"
        		+ "\nWorld Bank Team.");
        mailSender.send(message);
    }
    
    public void forgotPasswordEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Dear User,\n"
        		+ "\nYour One Time Password (OTP) to Reset Your Password is: " + otp
        		+ "\n\nBest Regards,"
        		+ "\nWorld Bank Team.");
        mailSender.send(message);
    }
    
    public void sendSupportEmail(String name, String email, String description) {
    	
	    try {	
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo("nithingowtham2002@gmail.com"); 
	        message.setSubject("New Support Request from " + name);
	        message.setText("Name: " + name + "\nEmail: " + email + "\n\nDescription:\n\n" + description
	        		+ "\n\nBest Regards,"
	        		+ "\nWorld Bank Team.");
	
	        mailSender.send(message);
	        
	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(email);
	        msg.setSubject("Your Support Request " + name);
	        msg.setText("Name: " + name + "\nEmail: " + email + "\n\nDescription:\n\n" + description
	        		+ "\n\nThank you for contacting us, We will resolve your issues as soon as possible."
	        		+ "\n\nBest Regards,"
	        		+ "\nWorld Bank Support Team.");
	
	        mailSender.send(msg);
	    }
	    catch (Exception e){
	    	throw new EmailSendingException("Failed to send support email", e);
	    }
    }
    
    public void transactionEmail(String toEmail, Double amount, String type, String accountType) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Transaction Notification");

        String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        String transactionAction = type.equalsIgnoreCase("Debit") ? "debited from" :
                                    type.equalsIgnoreCase("Credit") ? "credited to" : "processed in";

        message.setText("Dear User,\n\n"
            + "An amount of Rs. " + String.format("%.2f", amount)
            + " has been " + transactionAction + " your " + accountType.toLowerCase() + " account"
            + " on " + formattedTime + ".\n\n"
            + "Best Regards,\n"
            + "World Bank Team.");

        mailSender.send(message);
    }
    
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wbank3252@gmail.com"); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText("Dear User,\n\n"
        		+ body
                + "\n\nBest Regards,\n"
                + "World Bank Team.");
        mailSender.send(message);
    }
    
}
