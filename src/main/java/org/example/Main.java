package org.example;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import edu.esprit.service.EvenementService;
import edu.esprit.service.FeedbackService;
import edu.esprit.utils.DataSource;

import java.sql.Date;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(DataSource.getInstance());
        EvenementService es=new EvenementService();
        FeedbackService fs=new FeedbackService();

//        try {
//            es.ajouter(new Evenement("Nomevent","desc","Tunis", Date.valueOf("2024-04-15"),Date.valueOf("2024-04-15"),1,4,"heheh"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            es.ajouter(new Evenement("Nomevent","desc","Tunis", Date.valueOf("2024-04-15"),Date.valueOf("2024-04-15"),1,4,"heheh"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            es.modifier(new Evenement( 1,"Modif","","",Date.valueOf("2024-04-15"),Date.valueOf("2024-04-15"),1,0,""));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }


//        try {
//            System.out.println("alll \n"+es.getAll());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println("one by Id \n"+es.getOneById(1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {es.supprimer(1);
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }
//        try {
//            fs.ajouter(new Feedback(2,"Tunis"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            fs.ajouter(new Feedback(2,"Textttt"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            fs.modifier(new Feedback( 2,2,"Modification"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println("alll \n"+fs.getAll());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println("one by Id \n"+fs.getOneById(1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {fs.supprimer(2);
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }
//        try {System.out.println(fs.getAllById(2));
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }

    }
}