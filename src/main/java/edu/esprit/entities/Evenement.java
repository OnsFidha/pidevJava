package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Evenement {
    private int id;
    private String nom;
    private String description;
    private String lieu;
    private Date dateDebut;
    private Date dateFin;
    private Integer nbreParticipants;
    private Integer nbreMax;
    private String image;
    private int id_user_id;

    public int getId_user_id() {
        return id_user_id;
    }

    public void setId_user_id(int id_user_id) {
        this.id_user_id = id_user_id;
    }

    public Evenement() {
    }

    public Evenement(int id, String nom, String description, String lieu, Date dateDebut, Date dateFin, Integer nbreParticipants, Integer nbreMax, String image,int id_user_id) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreParticipants = nbreParticipants;
        this.nbreMax = nbreMax;
        this.image = image;
        this.id_user_id=id_user_id;
    }

    public Evenement(String nom, String description, String lieu, Date dateDebut, Date dateFin, Integer nbreParticipants, Integer nbreMax, String image, int id_user_id) {
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreParticipants = nbreParticipants;
        this.nbreMax = nbreMax;
        this.image = image;
        this.id_user_id=id_user_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNbreParticipants() {
        return nbreParticipants;
    }

    public void setNbreParticipants(Integer nbreParticipants) {
        this.nbreParticipants = nbreParticipants;
    }

    public Integer getNbreMax() {
        return nbreMax;
    }

    public void setNbreMax(Integer nbreMax) {
        this.nbreMax = nbreMax;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return id == evenement.id && Objects.equals(nom, evenement.nom) && Objects.equals(description, evenement.description) && Objects.equals(lieu, evenement.lieu) && Objects.equals(dateDebut, evenement.dateDebut) && Objects.equals(dateFin, evenement.dateFin) && Objects.equals(nbreParticipants, evenement.nbreParticipants) && Objects.equals(nbreMax, evenement.nbreMax) && Objects.equals(image, evenement.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, lieu, dateDebut, dateFin, nbreParticipants, nbreMax, image);
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbreParticipants=" + nbreParticipants +
                ", nbreMax=" + nbreMax +
                ", image='" + image + '\'' +
                '}';
    }


}
