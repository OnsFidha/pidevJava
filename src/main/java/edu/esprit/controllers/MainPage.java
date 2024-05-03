package edu.esprit.controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class MainPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView musée;
    @FXML
    private HBox pub;
    @FXML
    private HBox users;


    @FXML
    private Circle circle;

    @FXML
    void initialize() {

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






