package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.service.ReponseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import edu.esprit.utils.DataSource;

import java.sql.*;

public class ReponseClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    private Label date_rep;


    @FXML
    private Label reponse;

    // Champ privé pour stocker la réclamation sélectionnée
    private Reclamation selectedReclamation;


    // Méthode pour définir la réclamation sélectionnée
    public void setSelectedReclamation(Reclamation selectedReclamation) {
        this.selectedReclamation = selectedReclamation;
        // Mettre à jour l'interface utilisateur avec les détails de la réclamation sélectionnée
        ReponseDeClient();
    }


    @FXML
    void ListReclam(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Ajout Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));
        ReponseDeClient();

    }

    public void ReponseDeClient() {
        try {
            if (selectedReclamation != null) {
                ReponseService reponseService = new ReponseService();
                int reclamationId = selectedReclamation.getId();
                Reponse rep = reponseService.getReponseByReclamationId(reclamationId);

                if (rep != null) {
                    reponse.setText(rep.getReponse());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = dateFormat.format(rep.getDate_reponse());
                    date_rep.setText(formattedDate);
                } else {
                    reponse.setText("Votre réclamation n'a pas encore été répondue.");
                    date_rep.setText("*********");
                }
            } else {
                reponse.setText("Aucune réclamation sélectionnée.");
                date_rep.setText("Aucune réclamation sélectionnée.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
