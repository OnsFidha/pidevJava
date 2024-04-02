package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AjouterPub {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField lieuPub;

    @FXML
    private TextField photoPub;

    @FXML
    private TextArea textPub;

    @FXML
    private TextField typePub;

    @FXML
    void addPub(ActionEvent event) {
        Publication p=new Publication(typePub.getText(),textPub.getText(),lieuPub.getText(),2,photoPub.getText());
        PublicationService ps=new PublicationService();
        try {
            ps.ajouter(p);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La publication a été ajouté avec succée");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherPub.fxml"));
            try {
                Parent root=loader.load();
                AfficherPub pub= loader.getController();
                pub.setTypePub(typePub.getText());
                pub.setTextPub(textPub.getText());
                pub.setLieuPub(lieuPub.getText());
                pub.setDateCreationPub(new Date());
                pub.setDateModificationPub(new Date());
                lieuPub.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(e.getMessage());
            alert1.show();
        }

    }

    @FXML
    void initialize() {

    }

}
