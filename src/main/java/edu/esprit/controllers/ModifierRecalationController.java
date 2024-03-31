package edu.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierRecalationController  implements Initializable {

        @FXML
        private Button Reclamlistbtn;

        @FXML
        private Circle circle;

        @FXML
        private ComboBox<?> comb;

        @FXML
        private TextArea description;

        @FXML
        private Button modifierbtn;

        @FXML
        void ModifierReclam(ActionEvent event) {

        }

        @FXML
        void Selecttype(ActionEvent event) {

        }

        @FXML
        void rrtourListReclam(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("artistool - list Reclamation");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));


        
    }
}
