package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
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
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Label username;
    private Evenement event;
    @FXML
    void initialize() {

    }
    public void setData(Evenement event){
//            Image image = new Image(getClass().getResourceAsStream(event.getImage()));
////            Image image = new Image("/img/Event5.png");
//            EventImage.setImage(image);
        if (event.getImage() != null) {
            Image image = new Image(event.getImage());
            eventimg.setImage(image);
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
    public void setEventData(int id) {
        EvenementService eventService = new EvenementService();
        try {
        Evenement event = eventService.getOneById(id);
//            Image image = new Image(getClass().getResourceAsStream(event.getImage()));
////            Image image = new Image("/img/Event5.png");
//            EventImage.setImage(image);
        if (event.getImage() != null) {
            Image image = new Image(event.getImage());
            eventimg.setImage(image);
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
    void onDeleteClicked(MouseEvent Mevent) {
        // Retrieve the id of the event to be deleted
        int eventId = event.getId(); // Replace this with how you retrieve the event id

        // Call the supprimer function in EvenementService
        EvenementService es = new EvenementService();
        try {
            es.supprimer(eventId);

            // Show an alert message indicating successful deletion
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("L'événement a été supprimé avec succès.");

            // Show the alert and wait for the user's response
            alert.showAndWait();

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
