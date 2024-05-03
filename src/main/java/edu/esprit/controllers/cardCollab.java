package edu.esprit.controllers;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import edu.esprit.entities.Collaboration;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import javax.mail.MessagingException;


public class cardCollab {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label compétances;

    @FXML
    private Label cv;

    @FXML
    private Label dispo;

    @FXML
    private Label user;

    @FXML
    private ImageView userimage;
    @FXML
    private VBox competencesContainer;

    @FXML
    void initialize() {

    }
    public void setData(Collaboration collaborateur) {
        user.setText("Noussa"); // Remplir le nom du collaborateur dans le label user

        String competencesString = collaborateur.getCompetence();
        String[] competences = competencesString.split(" ");

        Color[] colors = {
                Color.web("#BEE5B4"),
                Color.web("#91DAF0"),
                Color.web("#F26A77"),
                Color.web("#F9A936")
        };

        // Effacer le contenu actuel de competencesContainer avant d'ajouter de nouveaux labels
        competencesContainer.getChildren().clear();

        for (int i = 0; i < competences.length; i++) {
            BorderPane competencePane = new BorderPane();
            Label competenceLabel = new Label(competences[i]);
            competenceLabel.setTextFill(Color.WHITE); // Appliquer la couleur correspondante

            competencePane.setCenter(competenceLabel);

            competencePane.setBackground(new Background(new BackgroundFill(colors[i % colors.length],  new CornerRadii(10),  new Insets(2))));

            // Ajuster la largeur du BorderPane en fonction de la longueur du texte de la compétence
            competencePane.setMinWidth(competenceLabel.getWidth()+10 ); // Ajouter une marge supplémentaire

            competencesContainer.getChildren().add(competencePane);
        }


        cv.setText(collaborateur.getCv()); // Remplir le CV du collaborateur dans le label cv
        dispo.setText(collaborateur.getDisponibilite()); // Remplir la disponibilité du collaborateur dans le label dispo
    }


    public void contacter(ActionEvent actionEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir contacter cet utilisateur par e-mail ?");

        confirmationAlert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    String recepient = "onsfidha3@gmail.com"; // Adresse e-mail de l'expéditeur
                    String subject = "test";
                    String emailMessage = "Ceci est un test d'e-mail.";

                    try {
                        EmailManager.sendEmail(recepient, subject, emailMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Une erreur s'est produite lors de l'envoi de l'e-mail.");
                        errorAlert.showAndWait();
                    }
                });
    }
}
