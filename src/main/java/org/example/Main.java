package org.example;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.service.ReclamationService;
import edu.esprit.service.ReponseService;
import edu.esprit.utils.DataSource;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(DataSource.getInstance());

        ReclamationService reclam = new ReclamationService();
        ReponseService rep = new ReponseService();

        try {
            reclam.ajouter(new Reclamation("type21234","decription212345 "));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //try {
           // rep.ajouter(new Reponse(1,"ajout reponse test 22 "));
        //} catch (SQLException e) {
         //   System.out.println(e.getMessage());
        //}



       // try {rep.supprimer(3);
        //}catch (SQLException s){
          //  System.out.println(s.getMessage());
        //}

        //try {
          //  reclam.modifier(new Reclamation(2,"type modif","description modif"));
        //} catch (SQLException e) {
          //  System.out.println(e.getMessage());
        //}

        //try {
          //rep.modifier(new Reponse(3,1,"reponse modif"));
        //} catch (SQLException e) {
          //System.out.println(e.getMessage());
        //}

        try {
            System.out.println("liste des reponses \n"+rep.getAll());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("one by Id \n"+rep.getOneById(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}