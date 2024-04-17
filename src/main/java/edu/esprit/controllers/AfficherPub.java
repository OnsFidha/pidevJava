package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.esprit.service.CommentaireService;
import javafx.animation.TranslateTransition;
import edu.esprit.entities.Commentaire;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

public class AfficherPub {


        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private AnchorPane comments;

        @FXML
        private Label dateCreationPub;

        @FXML
        private Label dateModificationPub;

        @FXML
        private Label lieuPub;

        @FXML
        private Label textPub;

        @FXML
        private Label typePub;
        private Publication p;
        @FXML
        private GridPane details;
        private HashMap<Integer, TextField> commentaireFields; // HashMap pour stocker les TextField des commentaires
        List<Commentaire> commentaires;
        public void initData(Publication publication) {
            this.p = publication;
        }
        @FXML
        void initialize() {
//        comments.setVisible(false);
            commentaireFields = new HashMap<>(); // Initialisation de la HashMap
        }

        private void configureCarousel() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/comments.fxml"));
            CommentaireService cs = new CommentaireService();
            commentaires = new ArrayList<>();
            try {
                // Obtenez tous les commentaires par ID de publication
                List<Commentaire> allCommentaires = cs.getAll();
                for (Commentaire commentaire : allCommentaires) {
                    if (commentaire.getId_publication() == this.p.getId()) {
                        commentaires.add(commentaire);
                    }
                }

                try {
                    AnchorPane commentsView = loader.load();
                    ListComm controller = loader.getController();
                    VBox container = controller.getContainer();
                    container.getStyleClass().add("comment-container"); // Appliquer le style au conteneur VBox

                    // Pour chaque commentaire, créez des labels pour afficher les détails du commentaire
                    for (Commentaire commentaire : commentaires) {
                        Label userLabel = new Label("Utilisateur: " + "test mr");
                        userLabel.getStyleClass().add("comment-label"); // Appliquer le style au label

                        TextField commentaireField = new TextField(commentaire.getText());
                        commentaireField.setEditable(false);
                        commentaireField.getStyleClass().addAll("comment-textfield", "comment-textfield-centered");                        commentaireFields.put(commentaire.getId(), commentaireField); // Ajout du TextField à la HashMap
                        Button editButton = new Button("Modifier");
                        editButton.setOnAction(event -> {
                            // Vérifiez si le commentaire est actuellement éditable
                            if (!commentaireField.isEditable()) {
                                // Si non, activez l'édition du commentaire et définissez le focus sur le champ de texte
                                commentaireField.setEditable(true);
                                commentaireField.requestFocus();
                            } else {
                                // Si le commentaire est déjà en mode édition, appelez la méthode editComment pour effectuer la modification
                                editComment(commentaire.getId());
                                commentaireField.setEditable(false);
                            }
                        });

                        Button deleteButton = new Button("Supprimer");
                        deleteButton.setOnAction(event -> {
                            try {
                                deleteComment(commentaire.getId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        // Ajoutez les labels au conteneur VBox
                        container.getChildren().addAll(userLabel, commentaireField, editButton, deleteButton);
                    }
                    Button addB = new Button("ajouter");
                    addB.setOnAction(event ->
                            addC(event));
                    container.getChildren().add(addB);
                    // Ajoutez la vue des commentaires à votre interface utilisateur
                    comments.getChildren().add(commentsView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private void deleteComment(int id) throws SQLException {
            CommentaireService cs = new CommentaireService();
            cs.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer votre commentaire. Êtes-vous sûr ?"
                        , "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    alert.setContentText("Votre commentaire a été supprimée avec succès.");
                commentaires.removeIf(commentaire -> commentaire.getId() == id);
                this.configureCarousel();
                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), comments);
                transition.setFromX(-comments.getPrefWidth()); // Déplace le composant depuis l'extérieur de l'écran
                transition.setToX(0); // Déplace le composant vers sa position d'origine
                transition.play();
                comments.setVisible(true);
                details.setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression :\n" + e.getMessage());
            }
        }

        private void editComment(int id) {
            String newText = commentaireFields.get(id).getText(); // Récupérer le nouveau texte du commentaire à partir de la HashMap
            // Appeler la méthode de service pour modifier le commentaire dans la base de données
            CommentaireService cs = new CommentaireService();
            Commentaire commentaire = new Commentaire();
            commentaire.setId(id);
            commentaire.setText(newText);
            try {
                cs.modifier(commentaire);
                // Afficher une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le commentaire a été modifié avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception
            }
        }
    public void setDateCreationPub(Date dateCreationPub) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(dateCreationPub);
        this.dateCreationPub.setText(formattedDate);
    }

    public void setDateModificationPub(Date dateModificationPub) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (dateModificationPub != null) {
            String formattedDate = dateFormat.format(dateModificationPub);
            this.dateModificationPub.setText(formattedDate);
        } else {
            this.dateModificationPub.setText("Pas de modification");
        }
    }

    public void setLieuPub(String lieuPub) {
        this.lieuPub.setText(lieuPub);

    }

    public void setTextPub(String textPub) {
        this.textPub.setText(textPub);

    }

    public void setTypePub(String typePub) {
        this.typePub.setText(typePub);

    }

    @FXML
    void retour(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {
            Parent root = loader.load();
            lieuPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modify(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPub.fxml"));
            Parent root = loader.load();

            // Pass the selected pub to the edit controller
            ModifierPub modifierPub = loader.getController();
            modifierPub.initData(this.p);
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    void delete(ActionEvent event) throws SQLException {

        PublicationService p = new PublicationService();
        p.supprimer(this.p.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer votre Publication. Êtes-vous sûr ?"
                    , "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

                alert.setContentText("Votre Publication a été supprimée avec succès.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            try {
                Parent root = loader.load();
                lieuPub.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression :\n" + e.getMessage());
        }
    }

    @FXML
    private void showComments(ActionEvent event) {
        configureCarousel();
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), comments);
        transition.setFromX(-comments.getPrefWidth()); // Déplace le composant depuis l'extérieur de l'écran
        transition.setToX(0); // Déplace le composant vers sa position d'origine
        transition.play();
        comments.setVisible(true);
        details.setVisible(false);

    }
    @FXML
    private void hideComments(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), comments);
        transition.setFromX(0); // Déplace le composant depuis sa position d'origine
        transition.setToX(-comments.getPrefWidth()); // Déplace le composant vers l'extérieur de l'écran
        details.setVisible(true);
        transition.setOnFinished(e -> comments.setVisible(false)); // Masque le composant une fois la transition terminée
        transition.play();

    }
     @FXML
    void addC(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCom.fxml"));
        try {

            Parent root = loader.load();
            AjouterCom comContr = loader.getController();
            comContr.initData(this.p);
            lieuPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
