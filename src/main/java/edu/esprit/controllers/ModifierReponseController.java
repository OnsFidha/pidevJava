package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Reponse;
import edu.esprit.service.ReclamationService;
import edu.esprit.service.ReponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ModifierReponseController {

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

    Reponse rep = new Reponse();
    @FXML
    void ListReclam(MouseEvent event) {
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
    void ListRep(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
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
    void ModifierReponse(ActionEvent event) {
        if (rep == null) {
            System.out.println("Choisir une Réponse");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modifier réclamation");
            alert.setHeaderText(null);
            alert.setContentText("La Réponse n'est pas modifiée!");
            alert.showAndWait();
        } else {
            rep.setReponse(reponse.getText());

            // Appel de la méthode modifier avec les champs à mettre à jour
            ReponseService sr = new ReponseService();
            try {
                sr.modifier(rep);
                System.out.println("Modification terminée");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Modification terminée avec succès.");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Votre réponse a été modifiée avec succès.");
                successAlert.showAndWait();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la modification: " + ex.getMessage());
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur lors de la modification.");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur s'est produite lors de la modification de votre réclamation.");
                errorAlert.showAndWait();
            }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
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

    void setData(int id, String sub ) {
        rep.setId(id);
        reponse.setText(sub);

    }


}
