package edu.esprit.controllers.front.produits;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowProductsEvent {

    @FXML
    public void loadProductWindow(MouseEvent event){
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(FrontTemplate.class.getResource("/front/produits/FrontTemplate.fxml"));
            Parent root = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
