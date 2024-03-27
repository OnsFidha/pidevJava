package org.example;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import edu.esprit.utils.DataSource;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(DataSource.getInstance());
        PublicationService ps=new PublicationService();

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
//            ps.modifier(new Publication(,"offreModif2","","",0,""));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }


//        try {
//            System.out.println("alll \n"+ps.getAll());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
        try {
            System.out.println("one by Id \n"+ps.getOneById(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        try {ps.supprimer(11);
//        }catch (SQLException s){
//            System.out.println(s.getMessage());
//        }
    }
}