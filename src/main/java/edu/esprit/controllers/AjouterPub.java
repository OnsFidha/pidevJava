package edu.esprit.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import edu.esprit.utils.SessionManager;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class AjouterPub {
    private URL location;

    @FXML
    private TextField TextPub;

    @FXML
    private Circle circle;

    @FXML
    private Label descError;

    @FXML
    private Label lieuError;

    @FXML
    private TextField lieuPub;

    @FXML
    private Label photoError;

    @FXML
    private TextField photoPub;
    @FXML
    private Label logedUsernamee;
    @FXML
    private Label typeError;

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    private ImageView imagePub;

    @FXML
    private ComboBox typePub;
    @FXML
    private WebView webView;
    int id= SessionManager.getId_user() ;
    @FXML
    void retour() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {
            Parent root = loader.load();
            lieuPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void addPub(ActionEvent event) throws SQLException {
        boolean isValid = true; // Variable pour suivre l'état de la validation
        String selectedType = (String) typePub.getValue(); // Récupérer la valeur sélectionnée dans la ComboBox

        // Vérification du champ Type de publication
        if (selectedType==null) {
            typeError.setText("Veuillez entrer un type de publication.");
            isValid = false;
        } else {
            typeError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }

        // Vérification du champ Lieu de publication
        if (lieuPub.getText().isEmpty()) {
            lieuError.setText("Veuillez entrer un lieu de publication.");
            isValid = false;
        } else {
            lieuError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }

        // Vérification du champ Description
        if (TextPub.getText().isEmpty()) {
            descError.setText("Veuillez entrer une description.");
            isValid = false;
        }
        else {
            if (TextPub.getText().length() < 5 || TextPub.getText().length() > 800) {
                descError.setText("La description doit contenir entre 5 et 800\n caractères.");
            } else {
                descError.setText(""); // Effacer le message d'erreur s'il y en avait un}
            }
        }
        // Vérification du champ Photo
        if (photoPub.getText().isEmpty()) {
            photoError.setText("Veuillez entrer une photo.");
            isValid = false;
        } else {
            if ( photoPub.getText().matches(".+\\.(jpeg|jpg|png|gif)$")) {
                photoError.setText("Veuillez sélectionner une image\n de type JPEG, PNG ou GIF.");}
            else{
            photoError.setText(""); // Effacer le message d'erreur s'il y en avait un
        }}

        // Si tous les champs sont remplis, ajouter la publication
        if (isValid) {
            Publication p = new Publication(selectedType, TextPub.getText(), lieuPub.getText(), 2, photoPub.getText(),id);
            PublicationService ps = new PublicationService();
            try {
                ps.ajouter(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La publication a été ajoutée avec succès");
                alert.show();

                // Rediriger vers la page de liste des publications
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
                Parent root = loader.load();
                lieuPub.getScene().setRoot(root);
            } catch (SQLException | IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de l'ajout de la publication : " + e.getMessage());
                errorAlert.show();
            }
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

                // Charger l'image dans l'ImageView
                Image image = new Image(new FileInputStream(selectedFile));
                imagePub.setImage(image);
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
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));

        photoPub.setEditable(false);
        WebEngine engine = webView.getEngine();
            engine.load(getClass().getResource("/map.html").toExternalForm());
            // Permet à JavaScript d'appeler la méthode processCoordinates() dans le contrôleur
            engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    JSObject window = (JSObject) engine.executeScript("window");
                    window.setMember("javafxHandler", new JavaFXHandler());
                }
            });

    }
    public class JavaFXHandler {
        public void processCoordinates(double lat, double lng) {
            // Traitez les coordonnées dans JavaFX
            System.out.println("Latitude: " + lat + ", Longitude: " + lng);
        }
        public void processLocation(String city, String country) {
            System.out.println("c: " + city + ", cou: " + country);
            lieuPub.setText(city + ", " + country);
        }
    }

}
