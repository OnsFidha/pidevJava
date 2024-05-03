package edu.esprit.controllers;

import edu.esprit.controllers.produitsfront.ListeProduits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class BaseFront extends FrontContentPanel{

    @FXML
    private Circle circle;

    @FXML
    void showProducts(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produitsfront/ListeProduits.fxml"));
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

}
