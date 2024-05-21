package edu.esprit.controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailManager {
    public static void sendEmail(String recipientEmail, String code, String emailMessage) throws MessagingException {
        final String username = "onsfidha3@gmail.com";
        final String password = "yujuxirrihgqluzz";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // Utilisation de TLS

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message emailMessageObj = new MimeMessage(session);
            emailMessageObj.setFrom(new InternetAddress(username));
            emailMessageObj.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            emailMessageObj.setSubject("Confirmation de prise en compte de votre collaboration"); // Remplacez par le sujet souhaité
            emailMessageObj.setText("Je tiens à vous informer que nous avons bien pris en compte votre collaboration et que nous sommes ravis de travailler avec vous. Votre contribution est précieuse pour nous et nous sommes impatients de commencer.\n" +
                    "\n" +
                    "N'hésitez pas à me contacter dès que possible pour discuter des prochaines étapes et des détails de notre collaboration. Je suis disponible pour répondre à toutes vos questions et pour discuter de tout ce dont vous pourriez avoir besoin.\n" +
                    "\n" +
                    "Je vous remercie pour votre engagement et votre collaboration. Ensemble, nous pouvons atteindre de grands succès."); // Remplacez par le contenu du message

            Transport.send(emailMessageObj);
            System.out.println("Email envoyé avec succès");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
