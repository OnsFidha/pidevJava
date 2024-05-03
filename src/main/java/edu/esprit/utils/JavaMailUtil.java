package edu.esprit.utils;

import edu.esprit.entities.Evenement;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
    public static void sendEmail(String recepient,String s) throws Exception
    {
        System.out.println("Prepare");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        System.out.println("Prepare");
        String myAccountEmail="sana.khiari@esprit.tn";
        String password="211JFT4825@";
        Session session=Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail,password);

            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient,s);
        Transport.send(message);
        System.out.println("Email envoyee avec succes");




    }


    private static Message prepareMessage(Session session,String myAccountEmail,String recepient,String msg) {
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress (myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress (recepient));
            message.setSubject("Reclamation ♥");
            message.setText(msg);
            message.reply(false);
            return message;

        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public static void sendEventEmail(String recepient,String s,Evenement event) throws Exception
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
        Message message = prepareEventMessage(session,myAccountEmail,recepient, event);
        Transport.send(message);
        System.out.println("Email envoyé avec succés");




    }

    public static void sendEmail2(String recepient,String s, String o) throws Exception
    {
        System.out.println("Prepare");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        System.out.println("Prepare");
        String myAccountEmail="sana.khiari@esprit.tn";
        String password="211JFT4825@";
        Session session=Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail,password);

            }
        });
        Message message = prepareMessage2(session,myAccountEmail,recepient,s,o);
        Transport.send(message);
        System.out.println("Email envoyee avec succes");




    }


    private static Message prepareMessage2(Session session,String myAccountEmail,String recepient,String msg, String Objet) {
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress (myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress (recepient));
            message.setSubject(Objet);
            message.setText(msg);
            message.reply(false);
            return message;

        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return null;
    }
    private static Message prepareEventMessage(Session session, String myAccountEmail, String recepient, Evenement event) {
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress (myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress (recepient));
            message.setSubject("Invitation ♥");
            message.setText("Cher(e) Participant(e),\n\n"
                    + "Vous êtes cordialement invité(e) à l'événement : " + event.getNom() + "\n"
                    + "Description de l'événement : " + event.getDescription() + "\n"
                    + "Date : " + event.getDateDebut() + "\n"
                    + "Lieu : " + event.getLieu() + "\n\n"
                    + "Nous espérons vous y voir nombreux et partager ensemble des moments mémorables.\n\n"
                    + "Cordialement,\n"
                    + "Team ARTISTOOL");
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