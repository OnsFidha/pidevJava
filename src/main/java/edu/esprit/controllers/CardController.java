package edu.esprit.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CardController {



        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label descPub;

        @FXML
        private ImageView imgPub;
        private String [] colors={"B9E5FF"};

        @FXML
        void initialize() {

        }
        void setData(Publication pub){
               Image img = new Image(getClass().getResourceAsStream(pub.getPhoto()));
                imgPub.setImage(img);
                descPub.setText(pub.getText());

        }

    }


