package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Reclamation {
    private int id;
    private String type;
    private String description;
    private boolean etat;
    private Date date_creation;

    public Reclamation() {
    }

    public Reclamation(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.etat = false;

    }

    public Reclamation(String type, String description) {
        this.type = type;
        this.description = description;
        this.etat = false;
        this.date_creation = new Date();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEtat() {
        return etat;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id && etat == that.etat && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(date_creation, that.date_creation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, etat, date_creation);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", etat=" + etat +
                ", date_creation=" + date_creation +
                '}';
    }
}
