package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import edu.esprit.service.EvenementService;
import edu.esprit.service.FeedbackService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listfeedbackadmin implements Initializable {
    @FXML
    private GridPane feedbackgrid;
    private List<Feedback> feedbacks;
    private FeedbackService feedbackService = new FeedbackService();
    private int eventId;

    public void setEventId(int eventId) {
        // Use the event ID to retrieve the corresponding feedbacks
        FeedbackService feedbackService = new FeedbackService();
        this.eventId = eventId;
        try {
            feedbacks = feedbackService.getAllById(eventId);

            // Clear existing feedbacks from the grid pane
            feedbackgrid.getChildren().clear();

            int columns = 0;
            int rows = 1;

            for (Feedback feedback : feedbacks) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FeedbackCardAdmin.fxml"));
                VBox feedbackbox = fxmlLoader.load();

                FeedbackAdmin feedbackController = fxmlLoader.getController();
                feedbackController.setData(feedback);

                if (columns == 1) {
                    columns = 0;
                    rows++;
                }

                feedbackgrid.add(feedbackbox, columns++, rows);
                GridPane.setMargin(feedbackbox, new Insets(10));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle any potential errors here
        }
    }
    @FXML
    void goBack(MouseEvent event) {
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdmin.fxml"));
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

    @FXML
    void AddFb(ActionEvent event) {
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddFeedAdmin.fxml"));
            Parent root = loader.load();
            // Get the ListFeedbacks controller
            AddFeedAdmin controller = loader.getController();

            // Set the event ID
            controller.setEventId(eventId);



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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        FeedbackService feedbackService = new FeedbackService();
//        try {
//
//            feedbacks = feedbackService.getAll();
//
//
//            int columns = 0;
//            int rows = 1;
//
//            for (Feedback feedback : feedbacks) {
//
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FeedbackCard.fxml"));
//                VBox feedbackbox = fxmlLoader.load();
//
//                FeedbackController feedbackController = fxmlLoader.getController();
//                feedbackController.setData(feedback);
//
//                if (columns == 1) {
//                    columns = 0;
//                    rows++;
//                }
//
//                feedbackgrid.add(feedbackbox, columns++, rows);
//                GridPane.setMargin(feedbackbox, new Insets(10));
//            }
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//            // Handle any potential errors here
//        }
    }




}

