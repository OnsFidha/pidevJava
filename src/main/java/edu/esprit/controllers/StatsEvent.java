package edu.esprit.controllers;

import edu.esprit.entities.Participation;
import edu.esprit.service.ParticipationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class StatsEvent {
    @FXML
    private BarChart<String, Number> barChart;

    public void initialize() {
        ParticipationService participationService = new ParticipationService();

        try {
            // Récupérer les statistiques de participation par événement depuis le service
            List<Participation> participationStats = participationService.getParticipationStatsByEvent();

            // Créer une série de données pour le graphique
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Participation participation : participationStats) {
                series.getData().add(new XYChart.Data<>(String.valueOf(participation.getId_evenment()), participation.getId_user()));
            }



            // Ajouter la série de données au graphique
            barChart.getData().add(series);

            // Configurer les axes du graphique
            CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
            xAxis.setLabel("Evenement");

            NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
            yAxis.setLabel("Nombre de participations");
            System.out.println(yAxis);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }

    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
            Parent root = loader.load();

            // Pass any necessary data back to the event page
//            AdminEventController eventPageController = loader.getController();
//            eventPageController.setEventData(eventId);

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
            alert.setContentText("An error occurred while navigating to the event page. Please try again.");
            alert.showAndWait();
        }
    }
}

