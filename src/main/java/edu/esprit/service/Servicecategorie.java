package edu.esprit.service;

import edu.esprit.entities.Categorie;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class Servicecategorie implements IService <Categorie>{
    Connection conn= DataSource.getInstance().getConn();
    @Override
    public void ajouter(Categorie categorie) {
        String req= "INSERT INTO `categorie`(`nom`,`description`) VALUES (?,?)";
    try {
           PreparedStatement pst = conn.prepareStatement(req);
           pst.setString(1,categorie.getNom());
           pst.setString(2,categorie.getDescription());
           pst.executeUpdate();
           System.out.println("categorie ajoutée");
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }

    }

    @Override
    public void modifier(Categorie categorie){
        String req= "UPDATE `categorie` SET `nom`=?,`description`=? WHERE `id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1,categorie.getNom());
            pst.setString(2,categorie.getDescription());
            pst.setInt(3,categorie.getId());
            pst.executeUpdate();
            System.out.println("categorie modifiée");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void supprimer(int id) {

        String req= "DELETE FROM `categorie` WHERE `id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("categorie supprimée ");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Set<Categorie> getAll() {
        Set<Categorie> categories=new HashSet<>();
        String req ="select * from categorie";
        try {
            Statement st= conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                Categorie categorie = new Categorie(id,nom,description);
                categories.add(categorie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Categorie getOneById(int id){
        Categorie categorie = null;
        String req ="select * from categorie WHERE id=?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                categorie = new Categorie(id,nom,description);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorie;
    }
}
