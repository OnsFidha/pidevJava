package edu.esprit.entities;

public class Collaboration {
    private int id;
    private String disponibilite;
    private String competence;
    private String cv;
    private int id_publication;
    private int id_user;

    public Collaboration() {

    }

    public Collaboration(String disponibilite, String competence, String cv, int id_publication,int id_user) {
        this.disponibilite = disponibilite;
        this.competence = competence;
        this.cv = cv;
        this.id_publication = id_publication;
        this.id_user=id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public int getId_publication() {
        return id_publication;
    }



    public int getId_user() {
        return id_user;
    }


    @Override
    public String toString() {
        return "Collaboration{" +
                "id=" + id +
                ", disponibilite='" + disponibilite + '\'' +
                ", competence='" + competence + '\'' +
                ", cv='" + cv + '\'' +
                '}';
    }
}
