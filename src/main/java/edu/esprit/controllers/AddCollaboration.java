package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import edu.esprit.entities.Collaboration;
import edu.esprit.entities.Publication;
import edu.esprit.service.CollaborationService;
import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class AddCollaboration {
    @FXML
    private Label logedUsernamee;
    @FXML
    private ResourceBundle resources;

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    private TextField competence;

    @FXML
    private Label competenceError;

    @FXML
    private TextField cv;

    @FXML
    private Label dispoError;

    @FXML
    private TextField disponibilite;

    @FXML
    private Label fichierError;
    private Publication p;
    int id= SessionManager.getId_user() ;
    @FXML
    void initialize() {

        cv.setEditable(false);
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));

    }

    public void choose_file(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("fichier",  "*.pdf"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/cv/";

            // Générer un nom de fichier unique
            String fileName = "CV_" + System.currentTimeMillis() + getFileExtension(selectedFile.getName());

            try {
                // Copier le fichier sélectionné dans le répertoire de destination
                Path destinationPath = new File(destinationDirectory + fileName).toPath();
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Mettre à jour le chemin de la photo dans votre modèle
                String photoPath = destinationPath.toUri().toString();
                cv.setText(fileName);


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
    public void initData(Publication p) {
        this.p = p;
    }

    @FXML
    void addcolab(ActionEvent event) {
        boolean isValid = true;
        if (disponibilite.getText().isEmpty()) {
            dispoError.setText("Veuillez entrer votre disponibilité.");
            isValid = false;
        } else {
            dispoError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }

        // Vérification du champ Lieu de publication
        if (competence.getText().isEmpty()) {
            competenceError.setText("Veuillez entrer vos compétances .");
            isValid = false;
        } else {
            competenceError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }
        if (cv.getText().isEmpty()) {
            fichierError.setText("Veuillez entrer votre CV .");
            isValid = false;
        } else {
            fichierError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }
        if (isValid) {
        Collaboration c = new Collaboration(disponibilite.getText(), competence.getText(), cv.getText(),p.getId(),id);
        CollaborationService cs = new CollaborationService();
        try {
            cs.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("votre collaboration a été ajoutée avec succès");
            alert.show();

            // Rediriger vers la page de liste des publications
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            Parent root = loader.load();
            cv.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Une erreur s'est produite lors de l'ajout de la collaboration : " + e.getMessage());
            errorAlert.show();
        }}
    }
    }

