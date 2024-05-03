package edu.esprit.controllers;

import edu.esprit.service.PublicationService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;
import java.util.Map;

public class AdminPage {

    @FXML
    private GridPane gridPane;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private PublicationService publicationService = new PublicationService(); // Remplacez par votre classe de service

    public void initialize() {
        stat();
    }

    private void stat() {      Map<String, Integer> countByType = null;
        try {
            countByType = publicationService.getCountByType();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Créer l'axe des catégories (noms des types de publications)
        xAxis.setLabel("Types de publications");

        // Créer l'axe des nombres (nombre de publications)
        yAxis.setLabel("Nombre de publications");

        // Ajouter les données de nombre de publications au graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            XYChart.Data<String, Number> data = new XYChart.Data<>(type, count);
            StackPane stackPane = createDataNode(type, count);
            data.setNode(stackPane);
            series.getData().add(data);
        }

        // Ajouter la série de données au graphique
        barChart.getData().add(series);
    }

    private StackPane createDataNode(String type, int count) {
        StackPane stackPane = new StackPane();
        Label label = new Label(type + ": " + count);
        stackPane.getChildren().add(label);

        stackPane.setOnMouseEntered(event -> {
            label.setVisible(true);
        });

        stackPane.setOnMouseExited(event -> {
            label.setVisible(false);
        });

        label.setVisible(false);
        return stackPane;
    }
}
