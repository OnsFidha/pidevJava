package edu.esprit.entities;

import java.util.Objects;

public class Categorie {
    private int id;
    private String nom;
    private String description;

    public Categorie() {
    }

    public Categorie(String nom,String description) {
        this.setNom(nom);
        this.setDescription(description);
    }

    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.setNom(nom);
        this.setDescription(description);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        if (nom==null || nom.trim().equalsIgnoreCase("")){
            throw new IllegalArgumentException("Le champ Nom est obligatoire");
        }
        this.nom = nom;
    }

    public void setDescription(String description) {
        if (description==null || description.trim().equalsIgnoreCase("")){
            throw new IllegalArgumentException("Le champ Description est obligatoire");
        }
        this.description = description;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
