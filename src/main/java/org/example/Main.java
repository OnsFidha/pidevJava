package org.example;


import edu.esprit.entities.Collaboration;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.service.CollaborationService;
import edu.esprit.service.CommentaireService;
import edu.esprit.service.PublicationService;
import edu.esprit.utils.DataSource;
import java.sql.SQLException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(DataSource.getInstance());
        PublicationService ps=new PublicationService();
        CommentaireService cs=new CommentaireService();
        CollaborationService cols=new CollaborationService();


                try {
                    System.out.println(cols.getListByIdPublication(4));
        } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
//
//        try {
//            ps.ajouter(new Publication("offre2","test2 test2","Tunis",3,"heheh2"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
        /**
         * TEST CRUD PUBLICATION
        **/
//        try {
//            ps.ajouter(new Publication("offre","test test","Tunis",2,"heheh"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            ps.ajouter(new Publication("offre2","test2 test2","Tunis",3,"heheh2"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            ps.modifier(new Publication(1,"offreModif2","modif","modif",""));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }


//        try {
//            System.out.println("alll \n"+ps.getAll());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println("one by Id \n"+ps.getOneById(1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }  try {
//            System.out.println("liste comm by pub\n"+ps.getCommentairesByPublicationId(1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {ps.supprimer(11);
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }
        /**
         * TEST CRUD COMMENTAIRE
        **/
//        try {
//            cs.ajouter(new Commentaire("test",1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            cs.ajouter(new Commentaire("test3",1));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            cs.modifier(new Commentaire(7,"CommentModif2"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }


//        try {
//            System.out.println("alll \n"+cs.getAll());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {cs.supprimer(7);
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }
//        try {
//            System.out.println("one by Id \n"+cs.getOneById(7));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }
}