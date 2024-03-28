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

    public Reponse(int relation_id, String reponse, Date date_reponse) {
        this.relation_id = relation_id;
        this.reponse = reponse;
        this.date_reponse = date_reponse;
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
}
