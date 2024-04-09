package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class AjouterPub {
    private URL location;

    @FXML
    private TextField TextPub;

    @FXML
    private Circle circle;

    @FXML
    private TextField lieuPub;

    @FXML
    private TextField photoPub;

    @FXML
    private TextField typePub;


    @FXML
    void addPub(ActionEvent event) {
        Publication p=new Publication(typePub.getText(),TextPub.getText(),lieuPub.getText(),2,photoPub.getText());
        PublicationService ps=new PublicationService();
        try {
            ps.ajouter(p);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La publication a été ajouté avec succée");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherPub.fxml"));
            try {
                Parent root=loader.load();
                AfficherPub pub= loader.getController();
                pub.setTypePub(typePub.getText());
                pub.setTextPub(TextPub.getText());
                pub.setLieuPub(lieuPub.getText());
                pub.setDateCreationPub(new Date());
                pub.setDateModificationPub(new Date());
                lieuPub.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(e.getMessage());
            alert1.show();
        }

    }
    public void choose_file(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/pub/";

            // Générer un nom de fichier unique
            String fileName = "photo_" + System.currentTimeMillis() + getFileExtension(selectedFile.getName());

            try {
                // Copier le fichier sélectionné dans le répertoire de destination
                Path destinationPath = new File(destinationDirectory + fileName).toPath();
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Mettre à jour le chemin de la photo dans votre modèle
                String photoPath = destinationPath.toUri().toString();
                photoPub.setText(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
    @FXML
    void initialize() {

    }

}
