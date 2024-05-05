package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Utilisateur;
import edu.esprit.service.EvenementService;
import edu.esprit.services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminEventController {
    @FXML
    private Label datedeb;

    @FXML
    private Label datefin;

    @FXML
    private Label eventdesc;

    @FXML
    private Label eventname;

    @FXML
    private Label lieu;

    @FXML
    private Label nbr;

    @FXML
    private ImageView userimg;

    @FXML
    private Label username;
    @FXML
    private ImageView eventimg;

    private Evenement event;

    ServiceUtilisateur serviceuser = new ServiceUtilisateur();


    public void setData(Evenement event){
        Utilisateur userevent = serviceuser.getUtilisateurById(event.getId_user_id());


        if (event.getImage() != null) {
            String imagePath = "file:///" + event.getImage();
            Image image = new Image(imagePath);
            eventimg.setImage(image);
        } else {
            // Handle the case where image path is null
            // For example, set a default image or display an error message
            System.err.println("Image path is null for the event: " + event.getNom());
        }

//            image= new Image(getClass().getResourceAsStream(event.getProfileImageSrc()));
        String imagePath = userevent.getImage();

        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        userimg.setImage(image);

        // Set username and event details
        username.setText(userevent.getPrename()+" "+ userevent.getName());
        eventname.setText(event.getNom());
        eventdesc.setText(event.getDescription());
        datedeb.setText(event.getDateDebut().toString());
        datefin.setText(event.getDateFin().toString());
        lieu.setText(event.getLieu());
        nbr.setText(event.getNbreMax().toString());
        this.event = event;
//
    }

    public void setEventData(int id) {
        Utilisateur userevent = serviceuser.getUtilisateurById(event.getId_user_id());

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
            String imagePath = userevent.getImage();

            int img = imagePath.lastIndexOf("\\");
            String nomFichier = imagePath.substring(img + 1);
            Image image = new Image("assets/uploads/"+nomFichier);
            userimg.setImage(image);

            // Set username and event details
            username.setText(userevent.getPrename()+" "+ userevent.getName());
            eventname.setText(event.getNom());
            eventdesc.setText(event.getDescription());
            lieu.setText(event.getLieu());
            datedeb.setText(event.getDateDebut().toString());
            datefin.setText(event.getDateFin().toString());

            nbr.setText(Integer.toString(event.getNbreMax()));
            this.event = event;
        }

        catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // or any other appropriate handling
        }


    }


    @FXML
    void delete(MouseEvent Mevent) {
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
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
    void edit(MouseEvent Mouseevent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiereventadmin.fxml"));
            Parent root = loader.load();

            // Pass the selected event to the edit controller
            modifiereventadmin editController = loader.getController();
            editController.initData(this.event);

            // Get the current stage
            Stage stage = (Stage) ((Node) Mouseevent.getSource()).getScene().getWindow();

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
    void goFeed(MouseEvent Mevent) {
        int eventId = event.getId();
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lisfeedadmin.fxml"));
            Parent root = loader.load();

            // Get the ListFeedbacks controller
            listfeedbackadmin controller = loader.getController();

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

}
