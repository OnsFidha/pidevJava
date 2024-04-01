package edu.esprit.service;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class Serviceproduit implements IService <Produit>{
    Connection conn= DataSource.getInstance().getConn();
    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();
    @Override
    public void ajouter(Produit produit) {
        String req= "INSERT INTO `produit`(`categorie_id`, `nom`, `prix`, `quantite`, `description`, `image`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1,produit.getCategorie().getId());
            pst.setString(2,produit.getNom());
            pst.setDouble(3,produit.getPrix());
            pst.setInt(4,produit.getQuantite());
            pst.setString(5,produit.getDescription());
            pst.setString(6,produit.getImage());
            pst.executeUpdate();
            System.out.println("produit ajoutée");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Produit produit){
        String req= "UPDATE `produit` SET`categorie_id`=?,`nom`=?,`prix`=?,`quantite`=?,`description`=?,`image`=? WHERE `id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1,produit.getCategorie().getId());
            pst.setString(2,produit.getNom());
            pst.setDouble(3,produit.getPrix());
            pst.setInt(4,produit.getQuantite());
            pst.setString(5,produit.getDescription());
            pst.setString(6,produit.getImage());
            pst.setInt(7,produit.getId());
            pst.executeUpdate();
            System.out.println("produit modifiée");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {

        String req= "DELETE FROM `produit` WHERE `id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("produit supprimée ");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Set<Produit> getAll() {
        Set<Produit> produits=new HashSet<>();
        String req ="select * from produit";
        try {
            Statement st= conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                int id = rs.getInt("id");
                Produit produit = getProduit(id, rs);
                produits.add(produit);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produits;
    }

    @Override
    public Produit getOneById(int id){
        Produit produit = null;
        String req ="select * from produit WHERE id=?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                produit = getProduit(id, rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produit;
    }

    private Produit getProduit(int id, ResultSet rs) throws SQLException {
        int categorie_id = rs.getInt("categorie_id");
        Categorie categorie = serviceCategorie.getOneById(categorie_id);
        String nom = rs.getString("nom");
        double prix = rs.getDouble("prix");
        int quantite = rs.getInt("quantite");
        String description = rs.getString("description");
        String image = rs.getString("image");
        return new Produit(id,categorie,quantite, prix,nom,description,image);
    }
}
