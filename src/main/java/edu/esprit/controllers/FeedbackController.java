package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FeedbackController {

    @FXML
    private ImageView UserImg;

    @FXML
    private Label nbreactions;

    @FXML
    private Label text;

    @FXML
    private Label username;
    public void setData(Feedback feedback){



        Image image = new Image("/img/user2.png");
        UserImg.setImage(image);


        username.setText("Syrine Zaier");
        text.setText(feedback.getText());

    }

}
