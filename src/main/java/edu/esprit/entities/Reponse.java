package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;
public class Reponse {
    private int id;
    private int relation_id;
    private String reponse;
    private Date date_reponse;

    public Reponse() {
    }

<<<<<<< HEAD
    public Reponse(int relation_id, String reponse, Date date_reponse) {
        this.relation_id = relation_id;
        this.reponse = reponse;
        this.date_reponse = date_reponse;
=======
    public Reponse(int id, int relation_id, String reponse) {
        this.id = id;
        this.relation_id = relation_id;
        this.reponse = reponse;

    }

    public Reponse(int relation_id, String reponse) {
        this.relation_id = relation_id;
        this.reponse = reponse;
        this.date_reponse =  new Date();
>>>>>>> ons
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(int relation_id) {
        this.relation_id = relation_id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }
<<<<<<< HEAD
=======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse1 = (Reponse) o;
        return id == reponse1.id && relation_id == reponse1.relation_id && Objects.equals(reponse, reponse1.reponse) && Objects.equals(date_reponse, reponse1.date_reponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relation_id, reponse, date_reponse);
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", relation_id=" + relation_id +
                ", reponse='" + reponse + '\'' +
                ", date_reponse=" + date_reponse +
                '}';
    }
>>>>>>> ons
}
