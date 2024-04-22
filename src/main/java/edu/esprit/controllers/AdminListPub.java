package edu.esprit.controllers;

import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Pagination;
import javafx.geometry.Insets;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminListPub implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button BtnRecherche;

    @FXML
    private Circle circle;

    @FXML
    private GridPane pubList;
    @FXML
    private Pagination pagination;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));

        // Initialize the publication list
        initPublicationList();
    }

    private void initPublicationList() {
        PublicationService ps = new PublicationService();
        List<Publication> publications;

        try {
            // Get all publications from the service
            publications = ps.getAll();

            int itemsPerPage = 3;
            int pageCount = (int) Math.ceil((double) publications.size() / itemsPerPage);

            pagination.setPageCount(pageCount);
            pagination.setCurrentPageIndex(0); // Définir la page actuelle sur la première page

            pagination.setPageFactory(pageIndex -> {
                int fromIndex = pageIndex * itemsPerPage;
                int toIndex = Math.min(fromIndex + itemsPerPage, publications.size());

                // Créer une nouvelle GridPane pour chaque page
                GridPane pageGrid = new GridPane();
                pageGrid.setAlignment(Pos.CENTER);
                pageGrid.setHgap(10);
                pageGrid.setVgap(10);

                // Ajouter les publications de la page actuelle à la GridPane de la page
                for (int i = fromIndex; i < toIndex; i++) {
                    Publication publication = publications.get(i);

                    // Load the pub card FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardPub.fxml"));
                    AnchorPane card;
                    try {
                        card = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }

                    // Pass the data to the controller of the card
                    CardController controller = loader.getController();
                    controller.setData(publication);
                    card.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            redirectToDetail(publication);
                        }
                    });

                    // Créer les boutons
                    Button detailButton = new Button("", new ImageView(new Image("/icons/detailss.png", 35, 35, true, true)));
                    detailButton.getStyleClass().addAll("btn-icone", "small-button");
                    detailButton.setOnAction(event -> redirectToDetail(publication));

                    Button deleteButton = new Button("", new ImageView(new Image("/icons/delete.png", 35, 35, true, true)));
                    deleteButton.getStyleClass().addAll("btn-icone", "small-button");
                    deleteButton.setOnAction(event -> deletePublication(publication));

                    // Créer un HBox pour contenir les boutons
                    HBox buttonBox = new HBox(detailButton, deleteButton);
                    buttonBox.setAlignment(Pos.CENTER);
                    buttonBox.setSpacing(10);

                    // Récupérer la VBox existante de la carte
                    VBox cardContent = (VBox) card.getChildren().get(0);

                    // Ajouter la ligne supplémentaire pour les boutons
                    cardContent.getChildren().add(buttonBox);

                    // Ajouter la carte à la GridPane de la page
                    pageGrid.add(card, (i - fromIndex) % 3, (i - fromIndex) / 3);
                }

                return pageGrid;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePublication(Publication publication) {
        PublicationService publicationService = new PublicationService();
        try {
            if (JOptionPane.showConfirmDialog(null, "Attention, vous allez supprimer la Publication. Êtes-vous sûr ?", "Supprimer ", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                publicationService.supprimer(publication.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("la Publication a été supprimée avec succès.");
                alert.showAndWait();
                // Rafraîchir la liste après la suppression
                initPublicationList();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression :\n" + e.getMessage());
        }
    }

    private void redirectToDetail(Publication publication) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPubAd.fxml"));
        try {
            Parent root = loader.load();
            AfficherPub controller = loader.getController();
            controller.initData(publication);
            controller.setTypePub(publication.getType());
            controller.setTextPub(publication.getText());
            controller.setLieuPub(publication.getLieu());
            controller.setDateCreationPub(new Date());
            controller.setPhoto(publication.getPhoto());
            controller.setDateModificationPub(new Date());
            pubList.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
