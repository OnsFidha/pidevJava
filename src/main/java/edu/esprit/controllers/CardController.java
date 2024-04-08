package edu.esprit.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import edu.esprit.entities.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CardController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label DateCreation;

        @FXML
        private Label DescPub;



        @FXML
        private Label TypePub;

        @FXML
        void initialize() {


        }

        public void setData(Publication pub){
                DescPub.setText(pub.getText());
                TypePub.setText(pub.getType());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(pub.getDateCreation());
                DateCreation.setText(formattedDate);

        }

}
