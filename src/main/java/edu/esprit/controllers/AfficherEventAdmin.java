package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEventAdmin implements Initializable {
    @FXML
    private Button BtnRecherche;

    @FXML
    private Circle circle;

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
}
