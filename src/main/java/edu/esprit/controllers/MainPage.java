package edu.esprit.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.control.Label;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import edu.esprit.utils.SessionManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;


public class MainPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView musée;

    @FXML
    private HBox event;
    @FXML
    private Circle circle;

    @FXML
    private Label logedUsernamee;

    @FXML
    private HBox home;

    @FXML
    private HBox produit;

    @FXML
    private HBox pub;

    @FXML
    private HBox recla;

    @FXML
    private HBox users;
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    void initialize() {

        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));

        EventHandler<MouseEvent> clickHandler4 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListEvent();
            }
        };

        // Ajouter l'EventHandler au HBox
        event.setOnMouseClicked(clickHandler4);
        EventHandler<MouseEvent> clickHandler3 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListRecla();
            }
        };

        // Ajouter l'EventHandler au HBox
        recla.setOnMouseClicked(clickHandler3);
        // Créer un EventHandler pour gérer les clics sur le HBox
        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListPub();
            }
        };

        // Ajouter l'EventHandler au HBox
        pub.setOnMouseClicked(clickHandler);
        EventHandler<MouseEvent> clickHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListUsers();
            }
        };

        // Ajouter l'EventHandler au HBox
        users.setOnMouseClicked(clickHandler2);
        EventHandler<MouseEvent> clickHandler5 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListProduit();
            }
        };

        // Ajouter l'EventHandler au HBox
        produit.setOnMouseClicked(clickHandler5);
    }

    private void getListProduit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BaseFront.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListEvent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListRecla() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListUsers() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void openURL() {
     try {
        Desktop.getDesktop().browse(new URI("https://www.artsteps.com/embed/660dbb9d33b5196332b04c32/560/315"));
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }}

void getListPub(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
    try {

        Parent root = loader.load();
        pub.getScene().setRoot(root);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
}






