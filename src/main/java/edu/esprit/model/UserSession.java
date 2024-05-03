package edu.esprit.model;

import edu.esprit.entities.Commande;
import edu.esprit.entities.DetailCommande;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;

import java.math.BigDecimal;
import java.util.*;

public class UserSession {
    private static User loggedUser;
    private static Commande commande;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        UserSession.loggedUser = loggedUser;
    }

    public static Commande getCommande() {
        return commande;
    }

    public static void setCommande(Commande commande) {
        UserSession.commande = commande;
    }

    public static void addProductToCommande(Produit produit){
        if (commande==null){
            List<DetailCommande> detailsCommande = new ArrayList<>();
            detailsCommande.add(new DetailCommande(produit, produit.getPrix(), 1));
            commande = new Commande(loggedUser, Calendar.getInstance().getTime(), "", produit.getPrix(),
                    detailsCommande);
        }else{
            Optional<DetailCommande> optDetailCommande = commande.getDetailsCommande().stream()
                    .filter(detailCommande1 -> detailCommande1.getProduit().getId()==produit.getId()).findAny();
            if (optDetailCommande.isPresent()){
                DetailCommande detailCommande = optDetailCommande.get();
                detailCommande.setQuantite(detailCommande.getQuantite()+1);
            }else {
                commande.getDetailsCommande().add(new DetailCommande(produit, produit.getPrix(), 1));
            }
            commande.setMontant_total(BigDecimal.valueOf(commande.getMontant_total()).add(BigDecimal.valueOf(produit.getPrix())).doubleValue());
        }
        System.out.println("Current Commande ----------------: "+commande);
    }

    public static void minusProductToCommande(Produit produit){
        if (Objects.nonNull(commande)){
            Optional<DetailCommande> optDetailCommande = commande.getDetailsCommande().stream()
                    .filter(detailCommande1 -> detailCommande1.getProduit().getId()==produit.getId()).findAny();
            if (optDetailCommande.isPresent()){
                DetailCommande detailCommande = optDetailCommande.get();
                if (detailCommande.getQuantite()>1) {
                    detailCommande.setQuantite(detailCommande.getQuantite() - 1);
                }else {
                    commande.getDetailsCommande().remove(detailCommande);
                }
                commande.setMontant_total(BigDecimal.valueOf(commande.getMontant_total()).subtract(BigDecimal.valueOf(produit.getPrix())).doubleValue());
            }
        }
        System.out.println("Current Commande ----------------: "+commande);
    }
}
