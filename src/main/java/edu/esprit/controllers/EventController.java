package edu.esprit.controllers;

import edu.esprit.entities.Evenement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventController {




        @FXML
        private ImageView EventImage;

        @FXML
        private Label EventName;

        @FXML
        private ImageView ProfileImage;

        @FXML
        private Label comments;

        @FXML
        private Label starts;

        @FXML
        private Label username;

        public void setData(Evenement event){
//            Image image = new Image(getClass().getResourceAsStream(event.getImage()));
////            Image image = new Image("/img/Event5.png");
//            EventImage.setImage(image);
            if (event.getImage() != null) {
                Image image = new Image(event.getImage());
                EventImage.setImage(image);
            } else {
                // Handle the case where image path is null
                // For example, set a default image or display an error message
                System.err.println("Image path is null for the event: " + event.getNom());
            }

//            image= new Image(getClass().getResourceAsStream(event.getProfileImageSrc()));
            Image image = new Image("/img/user2.png");
            ProfileImage.setImage(image);

//            username.setText(event.getUsername());
            username.setText("Syrine Zaier");
            EventName.setText(event.getNom());
//            starts.setText(event.getNbStars());
//            comments.setText(event.getNbComments());
            starts.setText("4/5");
            comments.setText("3K");
        }



}
