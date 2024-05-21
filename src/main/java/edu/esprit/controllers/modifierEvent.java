package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;

public class modifierEvent {
    @FXML
    private TextField NomEvent;

    @FXML
    private TextArea DescEvent;

    @FXML
    private TextField LieuEvent;

    @FXML
    private TextField NbrparticipantsEvent;

    @FXML
    private DatePicker DDEvent;

    @FXML
    private DatePicker DFEvent;
    @FXML
    private ImageView eventimg;


    private Evenement event;
    private Evenement Nevent= new Evenement();

    public void initData(Evenement event) {
        this.event = event;

        // Pre-fill text fields with event details
        NomEvent.setText(event.getNom());
        DescEvent.setText(event.getDescription());
        LieuEvent.setText(event.getLieu());
        NbrparticipantsEvent.setText(Integer.toString(event.getNbreMax()));
        String imageName = event.getImage();
        String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uploads/";
        String imagePath = destinationDirectory + imageName;
        File file = new File(imagePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            eventimg.setImage(image);
        } else {

        }

        // You can pre-fill date pickers similarly
        // For example:
//
        java.util.Date startDate = new java.util.Date(event.getDateDebut().getTime());
        java.util.Date endDate = new java.util.Date(event.getDateFin().getTime());

        DDEvent.setValue(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        DFEvent.setValue(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    }
    @FXML
    void edit(ActionEvent Aevent) {
        int eventId = event.getId();
        // Vérifiez que tous les champs sont remplis
        if (NomEvent.getText().isEmpty() || DescEvent.getText().isEmpty() || LieuEvent.getText().isEmpty() ||
                DDEvent.getValue() == null || DFEvent.getValue() == null || NbrparticipantsEvent.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        // Vérifiez que la longueur de la description ne dépasse pas 255 caractères
        if (DescEvent.getText().length() > 255) {
            showAlert("La description ne peut pas dépasser 255 caractères.");
            return;
        }

        // Vérifiez que NbrparticipantsEvent contient uniquement des chiffres
        if (!isNumeric(NbrparticipantsEvent.getText())) {
            showAlert("Le champ Nombre de participants ne peut contenir que des chiffres.");
            return;
        }

        // Vérifiez que la date de début est avant la date de fin
        if (DDEvent.getValue().isAfter(DFEvent.getValue())) {
            showAlert("La date de début doit être avant la date de fin.");
            return;
        }
        // Check if the image has been changed
        String oldImagePath = event.getImage(); // Get the current image path
        Image currentImage = eventimg.getImage(); // Get the current image displayed in the ImageView
        String newImagePath = null; // Initialize variable to store the new image path
        if (currentImage != null) {
            // Get the path of the new image
            newImagePath = Nevent.getImage();
            // If newImagePath is null, it means a new image has not been selected, so we keep the old image path
            if (newImagePath == null) {
                newImagePath = oldImagePath;
            }
        } else {
            // Handle the case where the current image is null
            // This might happen if the user previously deleted the image or if there was an error loading the image
            // In this case, we keep the old image path
            newImagePath = oldImagePath;
        }

        Evenement ev=new Evenement(eventId,NomEvent.getText(),DescEvent.getText(),LieuEvent.getText(), Date.valueOf(DDEvent.getValue()),Date.valueOf(DFEvent.getValue()),0,Integer.parseInt(NbrparticipantsEvent.getText()),newImagePath, SessionManager.getId_user());
        EvenementService es=new EvenementService();
        try {
            es.modifier(ev);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'évenement a été modifié avec succée");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
            try {
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((Node) Aevent.getSource()).getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
            //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
//            try {
//                Parent root = loader.load();
//
//                // Get the current stage
//                Stage stage = (Stage) ((Node) Aevent.getSource()).getScene().getWindow();
//
//                // Set the new scene
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
////
//            catch (IOException e) {
//                throw new RuntimeException(e);
//            }
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

    // Utility method to check if a string contains only numeric characters
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    @FXML
    public void choose_file(ActionEvent Aevent) {
        String fileName;
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("Tous les fichiers", "."));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
              String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uploads/";
            //    String destinationDirectory = "C:/Users/21655/OneDrive/Desktop/pidev/public/uploads/";


                // Générer un nom de fichier unique
                fileName = "photo_" + System.currentTimeMillis() + getFileExtension(selectedFile.getName());

                try {
                    // Copy the selected file to the destination directory
                    Path destinationPath = new File(destinationDirectory + fileName).toPath();
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the relative path of the selected image in the event object
                    String relativeImagePath = fileName;
                    Nevent.setImage(relativeImagePath); // Update the Event object with the relative image path

                    // Update the image of the ImageView
                    String photoPath = destinationPath.toUri().toString();
                    Image image = new Image(photoPath);
                    eventimg.setImage(image); // Update the ImageView with the new image

                    // Optionally, you can store the absolute path of the selected image
                    String absoluteImagePath = destinationPath.toString();
                    // Use the absoluteImagePath variable as needed

                } catch (IOException e) {
                    // Handle the exception gracefully
                    e.printStackTrace(); // You might want to log this instead
                    // Show an error message to the user
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("File Copy Error");
                    alert.setContentText("An error occurred while copying the file. Please try again.");
                    alert.showAndWait();
                }
            } else {
                System.out.println("Aucun fichier sélectionné.");
            }
        }
    }
    // Utility method to get file extension
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }

    @FXML
    void goBack(MouseEvent mevent) {
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
            Parent root = loader.load();
            // Get the AfficherEventController
            AfficherEventController eventController = loader.getController();

            // Pass the event data to the AfficherEventController
            eventController.setEventData(this.event.getId());

            // Get the current stage
            Stage stage = (Stage) ((Node) mevent.getSource()).getScene().getWindow();

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
