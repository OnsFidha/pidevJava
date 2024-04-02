package edu.esprit.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AfficherPub {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dateCreationPub;

    @FXML
    private Label dateModificationPub;

    @FXML
    private Label lieuPub;

    @FXML
    private Label textPub;

    @FXML
    private Label typePub;

    @FXML
    void initialize() {

    }

    public void setDateCreationPub(Date dateCreationPub) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(dateCreationPub);
        this.dateCreationPub.setText(formattedDate);
    }

    public void setDateModificationPub(Date dateModificationPub) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         String formattedDate = dateFormat.format(dateModificationPub);
         this.dateModificationPub.setText(formattedDate);
    }

    public void setLieuPub(String lieuPub) {
        this.lieuPub.setText(lieuPub);
    }

    public void setTextPub(String textPub) {
        this.textPub.setText(textPub);
    }

    public void setTypePub(String typePub) {
        this.typePub.setText(typePub);
    }
}
