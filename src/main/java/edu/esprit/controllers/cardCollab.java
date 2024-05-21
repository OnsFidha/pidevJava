package edu.esprit.controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import edu.esprit.entities.Collaboration;
import edu.esprit.service.CollaborationService;
import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class cardCollab {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label compétances;

    @FXML
    private Label cv;

    @FXML
    private Label dispo;

    @FXML
    private Label user;

    @FXML
    private ImageView userimage;
    @FXML
    private VBox competencesContainer;

   // String imagePath = SessionManager.getImage();
    //String nameP= SessionManager.getName()+" "+SessionManager.getPrename();


    @FXML
    void initialize() {
        cv.setOnMouseClicked(event -> {
            // Récupérer le nom du fichier PDF depuis le label
            String fileName = cv.getText();
            if (fileName != null && !fileName.isEmpty()) {
                // Récupérer le chemin complet du fichier PDF
                String sourceFilePath = "C:/Users/HP/Desktop/projetIntegration/pidev/public/cv/" + fileName;
                File sourceFile = new File(sourceFilePath);
                if (sourceFile.exists()) {
                    // Ouvrir une boîte de dialogue pour choisir l'emplacement de téléchargement
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Enregistrer sous");
                    fileChooser.setInitialFileName(fileName);
                    File destinationFile = fileChooser.showSaveDialog(new Stage());
                    if (destinationFile != null) {
                        try {
                            // Copier le fichier depuis son emplacement actuel vers l'emplacement de téléchargement
                            Files.copy(Paths.get(sourceFilePath), destinationFile.toPath());
                            // Ouvrir le fichier PDF après le téléchargement
                            Desktop.getDesktop().open(destinationFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Gérer les erreurs de copie ou d'ouverture du fichier
                        }
                    }
                } else {
                    // Gérer le cas où le fichier source n'existe pas
                }
            }
        });
    }

    public void setData(Collaboration collaborateur) {
        CollaborationService cs=new CollaborationService();
        try {
            String userName = cs.getUserNameByCollaborationId(collaborateur.getId());
            String userImage = cs.getUserImageByCollaborationId(collaborateur.getId());

            if (userName != null) {
                user.setText(userName);
            }

            if (userImage != null) {
                String uImage = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uplaods/" + userImage;

                Image image = new Image(new File(uImage).toURI().toString());
                userimage.setImage(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de récupération des données de l'utilisateur
        }

        String competencesString = collaborateur.getCompetence();
        String[] competences = competencesString.split(" ");

        Color[] colors = {
                Color.web("#BEE5B4"),
                Color.web("#91DAF0"),
                Color.web("#F26A77"),
                Color.web("#F9A936")
        };

        // Effacer le contenu actuel de competencesContainer avant d'ajouter de nouveaux labels
        competencesContainer.getChildren().clear();

        for (int i = 0; i < competences.length; i++) {
            BorderPane competencePane = new BorderPane();
            Label competenceLabel = new Label(competences[i]);
            competenceLabel.setTextFill(Color.WHITE); // Appliquer la couleur correspondante

            competencePane.setCenter(competenceLabel);

            competencePane.setBackground(new Background(new BackgroundFill(colors[i % colors.length],  new CornerRadii(10),  new Insets(2))));

            // Ajuster la largeur du BorderPane en fonction de la longueur du texte de la compétence
            competencePane.setMinWidth(competenceLabel.getWidth()+10 ); // Ajouter une marge supplémentaire

            competencesContainer.getChildren().add(competencePane);
        }


        cv.setText(collaborateur.getCv()); // Remplir le CV du collaborateur dans le label cv
        dispo.setText(collaborateur.getDisponibilite()); // Remplir la disponibilité du collaborateur dans le label dispo
    }


    public void contacter(ActionEvent actionEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir contacter cet utilisateur par e-mail ?");

        confirmationAlert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    String recepient = "onsfidha3@gmail.com"; // Adresse e-mail de l'expéditeur
                    String subject = "test";
                    String emailMessage = "Ceci est un test d'e-mail.";

                    try {
                        EmailManager.sendEmail(recepient, subject, emailMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Une erreur s'est produite lors de l'envoi de l'e-mail.");
                        errorAlert.showAndWait();
                    }
                });
    }
}
