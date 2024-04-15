package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class ModifierPub {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private TextField TextPub;

    @FXML
    private Circle circle;

    @FXML
    private TextField lieuPub;

    @FXML
    private TextField photoPub;

    @FXML
    private TextField typePub;


    @FXML
    void ModifyPub(ActionEvent event) {
        try {
            // Créer une nouvelle instance de Publication avec les données mises à jour
            Publication updatedPublication = new Publication(this.p.getId(),typePub.getText(), TextPub.getText(), lieuPub.getText(), photoPub.getText());

            // Mettre à jour la publication dans la base de données
            PublicationService ps = new PublicationService();
            ps.modifier(updatedPublication);

            // Afficher une alerte pour indiquer que la modification a réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La publication a été modifiée avec succès");
            alert.show();

            // Rediriger vers la liste des publications
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            Parent root = loader.load();
            lieuPub.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            // Gérer les exceptions en affichant une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de la publication : " + e.getMessage());
            alert.show();
        }
    }


    @FXML
    void choose_file(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
    private Publication p;
    public void initData(Publication p) {
        this.p = p;

        // Pre-fill text fields with event details
        lieuPub.setText(p.getLieu());
        typePub.setText(p.getType());
       // photoPub.setText(p.getPhoto());
        TextPub.setText(p.getText());
    }
}
