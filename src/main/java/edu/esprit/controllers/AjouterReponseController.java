package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.service.ReclamationService;
import edu.esprit.service.ReponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AjouterReponseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnRecherche;

    @FXML
    private Button Reclamlistbtn;

    @FXML
    private Button ReponseListBtn;

    @FXML
    private Circle circle;

    @FXML
    private Button enregistrerbtn;

    @FXML
    private TextArea reponse;

    @FXML
    void ajoutReponse(ActionEvent event) {
        try {
            int rec_id = AfficherReclamationAdmin.selected.getId();
            String reponseText = reponse.getText();

            // Check if the reponseText is empty
            if (reponseText.isEmpty()) {
                System.out.println("Reponse text is empty.");
                return;
            }

            // Create a new Reponse object
            Reponse rep = new Reponse();
            rep.setReponse(reponseText);
            rep.setDate_reponse(new Date());
            rep.setRelation_id(rec_id);

            // Check if a Reponse with the same relation_id already exists
            ReponseService reponseService = new ReponseService();


            // Insert the Reponse object into the database
            reponseService.ajouter(rep);

            // Update the state of the corresponding Reclamation to true
            ReclamationService recService = new ReclamationService();
            Reclamation rec = recService.getOneById(rec_id);
            if (rec != null) {
                rec.setEtat(true);
                recService.modifier(rec);
                System.out.println("State of the reclamation with ID " + rec_id + " has been successfully updated to true.");
                System.out.println(rec.isEtat());
                System.out.println(rec.getId());
                System.out.println(rec);
            } else {
                System.out.println("Reclamation with ID " + rec_id + " not found.");
            }

            System.out.println("Reponse added successfully.");
        } catch (SQLException ex) {
            System.out.println("An error occurred while adding the Reponse:");
            ex.printStackTrace();
        }
    }




    @FXML
    void retourListReclam(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
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
    void retourListReponse(ActionEvent event) {

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


    }


}
