package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminEventController {
    @FXML
    private Label datedeb;

    @FXML
    private Label datefin;

    @FXML
    private Label eventdesc;

    @FXML
    private Label eventname;

    @FXML
    private Label lieu;

    @FXML
    private Label nbr;

    @FXML
    private ImageView userimg;

    @FXML
    private Label username;
    @FXML
    private ImageView eventimg;

    public void setData(Evenement event){

        if (event.getImage() != null) {
            Image image = new Image(event.getImage());
            eventimg.setImage(image);
        } else {
            // Handle the case where image path is null
            // For example, set a default image or display an error message
            System.err.println("Image path is null for the event: " + event.getNom());
        }

//            image= new Image(getClass().getResourceAsStream(event.getProfileImageSrc()));
        Image image = new Image("/img/user2.png");
        userimg.setImage(image);

//            username.setText(event.getUsername());
        username.setText("Syrine Zaier");
        eventname.setText(event.getNom());
        eventdesc.setText(event.getDescription());
        datedeb.setText(event.getDateDebut().toString());
        datefin.setText(event.getDateFin().toString());
        lieu.setText(event.getLieu());
        nbr.setText(event.getNbreMax().toString());
//
    }


    @FXML
    void delete(MouseEvent event) {

    }

    @FXML
    void edit(MouseEvent event) {

    }

    @FXML
    void goFeed(MouseEvent event) {

    }

}
