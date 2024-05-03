package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Commentaire {
    private int id;
    private int id_publication_id;
    private String text;
    private Date dateCreation;
    public Commentaire() {
    }

    public Commentaire( String text,int id_publication_id) {
        this.id_publication_id = id_publication_id;
        this.text = text;
    }

    public Commentaire(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_publication() {
        return id_publication_id;
    }

    public void setId_publication(int id_publication_id) {
        this.id_publication_id = id_publication_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", id_publication_id=" + id_publication_id +
                ", text='" + text + '\'' +
                ", dateCreation=" + dateCreation +
                '}'+'\n';
    }

}
