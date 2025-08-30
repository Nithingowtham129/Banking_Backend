package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EmailSendingException;

import sibApi.TransactionalEmailsApi;
import sibModel.*;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name:World Bank}")
    private String senderName;

    // ✅ Common method to send emails via Brevo API
    private void sendBrevoEmail(String to, String toName, String subject, String textContent, String htmlContent) {
        try {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKeyAuth.setApiKey(apiKey);

            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail(senderEmail);
            sender.setName(senderName);

            SendSmtpEmailTo recipient = new SendSmtpEmailTo();
            recipient.setEmail(to);
            if (toName != null && !toName.isEmpty()) recipient.setName(toName);

            List<SendSmtpEmailTo> recipients = new ArrayList<>();
            recipients.add(recipient);

            SendSmtpEmail email = new SendSmtpEmail();
            email.setSender(sender);
            email.setTo(recipients);
            email.setSubject(subject);
            email.setTextContent(textContent);
            email.setHtmlContent(htmlContent);

            apiInstance.sendTransacEmail(email);

        } catch (Exception e) {
            throw new EmailSendingException("Failed to send email", e);
        }
    }

    // ------------------ Specific email methods ------------------

    public void sendAccountCreationEmail(String to, String name, String accountType) {
        String subject = "Your New " + accountType + " Account";
        String textBody = "Hi " + name + ",\n\nYour " + accountType + " account has been successfully created.\n\nThank you for choosing our bank.\n\nRegards,\nWorldBank Team";
        String htmlBody = "<p>Hi " + name + ",</p><p>Your <b>" + accountType + "</b> account has been successfully created.</p><p>Thank you for choosing our bank.</p><p>Regards,<br>WorldBank Team</p>";
        sendBrevoEmail(to, name, subject, textBody, htmlBody);
    }

    public void sendUserRegistrationEmail(String to, String name) {
        String subject = "Registration Successful";
        String textBody = "Hi " + name + ",\n\nYour Registration has been successfully completed.\n\nThank you for choosing our bank.\n\nRegards,\nWorldBank Team";
        String htmlBody = "<p>Hi " + name + ",</p><p>Your registration has been successfully completed.</p><p>Thank you for choosing our bank.</p><p>Regards,<br>WorldBank Team</p>";
        sendBrevoEmail(to, name, subject, textBody, htmlBody);
    }

    public void sendOtpEmail(String to, String otp) {
        String subject = "Your OTP Code";
        String textBody = "Dear User,\n\nYour One Time Password (OTP) is: " + otp + "\n\nBest Regards,\nWorld Bank Team.";
        String htmlBody = "<p>Dear User,</p><p>Your One Time Password (OTP) is: <b>" + otp + "</b></p><p>Best Regards,<br>World Bank Team</p>";
        sendBrevoEmail(to, null, subject, textBody, htmlBody);
    }

    public void forgotPasswordEmail(String to, String otp) {
        String subject = "Password Reset OTP";
        String textBody = "Dear User,\n\nYour OTP to reset password is: " + otp + "\n\nBest Regards,\nWorld Bank Team.";
        String htmlBody = "<p>Dear User,</p><p>Your OTP to reset password is: <b>" + otp + "</b></p><p>Best Regards,<br>World Bank Team</p>";
        sendBrevoEmail(to, null, subject, textBody, htmlBody);
    }

    public void sendSupportEmail(String name, String email, String description) {
        try {
            String adminSubject = "New Support Request from " + name;
            String adminText = "Name: " + name + "\nEmail: " + email + "\nDescription:\n" + description;
            String adminHtml = "<p>Name: " + name + "</p><p>Email: " + email + "</p><p>Description:<br>" + description + "</p>";
            sendBrevoEmail("nithingowtham2002@gmail.com", name, adminSubject, adminText, adminHtml);

            String userSubject = "Support Request Received";
            String userText = "Dear " + name + ",\n\nYour support request has been received. We will contact you soon.\n\nBest Regards,\nWorld Bank Support Team";
            String userHtml = "<p>Dear " + name + ",</p><p>Your support request has been received. We will contact you soon.</p><p>Best Regards,<br>World Bank Support Team</p>";
            sendBrevoEmail(email, name, userSubject, userText, userHtml);

        } catch (Exception e) {
            throw new EmailSendingException("Failed to send support email", e);
        }
    }

    public void transactionEmail(String to, Double amount, String type, String accountType) {
        String subject = "Transaction Notification";
        String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String transactionAction = type.equalsIgnoreCase("Debit") ? "debited from" : "credited to";

        String textBody = "Dear User,\n\nRs. " + String.format("%.2f", amount) + " has been " + transactionAction + " your " + accountType + " account on " + formattedTime + ".\n\nBest Regards,\nWorld Bank Team.";
        String htmlBody = "<p>Dear User,</p><p>Rs. <b>" + String.format("%.2f", amount) + "</b> has been " + transactionAction + " your " + accountType + " account on " + formattedTime + ".</p><p>Best Regards,<br>World Bank Team</p>";

        sendBrevoEmail(to, null, subject, textBody, htmlBody);
    }

    public void sendEmail(String to, String subject, String body) {
        String textBody = "Dear User,\n\n" + body + "\n\nBest Regards,\nWorld Bank Team.";
        String htmlBody = "<p>Dear User,</p><p>" + body + "</p><p>Best Regards,<br>World Bank Team</p>";
        sendBrevoEmail(to, null, subject, textBody, htmlBody);
    }
}