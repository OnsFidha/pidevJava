package edu.esprit.controllers;

import edu.esprit.controllers.categories.AfficherCategories;
import edu.esprit.controllers.commandes.AfficherCommandes;
import edu.esprit.controllers.produits.AfficherProduits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class BaseAdmin extends AdminContentPanel {

    @FXML
    private Button BtnRecherche;

    @FXML
    private HBox categoriesHbox;

    @FXML
    private Circle circle;

    @FXML
    private Label d;

    @FXML
    private HBox productsHbox;

    @FXML
    void showCategories(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/AfficherCategories.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView

            AfficherCategories controller = loader.getController();
            controller.setAdminPanelContent(super.getAdminPanelContent());
            controller.showData();
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showProducts(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            AfficherProduits controller = loader.getController();
            controller.setAdminPanelContent(super.getAdminPanelContent());
            controller.showData();
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showCommandes(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/commandes/AfficherCommandes.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            AfficherCommandes controller = loader.getController();
            controller.showData();
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
