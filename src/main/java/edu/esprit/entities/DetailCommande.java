package edu.esprit.entities;

public class DetailCommande {
    private int id;
    private int commande_id ;

    private Produit produit ;
    private double prix;
    private int quantite;

    public DetailCommande(int commande_id, Produit produit, double prix, int quantite) {
        this.commande_id = commande_id;
        this.produit = produit;
        this.prix = prix;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit_id(Produit produit) {
        this.produit = produit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "DetailCommande{" +
                "id=" + id +
                ", commande_id=" + commande_id +
                ", produit_id=" + produit +
                ", prix=" + prix +
                ", quantite=" + quantite +
                '}';
    }
}
