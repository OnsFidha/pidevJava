package edu.esprit.controllers;

import edu.esprit.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;


public class ListComm {
    @FXML
    private Label logedUsernamee;
    @FXML
    private Circle circle;
    @FXML
    private VBox container;
    @FXML
    private Button  editButton;
    @FXML
    private Button deleteButton;

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    private void editComment() {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(" h ");
    }

    @FXML
    private void deleteComment() {
        // Ajoutez le code pour la suppression du commentaire ici
    }
    public VBox getContainer() {
        return container;
    }


}
