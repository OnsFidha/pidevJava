package edu.esprit.controllers;

import java.io.File;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CardController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label DateCreation;

        @FXML
        private ImageView imgPub;

        @FXML
        private Label userPub;
        private Publication publication;

        public Publication getPublication() {
                return publication;
        }
        @FXML
        void initialize() {
        }
        @FXML
        void onclick(MouseEvent event) {


        }
        public void setData(Publication pub){

                String imageName = pub.getPhoto();
                String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/pub/";
                String imagePath = destinationDirectory + imageName;
                File file = new File(imagePath);
                if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imgPub.setImage(image);
                } else {

                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(pub.getDateCreation());
                DateCreation.setText(formattedDate);

        }

}




