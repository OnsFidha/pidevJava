package edu.esprit.controllers;

import edu.esprit.entities.Evenement;

import edu.esprit.service.EvenementService;
import edu.esprit.service.ParticipationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AfficherEvent implements Initializable {
    @FXML
    private GridPane eventgrid;
    @FXML
    private TextField eventsearch;
    @FXML
    private HBox event;
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

    private List<Evenement> events;
    private EvenementService evenementService = new EvenementService();

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        EvenementService evenementService = new EvenementService();
//
//        try {
//            List<Evenement> events = evenementService.getAll();
//
//            int columns = 0;
//            int rows = 1;
//
//            for (Evenement event : events) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
//                VBox eventbox = fxmlLoader.load();
//
//                EventController eventController = fxmlLoader.getController();
//                eventController.setData(event);
//                // Add event handling logic
//                eventbox.setOnMouseClicked(mouseEvent -> {
//                    if (mouseEvent.getClickCount() == 2) { // Double click
//                        redirectToEventPage(event);
//                    }
//                });
//
//                if (columns == 3) {
//                    columns = 0;
//                    rows++;
//                }
//
//                eventgrid.add(eventbox, columns++, rows);
//                GridPane.setMargin(eventbox, new Insets(10));
//            }
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//            // Handle any potential errors here
//        }
//
//    }
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    EvenementService evenementService = new EvenementService();
    ParticipationService participationService = new ParticipationService();

    try {
        List<Evenement> events = evenementService.getAll();

        // Create a map to store each event with its participation count
        Map<Evenement, Integer> participationCountMap = new HashMap<>();

        // Populate the map with participation counts for each event
        for (Evenement event : events) {
            int participationCount = participationService.getParticipationCountByEvent(event.getId());
            participationCountMap.put(event, participationCount);
        }

        // Sort the events based on their participation count
        events.sort(Comparator.comparingInt(participationCountMap::get).reversed());

        int columns = 0;
        int rows = 1;

        for (Evenement event : events) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
            VBox eventbox = fxmlLoader.load();

            EventController eventController = fxmlLoader.getController();
            eventController.setData(event);
            // Add event handling logic
            eventbox.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) { // Double click
                    redirectToEventPage(event);
                }
            });

            if (columns == 3) {
                columns = 0;
                rows++;
            }

            eventgrid.add(eventbox, columns++, rows);
            GridPane.setMargin(eventbox, new Insets(10));
        }
    } catch (IOException | SQLException e) {
        e.printStackTrace();
        // Handle any potential errors here
    }
    EventHandler<MouseEvent> clickHandler4 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            getHome();
        }
    };

    // Ajouter l'EventHandler au HBox
    home.setOnMouseClicked(clickHandler4);
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
    @FXML
    void MoveToAdd(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvenement.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception gracefully
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Navigation Error");
            alert.setContentText("An error occurred while navigating to the new page. Please try again.");
            alert.showAndWait();
        }
    }
    @FXML
    void movetoCalendar(MouseEvent event) {
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception gracefully
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Navigation Error");
            alert.setContentText("An error occurred while navigating to the previous page. Please try again.");
            alert.showAndWait();
        }

    }


    private void redirectToEventPage(Evenement selectedEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
            Parent root = loader.load();

            // Pass event data to the event page controller
            AfficherEventController eventController = loader.getController();
            eventController.setData(selectedEvent);


            // Display the event page in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error loading the event page
        }
    }
    @FXML
    void Recherche(ActionEvent Aevent) {
        String recherche = eventsearch.getText().toLowerCase(); // Convertir la recherche en minuscules

        try {
            List<Evenement> filteredEvents = evenementService.getAll().stream()
                    .filter(event -> event.getNom().toLowerCase().contains(recherche))
                    .collect(Collectors.toList());

            updateEventGrid(filteredEvents); // Mettre à jour la grille avec les événements filtrés
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateEventGrid(List<Evenement> events) throws IOException {
        eventgrid.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Evenement event : events) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
            Pane eventBox = fxmlLoader.load();
            EventController eventController = fxmlLoader.getController();
            eventController.setData(event);
            if (column == 3) {
                column = 0;
                ++row;
            }
            eventgrid.add(eventBox, column++, row);
            GridPane.setMargin(eventBox, new Insets(10));
        }
    }



    private void getListProduit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/FrontTemplate.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void getListPub(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
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


}
