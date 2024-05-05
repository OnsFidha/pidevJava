package edu.esprit.entities;

import java.util.Date;
import java.util.List;

public class Commande {
    private int id;
    private Utilisateur user ;

    private Date date_commande;
    private String reference;
    private double montant_total;

    private List<DetailCommande> detailsCommande;

    public Commande(Utilisateur user, Date date_commande, String reference, double montant_total, List<DetailCommande> detailsCommande) {
        this.user = user;
        this.date_commande = date_commande;
        this.reference = reference;
        this.montant_total = montant_total;
        this.detailsCommande = detailsCommande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(double montant_total) {
        this.montant_total = montant_total;
    }

    public List<DetailCommande> getDetailsCommande() {
        return detailsCommande;
    }

    public void setDetailsCommande(List<DetailCommande> detailsCommande) {
        this.detailsCommande = detailsCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", user=" + user +
                ", date_commande=" + date_commande +
                ", reference='" + reference + '\'' +
                ", montant_total=" + montant_total +
                ", detailsCommande=" + detailsCommande +
                '}';
    }
}
