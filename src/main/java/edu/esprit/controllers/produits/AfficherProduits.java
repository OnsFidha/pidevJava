package edu.esprit.controllers.produits;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Serviceproduit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

import static edu.esprit.utils.CommonUtils.createGridHeaderLabel;

public class AfficherProduits extends AdminContentPanel{
    @FXML
    private GridPane gridProduits;

    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    public void showData() {
        try {
            Set<Produit> produits = serviceProduit.getAll();
            int index=0;
            gridProduits.addRow(index, createGridHeaderLabel("Nom"),
                    createGridHeaderLabel("Catégorie"), createGridHeaderLabel("Prix"),
                    createGridHeaderLabel("Quantité"), createGridHeaderLabel("Description"));
            index++;
            for (Produit produit: produits){
                Button btnModifier = getUpdateButton(produit);
                Button btnSupprimer = getDeleteButton(produit);
                HBox hbox = new HBox(10); // spacing between nodes
                hbox.getChildren().addAll(btnModifier, btnSupprimer);
                gridProduits.addRow(index, new Label(produit.getNom()), new Label(produit.getCategorie().getNom()),
                        new Label(Objects.toString(produit.getPrix())), new Label(Objects.toString(produit.getQuantite())),
                        new Label(produit.getDescription()), hbox);
                index++;
            }
        } catch (SQLException e) {
            displayAlertErreure("Error", "Il y a un problème lors de l'affichage des produits");
        }
    }

    private Button getDeleteButton(Produit produit) {
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.getStyleClass().add("btn-delete");
        EventHandler<ActionEvent> btnSupprimerHandler = event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Voulez-vous supprimer le produit: "+ produit.getNom());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceProduit.supprimer(produit.getId());
                        gridProduits.getChildren().clear();
                        showData();
                    } catch (SQLException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de la suppression du produit");
                    }
                }
            });
        };
        btnSupprimer.setOnAction(btnSupprimerHandler);
        return btnSupprimer;
    }

    private Button getUpdateButton(Produit produit) {
        Button btn = new Button("Modifier");
        btn.getStyleClass().add("btn-update");
        AnchorPane tmpAdminContentPanel = super.getAdminPanelContent();
        EventHandler<ActionEvent> btnHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Voulez-vous modifier le produit: " + produit.getNom());
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Load the AnotherView.fxml file
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/ModifierProduit.fxml"));
                            Parent root = loader.load();

                            ModifierProduit controller = loader.getController();
                            controller.setAdminPanelContent(tmpAdminContentPanel);
                            tmpAdminContentPanel.getChildren().clear();
                            tmpAdminContentPanel.getChildren().setAll(root);
                            controller.setProduitDetails(produit.getId());
                        } catch (IOException e) {
                            displayAlertErreure("Error", "Il y a un problème lors de la suppression du produit");
                        }
                    }
                });
            }
        };
        btn.setOnAction(btnHandler);
        return btn;
    }

    @FXML
    void goToAddProductView(ActionEvent event) {
        // Load the AnotherView.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/AjouterProduit.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            AdminContentPanel controller = loader.getController();
            controller.setAdminPanelContent(super.getAdminPanelContent());
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            displayAlertErreure("Error", "Il y a un problème lors de la redirection vers la bonne interface");
        }
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
