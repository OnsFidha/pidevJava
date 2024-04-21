package org.example;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Serviceproduit;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainProduit {
    public static void main(String[] args) {
        //System.out.println(DataSource.getInstance());
        ////////////////Ajouter produit//////////////////////
        IService<Produit> sp= Serviceproduit.getInstance();
        //try {
          //  sp.ajouter(new Produit(new Categorie(3, "musique", ""), -1, 10.50, "guitare", "outil de loisir1", "iamge path"));
       // }catch (IllegalArgumentException | SQLException ex){
          // System.out.println("vérifier les détails de produit");
       // }
        //////////////Modifier produit/////////////////////////
        //sp.modifier(new Produit(4,new Categorie(3, "musique", ""), 5, 10.50, "guitare", "outil de loisir1", "iamge path"));
        ///////////////Supprimer produit//////////////////////////////////
       // sp.supprimer(3);
        ///////////////getall////////////////////////////////////
        ///System.out.println(sp.getAll());
        ///////////////getOneById////////////////////////////////////
       //System.out.println(sp.getOneById(4));
    }
}