package edu.esprit.controllers;

import edu.esprit.entities.Evenement;

import edu.esprit.service.EvenementService;
import edu.esprit.service.ParticipationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

//    void Recherche(ActionEvent event) {
//        int column = 0;
//        int row = 1;
//        String recherche = eventsearch.getText();
//        try {
//            eventgrid.getChildren().clear();
//            for (Evenement eventt : evenementService.Rechreche(recherche)){
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/Event.fxml"));
//                Pane userBox = fxmlLoader.load();
//                EventController cardC = fxmlLoader.getController();
//                cardC.setData(eventt);
//                if (column == 3) {
//                    column = 0;
//                    ++row;
//                }
//                eventgrid.add(userBox, column++, row);
//                GridPane.setMargin(userBox, new Insets(10));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        events= new ArrayList<>(data());
//
//        int columns=0;
//        int rows=1;
//        try{
//            for ( int i=0; i < events.size(); i++){
//                FXMLLoader fxmlloader = new FXMLLoader();
//                fxmlloader.setLocation(getClass().getResource("/Event.fxml"));
//
//                VBox eventbox = fxmlloader.load();
//
//                EventController eventController = fxmlloader.getController();
//                eventController.setData(events.get(i));
//
//                if ( columns == 3){
//                    columns = 0;
//                    ++rows;
//                }
//
//                eventgrid.add(eventbox, columns++, rows);
//                GridPane.setMargin(eventbox, new Insets(10));
//            }
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//
//
//    }
//    private List<Evenement> data(){
//        List<Evenement> ls= new ArrayList<>();
//        try {
//            // Retrieve events from the database using the service class
//            ls = evenementService.getAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle any potential errors here
//        }


//        Evenement event = new Event();
//        event.setProfileImageSrc("/img/user2.png");
//        event.setEventImageSrc("/img/Event5.png");
//        event.setUsername("Syrine Zaier");
//        event.setEventName("Art Event");
//        event.setNbStars("0/5");
//        event.setNbComments("2K");
//        ls.add(event);
//
//        Evenement event2 = new Event();
//        event2.setProfileImageSrc("/img/user1.png");
//        event2.setEventImageSrc("/img/Event3.png");
//        event2.setUsername("Syrine Zaier");
//        event2.setEventName("Art Event");
//        event2.setNbStars("5/5");
//        event2.setNbComments("24K");
//        ls.add(event2);
//
//        Evenement event3 = new Event();
//        event3.setProfileImageSrc("/img/user1.png");
//        event3.setEventImageSrc("/img/Event3.png");
//        event3.setUsername("Syrine Zaier");
//        event3.setEventName("Art Event");
//        event3.setNbStars("5/5");
//        event3.setNbComments("24K");
//        ls.add(event3);
//        Evenement event4 = new Event();
//        event4.setProfileImageSrc("/img/user1.png");
//        event4.setEventImageSrc("/img/Event3.png");
//        event4.setUsername("Syrine Zaier");
//        event4.setEventName("Art Event");
//        event4.setNbStars("5/5");
//        event4.setNbComments("24K");
//        ls.add(event4);
//        return ls;
//    }
}
