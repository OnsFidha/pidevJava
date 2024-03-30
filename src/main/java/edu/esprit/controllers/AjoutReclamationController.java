package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AjoutReclamationController implements Initializable {



    @FXML
    private Button Reclamlistbtn;

    @FXML
    private Circle circle;


    @FXML
    private TextArea description;

    @FXML
    private Button enregistrerbtn;



    @FXML
    private ComboBox<String> comb;

    @FXML
    void Selecttype(ActionEvent event) {
        String selectedItem = comb.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);

    }

    @FXML
    void Select(ActionEvent event) {
        String selectedItem = comb.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));


        comb.setItems(FXCollections.observableArrayList("Publication non visible sur la plateforme",
                "Difficulté à publier du contenu",
                "Difficulté à trouver des collaborations appropriées",
                "Problèmes de communication avec les collaborateurs",
                "Autre"));
    }


    public Boolean ValidateFields() {
        if (description.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate fields");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Into The Fields");
            alert.showAndWait();
            return false;
        }

        return true;

    }
    @FXML
    void ajouterReclam(ActionEvent event) throws SQLException {

        if(ValidateFields() ){
            Reclamation r = new Reclamation();
            r.setDescription(description.getText());
            r.setType(comb.getSelectionModel().getSelectedItem());

            // Initialiser la date de création à la date actuelle
            r.setDate_creation(new Date());

            ReclamationService pst = new ReclamationService();
            pst.ajouter(r);



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("réclamer");
            alert.setHeaderText(null);
            alert.setContentText("Votre réclamation a ete bien ajoute");
            alert.showAndWait();
        }

    }

    @FXML
    void rrtourListReclam(ActionEvent event) {

    }
}

