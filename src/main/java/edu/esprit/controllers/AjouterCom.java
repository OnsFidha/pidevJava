package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonParser;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.service.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;

public class AjouterCom {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label textError;

    @FXML
    private Circle circle;

    @FXML
    private TextArea text;
    private Publication p;

    @FXML
    void addCom(ActionEvent event) {
        String commentaireText = text.getText();
        boolean isValid = true;

        // Validation du commentaire
        if (commentaireText.isEmpty()) {
            textError.setText("Veuillez entrer votre commentaire.");
            isValid = false;
        } else if (commentaireText.length() < 5 || commentaireText.length() > 800) {
            textError.setText("Le commentaire doit contenir entre 5 et 800 caractères.");
            isValid = false;
        } else {
            textError.setText(""); // Effacez les erreurs précédentes si le texte est valide
        }
        if (isValid) {
            // Vérifiez d'abord s'il y a des mots interdits dans le commentaire
            try {
                String filteredText = BadWordsApi.filterBadWords(commentaireText);
                if (!filteredText.equals(commentaireText)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Le commentaire contient des mots interdits et a été filtré.");
                    alert.show();
                    commentaireText = filteredText; // Remplacez le texte par le texte filtré
                }

                Commentaire commentaire = new Commentaire();
                commentaire.setId_publication(this.p.getId());
                commentaire.setText(commentaireText);

                CommentaireService commentaireService = new CommentaireService();
                try {
                    commentaireService.ajouter(commentaire);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Le commentaire a été ajouté avec succès");
                    alert.show();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
                    Parent root = loader.load();
                    text.getScene().setRoot(root);
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                // Gérer les erreurs liées à l'appel de l'API
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la vérification des mots interdits : " + e.getMessage());
                alert.show();
            }
        }
    }

    public void initData(Publication p) {
        this.p = p;
    }

    @FXML
    void initialize() {
    }
}
