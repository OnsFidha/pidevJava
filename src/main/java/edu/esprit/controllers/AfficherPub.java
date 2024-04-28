package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import edu.esprit.service.CommentaireService;
import javafx.animation.TranslateTransition;
import edu.esprit.entities.Commentaire;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.HBox;
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
        private ImageView imagePub;

        @FXML
        private Label typePub;
        private Label errortext;
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
        @FXML
        private void configureCarousel() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/comments.fxml"));
            CommentaireService cs = new CommentaireService();
            commentaires = new ArrayList<>();
            commentaireFields = new HashMap<>(); // Initialisation de la HashMap

            try {
                // Obtenez tous les commentaires par ID de publication
                List<Commentaire> allCommentaires = cs.getAll();
                for (Commentaire commentaire : allCommentaires) {
                    if (commentaire.getId_publication() == this.p.getId()) {
                        commentaires.add(commentaire);
                    }
                }

                try {
                    AnchorPane commentsView;
                    ListComm controller;

                    commentsView = loader.load();
                    controller = loader.getController();
                    VBox container = controller.getContainer();
                    container.getStyleClass().add("comment-container"); // Appliquer le style au conteneur VBox
                    commentsView.getStyleClass().add("comment-container");
                    commentsView.setPrefHeight(480);
                    container.setPrefHeight(480);

                    if (commentaires.isEmpty()) {
                        // S'il n'y a pas de commentaires, affichez un message indiquant l'absence de commentaires
                        Label noCommentLabel = new Label("Il n'y a pas de commentaires pour cette publication.");
                        noCommentLabel.getStyleClass().addAll("no-comment-label", "large-text", "grey-text", "centered-text");

                        container.getChildren().add(noCommentLabel);
                    } else {
                        // Pour chaque commentaire, créez des labels pour afficher les détails du commentaire
                        for (Commentaire commentaire : commentaires) {
                            commentsView.getStyleClass().add("comment-container");
                            HBox commentBox = new HBox(); // Utilisation de HBox pour chaque commentaire
                            commentBox.setSpacing(20); // Définir l'espacement entre les éléments
                            commentBox.getStyleClass().add("comment-card"); // Appliquer le style à la boîte de commentaire

                            VBox commentContent = new VBox(); // Créer un VBox pour contenir le texte du commentaire et le nom d'utilisateur
                            commentContent.setAlignment(Pos.TOP_LEFT); // Aligner les éléments en haut à gauche

                            Label userLabel = new Label("Utilisateur: " + "test mr");
                            userLabel.getStyleClass().addAll("user-label", "small-text", "grey-text"); // Appliquer le style au label de l'utilisateur
                             errortext = new Label();
                             errortext.getStyleClass().addAll("error","comment-textfield");
                            TextField commentaireField = new TextField(commentaire.getText());
                            commentaireField.setEditable(false);
                            commentaireField.getStyleClass().addAll("comment-textfield", "comment-textfield-centered");
                            commentaireField.getStyleClass().addAll("comment-text", "small-text", "grey-text"); // Appliquer le style au champ de texte
                            commentaireFields.put(commentaire.getId(), commentaireField); // Ajouter le champ de texte à la HashMap
                            commentContent.getChildren().addAll(commentaireField, errortext,userLabel); // Ajouter d'abord le texte du commentaire puis le nom d'utilisateur

                            HBox buttonBox = new HBox(); // Créer une boîte horizontale pour les boutons
                            buttonBox.setAlignment(Pos.CENTER_RIGHT); // Aligner les boutons à droite
                            buttonBox.setSpacing(10); // Définir l'espacement entre les boutons
                            Button editButton = createIconButton("/icons/edit.png", () -> {
                                if (!commentaireField.isEditable()) {
                                    // Si le commentaire n'est pas en mode édition, activez l'édition et définissez le focus sur le champ de texte
                                    commentaireField.setEditable(true);
                                    commentaireField.requestFocus();
                                } else {
                                    // Si le commentaire est en mode édition, effectuez la modification
                                    try {
                                        editComment(commentaire.getId());
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    // Vérifiez si la modification a réussi avant d'afficher l'alerte
                                }
                            });
                            Button deleteButton = createIconButton("/icons/delete.png", () -> {
                                deleteComment(commentaire.getId());
                            });
                            editButton.getStyleClass().addAll("btn-icone", "small-button");
                            deleteButton.getStyleClass().addAll("btn-icone", "small-button");

                            buttonBox.getChildren().addAll(editButton, deleteButton); // Ajouter les boutons à la boîte horizontale
                            commentBox.getChildren().addAll(commentContent, buttonBox);
                            container.getChildren().add(commentBox);
                        }
                    }

                    // Ajouter les boutons de retour et d'ajout à la vue des commentaires
                    Button addButton = createIconButton("/icons/addC.png", () -> addC());
                    addButton.getStyleClass().addAll("btn-icone", "small-button", "custom-add-button");
                    Button retourBtn = createIconButton("/icons/previous.png", () -> retour());
                    retourBtn.getStyleClass().addAll("btn-icone", "small-button", "custom-add-button");
                    HBox btn = new HBox(); // Créer une boîte horizontale pour les boutons
                    btn.setSpacing(10);
                    btn.getChildren().addAll(addButton, retourBtn);
                    btn.setAlignment(Pos.CENTER);
                    container.getChildren().add(btn);

                    // Ajouter la vue des commentaires à votre interface utilisateur
                    comments.getChildren().add(commentsView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private Button createIconButton(String iconUrl, Runnable action) {
                        // Création du bouton
                        Button button = new Button();Image icon = new Image(iconUrl);
                    ImageView imageView = new ImageView(icon);
                    imageView.setFitWidth(35); // Définir la largeur souhaitée de l'image
                    imageView.setFitHeight(35); // Définir la hauteur souhaitée de l'image

        // Configuration du bouton avec l'icône
                    button.setGraphic(imageView);
                    button.setOnAction(event -> action.run());
                    button.getStyleClass().add("small-button");

                        return button;
                    }

        private void editComment(int id) throws SQLException {
            TextField commentaireField = commentaireFields.get(id);

            // Réinitialiser le message d'erreur à chaque fois que vous modifiez le commentaire
            errortext.setText("");

            boolean isValid = true;

            // Vérifier si le commentaire est vide
            if (commentaireField.getText().isEmpty()) {
                errortext.setText("Le commentaire est obligatoire");
                isValid = false;
            }

            // Vérifier si le commentaire contient moins de 5 caractères
            else if (commentaireField.getText().length() < 5) {
                errortext.setText("Le commentaire doit contenir au moins 5 caractères");
                isValid = false;
            }

            // Vérifier si le commentaire contient plus de 800 caractères
            else if (commentaireField.getText().length() > 800) {
                errortext.setText("Le commentaire ne peut pas dépasser 800 caractères");
                isValid = false;
            }

            // Vérification des mots interdits dans le commentaire
            try {
                String filteredText = BadWordsApi.filterBadWords(commentaireField.getText());
                if (!filteredText.equals(commentaireField.getText())) {
                    errortext.setText("Le commentaire contient des mots interdits et a été filtré.");
                    isValid = false;
                }
            } catch (IOException e) {
                // Gérer les erreurs liées à l'appel de l'API
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la vérification des mots interdits : " + e.getMessage());
                alert.show();
                isValid = false;
            }

            // Si le commentaire est valide, procédez à la modification
            if (isValid) {
                String newText = commentaireField.getText();
                CommentaireService cs = new CommentaireService();
                Commentaire commentaire = new Commentaire();
                commentaire.setId(id);
                commentaire.setText(newText);

                try {
                    cs.modifier(commentaire);
                    commentaireField.setEditable(false);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Le commentaire a été modifié avec succès.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erreur lors de la modification du commentaire :\n" + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Si le commentaire est invalide, restaurer le focus sur le TextField
                commentaireField.requestFocus();
            }
        }

        private void deleteComment(int id) {
                    CommentaireService cs = new CommentaireService();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    try {
                        if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer votre commentaire. Êtes-vous sûr ?",
                                "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                            cs.supprimer(id);
                            alert.setContentText("Votre commentaire a été supprimé avec succès.");
                            commentaires.removeIf(commentaire -> commentaire.getId() == id);
                            this.configureCarousel();
                            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), comments);
                            transition.setFromX(-comments.getPrefWidth());
                            transition.setToX(0);
                            transition.play();
                            comments.setVisible(true);
                            details.setVisible(false);
                        }
                    } catch (Exception e) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur lors de la suppression :\n" + e.getMessage());
                        alert.showAndWait();
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
        void back() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminListPub.fxml"));
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

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer votre Publication. Êtes-vous sûr ?"
                        , "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    p.supprimer(this.p.getId());
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
        void addC() {
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

        public void setPhoto(String imageName) {
            String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/pub/";
            String imagePath = destinationDirectory + imageName;
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imagePub.setImage(image);
            } else {

            }

        }

        public void show(ActionEvent actionEvent)   {
            configure();
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), comments);
            transition.setFromX(-comments.getPrefWidth()); // Déplace le composant depuis l'extérieur de l'écran
            transition.setToX(0); // Déplace le composant vers sa position d'origine
            transition.play();
            comments.setVisible(true);
            details.setVisible(false);
         }


        @FXML
        private void configure() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/comments.fxml"));
            CommentaireService cs = new CommentaireService();
            commentaires = new ArrayList<>();
            commentaireFields = new HashMap<>(); // Initialisation de la HashMap

            try {
                // Obtenez tous les commentaires par ID de publication
                List<Commentaire> allCommentaires = cs.getAll();
                for (Commentaire commentaire : allCommentaires) {
                    if (commentaire.getId_publication() == this.p.getId()) {
                        commentaires.add(commentaire);
                    }
                }

                try {
                    AnchorPane commentsView;
                    ListComm controller;

                    commentsView = loader.load();
                    controller = loader.getController();
                    VBox container = controller.getContainer();
                    container.getStyleClass().add("comment-container"); // Appliquer le style au conteneur VBox
                    commentsView.getStyleClass().add("comment-container");
                    commentsView.setPrefHeight(480);
                    container.setPrefHeight(480);

                    if (commentaires.isEmpty()) {
                        // S'il n'y a pas de commentaires, affichez un message indiquant l'absence de commentaires
                        Label noCommentLabel = new Label("Il n'y a pas de commentaires pour cette publication.");
                        noCommentLabel.getStyleClass().addAll("no-comment-label", "large-text", "grey-text", "centered-text");

                        container.getChildren().add(noCommentLabel);
                    } else {
                        // Pour chaque commentaire, créez des labels pour afficher les détails du commentaire
                        for (Commentaire commentaire : commentaires) {
                            commentsView.getStyleClass().add("comment-container");
                            HBox commentBox = new HBox(); // Utilisation de HBox pour chaque commentaire
                            commentBox.setSpacing(20); // Définir l'espacement entre les éléments
                            commentBox.getStyleClass().add("comment-card"); // Appliquer le style à la boîte de commentaire

                            VBox commentContent = new VBox(); // Créer un VBox pour contenir le texte du commentaire et le nom d'utilisateur
                            commentContent.setAlignment(Pos.TOP_LEFT); // Aligner les éléments en haut à gauche

                            Label userLabel = new Label("Utilisateur: " + "test mr");
                            userLabel.getStyleClass().addAll("user-label", "small-text", "grey-text"); // Appliquer le style au label de l'utilisateur

                            TextField commentaireField = new TextField(commentaire.getText());
                            commentaireField.setEditable(false);
                            commentaireField.getStyleClass().addAll("comment-textfield", "comment-textfield-centered");
                            commentaireField.getStyleClass().addAll("comment-text", "small-text", "grey-text"); // Appliquer le style au champ de texte
                            commentaireFields.put(commentaire.getId(), commentaireField); // Ajouter le champ de texte à la HashMap
                            commentContent.getChildren().addAll(commentaireField, userLabel); // Ajouter d'abord le texte du commentaire puis le nom d'utilisateur

                            HBox buttonBox = new HBox(); // Créer une boîte horizontale pour les boutons
                            buttonBox.setAlignment(Pos.CENTER_RIGHT); // Aligner les boutons à droite
                            buttonBox.setSpacing(10); // Définir l'espacement entre les boutons

                            Button deleteButton = createIconButton("/icons/delete.png", () -> {
                                deleteComment(commentaire.getId());
                            });

                            deleteButton.getStyleClass().addAll("btn-icone", "small-button");

                            buttonBox.getChildren().addAll( deleteButton); // Ajouter les boutons à la boîte horizontale
                            commentBox.getChildren().addAll(commentContent, buttonBox);
                            container.getChildren().add(commentBox);
                        }
                    }

                    // Ajouter les boutons de retour et d'ajout à la vue des commentaires
                   Button retourBtn = createIconButton("/icons/previous.png", () -> back());
                    retourBtn.getStyleClass().addAll("btn-icone", "small-button", "custom-add-button");
                    HBox btn = new HBox(); // Créer une boîte horizontale pour les boutons
                    btn.setSpacing(10);
                    btn.getChildren().addAll( retourBtn);
                    btn.setAlignment(Pos.CENTER);
                    container.getChildren().add(btn);

                    // Ajouter la vue des commentaires à votre interface utilisateur
                    comments.getChildren().add(commentsView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    public void addColaboration(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addCollaboration.fxml"));
        try {

            Parent root = loader.load();
            AddCollaboration addCollaboration = loader.getController();
            addCollaboration.initData(this.p);
            lieuPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getlistCollab(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listCollab.fxml"));
        try {
            Parent root = loader.load();
            ListCollab listCollab = loader.getController();
            listCollab.initData(this.p);
            lieuPub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
