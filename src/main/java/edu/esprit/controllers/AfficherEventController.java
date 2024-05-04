package edu.esprit.controllers;

import com.dark.programs.speech.translator.GoogleTranslate;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Feedback;
import edu.esprit.entities.Participation;
import edu.esprit.service.EvenementService;
import edu.esprit.service.FeedbackService;
import edu.esprit.service.ParticipationService;
import edu.esprit.utils.JavaMailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ButtonType;


public class AfficherEventController {
    @FXML
    private Label DateD;

    @FXML
    private Label dateF;

    @FXML
    private Label eventdesc;

    @FXML
    private ImageView eventimg;

    @FXML
    private Label eventname;

    @FXML
    private Label lieu;

    @FXML
    private Label nbrmax;

    @FXML
    private ImageView userimg;
    @FXML
    private GridPane feedbackgrid;

    @FXML
    private Label username;
    private Evenement event;
    private List<Feedback> feedbacks;
    private FeedbackService feedbackService = new FeedbackService();

    @FXML
    void initialize() {

    }
    public void setData(Evenement event){
        if (event.getImage() != null) {
            try {
                // Prepend "file:///" to the file path
                String imagePath = "file:///" + event.getImage();
                Image image = new Image(imagePath);
                eventimg.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to load image for the event: " + event.getNom());
            }
        } else {
            // Handle the case where image path is null
            // For example, set a default image or display an error message
            System.err.println("Image path is null for the event: " + event.getNom());
        }


//            image= new Image(getClass().getResourceAsStream(event.getProfileImageSrc()));
        Image nimage = new Image("/img/user2.png");
        userimg.setImage(nimage);

//            username.setText(event.getUsername());
        username.setText("Syrine Zaier");
        eventname.setText(event.getNom());
        eventdesc.setText(event.getDescription());
        lieu.setText(event.getLieu());

        DateD.setText(event.getDateDebut().toString());
        dateF.setText(event.getDateFin().toString());

        nbrmax.setText(Integer.toString(event.getNbreMax()));
        this.event = event;

        FeedbackService feedbackService = new FeedbackService();

        try {
            feedbacks = feedbackService.getAllById(event.getId());


            // Clear existing feedbacks from the grid pane
            feedbackgrid.getChildren().clear();

            int columns = 0;
            int rows = 1;

            for (Feedback feedback : feedbacks) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FeedbackCard.fxml"));
                VBox feedbackbox = fxmlLoader.load();

                FeedbackController feedbackController = fxmlLoader.getController();
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
    void AddFb(MouseEvent event) {
        try {
            // Load the event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddFeedback.fxml"));
            Parent root = loader.load();
            // Get the ListFeedbacks controller
            AddFeedback controller = loader.getController();

            // Set the event ID
            controller.setEventId(this.event.getId());

//            // Pass any necessary data back to the event page
//            AfficherEventController eventPageController = loader.getController();
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

//    public void setEventId(int eventId) {
//        // Use the event ID to retrieve the corresponding feedbacks
//        FeedbackService feedbackService = new FeedbackService();
//
//        try {
//            feedbacks = feedbackService.getAllById(eventId);
//
//
//            // Clear existing feedbacks from the grid pane
//            feedbackgrid.getChildren().clear();
//
//            int columns = 0;
//            int rows = 1;
//
//            for (Feedback feedback : feedbacks) {
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
//    }


    public void setEventData(int id) {
        EvenementService eventService = new EvenementService();
        try {
        Evenement event = eventService.getOneById(id);
//            Image image = new Image(getClass().getResourceAsStream(event.getImage()));
////            Image image = new Image("/img/Event5.png");
//            EventImage.setImage(image);
            if (event != null) {
                setData(event);
            }
            if (event.getImage() != null) {
                try {
                    // Prepend "file:///" to the file path
                    String imagePath = "file:///" + event.getImage();
                    Image image = new Image(imagePath);
                    eventimg.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to load image for the event: " + event.getNom());
                }
            } else {
                // Handle the case where image path is null
                // For example, set a default image or display an error message
                System.err.println("Image path is null for the event: " + event.getNom());
            }

//            image= new Image(getClass().getResourceAsStream(event.getProfileImageSrc()));
        Image image = new Image("/img/user2.png");
        userimg.setImage(image);

//            username.setText(event.getUsername());
        username.setText("Syrine Zaier");
        eventname.setText(event.getNom());
        eventdesc.setText(event.getDescription());
        lieu.setText(event.getLieu());

        DateD.setText(event.getDateDebut().toString());
        dateF.setText(event.getDateFin().toString());

        nbrmax.setText(Integer.toString(event.getNbreMax()));
            this.event = event;
        }

        catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // or any other appropriate handling
        }


    }
    @FXML
    private void handleEditClicked(MouseEvent Mouseevent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierEvent.fxml"));
            Parent root = loader.load();

            // Pass the selected event to the edit controller
            modifierEvent editController = loader.getController();
            editController.initData(this.event);

            // Get the current stage
            Stage stage = (Stage) ((Node) Mouseevent.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

//            // Show the edit page in a new window
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }
    }
    @FXML
    void goBack(MouseEvent event) {
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
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
    @FXML
    void participer(ActionEvent Aevent) {
        // Check if the current number of participants is less than the maximum
        if (event.getNbreParticipants() < event.getNbreMax()) {
            // Increment the number of participants
            event.setNbreParticipants(event.getNbreParticipants() + 1);
            // Update the event in the database
            EvenementService evenementService = new EvenementService();
            try {


                // Add participation to the database
                ParticipationService participationService = new ParticipationService();
                // Assuming you have the user's ID stored in a variable called userId
                int userId = 2; // Replace 1 with the actual user ID
                if (!participationService.hasParticipated(userId, event.getId())) {
                    evenementService.modifier(event);
                    // Display success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous avez participé à l'événement avec succès.");
                    alert.showAndWait();
                Participation participation = new Participation(event.getId(), userId);
                participationService.ajouter(participation);

                // Send email to the user
                sendParticipationEmail("syrinezaier283@gmail.com", event.getNom(),event);}
                else {
                    // User has already participated, display error message
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Vous avez déjà participé à cet événement.");
                    alert2.showAndWait();
                }


            } catch (SQLException e) {
                // Handle database update error
                e.printStackTrace();
            }
        } else {
            // Display error message when max participants reached
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre maximum de participants est déjà atteint.");
            alert.showAndWait();
        }

    }

    private void sendParticipationEmail(String recipientEmail, String eventName, Evenement event) {
        try {
            JavaMailUtil.sendEventEmail(recipientEmail, eventName, event);
        } catch (Exception e) {
            // Handle email sending error
            e.printStackTrace();
        }
    }

    @FXML
    void Translate(MouseEvent event) throws IOException {
        String Newfeedtext = GoogleTranslate.translate("fr", eventdesc.getText());;
        eventdesc.setText(Newfeedtext);

    }
    @FXML
    void onDeleteClicked(MouseEvent Mevent) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmation");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet évènement ?");

        // Add "Yes" and "No" buttons to the dialog
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = confirmDialog.showAndWait();

        // If the user clicks "Yes", delete the event
        if (result.isPresent() && result.get() == yesButton) {
            // Retrieve the id of the event to be deleted
            int eventId = event.getId(); // Replace this with how you retrieve the event id

            EvenementService es = new EvenementService();
            try {
                es.supprimer(eventId);

                // Show a success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'événement a été supprimé avec succès.");
                successAlert.showAndWait();

                // After successful deletion, navigate back to the events page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
                try {
                    Parent root = loader.load();

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
    @FXML
    void ConsulterFeedbacks(MouseEvent Mevent) {
        int eventId = event.getId();
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListFeedbacks.fxml"));
            Parent root = loader.load();

            // Get the ListFeedbacks controller
            ListFeedbacks controller = loader.getController();

            // Set the event ID
            controller.setEventId(eventId);

            // Get the current stage
            Stage stage = (Stage) ((Node) Mevent.getSource()).getScene().getWindow();

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





//    public void setDateD(Date DateD) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String formattedDate = dateFormat.format(DateD);
//        this.DateD.setText(formattedDate);
//    }
//
//    public void setDateF(Date dateF) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String formattedDate = dateFormat.format(dateF);
//        this.dateF.setText(formattedDate);
//    }
//
//    public void setLieu(String lieu) {
//        this.lieu.setText(lieu);
//    }
//    public void setNom(String eventname) {
//        this.eventname.setText(eventname);
//    }
//    public void setDescription(String eventdesc) {
//        this.eventdesc.setText(eventdesc);
//    }
//    public void setNbr(String nbrmax) {
//        this.nbrmax.setText(nbrmax);
//    }
//    public void setUsername(String username) {
//        this.username.setText(username);
//    }


}
