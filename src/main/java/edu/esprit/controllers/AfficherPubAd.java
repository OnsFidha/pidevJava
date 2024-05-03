package edu.esprit.controllers;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class AfficherPubAd {

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane comments;

    @FXML
    private Label dateCreationPub;

    @FXML
    private Label dateModificationPub;

    @FXML
    private GridPane details;

    @FXML
    private ImageView imagePub;

    @FXML
    private Label lieuPub;

    @FXML
    private Label textPub;

    @FXML
    private Label typePub;

    @FXML
    void retour() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {
            Parent root = loader.load();
            textPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @FXML
//    void delete(ActionEvent event) throws SQLException {
//
//        PublicationService p = new PublicationService();
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        try {
//            if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer votre Publication. Êtes-vous sûr ?"
//                    , "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
//                p.supprimer(this.p.getId());
//            alert.setContentText("Votre Publication a été supprimée avec succès.");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
//            try {
//                Parent root = loader.load();
//                lieuPub.getScene().setRoot(root);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression :\n" + e.getMessage());
//        }
//    }

}
