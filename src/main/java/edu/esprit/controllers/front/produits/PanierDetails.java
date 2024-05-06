package edu.esprit.controllers.front.produits;

import edu.esprit.controllers.FrontContentPanel;
import edu.esprit.entities.Commande;
import edu.esprit.entities.DetailCommande;
import edu.esprit.model.UserCommande;
import edu.esprit.service.IService;
import edu.esprit.service.SendMail;
import edu.esprit.service.ServiceCommande;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static edu.esprit.utils.CommonUtils.createGridHeaderLabel;

public class PanierDetails extends FrontContentPanel {

    @FXML
    private Label montantTotalValue;

    @FXML
    private GridPane commandeDetailsGrid;

    IService<Commande> commandeIService = ServiceCommande.getInstance();

    public void afficherPanierDetails(){
        if(Objects.nonNull(UserCommande.getCommande())){
            montantTotalValue.setText(Objects.toString(UserCommande.getCommande().getMontant_total()));
            List<DetailCommande> detailCommandes = UserCommande.getCommande().getDetailsCommande();
            if(!commandeDetailsGrid.getChildren().isEmpty()){
                commandeDetailsGrid.getChildren().clear();
            }
            int index=0;
            commandeDetailsGrid.addRow(index, createGridHeaderLabel("Produit"),
                    createGridHeaderLabel("Prix"), createGridHeaderLabel("Quantité"),
                    createGridHeaderLabel("Total"));
            index++;
            for (DetailCommande detailCommande: detailCommandes){
                commandeDetailsGrid.addRow(index, new Label(detailCommande.getProduit().getNom()),
                        new Label(Objects.toString(detailCommande.getProduit().getPrix())),
                        getQuantiteCell(detailCommande),
                        new Label(Objects.toString(BigDecimal.valueOf(detailCommande.getPrix()).multiply(BigDecimal.valueOf(detailCommande.getQuantite())))));
                index++;
            }
        }
    }

    private HBox getQuantiteCell(DetailCommande detailCommande){
        Label quantiteValue = new Label(Objects.toString(detailCommande.getQuantite()));
        quantiteValue.setPadding(new Insets(3, 0, 0, 0));
        quantiteValue.setPrefWidth(30);
        quantiteValue.setAlignment(Pos.CENTER);
        HBox manageQuantiteVbox = new HBox(0);
        manageQuantiteVbox.getChildren().setAll(addProductElement(detailCommande), minusProductElement(detailCommande));
        HBox hBox = new HBox(10);
        hBox.getChildren().setAll(quantiteValue, manageQuantiteVbox);
        return hBox;
    }

    private static Button manageQuantiteButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(30);
        button.setPrefHeight(20);
        return button;
    }

    private Button addProductElement(DetailCommande detailCommande) {
        Button btn = manageQuantiteButton("+");
        EventHandler<ActionEvent> btnHandler = event -> {
            UserCommande.addProductToCommande(detailCommande.getProduit());
            afficherPanierDetails();
        };
        btn.setOnAction(btnHandler);
        return btn;
    }

    private Button minusProductElement(DetailCommande detailCommande) {
        Button btn = manageQuantiteButton(" - ");
        EventHandler<ActionEvent> btnHandler = event -> {
            UserCommande.minusProductToCommande(detailCommande.getProduit());
            afficherPanierDetails();
        };
        btn.setOnAction(btnHandler);
        return btn;
    }

    @FXML
    void retournerVersLaListeProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/ListeProduits.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            ListeProduits controller = loader.getController();
            controller.setBaseFrontContentPanel(super.getBaseFrontContentPanel());
            controller.showProducts();
            super.getBaseFrontContentPanel().getChildren().clear();
            super.getBaseFrontContentPanel().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void passerCommande(ActionEvent event) {
        if(Objects.isNull(UserCommande.getCommande())
                || Objects.isNull(UserCommande.getCommande().getDetailsCommande())
                || UserCommande.getCommande().getDetailsCommande().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Votre panier est vide, stp ajouter des produits au panier");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setContentText("Voulez-vous passer la commande");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    commandeIService.ajouter(UserCommande.getCommande());
                    String userEmail = UserCommande.getCommande().getUser().getEmail();
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setContentText("La commande est passé avec success ");
                    information.showAndWait().ifPresent(responseInfo -> {
                        UserCommande.setCommande(null);
                        retournerVersLaListeProduit(event);
                    });
                    SendMail.send(userEmail);
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Il y a un problème lors de la suppression du produit");
                    alert.showAndWait();
                }
            }
        });
    }
}
