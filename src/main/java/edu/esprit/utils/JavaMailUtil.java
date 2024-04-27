package edu.esprit.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
    public static void sendEventEmail(String recepient,String s) throws Exception
    {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        System.out.println(recepient);
        String myAccountEmail="syrine.zaier@esprit.tn";
        String password="SyrZai45085";
        Session session=Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail,password);

            }
        });
        Message message = prepareEventMessage(session,myAccountEmail,recepient,s);
        Transport.send(message);
        System.out.println("Email envoyé avec succés");




    }


    private static Message prepareEventMessage(Session session,String myAccountEmail,String recepient,String msg) {
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress (myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress (recepient));
            message.setSubject("Invitation ♥");
            message.setText("Test");
            message.reply(false);
            return message;

        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return null;
    }

}
