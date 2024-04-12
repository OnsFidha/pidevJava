package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AfficherEventController {
    @FXML
    private Label DateD;

    @FXML
    private Label dateF;

    @FXML
    private Label eventdesc;

    @FXML
    private ImageView eventimg;

    @FXML
    private Label eventname;

    @FXML
    private Label lieu;

    @FXML
    private Label nbrmax;

    @FXML
    private ImageView userimg;

    @FXML
    private Label username;
    @FXML
    void initialize() {

    }
    public void setData(Evenement event){
//            Image image = new Image(getClass().getResourceAsStream(event.getImage()));
////            Image image = new Image("/img/Event5.png");
//            EventImage.setImage(image);
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
        lieu.setText(event.getLieu());

        DateD.setText(event.getDateDebut().toString());
        dateF.setText(event.getDateFin().toString());

        nbrmax.setText(Integer.toString(event.getNbreMax()));


    }
    public void setDateD(Date DateD) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(DateD);
        this.DateD.setText(formattedDate);
    }

    public void setDateF(Date dateF) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(dateF);
        this.dateF.setText(formattedDate);
    }

    public void setLieu(String lieu) {
        this.lieu.setText(lieu);
    }
    public void setNom(String eventname) {
        this.eventname.setText(eventname);
    }
    public void setDescription(String eventdesc) {
        this.eventdesc.setText(eventdesc);
    }
    public void setNbr(String nbrmax) {
        this.nbrmax.setText(nbrmax);
    }
    public void setUsername(String username) {
        this.username.setText(username);
    }

}
