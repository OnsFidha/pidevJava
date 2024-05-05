package edu.esprit.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import edu.esprit.utils.SessionManager;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.ImagePattern;
import javafx.scene.web.WebView;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import netscape.javascript.JSObject;

public class ModifierPub {
    @FXML
    private Label logedUsernamee;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField TextPub;

    @FXML
    private Circle circle;

    @FXML
    private Label descError;

    @FXML
    private ImageView imagePub;

    @FXML
    private Label lieuError;

    @FXML
    private TextField lieuPub;

    @FXML
    private Label photoError;

    @FXML
    private TextField photoPub;

    @FXML
    private Label typeError;

    @FXML
    private ComboBox typePub;
    @FXML
    private WebView webView;
    private Publication p;
    boolean isValid;

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    void ModifyPub(ActionEvent event) {
        isValid = true; // Variable pour suivre l'état de la validation
        String selectedType = (String) typePub.getValue();
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
        } else {
            if (TextPub.getText().length() < 5 || TextPub.getText().length() > 800) {
                descError.setText("La description doit contenir entre 5 et 800 caractères.");
                isValid = false;
            } else {
                descError.setText(""); // Effacer le message d'erreur s'il y en avait un
            }
        }

        // Si tous les champs sont remplis et valides, modifier la publication
        if (isValid) {
            try {
                // Créer une nouvelle instance de Publication avec les données mises à jour
                Publication updatedPublication;
                if (photoPub.getText().isEmpty()) {
                    // Si aucun nouveau fichier n'a été sélectionné, conserver l'ancienne valeur de la photo
                    updatedPublication = new Publication(this.p.getId(), selectedType, TextPub.getText(), lieuPub.getText(), this.p.getPhoto());
                } else {
                    updatedPublication = new Publication(this.p.getId(), selectedType, TextPub.getText(), lieuPub.getText(), photoPub.getText());
                }

                // Mettre à jour la publication dans la base de données
                PublicationService ps = new PublicationService();
                ps.modifier(updatedPublication);

                // Afficher une alerte pour indiquer que la modification a réussi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La publication a été modifiée avec succès");
                alert.show();

                // Rediriger vers la liste des publications
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
                Parent root = loader.load();
                lieuPub.getScene().setRoot(root);
            } catch (SQLException | IOException e) {
                // Gérer les exceptions en affichant une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la modification de la publication : " + e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    void choose_file(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
//            if (!photoPub.getText().matches(".+\\.(jpeg|jpg|png|gif)$")) {
//                photoError.setText("Veuillez sélectionner une image de type JPEG, PNG ou GIF.");
//                 isValid = false;
//            } else {
//                photoError.setText(""); // Effacer le message d'erreur s'il y en avait un
//            }
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
            // Traitez le nom de la ville et du pays dans JavaFX
            lieuPub.setText(city + ", " + country);
        }


    }

    public void initData(Publication p) {
        this.p = p;

        // Pre-fill text fields with event details
        lieuPub.setText(p.getLieu());
        typePub.setValue(p.getType());
        String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/pub/";
        String imagePath = destinationDirectory + p.getPhoto();
        File file = new File(imagePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imagePub.setImage(image);
        } else {

        }
        //photoPub.setText(p.getPhoto());
        TextPub.setText(p.getText());
    }

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
}
