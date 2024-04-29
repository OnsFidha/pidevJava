package edu.esprit.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;



public class SendMail {
    private final static String senderEmail = "******************************";
    private final static String senderPassword = "******************************";
    public static void send(String recipientEmail) {
        // SMTP server configuration
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        // Create properties for the SMTP connection
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Create a Session object with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Confirmation commande");
            message.setText("Votre commande est bien prise en compte");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}