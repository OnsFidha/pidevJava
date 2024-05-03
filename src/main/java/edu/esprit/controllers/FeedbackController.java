package edu.esprit.controllers;

import com.dark.programs.speech.translator.GoogleTranslate;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FeedbackController {

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


        String nbrlikes= String.valueOf(feedback.getLikes());
        username.setText("Syrine Zaier");
        text.setText(feedback.getText());
        nbreactions.setText(nbrlikes);
        this.feedback=feedback;

    }
    @FXML
    void LikeFeed(MouseEvent event) {
        feedback.setLikes(feedback.getLikes() + 1);

        // Update the UI to display the new number of likes
        nbreactions.setText(String.valueOf(feedback.getLikes()));

        // Update the database to save the changes
        FeedbackService feedbackService = new FeedbackService();
        try {
            feedbackService.updateLikes(feedback.getId(), feedback.getLikes());
        } catch (SQLException e) {
            // Handle the SQLException
            e.printStackTrace();
        }

    }
    @FXML
    void Translate(MouseEvent event) throws IOException {
        String Newfeedtext = GoogleTranslate.translate("fr", text.getText());;
        text.setText(Newfeedtext);

    }

    @FXML
    void GoToEdit(MouseEvent event) {
         // Ensure feedback is not null
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateFeedback.fxml"));
                Parent root = loader.load();

                // Pass the selected feedback to the edit controller
                UpdateFeedback editController = loader.getController();
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
        // Retrieve the id of the feedback to be deleted
        int feedId = feedback.getId(); // Replace this with how you retrieve the feedback id

        // Show a confirmation dialog before proceeding with deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce feedback?");

        // Wait for the user's response
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the supprimer function in FeedbackService
                FeedbackService es = new FeedbackService();
                try {
                    es.supprimer(feedId);

                    // Show an alert message indicating successful deletion
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Ce feedback a été supprimé avec succés");

                    // Show the alert and wait for the user's response
                    alert.showAndWait();

                    // After successful deletion, navigate back to the events page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
                    try {
                        Parent root = loader.load();
                        // Pass any necessary data back to the event page
                        // Get the AfficherEventController
                        AfficherEventController eventController = loader.getController();

                        // Pass the event data to the AfficherEventController
                        eventController.setEventData(feedback.getId_evenment());

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
        });
    }
}
