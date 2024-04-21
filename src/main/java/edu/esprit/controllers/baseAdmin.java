package edu.esprit.controllers;

import edu.esprit.controllers.categories.AfficherCategories;
import edu.esprit.controllers.produits.AfficherProduits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class baseAdmin extends AdminContentPanel {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/afficherCategories.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/afficherProduits.fxml"));
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
}
