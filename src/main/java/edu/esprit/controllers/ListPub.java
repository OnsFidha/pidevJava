package edu.esprit.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
import org.intellij.lang.annotations.Pattern;

public class ListPub implements Initializable {

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();
    int id=SessionManager.getId_user();
    @FXML
    private Circle logedUserimage;
//
//    @FXML
//    private ImageView logedUserimage;

    @FXML
    private Label logedUsernamee;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    private Stage stage;
    private Scene scene;
    @FXML
    private HBox home;

    @FXML
    private GridPane pubList;
    private List<Publication> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        logedUserimage.setFill(new ImagePattern(image));

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

        // Créer un EventHandler pour gérer les clics sur le HBox
        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getHome();
            }
        };

        // Ajouter l'EventHandler au HBox
        home.setOnMouseClicked(clickHandler);
    }

    private void getHome() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
            try {

                Parent root = loader.load();
                pubList.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
