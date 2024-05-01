package edu.esprit.controllers;

import edu.esprit.entities.Participation;
import edu.esprit.service.ParticipationService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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
}
