package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import edu.esprit.service.EvenementService;
import edu.esprit.service.FeedbackService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FeedbackAdmin {

    @FXML
    private ImageView UserImg;

    @FXML
    private Label nbreactions;

    @FXML
    private Label text;

    @FXML
    private Label username;

    private Feedback feedback;
    public void setData(Feedback feedback){



        Image image = new Image("/img/user2.png");
        UserImg.setImage(image);


        username.setText("Syrine Zaier");
        text.setText(feedback.getText());
        this.feedback=feedback;

    }

    @FXML
    void GoToEdit(MouseEvent event) {
        // Ensure feedback is not null
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editfeedadmin.fxml"));
            Parent root = loader.load();

            // Pass the selected feedback to the edit controller
            editFeedAdmin editController = loader.getController();
            editController.initData(this.feedback);

            // Show the edit page in a new window
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.show();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }

    }
    @FXML
    void Delete(MouseEvent Mevent) {
        // Retrieve the id of the event to be deleted
        int feedId = feedback.getId(); // Replace this with how you retrieve the event id

        // Call the supprimer function in EvenementService
        FeedbackService es = new FeedbackService();
        try {
            es.supprimer(feedId);

            // Show an alert message indicating successful deletion
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("L'événement a été supprimé avec succès.");

            // Show the alert and wait for the user's response
            alert.showAndWait();

            // After successful deletion, navigate back to the events page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lisfeedadmin.fxml"));
            try {
                Parent root = loader.load();
                // Pass any necessary data back to the event page
                listfeedbackadmin list = loader.getController();
                list.setEventId(feedback.getId_evenment());

                // Get the current stage
                Stage stage = (Stage) ((Node) Mevent.getSource()).getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException
            }
        } catch (SQLException e) {
            // Exception handling
        }
    }

}
