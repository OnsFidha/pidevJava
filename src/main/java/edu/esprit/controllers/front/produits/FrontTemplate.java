package edu.esprit.controllers.front.produits;

import edu.esprit.controllers.AfficherEventController;
import edu.esprit.controllers.FrontContentPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FrontTemplate extends FrontContentPanel implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    void showProducts(MouseEvent event) {
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
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts(null);
    }
}
