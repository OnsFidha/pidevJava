package edu.esprit.entities;

import java.util.Objects;

public class Produit {
    private int id;
    private int categorie_id;
    private int quantite;
    private double prix;
    private String nom,description,image;
    public Produit(){

    }
    public Produit(int categorie_id, int quantite, double prix, String nom, String description, String image) {
        this.categorie_id = categorie_id;
        this.quantite = quantite;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Produit(int id, int categorie_id, int quantite, double prix, String nom, String description, String image) {
        this.id = id;
        this.categorie_id = categorie_id;
        this.quantite = quantite;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getCategorie_id() {
        return categorie_id;
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

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(double prix) {
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
                "categorie_id=" + categorie_id +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
