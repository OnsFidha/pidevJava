package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
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

public class Ajoutereventadmin {
    @FXML
    private DatePicker DDEvent;

    @FXML
    private DatePicker DFEvent;

    @FXML
    private TextArea DescEvent;

    @FXML
    private TextField LieuEvent;

    @FXML
    private TextField NbrparticipantsEvent;

    @FXML
    private TextField NomEvent;

    @FXML
    private ImageView eventimg;
    private Evenement event= new Evenement();

    @FXML
    void Add(ActionEvent Aevent) {
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

        Evenement ev=new Evenement(NomEvent.getText(),DescEvent.getText(),LieuEvent.getText(), Date.valueOf(DDEvent.getValue()),Date.valueOf(DFEvent.getValue()),0,Integer.parseInt(NbrparticipantsEvent.getText()),event.getImage());
        EvenementService es=new EvenementService();
        try {
            es.ajouter(ev);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'évenement a été ajouté avec succée");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
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
        } catch (SQLException e) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(e.getMessage());
            alert1.show();
        }


    }
    // Utility method to show an alert
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
                    new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                String destinationDirectory = "C:/Users/21655/OneDrive/Desktop/pidevJava/src/main/resources/img/";

                // Générer un nom de fichier unique
                fileName = "photo_" + System.currentTimeMillis() + getFileExtension(selectedFile.getName());

                try {
                    // Copy the selected file to the destination directory
                    Path destinationPath = new File(destinationDirectory + fileName).toPath();
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the relative path of the selected image in the event object
                    String relativeImagePath = "C:/Users/21655/OneDrive/Desktop/pidevJava/src/main/resources/img/" + fileName;
                    event.setImage(relativeImagePath); // Update the Event object with the relative image path

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
    void goBack(MouseEvent event) {
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
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
    void initialize() {

    }
}
