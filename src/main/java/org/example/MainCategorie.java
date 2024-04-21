package org.example;

import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class  MainCategorie {
    public static void main(String[] args) throws SQLException {
        //System.out.println(DataSource.getInstance());
        ////////////////Ajouter categorie//////////////////////
        IService<Categorie> sc= Servicecategorie.getInstance();

         //sc.ajouter(new Categorie("Musique1","outil de loisir1"));
         //////////////Modifier categorie/////////////////////////
        //sc.modifier(new Categorie(2,"Peinture","outil"));
        ///////////////Supprimer categorie//////////////////////////////////
        //sc.supprimer(2);
        ///////////////getall////////////////////////////////////
       // System.out.println(sc.getAll());
        ///////////////getOneById////////////////////////////////////
        try {
            System.out.println(sc.getOneById(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}