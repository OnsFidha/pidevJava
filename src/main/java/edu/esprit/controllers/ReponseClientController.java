package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

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
    private Label etatDeReclamation;

    @FXML
    private Label reponse;


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
            ReponseService reponseService = new ReponseService(); // Créez une instance de ReponseService
            int reclamationId = AfficherReclamationController.selected.getId();

            // Appelez la fonction getReponseByReclamationId
            Reponse rep = reponseService.getReponseByReclamationId(reclamationId);

            // Vérifiez si une réponse a été trouvée
            if (rep != null) {
                // Affichez les détails de la réponse
                reponse.setText(rep.getReponse());
                // Formatez la date en utilisant SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(rep.getDate_reponse());
                date_rep.setText(formattedDate);

            } else {
                reponse.setText("Votre réclamation n'a pas encore été répondue.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




}
