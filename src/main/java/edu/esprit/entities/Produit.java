package edu.esprit.entities;

import java.util.Objects;

public class Produit {
    private int id;
    private Categorie categorie;
    private int quantite;
    private double prix;
    private String nom,description,image;
    public Produit(){

    }
    public Produit(Categorie categorie, int quantite, double prix, String nom, String description, String image) {
        this.categorie = categorie;
        this.setQuantite(quantite);
        this.setPrix(prix);
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Produit(int id, Categorie categorie, int quantite, double prix, String nom, String description, String image) {
        this.id = id;
        this.categorie = categorie;
        this.setQuantite(quantite);
        this.setPrix(prix);
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrix() {
        return prix;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setQuantite(int quantite) {
        if (quantite<=0){
            throw new IllegalArgumentException("La quantité doit être supérieur à 0");
        }
        this.quantite = quantite;
    }

    public void setPrix(double prix) {
        if (quantite<=0){
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        }
        this.prix = prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id == produit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produit{" +
                "categorie=" + categorie +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
