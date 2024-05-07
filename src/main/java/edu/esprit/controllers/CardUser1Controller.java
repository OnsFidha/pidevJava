package edu.esprit.controllers;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CardUser1Controller {
    @FXML
    private Pane userpane;
    @FXML
    private ImageView pdpimage;
    @FXML
    private Label nomprenomlb;
    @FXML
    private Label emaillb;
    @FXML
    private Label phonelb;
    @FXML
    private Label roleslb;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();
    private String[] colors = {
            "#F2E6EC", "#FFE3DF", "#FFD9D0", "#E0EAF0", "#D4E4F1",
            "#F9E3D9", "#FFE6D6", "#FFDCCB", "#D8E7F0", "#C7E2F0",
            "#EDD3C2", "#FFD9C8", "#FFD2BE", "#CDDCF4", "#B6D7F3",
            "#EAD3DE", "#FFDBE2", "#FFD3D7", "#D3E6FA", "#BBDFFB",
            "#E8D3C9", "#FFD8CD", "#FFD0C5", "#D4E1F8", "#BBDBF8"
    }
            ;

    public void setData1(Utilisateur user) {
        String imagePath = user.getImage();
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                pdpimage.setImage(image);
            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imagePath);
            }
        } else {
            System.err.println("Image path is null for user: " + user);
        }
        nomprenomlb.setText(user.getName() + " " + user.getPrename());
        emaillb.setText(user.getEmail());
        roleslb.setText(user.getRoles());
        phonelb.setText(String.valueOf(user.getPhone()));
        nomprenomlb.setStyle("-fx-text-fill: #000");
        phonelb.setStyle("-fx-text-fill: #000");
        roleslb.setStyle("-fx-text-fill: #000");
        emaillb.setStyle("-fx-text-fill: #000");
        userpane.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        userpane.setStyle("-fx-border-radius: 5px;-fx-border-color:#000");
    }
}
