package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class ListComm {
    @FXML
    private VBox container;
    @FXML
    private Button  editButton;
    @FXML
    private Button deleteButton;

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
