package edu.esprit.controllers;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.esprit.entities.Collaboration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

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

        int colorIndex = 0;
        for (String competence : competences) {
            Label competenceLabel = new Label(competence);
            competenceLabel.setTextFill(colors[colorIndex]); // Appliquer la couleur correspondante
            compétances.getChildren().add(competenceLabel);
            colorIndex = (colorIndex + 1) % colors.length; // Pour alterner les couleurs
        }

        cv.setText(collaborateur.getCv()); // Remplir le CV du collaborateur dans le label cv
        dispo.setText(collaborateur.getDisponibilite()); // Remplir la disponibilité du collaborateur dans le label dispo
}

}
