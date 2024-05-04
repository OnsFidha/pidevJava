package edu.esprit.controllers.admin.commandes;

import edu.esprit.entities.Commande;
import edu.esprit.entities.DetailCommande;
import edu.esprit.service.IService;
import edu.esprit.service.ServiceCommande;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static edu.esprit.utils.CommonUtils.createGridHeaderLabel;

public class AfficherCommandes {

    @FXML
    private GridPane gridCommandes;
    IService<Commande> commandeService = ServiceCommande.getInstance();
    public void showData() {
        try {
            List<Commande> commandes =  commandeService.getAll();
            int index=0;
            gridCommandes.addRow(index, createGridHeaderLabel("Date"), createGridHeaderLabel("Utilisateur"),
                    createGridHeaderLabel("Produit(s)"), createGridHeaderLabel("Montant Total"));
            index++;
            for (Commande commande: commandes){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                gridCommandes.addRow(index, new Label(sdf.format(commande.getDate_commande())), new Label(commande.getUser().getNom()+" "+commande.getUser().getPrenom()),
                        getCommandeDetails(commande.getDetailsCommande()),
                        new Label(Objects.toString(commande.getMontant_total())));
                index++;
            }
        } catch (SQLException e) {
            displayAlertErreure("Error", "Il y a un problème lors de l'affichage des commandes");
        }
    }

    public VBox getCommandeDetails(List<DetailCommande> detailCommandeList){
        List<HBox> commandeDetails = new ArrayList<>();
        for (DetailCommande detailCommande: detailCommandeList){
            HBox hbox = new HBox(5);
            hbox.getChildren().setAll(new Label("Nom: "+detailCommande.getProduit().getNom()),
                    new Label("Quantité: "+detailCommande.getQuantite()),
                    new Label("Prix Unitaire: "+detailCommande.getPrix()));
            commandeDetails.add(hbox);

        }
        VBox vBox = new VBox(2);
        vBox.getChildren().setAll(commandeDetails);
        return vBox;
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
