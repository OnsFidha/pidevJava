package org.example;

import edu.esprit.service.Servicecategorie;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainCategorie {
    public static void main(String[] args) {
        //System.out.println(DataSource.getInstance());
        ////////////////Ajouter categorie//////////////////////
         Servicecategorie sc=new Servicecategorie();
         //sc.ajouter(new Categorie("Musique1","outil de loisir1"));
         //////////////Modifier categorie/////////////////////////
        //sc.modifier(new Categorie(2,"Peinture","outil"));
        ///////////////Supprimer categorie//////////////////////////////////
        //sc.supprimer(2);
        ///////////////getall////////////////////////////////////
       // System.out.println(sc.getAll());
        ///////////////getOneById////////////////////////////////////
        System.out.println(sc.getOneById(4));
    }
}