package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import java.util.ResourceBundle;
import edu.esprit.entities.Publication;
import edu.esprit.entities.Reclamation;
import edu.esprit.service.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class ListPub implements Initializable {


    @FXML
    private Circle circle;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    private Stage stage;
    private Scene scene;

    @FXML
    private GridPane pubList;
    private List<Publication> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));

        PublicationService ps=new PublicationService();
        List<Publication> publications;
        try {
            // Get all reclamations from the service
            publications = ps.getAll();

            int column = 0;
            int row = 0;
            int maxColumnsPerRow = 3;

            // Iterate over the list of publications
            for (Publication publication : publications) {
                // Load the pub card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardPub.fxml"));
                AnchorPane card = loader.load();

                // Pass the data to the controller of the card
                CardController controller = loader.getController();
                controller.setData(publication);
                card.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) {
                        redirectToEventPage(publication);
                    }     });
                // Add the card to the GridPane container
                pubList.add(card, column, row);

                // Increment row and reset column if needed
                column++;
                // Check if we need to start a new row
                if (column == maxColumnsPerRow) {
                    // Reset column and increment row
                    column = 0;
                    row++;
                }
                GridPane.setMargin(card, new Insets(7));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private void redirectToEventPage(Publication publication) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherPub.fxml"));
            try {
                Parent root=loader.load();
                AfficherPub pub= loader.getController();
                pub.initData(publication);
                pub.setTypePub(publication.getType());
                pub.setTextPub(publication.getText());
                pub.setLieuPub(publication.getLieu());
                pub.setDateCreationPub(new Date());
                pub.setPhoto(publication.getPhoto());
                pub.setDateModificationPub(new Date());
                pubList.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void add(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterPub.fxml"));
            pubList.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
