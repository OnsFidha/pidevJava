package edu.esprit.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import edu.esprit.entities.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReclamCardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label DateCreation;

    @FXML
    private Label DescReclam;

    @FXML
    private Label EtatReclam;

    @FXML
    private Label TypeReclam;

    @FXML
    void initialize() {


    }

    public void setData(Reclamation reclamation){
        DescReclam.setText(reclamation.getDescription());
        TypeReclam.setText(reclamation.getType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(reclamation.getDate_creation());
        DateCreation.setText(formattedDate);
        String etatText = reclamation.isEtat() ? "Traité" : "Non traité";
        EtatReclam.setText(etatText);
    }

}
