package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Publication {
    private int id;
    private int id_user_id;
    private String type;
    private String text;
    private String lieu;
    private Date dateCreation;
    private Date dateModification;
    private Integer avis;
    private String photo;

    public Publication() {
    }
    public Publication(String type, String text, String lieu, Integer avis, String photo) {
        this.type = type;
        this.text = text;
        this.lieu = lieu;
        this.avis = avis;
        this.photo = photo;
    }


    public Publication(int id,String type, String text, String lieu, String photo) {
        this.id=id;
        this.type = type;
        this.text = text;
        this.lieu = lieu;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public int getId_user_id() {
        return id_user_id;
    }

    public void setId_user_id(int id_user_id) {
        this.id_user_id = id_user_id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getLieu() {
        return lieu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public Integer getAvis() {
        return avis;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public void setAvis(Integer avis) {
        this.avis = avis;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return id == that.id && Objects.equals(type, that.type) && Objects.equals(text, that.text) && Objects.equals(lieu, that.lieu) && Objects.equals(dateCreation, that.dateCreation) && Objects.equals(dateModification, that.dateModification) && Objects.equals(avis, that.avis) && Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, text, lieu, dateCreation, dateModification, avis, photo);
    }

    @Override
    public String toString() {
        return "Publication{" +

                ", dateCreation=" + dateCreation +
                ", datemodificationn=" + dateModification +
                ", idUser=" + id_user_id +

                '}'+'\n';
    }
}
