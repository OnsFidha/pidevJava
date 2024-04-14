package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import edu.esprit.service.FeedbackService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;


public class UpdateFeedback {
    private int eventId;
    private int feedId;

    @FXML
    private TextArea TextFeed;


    public void initData(Feedback feedback) {



        // Pre-fill text fields with event details
        TextFeed.setText(feedback.getText());
        eventId= feedback.getId_evenment();
        feedId=feedback.getId();



    }

    @FXML
    void UpdateFeed(MouseEvent event) {
        if (TextFeed.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        // Vérifiez que la longueur de la description ne dépasse pas 255 caractères
        if (TextFeed.getText().length() > 255) {
            showAlert("Le feedback ne peut pas dépasser 255 caractères.");
            return;
        }
        Feedback feed=new Feedback(feedId,eventId,TextFeed.getText());
        FeedbackService fs=new FeedbackService();
        try {
            fs.modifier(feed);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Votre feedback a été modifié avec succées");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ListFeedbacks.fxml"));
            try {
                Parent root = loader.load();
                ListFeedbacks list = loader.getController();
                list.setEventId(eventId);

                // Get the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(e.getMessage());
            alert1.show();
        }

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setEventId(int eventId) {
        // Use the event ID to retrieve the corresponding feedbacks

        this.eventId = eventId;
    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListFeedbacks.fxml"));
            Parent root = loader.load();
            // Pass any necessary data back to the event page
            ListFeedbacks list = loader.getController();
            list.setEventId(eventId);



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