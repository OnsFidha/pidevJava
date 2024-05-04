package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherEventAdmin implements Initializable {
    @FXML
    private Button BtnRecherche;

    @FXML
    private Circle circle;
    @FXML
    private TextField eventsearch;

    @FXML
    private GridPane eventgrid;
    private List<Evenement> events;
    private EvenementService evenementService = new EvenementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EvenementService evenementService = new EvenementService();

        try {
            List<Evenement> events = evenementService.getAll();

            int columns = 0;
            int rows = 1;

            for (Evenement event : events) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvent.fxml"));
                VBox eventbox = fxmlLoader.load();

                AdminEventController eventController = fxmlLoader.getController();
                eventController.setData(event);


                if (columns == 1) {
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
    void goToStats(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statsevent.fxml"));
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
    void Ajouter(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajoutereventadmin.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvent.fxml"));
            Pane eventBox = fxmlLoader.load();
            AdminEventController eventController = fxmlLoader.getController();
            eventController.setData(event);
            if (column == 1) {
                column = 0;
                ++row;
            }
            eventgrid.add(eventBox, column++, row);
            GridPane.setMargin(eventBox, new Insets(10));
        }
    }
}
